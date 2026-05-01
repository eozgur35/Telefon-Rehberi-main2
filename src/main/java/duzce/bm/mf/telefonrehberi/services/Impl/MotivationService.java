package duzce.bm.mf.telefonrehberi.services.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import duzce.bm.mf.telefonrehberi.dto.MotivationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MotivationService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;


    public MotivationDto getMotivation() {
        try {
            String response = restTemplate.getForObject("https://zenquotes.io/api/random",String.class);

            JsonNode root = objectMapper.readTree(response);
            JsonNode firstQuote = root.get(0);

            MotivationDto motivationDto = new MotivationDto();
            motivationDto.setQuote(firstQuote.get("q").asText());
            motivationDto.setAuth(firstQuote.get("a").asText());

            return motivationDto;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Motivasyon sözü alınırken hata oluştu", e);
        }
    }
}
