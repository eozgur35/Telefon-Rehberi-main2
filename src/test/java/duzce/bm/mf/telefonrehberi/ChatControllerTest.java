package duzce.bm.mf.telefonrehberi;

import duzce.bm.mf.telefonrehberi.config.AppConfig;
import duzce.bm.mf.telefonrehberi.config.WebConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class, AppConfig.class})
@WebAppConfiguration
public class ChatControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static MockMvc mockMvc;

    @BeforeAll
    public static void setup(@Autowired WebApplicationContext wac) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }

    // ai e bos bir mesaj gonderiyor, donus tipi bad request olmali.
    @Test
    public void testChatEmptyMessage() throws Exception {
        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    // gecerli bir mesaj gondererek sistemi test ediyor
    // 200 donerse test basarili
    // api de sikinti olunca hata verebiliyor..
    @Test
    public void testChatMessageSuccess() throws Exception {
        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"Rehberde kimler var?\"}"))
                .andExpect(status().isOk());
    }
}