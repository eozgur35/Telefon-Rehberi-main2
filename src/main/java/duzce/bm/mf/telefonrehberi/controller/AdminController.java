package duzce.bm.mf.telefonrehberi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import duzce.bm.mf.telefonrehberi.dto.DepartmentDto;
import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;
import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.services.AdminService;
import duzce.bm.mf.telefonrehberi.services.DepartmentService;
import duzce.bm.mf.telefonrehberi.services.SubDepartmentService;
import duzce.bm.mf.telefonrehberi.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin/persons")
public class AdminController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AdminService adminPersonService;

    @Autowired
    SubDepartmentService subDepartmentService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    SessionUtil sessionUtil;

    @Autowired
    MessageSource messageSource;

    @GetMapping
    public String getPersonListPage(HttpSession session, Model model) {
        List<PersonDto> personDtoList = adminPersonService.getAllPerson();
        List<SubDepartmentDto> subDepartDtoList = subDepartmentService.getAllSubDepartments();
        List<DepartmentDto> departDtoList = departmentService.getAllDepartments();

        model.addAttribute("subDepartments", subDepartDtoList);
        try {
            model.addAttribute("subDepartmentsJson", objectMapper.writeValueAsString(subDepartDtoList));
        } catch (JsonProcessingException e) {
            model.addAttribute("subDepartmentsJson", "[]");
        }
        model.addAttribute("kisiler", personDtoList);
        model.addAttribute("departments", departDtoList);
        model.addAttribute("oturumEmail", session.getAttribute("oturumEmail"));

        return "admin-persons";
    }

    @PostMapping("/create")
    public String createPerson(@RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName,
                               @RequestParam(value = "titleName", required = false) String titleName,
                               @RequestParam(value = "extensionNumber", required = false) String extensionNumber,
                               @RequestParam(value = "roomNumber", required = false) String roomNumber,
                               @RequestParam(value = "email", required = false) String email,
                               @RequestParam(value = "subDepartmentId", required = false) int subDepartmentId,
                               HttpSession session,
                               RedirectAttributes ra) {
        PersonDto personDto = new PersonDto(0, firstName, lastName, titleName, extensionNumber, roomNumber, email, null, subDepartmentId, null);
        adminPersonService.saveOrUpdatePerson(personDto);
        String message = messageSource.getMessage("person.add.success", new Object[]{firstName, lastName}, LocaleContextHolder.getLocale());
        ra.addFlashAttribute("mesaj", message);
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
                               @RequestParam(value = "subDepartmentId", required = false) int subDepartmentId,
                               HttpSession session,
                               RedirectAttributes ra) {

        PersonDto personDto = new PersonDto(personId, firstName, lastName, titleName, extensionNumber, roomNumber, email, null, subDepartmentId, null);
        adminPersonService.saveOrUpdatePerson(personDto);
        String message = messageSource.getMessage("person.update.success", new Object[]{firstName, lastName}, LocaleContextHolder.getLocale());
        ra.addFlashAttribute("mesaj", message);
        return "redirect:/admin/persons";
    }

    @PostMapping("/delete")
    public String deletePerson(@RequestParam("personId") int personId, HttpSession session, RedirectAttributes ra) {
        boolean isDeleted = adminPersonService.deletePerson(personId);
        if (isDeleted) {
            String message = messageSource.getMessage("person.delete.success", new Object[]{personId}, LocaleContextHolder.getLocale());
            ra.addFlashAttribute("mesaj", message);
        } else {
            String message = messageSource.getMessage("person.notfound", null, LocaleContextHolder.getLocale());
            ra.addFlashAttribute("hata", message);
        }
        return "redirect:/admin/persons";
    }
}