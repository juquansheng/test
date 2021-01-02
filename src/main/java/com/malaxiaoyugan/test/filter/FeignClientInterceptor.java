/*
package com.malaxiaoyugan.test.filter;

import com.malaxiaoyugan.test.common.ProjectContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Objects;

import static com.malaxiaoyugan.test.common.ProjectContext.CONTEXT_KEY;

*/
/**
 * description: FeignClientInterceptor
 * date: 2020/12/15 15:29
 * author: juquansheng
 * version: 1.0 <br>
 *//*

public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            ProjectContext projectContext = ProjectContext.getContext();
            if (Objects.nonNull(projectContext)) {
                requestTemplate.header(CONTEXT_KEY, projectContext.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
