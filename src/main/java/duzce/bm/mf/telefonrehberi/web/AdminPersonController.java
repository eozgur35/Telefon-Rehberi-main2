package duzce.bm.mf.telefonrehberi.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import duzce.bm.mf.telefonrehberi.model.Department;
import duzce.bm.mf.telefonrehberi.model.Person;
import duzce.bm.mf.telefonrehberi.model.SubDepartment;
import duzce.bm.mf.telefonrehberi.model.User;
import duzce.bm.mf.telefonrehberi.enums.Role;
import duzce.bm.mf.telefonrehberi.services.IAdminPersonService;
import duzce.bm.mf.telefonrehberi.services.IDepartmentService;
import duzce.bm.mf.telefonrehberi.services.ISubDepartmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/persons")
public class AdminPersonController {

    // Spring Boot'un otomatik verdiği nesne yerine manuel oluşturuyoruz
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private IAdminPersonService adminPersonService;

    @Autowired
    private ISubDepartmentService subDepartmentService;

    @Autowired
    private IDepartmentService departmentService;

    // ── Kişi listesi ───────────────────────────────────────────────────
    @GetMapping
    public String personListesi(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        // DTO yerine doğrudan Model listelerini çekiyoruz
        List<Person> kisiler = adminPersonService.getAllPerson();
        List<SubDepartment> subList = subDepartmentService.getAllSubDepartments();
        List<Department> deptList = departmentService.getAllDepartments();

        model.addAttribute("subDepartments", subList);

        try {
            model.addAttribute("subDepartmentsJson", objectMapper.writeValueAsString(subList));
        } catch (JsonProcessingException e) {
            model.addAttribute("subDepartmentsJson", "[]");
        }

        model.addAttribute("kisiler", kisiler);
        model.addAttribute("departments", deptList);
        model.addAttribute("oturumEmail", session.getAttribute("oturumEmail"));

        System.out.println("Kişi sayısı: " + kisiler.size());
        System.out.println("Birim sayısı: " + deptList.size());
        System.out.println("Bölüm sayısı: " + subList.size());

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

        // DTO yerine Person Entity oluşturuyoruz
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setTitleName(titleName);
        person.setExtensionNumber(extensionNumber);
        person.setRoomNumber(roomNumber);
        person.setEmail(email);

        // Veritabanı ilişkisi olduğu için ID yerine doğrudan SubDepartment nesnesini bularak bağlıyoruz
        if (subDepartmentId != null) {
            SubDepartment subDepartment = subDepartmentService.getSubDepartmentById(subDepartmentId);
            person.setSubDepartment(subDepartment);
        }

        adminPersonService.savePerson(person);
        ra.addFlashAttribute("mesaj", firstName + " " + lastName + " başarıyla eklendi.");
        return "redirect:/admin/persons";
    }

    // ── Kişi güncelle ──────────────────────────────────────────────────
    @PostMapping("/guncelle")
    public String personGuncelle(@RequestParam("personId") Integer personId,
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

        Person person = new Person();
        person.setPersonId(personId); // Güncelleme olduğu için ID'sini de atıyoruz
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setTitleName(titleName);
        person.setExtensionNumber(extensionNumber);
        person.setRoomNumber(roomNumber);
        person.setEmail(email);

        if (subDepartmentId != null) {
            SubDepartment subDepartment = subDepartmentService.getSubDepartmentById(subDepartmentId);
            person.setSubDepartment(subDepartment);
        }

        adminPersonService.savePerson(person);
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
            ra.addFlashAttribute("mesaj", personId + " numaralı kişi silindi.");
        } else {
            ra.addFlashAttribute("hata", "Silinecek kişi bulunamadı.");
        }

        return "redirect:/admin/persons";
    }

    // ── Yardımcı ───────────────────────────────────────────────────────
    private boolean isAdmin(HttpSession session) {
        // DTO yerine User modeli üzerinden kontrol ediyoruz
        User user = (User) session.getAttribute("oturumUser");
        return user != null && user.getRole() == Role.ADMIN;
    }
}