package com.malaxiaoyugan.test.aop;


import com.malaxiaoyugan.test.annotation.AnnotationParse;
import com.malaxiaoyugan.test.annotation.RequiresPermissions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Aspect
@Component
@SuppressWarnings("all")
public class AuthAspect {

    public static final String ADMIN = "ADMIN";
    public static final long EXPIRE_TIME = 60 * 60 * 3;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    /*@Pointcut("execution(public * com.mxd.rest.*.*(..)) "
        + "&& !execution(* com.mxd.rest.*.*login(..)) "
        + "&& !execution(* com.mxd.rest.*.*logout(..)) "
            + "&& !execution(* com.mxd.rest.*.*info(..)) "
        + "&& !execution(* com.mxd.rest.*.updateFromPh(..))")
    public void privilege() {
    }*/
    //先全开放，方便测试
    @Pointcut("execution(public * com.malaxiaoyugan.test.*.*(..)) "
            + "&& !execution(* com.malaxiaoyugan.test.*.*(..)) ")
    public void privilege() {
    }


    @ResponseBody
    @Before("privilege()")
    public void methodBefore(JoinPoint joinPoint) throws Throwable {
        //本地请求直接放行
        String remoteAddr = request.getRemoteAddr();

//    	 if ("0:0:0:0:0:0:0:1".equals(remoteAddr) || "127.0.0.1".equals(remoteAddr)) {
//    		System.out.println("本地跳过");
//    	 }else {
        //获取访问目标方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        //看是否加了权限校验接口
        /*UserInfoResult userInfoInRequest = userInfoUtil.getUserInfoInRequest();
        if (userInfoInRequest == null){//未登录
            throw new CommonException(401,"没有访问权限");
        }*/
        RequiresPermissions annotation = targetMethod.getDeclaredAnnotation(RequiresPermissions.class);
        if (annotation != null) {
            //得到方法的访问权限
            final String methodAccess = AnnotationParse.privilegeParse(targetMethod);
            // 用户权限
            //List<String> auths = (List<String>) redisService.get(token);
            /*if (auths != null && auths.size() > 0) {
                if (!auths.contains(methodAccess)) {
                    throw new PHException("没有访问权限");
                }
            } else {
                throw new PHException("没有访问权限");
            }*/
        }
        //刷新过期时间
        //refreshRedis(userInfoInRequest.getToken());
    }

    private void refreshRedis(String token) {
        if (token != null) {

        }
    }

}
