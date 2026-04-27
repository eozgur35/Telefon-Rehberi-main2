package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.UserDao;
import duzce.bm.mf.telefonrehberi.dto.UserDto;
import duzce.bm.mf.telefonrehberi.entity.User;
import duzce.bm.mf.telefonrehberi.exception.ResourceNotFoundException;
import duzce.bm.mf.telefonrehberi.services.UserLoginService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class UserLoginServiceImpl implements UserLoginService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserLoginServiceImpl.class);

    @Autowired
    UserDao userDao;

    public UserDto findByEmail(String email) {

        logger.info("Kullanıcı arama işlemi başlatıldı: email={}", email);

        User user = userDao.findByEmail(email);

        if (Objects.nonNull(user)) {

            logger.info("Kullanıcı bulundu: email={}", email);

            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDto.setRole(user.getRole());

            logger.debug("User role: {}", userDto.getRole());

            return userDto;
        }

        logger.error("Kullanıcı bulunamadı: email={}", email);

        throw new ResourceNotFoundException("Kullanıcı bulunamadı: " + email);
    }
}