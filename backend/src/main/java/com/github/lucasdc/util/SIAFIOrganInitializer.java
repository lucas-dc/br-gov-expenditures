package com.github.lucasdc.util;

import com.github.lucasdc.service.SIAFICodesImporterService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SIAFIOrganInitializer implements CommandLineRunner {

    private final SIAFICodesImporterService importerService;

    public SIAFIOrganInitializer(SIAFICodesImporterService importerService) {
        this.importerService = importerService;
    }

    @Override
    public void run(String... args) throws Exception {
        importerService.importFromResourceFile("classpath:siafi_codes.txt");
    }

}
