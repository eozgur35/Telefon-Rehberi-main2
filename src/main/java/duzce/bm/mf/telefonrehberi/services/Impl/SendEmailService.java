package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.OtpDao;
import duzce.bm.mf.telefonrehberi.dao.UserDao;
import duzce.bm.mf.telefonrehberi.dto.ForgottenPasswordOtpDto;
import duzce.bm.mf.telefonrehberi.entity.Otp;
import duzce.bm.mf.telefonrehberi.entity.User;
import duzce.bm.mf.telefonrehberi.exception.InvalidOtpException;
import duzce.bm.mf.telefonrehberi.exception.UserNotFoundException;
import duzce.bm.mf.telefonrehberi.util.OtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class SendEmailService {

    private static final Logger logger = LoggerFactory.getLogger(SendEmailService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    OtpDao otpDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OtpUtil otpUtil;

    @Autowired
    JavaMailSender javaMailSender;

    public ResponseEntity<?> forgetPasswordService(ForgottenPasswordOtpDto forgottenPasswordOtpDto) {

        logger.info("Şifre sıfırlama isteği alındı: email={}", forgottenPasswordOtpDto.getEmail());

        User user = userDao.findByEmail(forgottenPasswordOtpDto.getEmail());

        if (Objects.isNull(user)) {
            logger.warn("Kullanıcı bulunamadı: email={}", forgottenPasswordOtpDto.getEmail());
            throw new UserNotFoundException("Bu e-posta ile kayıtlı kullanıcı bulunamadı.");
        }

        logger.info("OTP oluşturuluyor: email={}", user.getEmail());

        String otpCode = otpUtil.createOtp(user.getEmail(), String.valueOf(user.getUserId()));

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Reset Password");
        simpleMailMessage.setText("Otp sıfırlama kodu: " + otpCode);

        javaMailSender.send(simpleMailMessage);

        logger.info("OTP mail başarıyla gönderildi: email={}", user.getEmail());

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> verifyOtp(ForgottenPasswordOtpDto forgottenPasswordOtpDto) {

        logger.info("OTP doğrulama isteği: email={}", forgottenPasswordOtpDto.getEmail());

        Otp otp = otpDao.findTopByEmailOrderByIdDesc(forgottenPasswordOtpDto.getEmail());

        if (Objects.isNull(otp) || !forgottenPasswordOtpDto.getOtp().equals(otp.getOtp())) {

            logger.warn("OTP doğrulama başarısız: email={}", forgottenPasswordOtpDto.getEmail());

            if (Objects.nonNull(otp)) {
                otp.setVerified(false);
                otpDao.save(otp);
            }

            throw new InvalidOtpException("OTP code is incorrect");
        }

        otp.setVerified(true);
        otpDao.save(otp);

        logger.info("OTP doğrulama başarılı: email={}", forgottenPasswordOtpDto.getEmail());

        return ResponseEntity.ok().build();
    }

    public void resetPassword(ForgottenPasswordOtpDto forgottenPasswordOtpDto) {

        logger.info("Şifre reset işlemi başlatıldı: email={}", forgottenPasswordOtpDto.getEmail());

        User user = userDao.findByEmail(forgottenPasswordOtpDto.getEmail());
        Otp otp = otpDao.findTopByEmailOrderByIdDesc(forgottenPasswordOtpDto.getEmail());

        if (Objects.isNull(otp) || !otp.isVerified()) {
            logger.error("OTP geçersiz, şifre reset başarısız: email={}", forgottenPasswordOtpDto.getEmail());
            throw new RuntimeException("OTP not valid");
        }

        user.setPassword(passwordEncoder.encode(forgottenPasswordOtpDto.getNewPassword()));
        userDao.save(user);
        otpDao.delete(otp);

        logger.info("Şifre başarıyla güncellendi: email={}", forgottenPasswordOtpDto.getEmail());
    }
}