package com.malaxiaoyugan.test.aspect;



import com.malaxiaoyugan.test.common.ApiException;
import com.malaxiaoyugan.test.common.ApiResultEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;


/**
 * 更新失败，尝试重试切片
 */
@Aspect
@Configuration
public class TryAgainAspect {

	private int order = 1;

	public int getOrder() {
		return this.order;
	}

	@Pointcut("@annotation(TryAgain)")
	public void retryOnOptFailure() {
		// pointcut mark
	}


	@Around("retryOnOptFailure()")
	@Transactional(rollbackFor = Exception.class)
	public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {

		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		//获取全部注解
		//Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		//获取目标注解
		TryAgain tryAgain = method.getAnnotation(TryAgain.class);

		int maxRetries = tryAgain.value();

		int numAttempts = 0;
		do {
			numAttempts++;
			try {
				//再次执行业务代码
				return pjp.proceed();
			} catch (TryAgainException ex) {
				if (numAttempts > maxRetries) {
					//log failure information, and throw exception
//					如果大于 默认的重试机制 次数，我们这回就真正的抛出去了
					throw new ApiException(ApiResultEnum.ERROR_TRY_AGAIN_FAILED);
				}else{
					//如果 没达到最大的重试次数，将再次执行
					System.out.println("=====正在重试====="+numAttempts+"次");
				}
			}
		} while (true);
	}
}
