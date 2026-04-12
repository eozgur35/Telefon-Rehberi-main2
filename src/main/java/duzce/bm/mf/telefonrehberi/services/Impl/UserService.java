package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dto.UserDto;
import duzce.bm.mf.telefonrehberi.entity.User;
import duzce.bm.mf.telefonrehberi.repository.UserRepository;
import duzce.bm.mf.telefonrehberi.services.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    public UserDto findByEmail(String email)
    {
        Optional<User> dbUser = userRepository.findByEmail(email);

        if(dbUser.isPresent())
        {
            User user = dbUser.get();
            UserDto newUserDto = new UserDto();
            BeanUtils.copyProperties(user, newUserDto);
            newUserDto.setRole(user.getRole());
            return newUserDto;
        }
        return null;
    }
}
