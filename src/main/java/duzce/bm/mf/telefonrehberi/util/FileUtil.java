package duzce.bm.mf.telefonrehberi.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Component
public class FileUtil {

    public static String convertToBase64(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            return Base64.getEncoder().encodeToString(file.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Resim Base64'e çevrilemedi", e);
        }
    }
}