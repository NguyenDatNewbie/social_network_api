package fit.api.social_network.service.impl;

import fit.api.social_network.constant.SocialConstant;
import fit.api.social_network.model.entity.Message;
import fit.api.social_network.model.entity.Room;
import fit.api.social_network.model.entity.RoomMember;
import fit.api.social_network.model.entity.User;
import fit.api.social_network.model.request.chat.ChatMessageRequest;
import fit.api.social_network.repository.MessageRepository;
import fit.api.social_network.repository.RoomMemberRepository;
import fit.api.social_network.repository.RoomRepository;
import fit.api.social_network.repository.UserRepository;
import fit.api.social_network.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomMemberRepository roomMemberRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    @Override
    public Room newRoom(List<Long> members, String groupName, String sessionId){
        Room room = new Room();
        room.setSessionId(sessionId);
        if(members.size()>2){
            room.setType(SocialConstant.ROOM_TYPE_GROUP);
            room.setName(groupName);
        }
        else room.setType(SocialConstant.ROOM_TYPE_PERSONAL);
        Room newRoom = roomRepository.save(room);
        addUserToRoom(room,members);
        return newRoom;
    }
    public void addUserToRoom(Room room, List<Long> members){
        for(Long member: members){
            User user = userRepository.findById(member).orElse(null);
            if(user==null)
                continue;
            RoomMember roomMember = new RoomMember();
            roomMember.setRoom(room);
            roomMember.setUser(user);
            roomMemberRepository.save(roomMember);
        }
    }
    @Override
    public void sendMessage(WebSocketSession session,
                            ChatMessageRequest chatMessageRequest, Room room, User sender) throws IOException {
        Message message = new Message();
        message.setMessage(chatMessageRequest.getMessage());
        message.setRoom(room);
        message.setSender(sender);
        messageRepository.save(message);
        session.sendMessage(new TextMessage(chatMessageRequest.getMessage()));
    }
    @Override
    public WebSocketSession transferSession(String id, WebSocketSession session){
        return new WebSocketSession() {
            @Override
            public String getId() {
                return id;
            }

            @Override
            public URI getUri() {
                return session.getUri();
            }

            @Override
            public HttpHeaders getHandshakeHeaders() {
                return session.getHandshakeHeaders();
            }

            @Override
            public Map<String, Object> getAttributes() {
                return session.getAttributes();
            }

            @Override
            public Principal getPrincipal() {
                return session.getPrincipal();
            }

            @Override
            public InetSocketAddress getLocalAddress() {
                return session.getLocalAddress();
            }

            @Override
            public InetSocketAddress getRemoteAddress() {
                return session.getRemoteAddress();
            }

            @Override
            public String getAcceptedProtocol() {
                return session.getAcceptedProtocol();
            }

            @Override
            public void setTextMessageSizeLimit(int messageSizeLimit) {

            }

            @Override
            public int getTextMessageSizeLimit() {
                return session.getTextMessageSizeLimit();
            }

            @Override
            public void setBinaryMessageSizeLimit(int messageSizeLimit) {

            }

            @Override
            public int getBinaryMessageSizeLimit() {
                return session.getBinaryMessageSizeLimit();
            }

            @Override
            public List<WebSocketExtension> getExtensions() {
                return session.getExtensions();
            }

            @Override
            public void sendMessage(WebSocketMessage<?> message) throws IOException {
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public void close() throws IOException {

            }

            @Override
            public void close(CloseStatus status) throws IOException {

            }
        };
    }
}
