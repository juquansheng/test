package com.malaxiaoyugan.test.pay.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;


public interface MchInfoService {

    Map selectMchInfo(String jsonParam);

    JSONObject getByMchId(String mchId);

}
