package duzce.bm.mf.telefonrehberi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import duzce.bm.mf.telefonrehberi.dto.DepartmentDto;
import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;
import duzce.bm.mf.telefonrehberi.dto.UserDto;
import duzce.bm.mf.telefonrehberi.entity.Person;
import duzce.bm.mf.telefonrehberi.entity.User;
import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.enums.Role;
import duzce.bm.mf.telefonrehberi.repository.DepartmentRepository;
import duzce.bm.mf.telefonrehberi.repository.PersonRepository;
import duzce.bm.mf.telefonrehberi.repository.SubDepartmentRepository;
import duzce.bm.mf.telefonrehberi.services.IAdminPersonService;
import duzce.bm.mf.telefonrehberi.services.Impl.AdminPersonService;
import duzce.bm.mf.telefonrehberi.services.Impl.DepartmentService;
import duzce.bm.mf.telefonrehberi.services.Impl.SubDepartmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/persons")
public class AdminPersonController {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    AdminPersonService adminPersonService;
    @Autowired
    SubDepartmentService subDepartmentService;
    @Autowired
    DepartmentService departmentService;

    // ── Kişi listesi ───────────────────────────────────────────────────
    @GetMapping
    public String personListesi(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        List<PersonDto> kisiler = adminPersonService.getAllPerson();

        // Alt birimleri JS için hazırlıyoruz
        List<SubDepartmentDto> subList = subDepartmentService.getAllSubDepartments();
        List<DepartmentDto> deptList = departmentService.getAllDepartments();
        // JSON'A ÇEVİRMEDEN doğrudan listeyi model'e ekliyoruz:
        model.addAttribute("subDepartments", subList);

        try {
            model.addAttribute("subDepartmentsJson", objectMapper.writeValueAsString(subList));
        } catch (JsonProcessingException e) {
            model.addAttribute("subDepartmentsJson", "[]");
        }

        model.addAttribute("kisiler", kisiler);
        model.addAttribute("departments", deptList);
        model.addAttribute("oturumEmail", session.getAttribute("oturumEmail"));

        return "admin-persons";
    }

    // ── Yeni kişi ekle ─────────────────────────────────────────────────
    @PostMapping("/ekle")
    public String personEkle(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam(value = "titleName", required = false) String titleName,
                             @RequestParam(value = "extensionNumber", required = false) String extensionNumber,
                             @RequestParam(value = "roomNumber", required = false) String roomNumber,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "subDepartmentId", required = false) Integer subDepartmentId,
                             HttpSession session,
                             RedirectAttributes ra) {

        if (!isAdmin(session))
            return "redirect:/login";

        PersonDto personDto = new PersonDto(
            0,
                firstName,
                lastName,
                titleName,
                extensionNumber,
                roomNumber,
                email,
                null,
                subDepartmentId,
                null
        );


        adminPersonService.savePerson(personDto);
        ra.addFlashAttribute("mesaj", firstName + " " + lastName + " başarıyla eklendi.");
        return "redirect:/admin/persons";
    }

    // ── Kişi güncelle ──────────────────────────────────────────────────
    @PostMapping("/guncelle")
    public String personGuncelle(@RequestParam("personId") int personId,
                                 @RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName,
                                 @RequestParam(value = "titleName", required = false) String titleName,
                                 @RequestParam(value = "extensionNumber", required = false) String extensionNumber,
                                 @RequestParam(value = "roomNumber", required = false) String roomNumber,
                                 @RequestParam(value = "email", required = false) String email,
                                 @RequestParam(value = "subDepartmentId", required = false) Integer subDepartmentId,
                                 HttpSession session,
                                 RedirectAttributes ra) {

        if (!isAdmin(session)) return "redirect:/login";
        PersonDto personDto = new PersonDto(
                personId,
                firstName,
                lastName,
                titleName,
                extensionNumber,
                roomNumber,
                email,
                null,
                subDepartmentId,
                null
        );


        adminPersonService.savePerson(personDto);
        ra.addFlashAttribute("mesaj", firstName + " " + lastName + " başarıyla güncellendi.");
        return "redirect:/admin/persons";
    }

    // ── Kişi sil ───────────────────────────────────────────────────────
    @PostMapping("/sil")
    public String personSil(@RequestParam("personId") int personId,
                            HttpSession session,
                            RedirectAttributes ra) {

        if (!isAdmin(session)) return "redirect:/login";

        boolean sonuc = adminPersonService.deletePerson(personId);
        if (sonuc) {
            ra.addFlashAttribute("mesaj", personId + " " + "numaraali kisi" + " silindi.");
        } else {
            ra.addFlashAttribute("hata", "Silinecek kişi bulunamadı.");
        }

        return "redirect:/admin/persons";
    }

    // ── Yardımcı ───────────────────────────────────────────────────────
    private boolean isAdmin(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("oturumUser");
        return user != null && user.getRole() == Role.ADMIN;
    }
}