package com.github.lucasdc.service;

import com.github.lucasdc.entity.Branch;
import com.github.lucasdc.entity.Organ;
import com.github.lucasdc.repository.OrganRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class SIAFICodesImporterService {

    private final ResourceLoader resourceLoader;
    private final OrganRepository organRepository;

    public SIAFICodesImporterService(ResourceLoader resourceLoader, OrganRepository organRepository) {
        this.resourceLoader = resourceLoader;
        this.organRepository = organRepository;
    }

    public void importFromResourceFile(String location) throws Exception {
        Resource resource = resourceLoader.getResource(location);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineSplittedByQuotationMarks = line.split("\"");
                String code = lineSplittedByQuotationMarks[0].substring(0, lineSplittedByQuotationMarks[0].length()-1);
                String organName = lineSplittedByQuotationMarks[1];
                Branch branch = Branch.fromValue(lineSplittedByQuotationMarks[7].split(" ")[1]); // ignore the prefix "PODER "

                this.organRepository.save(new Organ(code, organName, branch));
            }
        }
    }
}
