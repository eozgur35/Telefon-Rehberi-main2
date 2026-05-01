package duzce.bm.mf.telefonrehberi.scheduler;


import duzce.bm.mf.telefonrehberi.dto.MotivationDto;
import duzce.bm.mf.telefonrehberi.services.Impl.MotivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MotivationScheduler {

    @Autowired
    MotivationService motivationService;

    MotivationDto motivationDto;

    @Scheduled(fixedRate = 60000, initialDelay = 1000)
    public void updateMotivation() {
        motivationDto = motivationService.getMotivation();
        System.out.println("Yeni motivasyon sözü: " + motivationDto.getQuote() + " - " + motivationDto.getAuth());
    }

    public MotivationDto getMotivationDto() {
        return motivationDto;
    }

}