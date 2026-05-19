package duzce.bm.mf.telefonrehberi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import duzce.bm.mf.telefonrehberi.dto.DepartmentDto;
import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;
import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.exception.DatabaseException;
import duzce.bm.mf.telefonrehberi.services.AdminService;
import duzce.bm.mf.telefonrehberi.services.DepartmentService;
import duzce.bm.mf.telefonrehberi.services.SubDepartmentService;
import duzce.bm.mf.telefonrehberi.util.FileUtil;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/persons")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AdminService adminPersonService;

    @Autowired
    SubDepartmentService subDepartmentService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public String getPersonListPage(HttpSession session, Model model) {

        logger.info("Admin person list sayfası açıldı");

        List<PersonDto> personDtoList = adminPersonService.getAllPerson();
        List<SubDepartmentDto> subDepartDtoList = subDepartmentService.getAllSubDepartments();
        List<DepartmentDto> departDtoList = departmentService.getAllDepartments();

        logger.debug("Person sayısı: {}", personDtoList.size());
        logger.debug("SubDepartment sayısı: {}", subDepartDtoList.size());

        try {
            model.addAttribute("subDepartmentsJson", objectMapper.writeValueAsString(subDepartDtoList));
        }
        catch (JsonProcessingException e) {
            logger.error("SubDepartment JSON dönüşüm hatası", e);
            model.addAttribute("subDepartmentsJson", "[]");
        }

        model.addAttribute("kisiler", personDtoList);
        model.addAttribute("departments", departDtoList);
        model.addAttribute("allSubDepts", subDepartDtoList);

        logger.debug("Oturum email: {}", session.getAttribute("oturumEmail"));

        return "admin-persons";
    }

    @PostMapping("/create")
    public String createPerson(@RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName,
                               @RequestParam(value = "titleName", required = false) String titleName,
                               @RequestParam(value = "extensionNumber", required = false) String extensionNumber,
                               @RequestParam(value = "roomNumber", required = false) String roomNumber,
                               @RequestParam(value = "email", required = false) String email,
                               @RequestParam(value = "subDepartmentId", required = false) Integer subDepartmentId,
                               @RequestParam(value = "file", required = false) MultipartFile file,
                               RedirectAttributes ra) {

        logger.info("Person create isteği: {} {}", firstName, lastName);

        String base64Photo = FileUtil.convertToBase64(file);

        PersonDto personDto = new PersonDto(0, firstName, lastName, titleName, extensionNumber, roomNumber, email, null, subDepartmentId, null, base64Photo);

        adminPersonService.saveOrUpdatePerson(personDto);

        logger.info("Person başarıyla eklendi: {} {}", firstName, lastName);

        ra.addFlashAttribute("mesaj", firstName + " " + lastName + " başarıyla eklendi!");

        return "redirect:/admin/persons";
    }

    @PostMapping("/update")
    public String updatePerson(@RequestParam("personId") int personId,
                               @RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName,
                               @RequestParam(value = "titleName", required = false) String titleName,
                               @RequestParam(value = "extensionNumber", required = false) String extensionNumber,
                               @RequestParam(value = "roomNumber", required = false) String roomNumber,
                               @RequestParam(value = "email", required = false) String email,
                               @RequestParam(value = "subDepartmentId", required = false) Integer subDepartmentId,
                               @RequestParam(value = "file", required = false) MultipartFile file,
                               RedirectAttributes ra) {

        logger.info("Person update isteği: id={}", personId);

        String base64Photo = FileUtil.convertToBase64(file);

        PersonDto personDto = new PersonDto(personId, firstName, lastName, titleName, extensionNumber, roomNumber, email, null, subDepartmentId, null, base64Photo);

        adminPersonService.saveOrUpdatePerson(personDto);

        logger.info("Person güncellendi: id={}", personId);

        ra.addFlashAttribute("mesaj", firstName + " " + lastName + " başarıyla güncellendi!");

        return "redirect:/admin/persons";
    }

    @PostMapping("/delete")
    public String deletePerson(@RequestParam("personId") int personId, RedirectAttributes ra) {

        logger.warn("Person silme isteği: id={}", personId);

        boolean isDeleted = adminPersonService.deletePerson(personId);

        if (isDeleted) {
            logger.info("Person başarıyla silindi: id={}", personId);
            ra.addFlashAttribute("mesaj", "Kişi başarıyla silindi!");
        } else {
            logger.error("Person silinemedi: id={}", personId);
            ra.addFlashAttribute("hata", "Kişi bulunamadı veya silinemedi!");
        }

        return "redirect:/admin/persons";
    }

