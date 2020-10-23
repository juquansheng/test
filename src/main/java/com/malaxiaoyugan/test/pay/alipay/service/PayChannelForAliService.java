package com.malaxiaoyugan.test.pay.alipay.service;

import java.util.Map;


public interface PayChannelForAliService {

    Map doAliPayWapReq(String jsonParam);

    Map doAliPayPcReq(String jsonParam);

    Map doAliPayMobileReq(String jsonParam);

    Map doAliPayQrReq(String jsonParam);

}
