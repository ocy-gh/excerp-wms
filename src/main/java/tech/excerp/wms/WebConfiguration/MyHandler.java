package tech.excerp.wms.WebConfiguration;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Service
public class MyHandler extends TextWebSocketHandler {
    private static final Map<String, WebSocketSession> users;
    public static final String CLIENT_ID = "username";

    static {
        users = new HashMap<String, WebSocketSession>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username= getClientId(session);
        if (username != null) {
            users.put(username, session);
            session.sendMessage(new TextMessage("成功建立socket连接"));
            System.out.println("建立连接:" + username);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            System.out.println(message.getPayload());
            WebSocketMessage msg = new TextMessage("server:"+message);
            session.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean sendMessageToUser(String clientId, TextMessage message) {
        if (users.get(clientId) == null) return false;

        WebSocketSession session = users.get(clientId);
        System.out.println("sendMessage:" + session);
        if (!session.isOpen()) return false;

        try {
            session.sendMessage(message);
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    public boolean sendMessageToAllUsers(TextMessage message) {
        Set<String> clientIds = users.keySet();
        WebSocketSession session = null;
        for (String clientId : clientIds) {
            try {
                session = users.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return  false;
            }
        }
        return  true;
    }


    private String getClientId(WebSocketSession session) {
        try {
            String clientId = (String) session.getAttributes().get(CLIENT_ID);
            return clientId;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("连接出错");
        users.remove(getClientId(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("连接已关闭：" + status);
        users.remove(getClientId(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}