package duzce.bm.mf.telefonrehberi.controller;

import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.Person;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import duzce.bm.mf.telefonrehberi.repository.DepartmentRepository;
import duzce.bm.mf.telefonrehberi.repository.PersonRepository;
import duzce.bm.mf.telefonrehberi.repository.SubDepartmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RehberController {

    // 1. Repository'leri buraya tanımla
    private final PersonRepository personRepository;
    private final DepartmentRepository departmentRepository;
    private final SubDepartmentRepository subDepartmentRepository;

    // 2. Constructor içine hepsini ekle (Spring bunları otomatik bağlar)
    public RehberController(PersonRepository personRepository,
                            DepartmentRepository departmentRepository,
                            SubDepartmentRepository subDepartmentRepository) {
        this.personRepository = personRepository;
        this.departmentRepository = departmentRepository;
        this.subDepartmentRepository = subDepartmentRepository;
    }

    @GetMapping("/")
    public String anaSayfa(Model model,
                           @RequestParam(name = "departmentId", required = false) Integer departmentId,
                           @RequestParam(name = "subDepartmentId", required = false) Integer subDepartmentId) {

        // Artık departmentRepository.findAll() hata vermeyecektir
        model.addAttribute("departments", departmentRepository.findAll());

        Department selectedDept = null;
        List<SubDepartment> subDepts = new ArrayList<>();
        List<Person> kisiler;

        if (departmentId != null) {
            selectedDept = departmentRepository.findById(departmentId).orElse(null);
            if (selectedDept != null) {
                subDepts = subDepartmentRepository.findByDepartment(selectedDept);
            }
        }

        if (subDepartmentId != null) {
            kisiler = personRepository.findBySubdepartmentSubDepartmentId(subDepartmentId);
        } else if (departmentId != null) {
            kisiler = personRepository.findBySubdepartmentDepartmentDepartmentId(departmentId);
        } else {
            kisiler = personRepository.findAll();
        }

        model.addAttribute("subDepartments", subDepts);
        model.addAttribute("selectedDepartment", selectedDept);
        model.addAttribute("selectedDepartmentId", departmentId);
        model.addAttribute("selectedSubId", subDepartmentId);
        model.addAttribute("kisiler", kisiler);

        return "rehber";
    }
}