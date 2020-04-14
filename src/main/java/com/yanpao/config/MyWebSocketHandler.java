package com.yanpao.config;

import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Creating a WebSocket server
 */
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{

        System.out.println("收到websocket消息:"+ message.toString());
        super.handleTextMessage(session, message);
        String msg = message.getPayload();
        System.out.println("收到websocket消息的消息体:{}"+ msg);
        if (!StringUtils.isEmpty(msg)) {
//            MessageDTO messageDTO = JSONObject.parseObject(msg, MessageDTO.class);
//            sendMessageToUser(messageDTO.getTargetUserName(), message);
        }
    }

}
