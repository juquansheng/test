package com.malaxiaoyugan.test.aspect;


import com.malaxiaoyugan.test.common.ApiException;
import com.malaxiaoyugan.test.common.ApiResultEnum;

/**
 * 更新重试异常
 */
public class TryAgainException extends ApiException {

    public TryAgainException(ApiResultEnum apiResultEnum) {
        super(apiResultEnum);
    }

}
