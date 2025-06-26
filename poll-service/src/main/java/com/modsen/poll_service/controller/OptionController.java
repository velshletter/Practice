package com.modsen.poll_service.controller;

import com.modsen.poll_service.dto.OptionRequestDto;
import com.modsen.poll_service.dto.OptionResponseDto;
import com.modsen.poll_service.service.OptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/polls/{pollId}/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;

    @PostMapping
    public ResponseEntity<OptionResponseDto> addOption(@PathVariable UUID pollId,
                                                       @Valid @RequestBody OptionRequestDto dto) {
        OptionResponseDto created = optionService.addOption(pollId, dto);
        return ResponseEntity
                .created(URI.create("/polls/" + pollId + "/options/" + created.id()))
                .body(created);
    }

    @GetMapping
    public ResponseEntity<List<OptionResponseDto>> getOptions(@PathVariable UUID pollId) {
        List<OptionResponseDto> options = optionService.getOptionsByPoll(pollId);
        return ResponseEntity.ok(options);
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<Void> deleteOption(@PathVariable UUID pollId, @PathVariable UUID optionId) {
        optionService.deleteOption(optionId);
        return ResponseEntity.noContent().build();
    }
}
