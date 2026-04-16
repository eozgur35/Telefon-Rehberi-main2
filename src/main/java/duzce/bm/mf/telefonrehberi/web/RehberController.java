package duzce.bm.mf.telefonrehberi.web;

import duzce.bm.mf.telefonrehberi.model.Department;
import duzce.bm.mf.telefonrehberi.model.Person;
import duzce.bm.mf.telefonrehberi.model.SubDepartment;
import duzce.bm.mf.telefonrehberi.services.IAdminPersonService;
import duzce.bm.mf.telefonrehberi.services.IDepartmentService;
import duzce.bm.mf.telefonrehberi.services.ISubDepartmentService;
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
    private IAdminPersonService adminPersonService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private ISubDepartmentService subDepartmentService;

    @GetMapping("/")
    public String anaSayfa(Model model,
                           @RequestParam(name = "departmentId", required = false) Integer departmentId,
                           @RequestParam(name = "subDepartmentId", required = false) Integer subDepartmentId) {

        model.addAttribute("departments", departmentService.getAllDepartments());

        Department selectedDept = null;
        List<SubDepartment> subDepts = new ArrayList<>();
        List<Person> kisiler;

        if (departmentId != null) {
            selectedDept = departmentService.getDepartmentById(departmentId);
            if (selectedDept != null) {
                // Bizim servisimizde metodun adını bu şekilde vermiştik
                subDepts = subDepartmentService.getSubDepartmentsByDepartmentId(departmentId);
            }
        }

        if (subDepartmentId != null) {
            kisiler = adminPersonService.findBySubDepartmentId(subDepartmentId);
        } else if (departmentId != null) {
            kisiler = adminPersonService.findByDepartmentId(departmentId);
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