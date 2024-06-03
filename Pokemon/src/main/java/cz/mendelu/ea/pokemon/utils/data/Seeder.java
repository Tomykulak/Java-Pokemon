package cz.mendelu.ea.pokemon.utils.data;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Seeder {

//    private boolean shouldSeedData() {
//    TODO
//    }
    @PostConstruct
    public void seedDefaultData() {
//        if (!shouldSeedData()) {
//            log.info("--- Default data already seeded ---");
//            return;
//        }
//        ...
//        log.info("--- Default data seeded ---");
    }
}
