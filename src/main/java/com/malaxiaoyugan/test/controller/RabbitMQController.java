package com.malaxiaoyugan.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.malaxiaoyugan.test.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public String send(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1","one");
        jsonObject.put("2","two");
        jsonObject.put("3","three");

        rabbitMQService.send(jsonObject);
        return "success";
    }
}
