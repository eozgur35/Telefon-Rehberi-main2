package duzce.bm.mf.telefonrehberi.filter;

import duzce.bm.mf.telefonrehberi.dto.UserDto;
import duzce.bm.mf.telefonrehberi.enums.Role;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class AdminSessionFilter implements Filter {

    private static final Logger logger =
            LoggerFactory.getLogger(AdminSessionFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        if (path.startsWith("/admin")) {

            logger.debug("Admin erişim kontrolü: {}", path);

            HttpSession session = req.getSession(false);

            if (Objects.isNull(session)) {
                logger.warn("Session yok, admin erişimi engellendi: {}", path);
                res.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            UserDto user = (UserDto) session.getAttribute("oturumUser");

            if (Objects.isNull(user)) {
                logger.warn("User session boş, admin erişimi engellendi: {}", path);
                res.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            if (user.getRole() != Role.ADMIN) {
                logger.warn("Admin olmayan kullanıcı erişimi engellendi: role={}, path={}",
                        user.getRole(), path);

                res.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            logger.debug("Admin erişimi onaylandı: {}", user.getEmail());
        }

        chain.doFilter(request, response);
    }
}