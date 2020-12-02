package com.github.srg13.votingsystem.dao;

import com.github.srg13.votingsystem.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface VoteDao extends JpaRepository<Vote, Integer> {

    Vote findByUserIdAndVoteDate(int userid, LocalDate voteDate);

}
