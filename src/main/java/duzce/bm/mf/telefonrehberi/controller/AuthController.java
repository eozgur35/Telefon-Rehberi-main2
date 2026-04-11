package duzce.bm.mf.telefonrehberi.controller;

import duzce.bm.mf.telefonrehberi.entity.User;
import duzce.bm.mf.telefonrehberi.enums.Role;
import duzce.bm.mf.telefonrehberi.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ── Login sayfasını göster ──────────────────────────────────────────
    @GetMapping("/login")
    public String loginSayfasi(HttpSession session, Model model,
                               @RequestParam(name = "girisEmail", required = false) String girisEmail) {
        // Zaten giriş yapmışsa admin paneline yönlendir
        if (session.getAttribute("oturumUser") != null) {
            return "redirect:/admin/persons";
        }
        model.addAttribute("girisEmail", girisEmail != null ? girisEmail : "");
        return "login";
    }

    // ── Login POST ──────────────────────────────────────────────────────
    @PostMapping("/login")
    public String loginYap(@RequestParam("email") String email,
                           @RequestParam("password") String password,
                           HttpSession session,
                           Model model) {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (!userOpt.isPresent()) {
            model.addAttribute("hata", "Bu e-posta adresiyle kayıtlı kullanıcı bulunamadı.");
            model.addAttribute("girisEmail", email);
            return "login";
        }

        User user = userOpt.get();

        // Şifre kontrolü — BCrypt kullanıyorsan aşağıya bak (PasswordEncoder versiyonu)
        if (!user.getPassword().equals(password)) {
            model.addAttribute("hata", "Şifre hatalı. Lütfen tekrar deneyin.");
            model.addAttribute("girisEmail", email);
            return "login";
        }

        // Sadece ADMIN girebilir
        if (user.getRole() != Role.ADMIN) {
            model.addAttribute("hata", "Bu sisteme yalnızca yöneticiler giriş yapabilir.");
            model.addAttribute("girisEmail", email);
            return "login";
        }

        // Session'a kaydet
        session.setAttribute("oturumUser", user);
        session.setAttribute("oturumEmail", user.getEmail());

        return "redirect:/admin/persons";
    }

    // ── Logout ─────────────────────────────────────────────────────────
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";  // /login'e yönlendir, / değil
    }
}