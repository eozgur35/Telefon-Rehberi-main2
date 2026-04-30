package duzce.bm.mf.telefonrehberi.util;

import duzce.bm.mf.telefonrehberi.dao.OtpDao;
import duzce.bm.mf.telefonrehberi.entity.Otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpUtil {

    @Autowired
    OtpDao otpDao;

    private final SecureRandom random=new SecureRandom();

    public String createOtp(String email, String userId){

        int number=random.nextInt(900000)+100000;
        otpDao.save(new Otp(email,userId,String.valueOf(number)));

        return String.valueOf(number);

    }

}