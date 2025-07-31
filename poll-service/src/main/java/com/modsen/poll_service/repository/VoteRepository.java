package com.modsen.poll_service.repository;

import com.modsen.poll_service.entity.Vote;
import com.modsen.poll_service.projection.VoteCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

    boolean existsByPollIdAndUserId(UUID pollId, UUID userId);

    List<Vote> findAllByPollId(UUID pollId);

    long countByPollIdAndOptionId(UUID pollId, UUID optionId);

    @Query("""
                SELECT v.option AS option, COUNT(v) AS voteCount
                FROM Vote v
                WHERE v.option.poll.id = :pollId
                GROUP BY v.option
            """)
    List<VoteCountProjection> countVotesGroupedByOption(@Param("pollId") UUID pollId);

}
