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

// Statik importlar (get, post ve status için şart)
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class, AppConfig.class})
@WebAppConfiguration
public class LoginControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static MockMvc mockMvc;

    @BeforeAll
    public static void setup(@Autowired WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    // login sayfasinin acilip acilmadigini test eder
    // ayriyeten sayfanin donus view i "login" olmali
    @Test
    public void testLoginPageLoad() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    // hatali bir giris denemesinde sistem yeniden login sayfasina atmali
    @Test
    public void testLoginFailure() throws Exception {
        mockMvc.perform(post("/login")
                        .param("email", "yanlis@duzce.edu.tr")
                        .param("password", "123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }


    // dogru giris degerlerini guncelle!!!



    // cikis islemini test ediyor
    @Test
    public void testLogoutRedirect() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection());
    }
}