//Departman ve bolum ekleme
    @PostMapping("/departments/create")
    public String createDepartment(@RequestParam("name") String name,
                                   @RequestParam(value = "phones", required = false) String phones,
                                   @RequestParam(value = "faxes", required = false) String faxes,
                                   RedirectAttributes ra) {
        logger.info("Department create isteği: {}", name);
        DepartmentDto dto = new DepartmentDto();
        dto.setName(name);
        dto.setPhones(phones);
        dto.setFaxes(faxes);
        departmentService.saveDepartment(dto);
        ra.addFlashAttribute("mesaj", name + " birimi başarıyla eklendi!");
        return "redirect:/admin/persons";
    }

    @PostMapping("/departments/update")
    public String updateDepartment(@RequestParam("departmentId") int departmentId,
                                   @RequestParam("name") String name,
                                   @RequestParam(value = "phones", required = false) String phones,
                                   @RequestParam(value = "faxes", required = false) String faxes,
                                   RedirectAttributes ra) {
        logger.info("Department update isteği: id={}", departmentId);
        DepartmentDto dto = new DepartmentDto();
        dto.setDepartmentId(departmentId);
        dto.setName(name);
        dto.setPhones(phones);
        dto.setFaxes(faxes);
        departmentService.saveDepartment(dto);
        ra.addFlashAttribute("mesaj", name + " birimi başarıyla güncellendi!");
        return "redirect:/admin/persons";
    }

    @PostMapping("/departments/delete")
    public String deleteDepartment(@RequestParam("departmentId") int departmentId, RedirectAttributes ra) {
        logger.warn("Department silme isteği: id={}", departmentId);
        boolean deleted = departmentService.deleteDepartment(departmentId);
        if (deleted) ra.addFlashAttribute("mesaj", "Birim başarıyla silindi!");
        else         ra.addFlashAttribute("hata", "Birim bulunamadı veya silinemedi!");
        return "redirect:/admin/persons";
    }

    @PostMapping("/subdepartments/create")
    public String createSubDepartment(@RequestParam("name") String name,
                                      @RequestParam("departmentId") int departmentId,
                                      RedirectAttributes ra) {
        logger.info("SubDepartment create isteği: {} -> deptId={}", name, departmentId);
        SubDepartmentDto dto = new SubDepartmentDto();
        dto.setName(name);
        dto.setDepartmentId(departmentId);
        subDepartmentService.saveSubDepartment(dto);
        ra.addFlashAttribute("mesaj", name + " bölümü başarıyla eklendi!");
        return "redirect:/admin/persons";
    }

    @PostMapping("/subdepartments/update")
    public String updateSubDepartment(@RequestParam("subDepartmentId") int subDepartmentId,
                                      @RequestParam("name") String name,
                                      @RequestParam("departmentId") int departmentId,
                                      RedirectAttributes ra) {
        logger.info("SubDepartment update isteği: id={}", subDepartmentId);
        SubDepartmentDto dto = new SubDepartmentDto();
        dto.setSubDepartmentId(subDepartmentId);
        dto.setName(name);
        dto.setDepartmentId(departmentId);
        subDepartmentService.saveSubDepartment(dto);
        ra.addFlashAttribute("mesaj", name + " bölümü başarıyla güncellendi!");
        return "redirect:/admin/persons";
    }

    @PostMapping("/subdepartments/delete")
    public String deleteSubDepartment(@RequestParam("subDepartmentId") int subDepartmentId, RedirectAttributes ra) {
        logger.warn("SubDepartment silme isteği: id={}", subDepartmentId);
        boolean deleted = subDepartmentService.deleteSubDepartment(subDepartmentId);
        if (deleted) ra.addFlashAttribute("mesaj", "Bölüm başarıyla silindi!");
        else         ra.addFlashAttribute("hata", "Bölüm bulunamadı veya silinemedi!");
        return "redirect:/admin/persons";
    }
}