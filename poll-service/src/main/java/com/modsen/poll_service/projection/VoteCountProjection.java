package com.modsen.poll_service.projection;

import com.modsen.poll_service.entity.Option;

public interface VoteCountProjection {
    Option getOption();
    Long getVoteCount();
}