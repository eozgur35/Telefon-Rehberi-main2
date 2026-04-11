package duzce.bm.mf.telefonrehberi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import duzce.bm.mf.telefonrehberi.entity.Person;
import duzce.bm.mf.telefonrehberi.entity.User;
import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.enums.Role;
import duzce.bm.mf.telefonrehberi.repository.DepartmentRepository;
import duzce.bm.mf.telefonrehberi.repository.PersonRepository;
import duzce.bm.mf.telefonrehberi.repository.SubDepartmentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PersonRepository personRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private SubDepartmentRepository subDepartmentRepository;
    @Autowired
    private ObjectMapper objectMapper;

    // ── Kişi listesi ───────────────────────────────────────────────────
    @GetMapping
    public String personListesi(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        // LAZY loading sorununu önlemek için entity yerine DTO kullan
        List<Person> personList = personRepository.findAll();
        List<PersonDto> kisiler = new ArrayList<>();

        for (Person p : personList) {
            // NullPointerException almamak için güvenli atamalar yapıyoruz
            String deptName = null;
            String subDeptName = null;
            Integer subDeptId = null;

            if (p.getSubdepartment() != null) {
                subDeptName = p.getSubdepartment().getName();
                subDeptId = p.getSubdepartment().getSubDepartmentId();

                if (p.getSubdepartment().getDepartment() != null) {
                    deptName = p.getSubdepartment().getDepartment().getName();
                }
            }

            // Yeni PersonDto yapısına göre verileri dolduruyoruz
            PersonDto dto = new PersonDto(
                    p.getPersonId(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getTitleName(),
                    p.getExtensionNumber(),
                    p.getRoomNumber(),
                    p.getEmail(),
                    subDeptName,
                    subDeptId,
                    deptName
            );

            kisiler.add(dto);
        }

        // Alt birimleri JS için hazırlıyoruz
        List<Map<String, Object>> subList = subDepartmentRepository.findAll().stream()
                .map(s -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("subDepartmentId", s.getSubDepartmentId());
                    m.put("name", s.getName());

                    // NullPointerException almamak için güvenli departman ID çekimi
                    m.put("departmentId", s.getDepartment() != null ? s.getDepartment().getDepartmentId() : 0);
                    return m;
                })
                .collect(Collectors.toList());

// JSON'A ÇEVİRMEDEN doğrudan listeyi model'e ekliyoruz:
        model.addAttribute("subDepartments", subList);

        try {
            model.addAttribute("subDepartmentsJson", objectMapper.writeValueAsString(subList));
        } catch (JsonProcessingException e) {
            model.addAttribute("subDepartmentsJson", "[]");
        }

        model.addAttribute("kisiler", kisiler);
        model.addAttribute("departments", departmentRepository.findAll());
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

        if (!isAdmin(session)) return "redirect:/login";

        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setTitleName(titleName);
        person.setExtensionNumber(extensionNumber);
        person.setRoomNumber(roomNumber);
        person.setEmail(email);

        if (subDepartmentId != null && subDepartmentId > 0) {
            subDepartmentRepository.findById(subDepartmentId)
                    .ifPresent(person::setSubdepartment);
        }

        personRepository.save(person);
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

        Optional<Person> personOpt = personRepository.findById(personId);
        if (!personOpt.isPresent()) {
            ra.addFlashAttribute("hata", "Güncellenecek kişi bulunamadı.");
            return "redirect:/admin/persons";
        }

        Person person = personOpt.get();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setTitleName(titleName);
        person.setExtensionNumber(extensionNumber);
        person.setRoomNumber(roomNumber);
        person.setEmail(email);

        if (subDepartmentId != null && subDepartmentId > 0) {
            subDepartmentRepository.findById(subDepartmentId)
                    .ifPresent(person::setSubdepartment);
        } else {
            person.setSubdepartment(null);
        }

        personRepository.save(person);
        ra.addFlashAttribute("mesaj", firstName + " " + lastName + " başarıyla güncellendi.");
        return "redirect:/admin/persons";
    }

    // ── Kişi sil ───────────────────────────────────────────────────────
    @PostMapping("/sil")
    public String personSil(@RequestParam("personId") int personId,
                            HttpSession session,
                            RedirectAttributes ra) {

        if (!isAdmin(session)) return "redirect:/login";

        Optional<Person> personOpt = personRepository.findById(personId);
        if (personOpt.isPresent()) {
            Person p = personOpt.get();
            personRepository.deleteById(personId);
            ra.addFlashAttribute("mesaj", p.getFirstName() + " " + p.getLastName() + " silindi.");
        } else {
            ra.addFlashAttribute("hata", "Silinecek kişi bulunamadı.");
        }

        return "redirect:/admin/persons";
    }

    // ── Yardımcı ───────────────────────────────────────────────────────
    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("oturumUser");
        return user != null && user.getRole() == Role.ADMIN;
    }
}