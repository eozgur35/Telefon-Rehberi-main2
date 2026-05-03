package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.OtpDao;
import duzce.bm.mf.telefonrehberi.dao.UserDao;
import duzce.bm.mf.telefonrehberi.dto.ForgottenPasswordOtpDto;
import duzce.bm.mf.telefonrehberi.entity.Otp;
import duzce.bm.mf.telefonrehberi.entity.User;
import duzce.bm.mf.telefonrehberi.exception.InvalidOtpException;
import duzce.bm.mf.telefonrehberi.exception.UserNotFoundException;
import duzce.bm.mf.telefonrehberi.util.OtpUtil;
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

    public void forgetPasswordService(ForgottenPasswordOtpDto forgottenPasswordOtpDto) {
        User user = userDao.findByEmail(forgottenPasswordOtpDto.getEmail());
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("Bu e-posta ile kayıtlı kullanıcı bulunamadı.");
        }
        String otpCode = otpUtil.createOtp(user.getEmail(), String.valueOf(user.getUserId()));

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Reset Password");
        simpleMailMessage.setText("Otp sıfırlama kodu: " + otpCode);
        javaMailSender.send(simpleMailMessage);
    }

    public void verifyOtp(ForgottenPasswordOtpDto forgottenPasswordOtpDto) {
        Otp otp = otpDao.findTopByEmailOrderByIdDesc(forgottenPasswordOtpDto.getEmail());
        if (Objects.isNull(otp) || !forgottenPasswordOtpDto.getOtp().equals(otp.getOtp())) {
            if (Objects.nonNull(otp)) {
                otp.setVerified(false);
                otpDao.save(otp);
            }
            throw new InvalidOtpException("OTP code is incorrect");
        }
        otp.setVerified(true);
        otpDao.save(otp);
    }

    public void resetPassword(ForgottenPasswordOtpDto forgottenPasswordOtpDto) {
        User user = userDao.findByEmail(forgottenPasswordOtpDto.getEmail());
        Otp otp = otpDao.findTopByEmailOrderByIdDesc(forgottenPasswordOtpDto.getEmail());
        if (Objects.isNull(otp) || !otp.isVerified()) {
            throw new RuntimeException("OTP not valid");
        }
        user.setPassword(passwordEncoder.encode(forgottenPasswordOtpDto.getNewPassword()));
        userDao.save(user);
        otpDao.delete(otp);
    }
}