package com.modsen.analytics_service.controller;

import com.modsen.analytics_service.dto.PollResultResponseDto;
import com.modsen.analytics_service.service.PollResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/results")
@RequiredArgsConstructor
public class ResultController {

    private final PollResultService pollResultService;

    @GetMapping
    public ResponseEntity<List<PollResultResponseDto>> getResults() {
        return ResponseEntity.ok(pollResultService.getAllPollResults());
    }



}
