package duzce.bm.mf.telefonrehberi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import duzce.bm.mf.telefonrehberi.entity.Person;
import duzce.bm.mf.telefonrehberi.entity.User;
import duzce.bm.mf.telefonrehberi.enums.Role;
import duzce.bm.mf.telefonrehberi.repository.DepartmentRepository;
import duzce.bm.mf.telefonrehberi.repository.PersonRepository;
import duzce.bm.mf.telefonrehberi.repository.SubDepartmentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/persons")
public class AdminPersonController {

    private final PersonRepository        personRepository;
    private final DepartmentRepository    departmentRepository;
    private final SubDepartmentRepository subDepartmentRepository;
    private final ObjectMapper            objectMapper;

    public AdminPersonController(PersonRepository personRepository,
                                 DepartmentRepository departmentRepository,
                                 SubDepartmentRepository subDepartmentRepository,
                                 ObjectMapper objectMapper) {
        this.personRepository        = personRepository;
        this.departmentRepository    = departmentRepository;
        this.subDepartmentRepository = subDepartmentRepository;
        this.objectMapper            = objectMapper;
    }

    // ── Kişi listesi ───────────────────────────────────────────────────
    @GetMapping
    public String personListesi(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        // LAZY loading sorununu önlemek için entity yerine DTO kullan
        List<PersonDTO> kisiler = personRepository.findAll().stream()
                .map(p -> new PersonDTO(
                        p.getPersonId(),
                        p.getFirstName(),
                        p.getLastName(),
                        p.getTitleName(),
                        p.getExtensionNumber(),
                        p.getRoomNumber(),
                        p.getEmail(),
                        p.getSubdepartment() != null ? p.getSubdepartment().getSubDepartmentId() : null,
                        p.getSubdepartment() != null ? p.getSubdepartment().getName() : null,
                        p.getSubdepartment() != null && p.getSubdepartment().getDepartment() != null
                                ? p.getSubdepartment().getDepartment().getName() : null
                ))
                .collect(Collectors.toList());

        // Alt birimleri JS için JSON olarak göm
        List<Map<String, Object>> subList = subDepartmentRepository.findAll().stream()
                .map(s -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("subDepartmentId", s.getSubDepartmentId());
                    m.put("name",            s.getName());
                    m.put("departmentId",    s.getDepartment().getDepartmentId());
                    return m;
                })
                .collect(Collectors.toList());

        try {
            model.addAttribute("subDepartmentsJson", objectMapper.writeValueAsString(subList));
        } catch (JsonProcessingException e) {
            model.addAttribute("subDepartmentsJson", "[]");
        }

        model.addAttribute("kisiler",     kisiler);
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("oturumEmail", session.getAttribute("oturumEmail"));

        return "admin-persons";
    }

    // ── Yeni kişi ekle ─────────────────────────────────────────────────
    @PostMapping("/ekle")
    public String personEkle(@RequestParam("firstName")       String firstName,
                             @RequestParam("lastName")        String lastName,
                             @RequestParam(value = "titleName",       required = false) String titleName,
                             @RequestParam(value = "extensionNumber", required = false) String extensionNumber,
                             @RequestParam(value = "roomNumber",      required = false) String roomNumber,
                             @RequestParam(value = "email",           required = false) String email,
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
    public String personGuncelle(@RequestParam("personId")         int personId,
                                 @RequestParam("firstName")        String firstName,
                                 @RequestParam("lastName")         String lastName,
                                 @RequestParam(value = "titleName",       required = false) String titleName,
                                 @RequestParam(value = "extensionNumber", required = false) String extensionNumber,
                                 @RequestParam(value = "roomNumber",      required = false) String roomNumber,
                                 @RequestParam(value = "email",           required = false) String email,
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

    // ── İç DTO sınıfı — LAZY loading sorununu çözer ────────────────────
    public static class PersonDTO {
        private final int     personId;
        private final String  firstName;
        private final String  lastName;
        private final String  titleName;
        private final String  extensionNumber;
        private final String  roomNumber;
        private final String  email;
        private final Integer subDeptId;
        private final String  subDeptName;
        private final String  deptName;

        public PersonDTO(int personId, String firstName, String lastName,
                         String titleName, String extensionNumber, String roomNumber,
                         String email, Integer subDeptId, String subDeptName, String deptName) {
            this.personId        = personId;
            this.firstName       = firstName;
            this.lastName        = lastName;
            this.titleName       = titleName;
            this.extensionNumber = extensionNumber;
            this.roomNumber      = roomNumber;
            this.email           = email;
            this.subDeptId       = subDeptId;
            this.subDeptName     = subDeptName;
            this.deptName        = deptName;
        }

        public int     getPersonId()        { return personId; }
        public String  getFirstName()       { return firstName; }
        public String  getLastName()        { return lastName; }
        public String  getTitleName()       { return titleName; }
        public String  getExtensionNumber() { return extensionNumber; }
        public String  getRoomNumber()      { return roomNumber; }
        public String  getEmail()           { return email; }
        public Integer getSubDeptId()       { return subDeptId; }
        public String  getSubDeptName()     { return subDeptName; }
        public String  getDeptName()        { return deptName; }
    }
}