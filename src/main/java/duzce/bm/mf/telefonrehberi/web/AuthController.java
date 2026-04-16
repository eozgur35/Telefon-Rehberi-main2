package duzce.bm.mf.telefonrehberi.web;

import duzce.bm.mf.telefonrehberi.model.User;
import duzce.bm.mf.telefonrehberi.enums.Role;
import duzce.bm.mf.telefonrehberi.services.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private IUserService userService;

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

        // Doğrudan veritabanından e-posta ve şifre ile eşleşen kullanıcıyı arıyoruz
        User user = userService.login(email, password);

        if (user == null) {
            model.addAttribute("hata", "E-posta adresi veya şifre hatalı. Lütfen tekrar deneyin.");
            model.addAttribute("girisEmail", email);
            return "login";
        }

        // Sadece ADMIN girebilir
        if (user.getRole() != Role.ADMIN) {
            model.addAttribute("hata", "Bu sisteme yalnızca yöneticiler giriş yapabilir.");
            model.addAttribute("girisEmail", email);
            return "login";
        }

        // Session'a Entity (Model) nesnemizi kaydediyoruz
        session.setAttribute("oturumUser", user);
        session.setAttribute("oturumEmail", user.getEmail());

        return "redirect:/admin/persons";
    }

    // ── Logout ─────────────────────────────────────────────────────────
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}