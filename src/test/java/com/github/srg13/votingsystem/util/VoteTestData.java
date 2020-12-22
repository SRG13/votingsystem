package com.github.srg13.votingsystem.util;

import com.github.srg13.votingsystem.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class VoteTestData {

    public static final int VOTE1_ID = 100054;

    public static final LocalDateTime VOTE_TIME = LocalDate.now().atTime(8, 0);

    public static final Vote VOTE = new Vote(VOTE1_ID, LocalDateTime.of(2020, 11, 11, 3, 13, 13));

    public static final List<Vote> VOTES = List.of(new Vote(100055, LocalDateTime.of(2020, 11, 11, 1, 0, 3)),
            new Vote(100052, LocalDateTime.of(2020, 11, 7, 18, 13, 55)),
            new Vote(100050, LocalDateTime.of(2020, 9, 5, 21, 45, 0)));

    public static final String VOTE_JSON = "{\"id\":" + VOTE1_ID + ",\"voteDateTime\":\"2020-11-11T03:13:33\"}";

    public static final String VOTES_JSON = "[{\"id\":100055,\"voteDateTime\":\"2020-11-11T01:00:03\"},{\"id\":100052,\"voteDateTime\":\"2020-11-07T18:13:55\"},{\"id\":100050,\"voteDateTime\":\"2020-09-05T21:45:00\"}]";

    public static Vote getNew() {
        return new Vote(null, VOTE_TIME);
    }
}
