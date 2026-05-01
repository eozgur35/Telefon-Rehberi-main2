package duzce.bm.mf.telefonrehberi.controller;

import duzce.bm.mf.telefonrehberi.dto.ForgottenPasswordOtpDto;
import duzce.bm.mf.telefonrehberi.services.Impl.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Transactional
public class ForgottenPasswordController {

    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgotten-password-send-otp")
    public String forgetPassword(@RequestParam String email, Model model) {
        ForgottenPasswordOtpDto dto = new ForgottenPasswordOtpDto();
        dto.setEmail(email);

        sendEmailService.forgetPasswordService(dto);

        model.addAttribute("email", email);
        return "verify-otp";
    }

    @PostMapping("/forgotten-password-verify")
    public String verifyPassword(@RequestParam String email,
                                 @RequestParam String otp,
                                 Model model) {
        ForgottenPasswordOtpDto dto = new ForgottenPasswordOtpDto();
        dto.setEmail(email);
        dto.setOtp(otp);

        sendEmailService.verifyOtp(dto);

        model.addAttribute("email", email);
        return "reset-password";
    }

    @PostMapping("/forgotten-password-reset")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword,
                                @RequestParam String newPasswordAgain,
                                Model model) {

        if (!newPassword.equals(newPasswordAgain)) {
            model.addAttribute("email", email);
            model.addAttribute("hata", messageSource.getMessage("password.reset.success", null, LocaleContextHolder.getLocale()));
            return "reset-password";
        }

        ForgottenPasswordOtpDto dto = new ForgottenPasswordOtpDto();
        dto.setEmail(email);
        dto.setNewPassword(newPassword);
        dto.setNewPasswordAgain(newPasswordAgain);

        sendEmailService.resetPassword(dto);

        model.addAttribute("mesaj", messageSource.getMessage("password.not.match", null, LocaleContextHolder.getLocale()));
        return "login";
    }
}