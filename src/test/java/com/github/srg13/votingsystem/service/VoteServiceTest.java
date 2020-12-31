package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.VoteDao;
import com.github.srg13.votingsystem.model.Vote;
import com.github.srg13.votingsystem.util.exception.IllegalRequestDataException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.time.LocalDate;

import static com.github.srg13.votingsystem.util.MenuTestData.MENU1_ID;
import static com.github.srg13.votingsystem.util.MenuTestData.MENU3_ID;
import static com.github.srg13.votingsystem.util.UserTestData.USER2_ID;
import static com.github.srg13.votingsystem.util.VoteTestData.VOTES_OF_USER2;
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
        assertThat(service.getAll(USER2_ID)).usingElementComparatorIgnoringFields("menu", "user").isEqualTo(VOTES_OF_USER2);
    }

    @Test
    void create() {
        Vote created = service.create(new Vote(), USER2_ID, MENU1_ID);
        int newId = created.getId();
        Vote newVote = new Vote();
        newVote.setId(newId);
        newVote.setVoteDateTime(created.getVoteDateTime());

        assertThat(repository.findById(newId).get())
                .usingRecursiveComparison().ignoringFields("menu", "user", "voteDateTime").isEqualTo(newVote);
    }

    @Test
    void voteTwiceAfterTimeThreshold() {
        service.create(new Vote(), USER2_ID, MENU1_ID);
        Vote vote = new Vote();
        vote.setVoteDateTime(LocalDate.now().atTime(13, 0));

        assertThrows(IllegalRequestDataException.class, () -> service.create(vote, USER2_ID, MENU1_ID));
    }

    @Test
    void voteTwiceBeforeTimeThreshold() {
        service.create(new Vote(), USER2_ID, MENU1_ID);
        Vote vote = new Vote();
        vote.setVoteDateTime(LocalDate.now().atTime(10, 0));
        Vote created = service.create(vote, USER2_ID, MENU1_ID);

        assertThat(repository.findById(created.getId()).get())
                .usingRecursiveComparison().ignoringFields("menu", "user", "voteDateTime").isEqualTo(vote);
    }

    @Test
    void voteForOldMenu() {
        assertThrows(IllegalRequestDataException.class, () -> service.create(new Vote(), USER2_ID, MENU3_ID));
    }
}