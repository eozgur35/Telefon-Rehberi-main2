package duzce.bm.mf.telefonrehberi.controller;

import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    AdminService adminPersonService;

    @Value("${anthropic.api.key}")
    private String apiKey;

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> body) {
        String userMessage = body.get("message");
        if (userMessage == null || userMessage.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("reply", "Mesaj boş olamaz."));
        }

        // Veritabanından tüm personeli çek ve metin olarak özetle
        List<PersonDto> kisiler = adminPersonService.getAllPerson();
        String personelBilgisi = kisiler.stream()
                .map(p -> String.format("- %s %s | Unvan: %s | Birim: %s | Bölüm: %s | Dahili: %s | Oda: %s | E-posta: %s",
                        nvl(p.getFirstName()), nvl(p.getLastName()),
                        nvl(p.getTitleName()), nvl(p.getDeptName()),
                        nvl(p.getSubDeptName()), nvl(p.getExtensionNumber()),
                        nvl(p.getRoomNumber()), nvl(p.getEmail())))
                .collect(Collectors.joining("\n"));

        String systemPrompt = """
            Sen bir kurum telefon rehberi asistanısın.
            Aşağıdaki personel listesini kullanarak soruları cevapla.
            Kısa ve net yanıtlar ver. Listede olmayan bilgiyi uydurma.
            
            PERSONEL LİSTESİ:
            """ + personelBilgisi;

        // Claude API isteği
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "claude-haiku-4-5-20251001");
        requestBody.put("max_tokens", 1024);
        requestBody.put("system", systemPrompt);
        requestBody.put("messages", List.of(Map.of("role", "user", "content", userMessage)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);
        headers.set("anthropic-version", "2023-06-01");

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://api.anthropic.com/v1/messages",
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody, headers),
                    Map.class
            );

            List<Map<String, Object>> content = (List<Map<String, Object>>) response.getBody().get("content");
            String reply = (String) content.get(0).get("text");
            return ResponseEntity.ok(Map.of("reply", reply));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("reply", "Asistan şu an yanıt veremiyor: " + e.getMessage()));
        }
    }

    private String nvl(Object val) {
        return val != null ? val.toString() : "—";
    }
}