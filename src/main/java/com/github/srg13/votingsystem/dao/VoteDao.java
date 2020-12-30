package com.github.srg13.votingsystem.dao;

import com.github.srg13.votingsystem.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteDao extends JpaRepository<Vote, Integer> {

    List<Vote> findByUserIdOrderByVoteDateTimeDesc(int userId);

    @Query(value = "SELECT v.id FROM votes v WHERE v.user_id=:userId AND FORMATDATETIME(VOTE_DATE_TIME, 'yyyy-MM-dd')=:date",
            nativeQuery = true)
    Integer findIdByUserIdAndDate(int userId, LocalDate date);
}
