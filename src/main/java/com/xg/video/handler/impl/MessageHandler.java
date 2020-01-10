package com.xg.video.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.xg.video.config.StrategyConfig;
import com.xg.video.controller.MessageController;
import com.xg.video.handler.Handler;
import com.xg.video.utils.SendHandlerUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: vodeowebsocket
 * @description: 消息处理
 * @author: gzk
 * @create: 2020-01-09 11:49
 **/
@Component
@EnableScheduling
public class MessageHandler implements Handler , InitializingBean {

    private static List<String> MESSAGE_LIST = new ArrayList<>();



    @Override
    public void handler(JSONObject content, Session session) {
        String msg = content.getString("msg");
        String userId = content.getString("userId");
        String s =  "<font color = 'red'>"+userId + "</font> : " + msg;
        MESSAGE_LIST.add(s);
        SendHandlerUtils.sendHandler("message",s);
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void InputMessage() throws IOException{
        //写入本地
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("/Users/didi/Desktop/message.txt"));

        MESSAGE_LIST.stream().forEach(item -> {
            try {
                bufferedOutputStream.write((item + "\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bufferedOutputStream.flush();
        bufferedOutputStream.close();


    }






    @Override
    public void afterPropertiesSet() throws Exception {
        StrategyConfig.HANDLER_STRATEGY.put("message",this);
    }

}
