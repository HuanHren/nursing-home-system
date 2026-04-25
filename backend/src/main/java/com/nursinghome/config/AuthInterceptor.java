package com.nursinghome.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nursinghome.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public AuthInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (uri.startsWith("/api/auth/")) {
            return true;
        }

        String role = request.getHeader("X-User-Role");
        String method = request.getMethod();
        boolean writeMethod = "POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method);

        if ("FAMILY".equals(role)) {
            if (writeMethod || uri.startsWith("/api/staff-info") || uri.startsWith("/api/sys-users")
                    || uri.startsWith("/api/room-beds")) {
                writeForbidden(response);
                return false;
            }
        }

        if ("STAFF".equals(role)) {
            if (uri.startsWith("/api/staff-info") || uri.startsWith("/api/payment-records")
                    || uri.startsWith("/api/sys-users")) {
                writeForbidden(response);
                return false;
            }
            if (writeMethod && (uri.startsWith("/api/elderly") || uri.startsWith("/api/room-beds")
                    || uri.startsWith("/api/notices"))) {
                writeForbidden(response);
                return false;
            }
        }

        return true;
    }

    private void writeForbidden(HttpServletResponse response) throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(Result.fail("当前角色无权限执行该操作")));
    }
}
