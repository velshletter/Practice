package com.modsen.analytics_service.service.impl;

import com.modsen.analytics_service.entity.OptionResult;
import com.modsen.analytics_service.entity.PollResult;
import com.modsen.analytics_service.repository.PollResultRepository;
import com.modsen.analytics_service.service.CsvExportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CsvExportServiceImpl implements CsvExportService {

    private final PollResultRepository pollResultRepository;

    public void writePollResultToCsv(UUID pollId, Writer writer) throws IOException {
        PollResult result = pollResultRepository.findById(pollId)
                .orElseThrow(() -> new EntityNotFoundException("Poll result not found"));

        String[] headers = {"Option Text", "Vote Count", "Poll ID", "Calculated At"};
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader(headers)
                .build();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, format)) {
            for (OptionResult option : result.getOptions()) {
                csvPrinter.printRecord(
                        option.getText(),
                        option.getVotes(),
                        result.getPollId(),
                        result.getCalculatedAt()
                );
            }
            csvPrinter.flush();
        }
    }
}
