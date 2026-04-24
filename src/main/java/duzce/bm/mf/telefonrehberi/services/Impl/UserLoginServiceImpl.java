package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.UserDao;
import duzce.bm.mf.telefonrehberi.dto.UserDto;
import duzce.bm.mf.telefonrehberi.entity.User;
import duzce.bm.mf.telefonrehberi.exception.ResourceNotFoundException;
import duzce.bm.mf.telefonrehberi.services.UserLoginService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    UserDao userDao;

    public UserDto findByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (Objects.nonNull(user)) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDto.setRole(user.getRole());
            return userDto;
        }
        throw new ResourceNotFoundException("Kullanıcı bulunamadı: " + email);
    }
}
