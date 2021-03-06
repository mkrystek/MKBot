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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.sqlite.JDBC;

@Component
public class MessageProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProvider.class);

    @Value("${chat_name}")
    private String chatName;

    @Value("${skype_username}")
    private String skypeUsername;

    private Connection databaseConnection;
    private List<String> chatParticipants;
    private Integer conversationId;
    private long lastMessageId;

    public void initialize() throws Exception {
        try {
            String skypeDbPath = String.format("%s\\Skype\\%s\\main.db", System.getenv("APPDATA"), skypeUsername);
            databaseConnection = JDBC.createConnection(JDBC.PREFIX + skypeDbPath, new Properties());
            extractConversationId();
            extractParticipants();
        } catch (SQLException e) {
            LOGGER.error("Problem creating MessageProvider: ", e);
            throw new Exception(e);
        }
        updateLastMessageId();
    }

    private void extractConversationId() throws SQLException {
        PreparedStatement ps = databaseConnection.prepareStatement("SELECT id FROM Conversations WHERE displayname = ?");
        ps.setString(1, chatName);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            conversationId = rs.getInt("id");
        }
        ps.close();
        rs.close();
    }

    private void extractParticipants() throws SQLException {
        chatParticipants = newArrayList();
        PreparedStatement ps = databaseConnection.prepareStatement("SELECT identity FROM Participants WHERE convo_id = ? AND rank <> 7");
        ps.setInt(1, conversationId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            chatParticipants.add(rs.getString("identity"));
        }
        ps.close();
        rs.close();
    }

    private void updateLastMessageId() {
        try {
            PreparedStatement ps = databaseConnection.prepareStatement("SELECT max(id) AS 'last_id' FROM Messages WHERE convo_id = ?");
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
                .prepareStatement("SELECT author, body_xml FROM Messages WHERE convo_id = ? AND id > ? AND author <> ?");
            ps.setInt(1, conversationId);
            ps.setLong(2, lastMessageId);
            ps.setString(3, skypeUsername);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newMessages.add(String.format("%s - %s", rs.getString("author"), rs.getString("body_xml")));
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
