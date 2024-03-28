package fit.api.social_network.service;

import fit.api.social_network.model.entity.Room;
import fit.api.social_network.model.entity.User;
import fit.api.social_network.model.request.chat.ChatMessageRequest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

public interface ChatService {
    Room newRoom(List<Long> members, String groupName, String sessionId);

    void sendMessage(WebSocketSession session,
                     ChatMessageRequest chatMessageRequest, Room room, User sender) throws IOException;

    WebSocketSession transferSession(String id, WebSocketSession session);
}
