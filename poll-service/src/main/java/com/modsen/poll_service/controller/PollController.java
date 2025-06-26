package com.modsen.poll_service.controller;

import com.modsen.poll_service.dto.PollRequestDto;
import com.modsen.poll_service.dto.PollResponseDto;
import com.modsen.poll_service.service.PollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/polls")
@RequiredArgsConstructor
public class PollController {

    private final PollService pollService;

    @GetMapping
    public ResponseEntity<List<PollResponseDto>> getAllPolls() {
        return ResponseEntity.ok(pollService.getPolls());
    }

    @GetMapping("/{pollId}")
    public ResponseEntity<PollResponseDto> getPollById(@PathVariable UUID pollId) {
        return ResponseEntity.ok(pollService.getPollById(pollId));
    }

    @PostMapping
    public ResponseEntity<PollResponseDto> createPoll(@Valid @RequestBody PollRequestDto request,
                                                   Principal principal) {
        UUID userId = getUserIdFromPrincipal(principal);

        PollResponseDto created = pollService.createPoll(request, userId);

        return ResponseEntity.created(URI.create("/polls/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{pollId}")
    public ResponseEntity<PollResponseDto> updatePoll(@PathVariable UUID pollId,
                                                   @Valid @RequestBody PollRequestDto request) {

        PollResponseDto saved = pollService.updatePoll(pollId, request);

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{pollId}")
    public ResponseEntity<Void> deletePoll(@PathVariable UUID pollId) {
        pollService.deletePoll(pollId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{pollId}/anonymity")
    public ResponseEntity<Void> setAnonymity(@PathVariable UUID pollId,
                                             @RequestParam boolean anonymous) {
        pollService.setPollAnonymity(pollId, anonymous);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{pollId}/dates")
    public ResponseEntity<Void> updateDates(@PathVariable UUID pollId, @RequestParam Instant start, @RequestParam Instant end) {
        pollService.setPollDates(pollId, start, end);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{pollId}/finish")
    public ResponseEntity<Void> finishPoll(@PathVariable UUID pollId) {
        pollService.finishPoll(pollId);
        return ResponseEntity.noContent().build();
    }

    private UUID getUserIdFromPrincipal(Principal principal) {
        return UUID.fromString(principal.getName());
    }
}
