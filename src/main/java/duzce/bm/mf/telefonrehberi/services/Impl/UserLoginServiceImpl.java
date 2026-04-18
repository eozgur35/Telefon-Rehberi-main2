package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dto.UserDto;
import duzce.bm.mf.telefonrehberi.entity.User;
import duzce.bm.mf.telefonrehberi.exception.BadRequestException;
import duzce.bm.mf.telefonrehberi.exception.ResourceNotFoundException;
import duzce.bm.mf.telefonrehberi.repository.UserRepository;
import duzce.bm.mf.telefonrehberi.services.UserLoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto findByEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new BadRequestException("Email boş olamaz");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Kullanıcı bulunamadı: " + email));

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);

        userDto.setRole(user.getRole());

        return userDto;
    }
}