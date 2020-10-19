package com.malaxiaoyugan.test.wxPay.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动加载微信相关参数配置
 */
@Component
public class InitConfigUtil implements ApplicationRunner {
	
	@Override
    public void run(ApplicationArguments var){
		//初始化 微信相关参数,涉及机密,此文件不会提交,请自行配置相关参数并加载
		WXConfigUtil.init("wx.yml");//微信

    }


	public static void main(String[] args) {
		WXConfigUtil.init("wx.yml");//微信
		System.out.println(WXConfigUtil.API_KEY);
	}
}