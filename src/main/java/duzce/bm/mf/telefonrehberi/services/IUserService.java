package duzce.bm.mf.telefonrehberi.services;

import duzce.bm.mf.telefonrehberi.dto.UserDto;

public interface IUserService {
    public UserDto findByEmail(String email);
}
