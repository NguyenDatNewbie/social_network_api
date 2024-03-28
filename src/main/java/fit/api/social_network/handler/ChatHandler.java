package fit.api.social_network.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import fit.api.social_network.controller.AbasicMethod;
import fit.api.social_network.model.entity.Room;
import fit.api.social_network.model.entity.RoomMember;
import fit.api.social_network.model.entity.User;
import fit.api.social_network.model.request.chat.ChatMessageRequest;
import fit.api.social_network.repository.RoomRepository;
import fit.api.social_network.repository.UserRepository;
import fit.api.social_network.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ChatHandler extends TextWebSocketHandler {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AbasicMethod abasicMethod;
    @Autowired
    ChatService chatService;
    @Autowired
    RoomRepository roomRepository;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Xử lý sau khi connect
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessageRequest chatMessageRequest = objectMapper.readValue(textMessage.getPayload(),ChatMessageRequest.class);
        User user = userRepository.findById(Long.parseLong("1")).orElse(null);
        if(user!=null){
            Room currentRoom = roomRepository.findById(chatMessageRequest.getRoomId()).orElse(new Room());
            if(chatMessageRequest.getRoomId()==0 || chatMessageRequest.getRoomId()==null){
                chatMessageRequest.getReceivers().add(0,user.getId());
                currentRoom = chatService.newRoom(chatMessageRequest.getReceivers(), chatMessageRequest.getGroupName(), session.getId());
                sessions.put(session.getId(),session);
            }
            else if(sessions.get(currentRoom.getSessionId())==null)
            {
                currentRoom.setSessionId(session.getId());
                sessions.put(session.getId(),session);
                roomRepository.save(currentRoom);
            }
            WebSocketSession webSocketSession = sessions.get(currentRoom.getSessionId());
            log.info("message: "+chatMessageRequest.getMessage()+" "+chatMessageRequest.getRoomId().toString());

            chatService.sendMessage(webSocketSession,chatMessageRequest,currentRoom,user);
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
    }

}
