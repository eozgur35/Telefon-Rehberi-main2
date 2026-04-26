package duzce.bm.mf.telefonrehberi.controller;

import duzce.bm.mf.telefonrehberi.dto.UserDto;
import duzce.bm.mf.telefonrehberi.enums.Role;
import duzce.bm.mf.telefonrehberi.services.UserLoginService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserLoginService userService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model,
                            @RequestParam(name = "girisEmail", required = false) String email) {

        logger.info("Login sayfası açıldı");

        if (Objects.nonNull(session.getAttribute("oturumUser"))) {
            logger.info("Zaten giriş yapmış kullanıcı admin sayfasına yönlendirildi");
            return "redirect:/admin/persons";
        }

        model.addAttribute("girisEmail", email != null ? email : "");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        logger.info("Login denemesi: {}", email);

        UserDto userDto = userService.findByEmail(email);

        if (Objects.isNull(userDto)) {
            logger.warn("Kullanıcı bulunamadı: {}", email);
            model.addAttribute("hata",
                    messageSource.getMessage("login.user.notfound", null, LocaleContextHolder.getLocale()));
            model.addAttribute("girisEmail", email);
            return "login";
        }

        if (!userDto.getPassword().equals(password)) {
            logger.warn("Hatalı şifre denemesi: {}", email);
            model.addAttribute("hata",
                    messageSource.getMessage("login.password.invalid", null, LocaleContextHolder.getLocale()));
            model.addAttribute("girisEmail", email);
            return "login";
        }

        if (userDto.getRole() != Role.ADMIN) {
            logger.warn("Admin olmayan kullanıcı giriş denemesi: {} | role={}", email, userDto.getRole());
            model.addAttribute("hata",
                    messageSource.getMessage("login.admin.only", null, LocaleContextHolder.getLocale()));
            model.addAttribute("girisEmail", email);
            return "login";
        }

        session.setAttribute("oturumUser", userDto);
        session.setAttribute("oturumEmail", userDto.getEmail());

        logger.info("Başarılı login: {}", email);

        return "redirect:/admin/persons";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        Object user = session.getAttribute("oturumUser");

        logger.info("Logout işlemi: {}",
                (user != null ? ((UserDto) user).getEmail() : "bilinmiyor"));

        session.invalidate();

        return "redirect:/login";
    }
}