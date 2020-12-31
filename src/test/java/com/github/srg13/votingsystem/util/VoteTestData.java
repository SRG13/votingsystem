package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

public class VoteTestData {

    public static final List<Vote> VOTES_OF_ADMIN =
            List.of(new Vote(100048, LocalDateTime.of(2020, 9, 5, 13, 0, 14)),
                    new Vote(100053, LocalDateTime.of(2020, 11, 11, 19, 55, 43)),
                    new Vote(100056, LocalDateTime.now()));

    public static final List<Vote> VOTES_OF_USER1 =
            List.of(new Vote(100049, LocalDateTime.of(2020, 9, 5, 8, 10, 3)),
                    new Vote(100051, LocalDateTime.of(2020, 11, 7, 10, 11, 11)),
                    new Vote(100054, LocalDateTime.of(2020, 11, 11, 3, 13, 33)),
                    new Vote(100057, LocalDateTime.now()));

    public static final List<Vote> VOTES_OF_USER2 =
            List.of(new Vote(100055, LocalDateTime.of(2020, 11, 11, 1, 0, 3)),
                    new Vote(100052, LocalDateTime.of(2020, 11, 7, 18, 13, 55)),
                    new Vote(100050, LocalDateTime.of(2020, 9, 5, 21, 45, 0)));
}
