package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.VoteDao;
import com.github.srg13.votingsystem.exception.IllegalRequestDataException;
import com.github.srg13.votingsystem.model.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.time.LocalDate;

import static com.github.srg13.votingsystem.util.RestaurantTestData.RESTAURANT3_ID;
import static com.github.srg13.votingsystem.util.UserTestData.USER_ID_NOT_VOTED;
import static com.github.srg13.votingsystem.util.VoteTestData.VOTES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class VoteServiceTest {

    @Autowired
    private VoteService service;

    @Autowired
    private VoteDao repository;

    @Test
    void getAll() {
        assertThat(service.getAll(USER_ID_NOT_VOTED)).usingElementComparatorIgnoringFields("restaurant", "user").isEqualTo(VOTES);
    }

    @Test
    void create() {
        Vote created = service.create(new Vote(), USER_ID_NOT_VOTED, RESTAURANT3_ID);
        int newId = created.getId();
        Vote newVote = new Vote();
        newVote.setId(newId);
        newVote.setVoteDateTime(created.getVoteDateTime());

        assertThat(repository.findByUserIdAndDate(USER_ID_NOT_VOTED, created.getVoteDateTime().toLocalDate()).get())
                .usingRecursiveComparison().ignoringFields("restaurant", "user").isEqualTo(newVote);
    }

    @Test
    void voteTwiceAfterTimeThreshold() {
        service.create(new Vote(), USER_ID_NOT_VOTED, RESTAURANT3_ID);
        Vote vote = new Vote();
        vote.setVoteDateTime(LocalDate.now().atTime(13, 0));
        assertThrows(IllegalRequestDataException.class, () -> service.create(vote, USER_ID_NOT_VOTED, RESTAURANT3_ID));
    }

    @Test
    void voteTwiceBeforeTimeThreshold() {
        service.create(new Vote(), USER_ID_NOT_VOTED, RESTAURANT3_ID);
        Vote vote = new Vote();
        vote.setVoteDateTime(LocalDate.now().atTime(10, 0));
        service.create(vote, USER_ID_NOT_VOTED, RESTAURANT3_ID);

        assertThat(repository.findByUserIdAndDate(USER_ID_NOT_VOTED, LocalDate.now()).get())
                .usingRecursiveComparison().ignoringFields("restaurant", "user").isEqualTo(vote);
    }
}