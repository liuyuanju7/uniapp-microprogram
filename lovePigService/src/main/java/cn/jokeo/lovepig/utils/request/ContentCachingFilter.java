package cn.jokeo.lovepig.utils.request;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 将request替换为可以重复获取request流内容的requst
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "contentCachingFilter", urlPatterns = "/*")
public class ContentCachingFilter extends OncePerRequestFilter {
    private final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String contentType = httpServletRequest.getContentType();
        if (StrUtil.isNotEmpty(contentType) && contentType.contains(ContentType.MULTIPART.getValue())) {
            httpServletRequest = multipartResolver.resolveMultipart(httpServletRequest);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            CachedBodyHttpServletRequest request = new CachedBodyHttpServletRequest(httpServletRequest);
            filterChain.doFilter(request, httpServletResponse);
        }

    }
}
