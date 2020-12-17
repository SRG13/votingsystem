package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.exception.IllegalRequestDataException;
import com.github.srg13.votingsystem.model.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.time.LocalDate;

import static com.github.srg13.votingsystem.util.MenuTestData.MENU1_ID;
import static com.github.srg13.votingsystem.util.MenuTestData.OLD_MENU_ID;
import static com.github.srg13.votingsystem.util.UserTestData.USER1_ID;
import static com.github.srg13.votingsystem.util.UserTestData.USER_ID_NOT_VOTED;
import static com.github.srg13.votingsystem.util.VoteTestData.VOTE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class VoteServiceTest {

    @Autowired
    private VoteService service;

    @Test
    void get() {
        Vote vote = service.get(USER1_ID, OLD_MENU_ID);
        assertThat(vote).usingRecursiveComparison().ignoringFields("menu", "voteDateTime", "user").isEqualTo(VOTE);
    }

    @Test
    void getNotExist() {
        assertThrows(IllegalRequestDataException.class, () -> service.get(USER1_ID, -1));
    }

    @Test
    void create() {
        Vote created = service.create(new Vote(), USER_ID_NOT_VOTED, MENU1_ID);
        int newId = created.getId();
        Vote newVote = new Vote();
        newVote.setId(newId);
        newVote.setVoteDateTime(created.getVoteDateTime());

        assertThat(service.get(USER_ID_NOT_VOTED, MENU1_ID))
                .usingRecursiveComparison().ignoringFields("menu", "user").isEqualTo(newVote);
    }

    @Test
    void voteTwiceAfterTimeThreshold() {
        service.create(new Vote(), USER_ID_NOT_VOTED, MENU1_ID);
        Vote vote = new Vote();
        vote.setVoteDateTime(LocalDate.now().atTime(13, 0));
        assertThrows(IllegalRequestDataException.class, () -> service.create(vote, USER_ID_NOT_VOTED, MENU1_ID));
    }

    @Test
    void voteTwiceBeforeTimeThreshold() {
        service.create(new Vote(), USER_ID_NOT_VOTED, MENU1_ID);
        Vote vote = new Vote();
        vote.setVoteDateTime(LocalDate.now().atTime(10, 0));
        service.create(vote, USER_ID_NOT_VOTED, MENU1_ID);

        assertThat(service.get(USER_ID_NOT_VOTED, MENU1_ID))
                .usingRecursiveComparison().ignoringFields("menu", "user").isEqualTo(vote);
    }

    @Test
    void voteForOldMenu() {
        assertThrows(IllegalRequestDataException.class, () -> service.create(new Vote(), 100000, OLD_MENU_ID));
    }
}