package com.xg.video.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.xg.video.config.StrategyConfig;
import com.xg.video.handler.Handler;
import com.xg.video.utils.SendHandlerUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: vodeowebsocket
 * @description: 播放状态处理
 * @author: gzk
 * @create: 2020-01-09 14:14
 **/
@Component
public class VideoStatusHandler implements Handler, InitializingBean {


    public static  Map<String , String> CHANNEL_VIDEO_STATUS = new ConcurrentHashMap<>();



    @Override
    public void handler(JSONObject content,Session session) {
        String status = content.getString("status");
        CHANNEL_VIDEO_STATUS.put("XBB",status);
        SendHandlerUtils.sendHandler("videoStatus",status);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        StrategyConfig.HANDLER_STRATEGY.put("video",this);
    }

}
