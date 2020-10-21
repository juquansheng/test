package com.malaxiaoyugan.test.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.malaxiaoyugan.test.dao.model.PayChannel;

import com.malaxiaoyugan.test.pay.service.PayChannelService;
import com.malaxiaoyugan.test.utils.MyBase64;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: 支付渠道接口
 * @author dingzhiwei jmdhappy@126.com
 * @date 2017-07-05
 * @version V1.0
 * @Copyright: www.xxpay.org
 */
@RestController
@Slf4j
public class PayChannelServiceController {


    @Autowired
    private PayChannelService payChannelService;

    @RequestMapping(value = "/pay_channel/select")
    public String selectPayChannel(@RequestParam String jsonParam) {
        // TODO 参数校验
        log.info("selectPayChannel << {}", jsonParam);
        JSONObject retObj = new JSONObject();
        retObj.put("code", "0000");
        if(StringUtils.isBlank(jsonParam)) {
            retObj.put("code", "0001"); // 参数错误
            retObj.put("msg", "缺少参数");
            return retObj.toJSONString();
        }
        JSONObject paramObj = JSON.parseObject(new String(MyBase64.decode(jsonParam)));
        String channelId = paramObj.getString("channelId");
        String mchId = paramObj.getString("mchId");
        PayChannel payChannel = payChannelService.selectPayChannel(channelId, mchId);
        if(payChannel == null) {
            retObj.put("code", "0002");
            retObj.put("msg", "数据对象不存在");
            return retObj.toJSONString();
        }
        retObj.put("result", JSON.toJSON(payChannel));
        log.info("selectPayChannel >> {}", retObj);
        return retObj.toJSONString();
    }


}
