package com.modsen.analytics_service.controller;

import com.modsen.analytics_service.dto.PollResultResponseDto;
import com.modsen.analytics_service.service.CsvExportService;
import com.modsen.analytics_service.service.PollResultService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/results")
@RequiredArgsConstructor
public class ResultController {

    private final PollResultService pollResultService;
    private final CsvExportService csvExportService;

    @GetMapping(value = "/{pollId}", produces = "text/csv")
    public void exportPollResultCsv(@PathVariable UUID pollId, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"poll_result_" + pollId + ".csv\"");

        csvExportService.writePollResultToCsv(pollId, response.getWriter());

    }

    @GetMapping
    public ResponseEntity<List<PollResultResponseDto>> getResults() {
        return ResponseEntity.ok(pollResultService.getAllPollResults());
    }



}
