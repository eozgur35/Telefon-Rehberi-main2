package duzce.bm.mf.telefonrehberi.services;

import duzce.bm.mf.telefonrehberi.dto.UserDto;

public interface UserLoginService {

    UserDto findByEmail(String email);

}
