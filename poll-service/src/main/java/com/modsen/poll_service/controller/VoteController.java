package com.modsen.poll_service.controller;

import com.modsen.poll_service.dto.VoteResponseDto;
import com.modsen.poll_service.service.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/polls/{pollId}/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> castVote(@PathVariable UUID pollId, @RequestParam UUID optionId, Principal principal, HttpServletRequest request) {
        UUID userId = UUID.fromString(principal.getName());
        String ipAddress = extractClientIp(request);

        voteService.castVote(pollId, optionId, userId, ipAddress);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<Boolean> hasVoted(@PathVariable UUID pollId, Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        boolean hasVoted = voteService.hasUserVoted(pollId, userId);

        return ResponseEntity.ok(hasVoted);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getVoteCount(@PathVariable UUID pollId, @RequestParam UUID optionId) {
        long count = voteService.getVoteCountForOption(pollId, optionId);
        return ResponseEntity.ok(count);
    }

    @GetMapping
    public ResponseEntity<List<VoteResponseDto>> getAllVotesForPoll(@PathVariable UUID pollId) {
        return ResponseEntity.ok(voteService.getVotesForPoll(pollId));
    }

    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        return xfHeader != null ? xfHeader.split(",")[0] : request.getRemoteAddr();
    }
}
