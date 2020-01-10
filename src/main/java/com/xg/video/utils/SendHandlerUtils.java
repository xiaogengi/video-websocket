package com.xg.video.utils;

import com.alibaba.fastjson.JSONObject;
import com.xg.video.controller.MessageController;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @program: vodeowebsocket
 * @description: 响应页面
 * @author: gzk
 * @create: 2020-01-09 16:08
 **/

public class SendHandlerUtils {

    public static void sendHandler(String key,String msg){
        MessageController.SESSION_LIST.stream().forEach(item -> {
            try {
                JSONObject json = new JSONObject();
                json.put("key",key);
                json.put("content", msg);
                item.getBasicRemote().sendText(json.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void sendHandler(String key, String msg, Session session){
        try {
            JSONObject json = new JSONObject();
            json.put("key",key);
            json.put("content", msg);
            session.getBasicRemote().sendText(json.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
