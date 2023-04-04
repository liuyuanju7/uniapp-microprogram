package cn.jokeo.lovepig.utils.request;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author joke
 * @date 2021/8/17 17:27
 */
public class RequestUtil {

    public static String getParameter(HttpServletRequest servletRequest, String name) throws IOException {
        String param = servletRequest.getParameter(name);
        if (param != null) {
            return param;
        }
        Map<String, String[]> parameterMap = servletRequest.getParameterMap();
        if (!parameterMap.isEmpty()) {
            param = parameterMap.get(name)[0];
            if (param != null) {
                return param;
            }
        }

        if (servletRequest instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) servletRequest;
            param = multiReq.getParameter(name);
            return param;
        } else {
            ServletInputStream inputStream = servletRequest.getInputStream();
            byte[] bytes = StreamUtils.copyToByteArray(inputStream);
            String requestBody = new String(bytes, StandardCharsets.UTF_8);
            JSONObject jsonObject = JSONUtil.parseObj(requestBody);
            Object param2 = jsonObject.get(name);
            if (param2 != null) {
                return param2.toString();
            }
        }
        return null;
    }
}
