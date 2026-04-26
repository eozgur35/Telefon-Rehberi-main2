package duzce.bm.mf.telefonrehberi.util;

import duzce.bm.mf.telefonrehberi.dto.UserDto;
import duzce.bm.mf.telefonrehberi.enums.Role;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

    private static final Logger logger =
            LoggerFactory.getLogger(SessionUtil.class);

    public boolean isAdmin(HttpSession session) {

        UserDto user = (UserDto) session.getAttribute("oturumUser");

        boolean result = (user != null && user.getRole() == Role.ADMIN);

        logger.debug("Admin kontrolü yapıldı -> result={}, user={}",
                result,
                (user != null ? user.getEmail() : "null"));

        return result;
    }
}