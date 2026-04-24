package duzce.bm.mf.telefonrehberi.controller;

import duzce.bm.mf.telefonrehberi.dto.UserDto;
import duzce.bm.mf.telefonrehberi.enums.Role;
import duzce.bm.mf.telefonrehberi.services.UserLoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    UserLoginService userService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model, @RequestParam(name = "girisEmail", required = false) String email) {
        if (Objects.nonNull(session.getAttribute("oturumUser"))) {
            return "redirect:/admin/persons";
        }
        model.addAttribute("girisEmail", email != null ? email : "");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
        UserDto userDto = userService.findByEmail(email);
        if (Objects.isNull(userDto)) {
            model.addAttribute("hata", messageSource.getMessage("login.user.notfound", null, LocaleContextHolder.getLocale()));
            model.addAttribute("girisEmail", email);
            return "login";
        }

        if (!userDto.getPassword().equals(password)) {
            model.addAttribute("hata", messageSource.getMessage("login.password.invalid", null, LocaleContextHolder.getLocale()));
            model.addAttribute("girisEmail", email);
            return "login";
        }

        if (userDto.getRole() != Role.ADMIN) {
            model.addAttribute("hata", messageSource.getMessage("login.admin.only", null, LocaleContextHolder.getLocale()));
            model.addAttribute("girisEmail", email);
            return "login";
        }
        session.setAttribute("oturumUser", userDto);
        session.setAttribute("oturumEmail", userDto.getEmail());

        return "redirect:/admin/persons";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}