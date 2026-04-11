package duzce.bm.mf.telefonrehberi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonDto {
    private int     personId;
    private String  firstName;
    private String  lastName;
    private String  titleName;
    private String  extensionNumber;
    private String  roomNumber;
    private String  email;
    private Integer subDeptId;
    private String  subDeptName;
    private String  deptName;
}
