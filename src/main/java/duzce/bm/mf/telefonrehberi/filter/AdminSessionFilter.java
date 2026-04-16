package duzce.bm.mf.telefonrehberi.filter;

import duzce.bm.mf.telefonrehberi.enums.Role;
import duzce.bm.mf.telefonrehberi.model.User; // DTO yerine Model import edildi
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * /admin/** yollarını korur.
 * Giriş yapmamış veya ADMIN rolü olmayan kullanıcıları /login'e yönlendirir.
 */
@Component
public class AdminSessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest)  request;
        HttpServletResponse res  = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // Admin yollarına erisim kontrolu
        if (path.startsWith(req.getContextPath() + "/admin")) {
            HttpSession session = req.getSession(false);

            User user = (session != null) ? (User) session.getAttribute("oturumUser") : null;

            if (user == null || user.getRole() != Role.ADMIN) {
                res.sendRedirect(req.getContextPath() + "/login");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}