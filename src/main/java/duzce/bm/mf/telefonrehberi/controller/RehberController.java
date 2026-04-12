package duzce.bm.mf.telefonrehberi.controller;

import duzce.bm.mf.telefonrehberi.dto.DepartmentDto;
import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;
import duzce.bm.mf.telefonrehberi.services.Impl.AdminPersonService;
import duzce.bm.mf.telefonrehberi.services.Impl.DepartmentService;
import duzce.bm.mf.telefonrehberi.services.Impl.SubDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RehberController {

    @Autowired
    AdminPersonService adminPersonService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    SubDepartmentService subDepartmentService;


    @GetMapping("/")
    public String anaSayfa(Model model,
                           @RequestParam(name = "departmentId", required = false) Integer departmentId,
                           @RequestParam(name = "subDepartmentId", required = false) Integer subDepartmentId) {

        // Artık departmentRepository.findAll() hata vermeyecektir
        model.addAttribute("departments", departmentService.getAllDepartments());

        DepartmentDto selectedDept = null;
        List<SubDepartmentDto> subDepts = new ArrayList<>();
        List<PersonDto> kisiler;

        if (departmentId != null) {
            selectedDept = departmentService.findById(departmentId);
            if (selectedDept != null) {
                subDepts = subDepartmentService.findByDepartment(departmentId);
            }
        }

        if (subDepartmentId != null) {
            kisiler = adminPersonService.findBySubdepartmentSubDepartmentId(subDepartmentId);
        } else if (departmentId != null) {
            kisiler = adminPersonService.findBySubdepartmentDepartmentDepartmentId(departmentId);
        } else {
            kisiler = adminPersonService.getAllPerson();
        }

        model.addAttribute("subDepartments", subDepts);
        model.addAttribute("selectedDepartment", selectedDept);
        model.addAttribute("selectedDepartmentId", departmentId);
        model.addAttribute("selectedSubId", subDepartmentId);
        model.addAttribute("kisiler", kisiler);

        return "rehber";
    }
}