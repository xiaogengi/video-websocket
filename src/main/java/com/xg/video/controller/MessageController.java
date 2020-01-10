package com.xg.video.controller;

import com.alibaba.fastjson.JSONObject;
import com.xg.video.config.StrategyConfig;
import com.xg.video.entity.RequestMessageEntity;
import com.xg.video.handler.impl.LoginHandler;
import com.xg.video.handler.impl.VideoStatusHandler;
import com.xg.video.handler.impl.VideoTimeHandler;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: vodeowebsocket
 * @description: 消息层
 * @author: gzk
 * @create: 2020-01-09 10:45
 **/
@Component
@ServerEndpoint("/MessageWebsocket")
public class MessageController {

    public static List<Session> SESSION_LIST  = new ArrayList<>();


    // 收到消息调用的方法
    @OnMessage
    public void onMessage(String message,Session session){
        RequestMessageEntity param = JSONObject.parseObject(message, RequestMessageEntity.class);
        StrategyConfig.getBean(param.getKey()).handler(param.getContent(),session);
    }



    // 建立连接调用的方法
    @OnOpen
    public void onOpen(Session session) {
        SESSION_LIST.add(session);
    }

    // 关闭连接调用的方法
    @OnClose
    public void onClose(Session session) {
        SESSION_LIST.remove(session);
        VideoStatusHandler.CHANNEL_VIDEO_STATUS = new ConcurrentHashMap<>();
        if(SESSION_LIST.size() == 0){
            LoginHandler.USER_LIST = new ArrayList<>();
            VideoTimeHandler.CHANNEL_USER_VIDEO_TIME = new ConcurrentHashMap<>();
        }
    }

    // 传输消息错误调用的方法
    @OnError
    public void OnError(Throwable error) {
        System.out.println("Connection error");
    }

}
