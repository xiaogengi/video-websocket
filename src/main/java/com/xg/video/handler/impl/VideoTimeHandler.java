package com.xg.video.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.xg.video.config.StrategyConfig;
import com.xg.video.controller.MessageController;
import com.xg.video.handler.Handler;
import com.xg.video.utils.SendHandlerUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.websocket.SendHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: vodeowebsocket
 * @description: 播放时间同步
 * @author: gzk
 * @create: 2020-01-09 15:09
 **/
@Component
public class VideoTimeHandler implements Handler, InitializingBean {


    private static Map<String,String> CHANNEL_USER_VIDEO_TIME = new ConcurrentHashMap<>();

    private static final BigDecimal DEFAULT_BIG_DECIMAL = new BigDecimal("0.51");

    /**
     * 1 查询是否有别人的播放进度
     * 2 如果有  切换别人的  如果没有 用自己的
     * @param content
     * @param session
     */
    @Override
    public void handler(JSONObject content, Session session) {
        BigDecimal time = content.getBigDecimal("time");
        String userId = content.getString("userId");
        CHANNEL_USER_VIDEO_TIME.put(userId,time.add(DEFAULT_BIG_DECIMAL).toString());
        //如果是点击同步播放  需要执行
        if(content.getInteger("type").equals(0)){
            //时间未更新
            for (String s : CHANNEL_USER_VIDEO_TIME.keySet()) {
                if(!userId.equals(s)){
                    userId = s;
                    break;
                }
            }
            String s = CHANNEL_USER_VIDEO_TIME.get(userId);
            SendHandlerUtils.sendHandler("videoTime", s == null || s == "" ? time.toString() : s ,session);
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        StrategyConfig.HANDLER_STRATEGY.put("videoTime",this);
    }

}
