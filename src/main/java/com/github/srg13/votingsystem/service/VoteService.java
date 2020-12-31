package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.MenuDao;
import com.github.srg13.votingsystem.dao.UserDao;
import com.github.srg13.votingsystem.dao.VoteDao;
import com.github.srg13.votingsystem.model.Vote;
import com.github.srg13.votingsystem.util.exception.IllegalRequestDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class VoteService {

    private final LocalTime TIME_THRESHOLD = LocalTime.of(11, 0);

    private final VoteDao voteRepository;

    private final MenuDao menuRepository;

    private final UserDao userRepository;

    @Autowired
    public VoteService(VoteDao voteRepository, MenuDao menuRepository, UserDao userRepository) {
        this.voteRepository = voteRepository;
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
    }

    public List<Vote> getAll(int userId) {
        return voteRepository.findByUserIdOrderByVoteDateTimeDesc(userId);
    }

    public Vote create(Vote vote, int userId, int menuId) {
        checkMenuExist(menuId, vote.getVoteDateTime().toLocalDate());

        Integer voteId = voteRepository.findIdByUserIdAndDate(userId, vote.getVoteDateTime().toLocalDate());
        if (voteId != null) {
            checkTimeForReVote(vote.getVoteDateTime().toLocalTime());
            vote.setId(voteId);
        }

        vote.setMenu(menuRepository.getOne(menuId));
        vote.setUser(userRepository.getOne(userId));

        return voteRepository.save(vote);
    }

    private void checkMenuExist(int menuId, LocalDate date) {
        if (!menuRepository.existsByIdAndDate(menuId, date)) {
            throw new IllegalRequestDataException("Menu with id =" + menuId + " is not relevant.");
        }
    }

    private void checkTimeForReVote(LocalTime voteTime) {
        if (voteTime.isAfter(TIME_THRESHOLD)) {
            throw new IllegalRequestDataException("Too late to change vote.");
        }
    }
}
