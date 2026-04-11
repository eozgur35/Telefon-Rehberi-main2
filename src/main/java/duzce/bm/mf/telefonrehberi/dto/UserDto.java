package duzce.bm.mf.telefonrehberi.dto;

import duzce.bm.mf.telefonrehberi.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private int userId;

    private String email;

    private String password;

    private Role role;

}
