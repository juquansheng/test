package com.malaxiaoyugan.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.malaxiaoyugan.test.aspect.TryAgainException;
import com.malaxiaoyugan.test.common.ApiResultEnum;
import com.malaxiaoyugan.test.rabbitMQ.producer.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: RabbitMQController
 * date: 2020/9/24 14:31
 * author: juquansheng
 * version: 1.0 <br>
 */
@RestController
@RequestMapping(value = "rabbit")
public class RabbitMQController {
    @Autowired
    private RabbitMQService rabbitMQService;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String send(String one,String two,String three){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1",one);
        jsonObject.put("2",two);
        jsonObject.put("3",three);

        rabbitMQService.send(jsonObject);
        if(false){
            //如果更新失败就抛出去，重试
            throw new TryAgainException(ApiResultEnum.ERROR_TRY_AGAIN);
        }
        return "success";
    }
}
