package duzce.bm.mf.telefonrehberi.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        StringBuilder params = new StringBuilder();

        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            params.append(name)
                    .append("=")
                    .append(request.getParameter(name))
                    .append(" ");
        }

        logger.info("[REQUEST] {} {} | PARAMS: {}",
                method, uri,
                params.length() > 0 ? params.toString() : "NONE");

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        logger.info("[RESPONSE] {} | STATUS: {}",
                request.getRequestURI(),
                response.getStatus());

        if (ex != null) {
            logger.error("[ERROR] Request sırasında hata oluştu -> {}", request.getRequestURI(), ex);
        }
    }
}