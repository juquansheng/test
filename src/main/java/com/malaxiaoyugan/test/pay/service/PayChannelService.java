package com.malaxiaoyugan.test.pay.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;


public interface PayChannelService {

    Map selectPayChannel(String jsonParam);

    JSONObject getByMchIdAndChannelId(String mchId, String channelId);
}
