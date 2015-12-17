package pl.mkrystek.mkbot.pl.mkrystek.mkbot.database;

import static com.google.common.collect.Lists.newArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.sqlite.JDBC;
import pl.mkrystek.mkbot.pl.mkrystek.mkbot.BotProperties;

public class SkypeDatabase {

    private Connection databaseConnection;
    private List<String> chatParticipants;
    private Integer conversationId;
    private int lastMessageId;

    public SkypeDatabase() {
        try {
            databaseConnection = JDBC.createConnection(JDBC.PREFIX + BotProperties.getSkypeDbPath(), new Properties());
            extractProperties();
            updateLastMessageId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void extractProperties() throws SQLException {
        PreparedStatement ps = databaseConnection.prepareStatement("SELECT participants, conv_dbid FROM Chats WHERE topic = ?");
        ps.setString(1, BotProperties.getChatName());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String participants = rs.getString("participants");
            chatParticipants = newArrayList(participants.split(" "));
            conversationId = rs.getInt("conv_dbid");
        } else {
            //chat name is not a group conversation
            //TODO handle direct conversations later
        }
        ps.close();
        rs.close();
    }

    private void updateLastMessageId() throws SQLException {
        PreparedStatement ps = databaseConnection.prepareStatement("SELECT inbox_message_id FROM Conversations WHERE displayname = ?");
        ps.setString(1, BotProperties.getChatName());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            lastMessageId = rs.getInt("inbox_message_id");
        }
        ps.close();
        rs.close();
    }

    public List<String> getNewMessages() {
        List<String> newMessages = newArrayList();
        try {
            PreparedStatement ps = databaseConnection
                .prepareStatement("SELECT from_dispname, body_xml FROM Messages WHERE convo_id = ? AND id > ?");
            ps.setInt(1, conversationId);
            ps.setInt(2, lastMessageId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newMessages.add(String.format("%s - %s", rs.getString("from_dispname"), rs.getString("body_xml")));
            }
            ps.close();
            rs.close();
            updateLastMessageId();
        } catch (SQLException e) {
            //do nothing, happens
        }

        return newMessages;
    }

    public void close() {
        try {
            if (databaseConnection != null) {
                databaseConnection.close();
            }
        } catch (SQLException e) {

        }
    }

    public List<String> getChatParticipants() {
        return chatParticipants;
    }
}
