package duzce.bm.mf.telefonrehberi.filter;

import duzce.bm.mf.telefonrehberi.dto.UserDto;
import duzce.bm.mf.telefonrehberi.enums.Role;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;


@Component
public class AdminSessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        if (path.startsWith("/admin")) {
            HttpSession session = req.getSession(false);
            UserDto user = (UserDto) session.getAttribute("oturumUser");

            if (Objects.isNull(user) || user.getRole() != Role.ADMIN) {
                res.sendRedirect(req.getContextPath() + "/login");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}