package com.moment.the.util.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @since 1.3.1
 * @author 전지환
 */
@RestController
@RequestMapping("/v1/request")
@RequiredArgsConstructor
public class HttpRequestUtils {
    @GetMapping("/getIp")
    public String getReqIp(){
        return getClientIpAddressIfServletRequestExist();
    }

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For", // 클라이언트의 원 IP 주소를 식별하는 사실상의 표준 헤더
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    /**
     * 서버 상황에 맞는 (proxy 유무)를 확인하여 필요한 header의 값을 가져옵니다.
     * @return ip
     * @author 전지환
     */
    public static String getClientIpAddressIfServletRequestExist() {
        if (Objects.isNull(RequestContextHolder.getRequestAttributes())) return "0.0.0.0";

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        for (String header: IP_HEADER_CANDIDATES) {
            String ipFromHeader = request.getHeader(header);
            if (Objects.nonNull(ipFromHeader) && ipFromHeader.length() != 0 && !"unknown".equalsIgnoreCase(ipFromHeader)) {
                String ip = ipFromHeader.split(",")[0];
                return ip;
            }
        }
        // 보안 문제로 방화벽등을 거쳐 요청이나 응답이 가공되어 나가고 들어오기에 가장 마지막에 수행한다.
        return request.getRemoteAddr();
    }
}
