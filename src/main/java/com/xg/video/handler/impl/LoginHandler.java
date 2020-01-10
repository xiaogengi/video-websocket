package com.xg.video.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.xg.video.config.StrategyConfig;
import com.xg.video.handler.Handler;
import com.xg.video.utils.SendHandlerUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: vodeowebsocket
 * @description: 登陆处理
 * @author: gzk
 * @create: 2020-01-09 14:15
 **/
@Component
public class LoginHandler implements Handler,InitializingBean {

    public static List<String> USER_LIST = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        StrategyConfig.HANDLER_STRATEGY.put("login",this);
    }

    @Override
    public void handler(JSONObject content, Session session) {
        String userId = content.getString("userId");
        USER_LIST.add(userId);
        SendHandlerUtils.sendHandler("login", userId + " ：登陆成功 ❤️",session);
    }

}
