package duzce.bm.mf.telefonrehberi.controller;

import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.services.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    AdminService adminPersonService;

    @Value("${anthropic.api.key}")
    private String apiKey;

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> body) {

        logger.info("Chat endpoint çağrıldı");

        String userMessage = body.get("message");

        if (userMessage == null || userMessage.isBlank()) {
            logger.warn("Boş mesaj geldi");
            return ResponseEntity.badRequest().body(Map.of("reply", "Mesaj boş olamaz."));
        }

        logger.debug("Kullanıcı mesajı: {}", userMessage);

        List<PersonDto> kisiler = adminPersonService.getAllPerson();
        logger.info("Veritabanından {} kişi çekildi", kisiler.size());

        String personelBilgisi = kisiler.stream()
                .map(p -> String.format("- %s %s | Unvan: %s | Birim: %s | Bölüm: %s | Dahili: %s | Oda: %s | E-posta: %s",
                        nvl(p.getFirstName()), nvl(p.getLastName()),
                        nvl(p.getTitleName()), nvl(p.getDeptName()),
                        nvl(p.getSubDeptName()), nvl(p.getExtensionNumber()),
                        nvl(p.getRoomNumber()), nvl(p.getEmail())))
                .collect(Collectors.joining("\n"));

        logger.debug("System prompt hazırlandı");

        String systemPrompt = """
            Sen bir kurum telefon rehberi asistanısın.
            Aşağıdaki personel listesini kullanarak soruları cevapla.
            Kısa ve net yanıtlar ver. Listede olmayan bilgiyi uydurma.
            
            PERSONEL LİSTESİ:
            """ + personelBilgisi;

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
            logger.info("Claude API isteği gönderiliyor");

            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://api.anthropic.com/v1/messages",
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody, headers),
                    Map.class
            );

            List<Map<String, Object>> content =
                    (List<Map<String, Object>>) response.getBody().get("content");

            String reply = (String) content.get(0).get("text");

            logger.info("Claude API başarılı cevap döndü");

            return ResponseEntity.ok(Map.of("reply", reply));

        } catch (Exception e) {
            logger.error("Claude API hatası oluştu", e);
            return ResponseEntity.status(500)
                    .body(Map.of("reply", "Asistan şu an yanıt veremiyor."));
        }
    }

    private String nvl(Object val) {
        return val != null ? val.toString() : "—";
    }
}