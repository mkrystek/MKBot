package pl.mkrystek.mkbot.message;

import static com.google.common.collect.Lists.newArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.JDBC;
import pl.mkrystek.mkbot.BotProperties;

public class MessageProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProvider.class);

    private Connection databaseConnection;
    private List<String> chatParticipants;
    private Integer conversationId;
    private long lastMessageId;

    public MessageProvider() throws Exception {
        try {
            databaseConnection = JDBC.createConnection(JDBC.PREFIX + BotProperties.getSkypeDbPath(), new Properties());
            extractProperties();
        } catch (SQLException e) {
            LOGGER.error("Problem creating MessageProvider: ", e);
            throw e;
        }
        updateLastMessageId();
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

    private void updateLastMessageId() {
        try {
            PreparedStatement ps = databaseConnection.prepareStatement("SELECT max(id) AS 'last_id' FROM messages WHERE convo_id = ?");
            ps.setLong(1, conversationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lastMessageId = rs.getLong("last_id");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error("Error during updating last message id: ", e);
        }
    }

    public List<String> getNewMessages() {
        List<String> newMessages = newArrayList();
        try {
            PreparedStatement ps = databaseConnection
                .prepareStatement("SELECT from_dispname, body_xml FROM Messages WHERE convo_id = ? AND id > ? AND author <> ?");
            ps.setInt(1, conversationId);
            ps.setLong(2, lastMessageId);
            ps.setString(3, BotProperties.getSkypeUsername());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newMessages.add(String.format("%s - %s", rs.getString("from_dispname"), rs.getString("body_xml")));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            LOGGER.error("Error during new message retrieval: ", e);
        } finally {
            updateLastMessageId();
        }

        return newMessages;
    }

    public void close() {
        try {
            if (databaseConnection != null) {
                databaseConnection.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Error while closing: ", e);
        }
    }

    public List<String> getChatParticipants() {
        return chatParticipants;
    }
}
