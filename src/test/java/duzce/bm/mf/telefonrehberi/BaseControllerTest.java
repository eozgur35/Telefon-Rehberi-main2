package duzce.bm.mf.telefonrehberi;

import duzce.bm.mf.telefonrehberi.config.AppConfig;
import duzce.bm.mf.telefonrehberi.config.WebConfig;
import duzce.bm.mf.telefonrehberi.controller.BaseController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class, AppConfig.class})
@WebAppConfiguration
public class BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static MockMvc mockMvc;

    // BaseController abstract veya doğrudan çağrılamaz olduğu için
    // test amaçlı onu miras alan gizli bir iç sınıf (mock controller) oluşturuyoruz.
    @Controller
    static class TestController extends BaseController {
        @GetMapping("/test-vize-validate")
        public void testMethod() {
            // Burası validate metodunu tetiklemek için kullanılabilir
        }
    }

    @BeforeAll
    public static void setup(@Autowired WebApplicationContext wac) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }

    @Test
    public void testBaseControllerStructure() throws Exception {
        // BaseController'ın genel çalışma mantığını (context yüklenmesini) test eder
        mockMvc.perform(get("/test-vize-validate"))
                .andExpect(status().isOk());
    }
}