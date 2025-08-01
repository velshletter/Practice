package com.modsen.analytics_service.service;

import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

public interface CsvExportService {

    void writePollResultToCsv(UUID pollId, Writer writer) throws IOException;
}
