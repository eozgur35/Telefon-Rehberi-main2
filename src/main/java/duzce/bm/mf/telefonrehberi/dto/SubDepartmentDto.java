package duzce.bm.mf.telefonrehberi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubDepartmentDto {
    private int subDepartmentId;
    private String name;
    private int departmentId;
}