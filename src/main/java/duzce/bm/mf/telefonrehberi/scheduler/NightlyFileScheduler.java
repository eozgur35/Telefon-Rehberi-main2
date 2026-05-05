package duzce.bm.mf.telefonrehberi.scheduler;

import duzce.bm.mf.telefonrehberi.dao.PersonDao;
import duzce.bm.mf.telefonrehberi.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Component
public class NightlyFileScheduler {

    @Autowired
    PersonDao personDao;

    @Value("${file.export.fullpath}")
    private String fullPath;

    @Scheduled(cron = "0 0 2 * * *")
    public void writePersonsToFile() {
        try {
            List<Person> persons = personDao.getAllPersons();

            String fileName = fullPath + LocalDate.now() + ".csv";
            FileOutputStream fos = new FileOutputStream(fileName);

            fos.write(0xEF);
            fos.write(0xBB);
            fos.write(0xBF);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));

            writer.write("Ad;Soyad;Dahili No;Ünvan;Oda;Email\n");

            for (Person p : persons) {
                writer.write(
                        p.getFirstName() + ";"
                                + p.getLastName() + ";"
                                + p.getExtensionNumber() + ";"
                                + p.getTitleName() + ";"
                                + p.getRoomNumber() + ";"
                                + p.getEmail()
                                + "\n"
                );
            }

            writer.close();
            System.out.println("Dosya oluşturuldu: " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
