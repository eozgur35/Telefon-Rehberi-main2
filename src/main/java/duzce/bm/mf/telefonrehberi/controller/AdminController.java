package duzce.bm.mf.telefonrehberi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import duzce.bm.mf.telefonrehberi.dto.DepartmentDto;
import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;
import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.services.AdminService;
import duzce.bm.mf.telefonrehberi.services.DepartmentService;
import duzce.bm.mf.telefonrehberi.services.SubDepartmentService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // Dosya yüklemek için eklendi
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64; // Base64 dönüşümü için eklendi
import java.util.List;
import java.util.Objects;

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

        model.addAttribute("subDepartments", subDepartDtoList);

        try {
            model.addAttribute("subDepartmentsJson", objectMapper.writeValueAsString(subDepartDtoList));
        }
        catch (JsonProcessingException e) {
            logger.error("SubDepartment JSON dönüşüm hatası", e);
            model.addAttribute("subDepartmentsJson", "[]");
        }

        model.addAttribute("kisiler", personDtoList);
        model.addAttribute("departments", departDtoList);

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
                               @RequestParam(value = "file", required = false) MultipartFile file, // Yeni Eklendi
                               RedirectAttributes ra) {

        logger.info("Person create isteği: {} {}", firstName, lastName);

        int subId = (Objects.nonNull(subDepartmentId)) ? subDepartmentId : 0;

        //Resmi base64e cevirme
        String base64Photo = null;
        if (file != null && !file.isEmpty()) {
            try {
                base64Photo = Base64.getEncoder().encodeToString(file.getBytes());
            } catch (Exception e) {
                logger.error("Resim işlenirken hata oluştu", e);
            }
        }

        PersonDto personDto = new PersonDto(0, firstName, lastName, titleName, extensionNumber, roomNumber, email, null, subId, null, base64Photo);

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
                               @RequestParam(value = "file", required = false) MultipartFile file, // Yeni Eklendi
                               RedirectAttributes ra) {

        logger.info("Person update isteği: id={}", personId);

        int subId = (Objects.nonNull(subDepartmentId)) ? subDepartmentId : 0;

        String base64Photo = null;
        if (file != null && !file.isEmpty()) {
            try {
                base64Photo = Base64.getEncoder().encodeToString(file.getBytes());
            } catch (Exception e) {
                logger.error("Resim işlenirken hata oluştu", e);
            }
        }

        PersonDto personDto = new PersonDto(personId, firstName, lastName, titleName, extensionNumber, roomNumber, email, null, subId, null, base64Photo);

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
}