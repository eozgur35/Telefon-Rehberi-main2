package duzce.bm.mf.telefonrehberi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
//----                 WAR İÇİN EKLE
//@SpringBootApplication
//public class Main extends SpringBootServletInitializer {
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(Main.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(Main.class, args);
//    }
//}

