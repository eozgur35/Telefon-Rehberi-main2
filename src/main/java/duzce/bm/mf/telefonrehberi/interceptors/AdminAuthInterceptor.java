package duzce.bm.mf.telefonrehberi.interceptors;

import duzce.bm.mf.telefonrehberi.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private static final Logger logger =
            LoggerFactory.getLogger(AdminAuthInterceptor.class);

    @Autowired
    private SessionUtil sessionUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();
        String method = request.getMethod();
        HttpSession session = request.getSession(false);

        logger.info("[INTERCEPTOR] {} {}", method, uri);

        if (Objects.isNull(session)) {
            logger.warn("Session bulunamadı -> LOGIN REDIRECT | {}", uri);
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        if (!sessionUtil.isAdmin(session)) {
            logger.warn("Admin yetkisi yok -> ERİŞİM ENGELLENDİ | user={}, uri={}",
                    session.getAttribute("oturumEmail"), uri);

            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        logger.info("Admin erişimi onaylandı -> user={}, uri={}",
                session.getAttribute("oturumEmail"), uri);

        return true;
    }
}