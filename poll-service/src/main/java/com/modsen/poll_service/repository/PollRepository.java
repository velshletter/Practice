package com.modsen.poll_service.repository;

import com.modsen.poll_service.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface PollRepository extends JpaRepository<Poll, UUID> {

    @Query("SELECT p FROM Poll p LEFT JOIN FETCH p.options WHERE p.endDate < :now")
    List<Poll> findByEndDateBeforeWithOptions(@Param("now") Instant now);

}
