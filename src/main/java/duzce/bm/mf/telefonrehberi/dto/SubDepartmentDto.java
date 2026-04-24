package duzce.bm.mf.telefonrehberi.dto;

public class SubDepartmentDto {
    private int subDepartmentId;
    private String name;
    private int departmentId;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubDepartmentId() {
        return subDepartmentId;
    }

    public void setSubDepartmentId(int subDepartmentId) {
        this.subDepartmentId = subDepartmentId;
    }
}