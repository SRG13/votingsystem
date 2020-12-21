package com.github.srg13.votingsystem.dao;

import com.github.srg13.votingsystem.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteDao extends JpaRepository<Vote, Integer> {

    Optional<Vote> findByUserIdAndMenuId(int userId, int menuId);

    List<Vote> findByUserIdOrderByVoteDateTimeDesc(int userId);

    @Query(value = "SELECT * FROM votes v WHERE v.user_id=:userId AND FORMATDATETIME(VOTE_DATE_TIME, 'yyyy-MM-dd')=:date",
            nativeQuery = true)
    Optional<Vote> findByUserIdAndDate(int userId, LocalDate date);

}
