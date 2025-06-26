package com.modsen.poll_service.repository;

import com.modsen.poll_service.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

    boolean existsByPollIdAndUserId(UUID pollId, UUID userId);

    List<Vote> findAllByPollId(UUID pollId);

    long countByPollIdAndOptionId(UUID pollId, UUID optionId);
}
