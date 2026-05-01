package duzce.bm.mf.telefonrehberi.controller;

import duzce.bm.mf.telefonrehberi.dto.ForgottenPasswordOtpDto;
import duzce.bm.mf.telefonrehberi.services.Impl.SendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Transactional
public class ForgottenPasswordController {

    private static final Logger logger = LoggerFactory.getLogger(ForgottenPasswordController.class);

    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {

        logger.info("Forgot password sayfası açıldı");

        return "forgot-password";
    }

    @PostMapping("/forgotten-password-send-otp")
    public String forgetPassword(@RequestParam String email, Model model) {

        logger.info("OTP gönderme isteği: {}", email);

        ForgottenPasswordOtpDto dto = new ForgottenPasswordOtpDto();
        dto.setEmail(email);

        sendEmailService.forgetPasswordService(dto);

        logger.info("OTP başarıyla gönderildi: {}", email);

        model.addAttribute("email", email);
        return "verify-otp";
    }

    @PostMapping("/forgotten-password-verify")
    public String verifyPassword(@RequestParam String email,
                                 @RequestParam String otp,
                                 Model model) {

        logger.info("OTP doğrulama isteği: {}", email);

        ForgottenPasswordOtpDto dto = new ForgottenPasswordOtpDto();
        dto.setEmail(email);
        dto.setOtp(otp);

        sendEmailService.verifyOtp(dto);

        logger.info("OTP doğrulama tamamlandı: {}", email);

        model.addAttribute("email", email);
        return "reset-password";
    }

    @PostMapping("/forgotten-password-reset")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword,
                                @RequestParam String newPasswordAgain,
                                Model model) {

        logger.info("Şifre reset işlemi başlatıldı: {}", email);

        if (!newPassword.equals(newPasswordAgain)) {

            logger.warn("Şifreler uyuşmuyor: {}", email);

            model.addAttribute("email", email);
            model.addAttribute("hata",
                    messageSource.getMessage("password.reset.success", null, LocaleContextHolder.getLocale()));

            return "reset-password";
        }

        ForgottenPasswordOtpDto dto = new ForgottenPasswordOtpDto();
        dto.setEmail(email);
        dto.setNewPassword(newPassword);
        dto.setNewPasswordAgain(newPasswordAgain);

        sendEmailService.resetPassword(dto);

        logger.info("Şifre başarıyla güncellendi: {}", email);

        model.addAttribute("mesaj",
                messageSource.getMessage("password.not.match", null, LocaleContextHolder.getLocale()));

        return "login";
    }
}