package duzce.bm.mf.telefonrehberi.util;

import duzce.bm.mf.telefonrehberi.dto.UserDto;
import duzce.bm.mf.telefonrehberi.enums.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

    public boolean isAdmin(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("oturumUser");
        return user != null && user.getRole() == Role.ADMIN;
    }
}
