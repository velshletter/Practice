package com.modsen.poll_service.repository;

import com.modsen.poll_service.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OptionRepository extends JpaRepository<Option, UUID> {
    List<Option> findByPollId(UUID pollId);
}