package duzce.bm.mf.telefonrehberi.interceptors;

import duzce.bm.mf.telefonrehberi.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionUtil sessionUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (Objects.isNull(session) || !sessionUtil.isAdmin(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}