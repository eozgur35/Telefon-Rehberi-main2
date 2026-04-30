package duzce.bm.mf.telefonrehberi.dao;

import duzce.bm.mf.telefonrehberi.entity.Otp;

public interface OtpDao {
    Otp findTopByEmailOrderByIdDesc(String email);
    void save(Otp otp);
    void delete(Otp otp);
}
