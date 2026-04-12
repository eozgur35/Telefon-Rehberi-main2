package duzce.bm.mf.telefonrehberi.controller;

import duzce.bm.mf.telefonrehberi.dto.DepartmentDto;
import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;
import duzce.bm.mf.telefonrehberi.services.AdminService;
import duzce.bm.mf.telefonrehberi.services.DepartmentService;
import duzce.bm.mf.telefonrehberi.services.SubDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class HomePageController {

    @Autowired
    AdminService adminPersonService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    SubDepartmentService subDepartmentService;


    @GetMapping("/")
    public String homePage(Model model,
                           @RequestParam(name = "departmentId", required = false) Integer departmentId,
                           @RequestParam(name = "subDepartmentId", required = false) Integer subDepartmentId) {

        model.addAttribute("departments", departmentService.getAllDepartments());

        DepartmentDto selectedDept = null;
        List<SubDepartmentDto> subDepts = new ArrayList<>();
        List<PersonDto> personDtoList;

        if (Objects.nonNull(departmentId)) {
            selectedDept = departmentService.findById(departmentId);
            if (Objects.nonNull(selectedDept)) {
                subDepts = subDepartmentService.getSubDepartmentsByDepartmentId(departmentId);
            }
        }

        if (Objects.nonNull(subDepartmentId)) {
            personDtoList = adminPersonService.getPersonsBySubDepartmentId(subDepartmentId);
        } else if (Objects.nonNull(departmentId)) {
            personDtoList = adminPersonService.getPersonsByDepartmentId(departmentId);
        } else {
            personDtoList = adminPersonService.getAllPerson();
        }
        model.addAttribute("subDepartments", subDepts);
        model.addAttribute("selectedDepartment", selectedDept);
        model.addAttribute("selectedDepartmentId", departmentId);
        model.addAttribute("selectedSubId", subDepartmentId);
        model.addAttribute("kisiler", personDtoList);

        return "rehber";
    }
}