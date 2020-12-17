package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

public class VoteTestData {
    public static final Vote VOTE = new Vote(100054, LocalDateTime.of(2020, 11, 11, 3, 13, 13));

    public static final List<Vote> VOTES = List.of(new Vote(100055, LocalDateTime.of(2020, 11, 11, 1, 0, 3)),
            new Vote(100052, LocalDateTime.of(2020, 11, 7, 18, 13, 55)),
            new Vote(100050, LocalDateTime.of(2020, 9, 5, 21, 45, 0)));
}
