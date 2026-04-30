package duzce.bm.mf.telefonrehberi;

import duzce.bm.mf.telefonrehberi.config.AppConfig;
import duzce.bm.mf.telefonrehberi.config.WebConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class, AppConfig.class})
@WebAppConfiguration
public class ForgottenPasswordControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static MockMvc mockMvc;

    @BeforeAll
    public static void setup(@Autowired WebApplicationContext wac) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }

    // Şifremi unuttum sayfasının yüklenmesi
    @Test
    public void testForgotPasswordPageLoad() throws Exception {
        mockMvc.perform(get("/forgot-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgot-password"));
    }

    // OTP gönderme isteği (Email ile)
    @Test
    public void testSendOtp() throws Exception {
        mockMvc.perform(post("/forgotten-password-send-otp")
                        .param("email", "test@duzce.edu.tr"))
                .andExpect(status().isOk())
                .andExpect(view().name("verify-otp"))
                .andExpect(model().attribute("email", "test@duzce.edu.tr"));
    }

    // OTP doğrulama testi
    @Test
    public void testVerifyOtp() throws Exception {
        mockMvc.perform(post("/forgotten-password-verify")
                        .param("email", "test@duzce.edu.tr")
                        .param("otp", "123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("reset-password"));
    }

    // Şifre sıfırlama testi (Şifreler uyuşmadığında)
    @Test
    public void testResetPasswordFail() throws Exception {
        mockMvc.perform(post("/forgotten-password-reset")
                        .param("email", "test@duzce.edu.tr")
                        .param("newPassword", "123")
                        .param("newPasswordAgain", "456"))
                .andExpect(status().isOk())
                .andExpect(view().name("reset-password"))
                .andExpect(model().attributeExists("hata"));
    }
}