package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.MenuDao;
import com.github.srg13.votingsystem.dao.UserDao;
import com.github.srg13.votingsystem.dao.VoteDao;
import com.github.srg13.votingsystem.exception.IllegalRequestDataException;
import com.github.srg13.votingsystem.exception.NotFoundException;
import com.github.srg13.votingsystem.model.Menu;
import com.github.srg13.votingsystem.model.Vote;
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

    public Vote get(int userId, int menuId) {
        return voteRepository.findByUserIdAndMenuId(userId, menuId)
                .orElseThrow(() -> new IllegalRequestDataException
                        ("Vote of user=" + userId + " for menu=" + menuId + " doesn't exist"));
    }

    public List<Vote> getAll(int userId) {
        return voteRepository.findByUserIdOrderByVoteDateTimeDesc(userId);
    }

    public Vote create(Vote vote, int userId, int menuId) {
        checkMenuDate(menuId);
        if (isVoted(userId)) {
            checkTimeForReVote(vote.getVoteDateTime().toLocalTime());
            vote.setId(voteRepository.findByUserIdAndDate(userId, LocalDate.now()).get().getId());
        }

        vote.setMenu(menuRepository.getOne(menuId));
        vote.setUser(userRepository.getOne(userId));


        return voteRepository.save(vote);
    }

    private void checkMenuDate(int menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu with id=" + menuId + " not found."));

        if (!(menu.getDate().equals(LocalDate.now()))) {
            throw new IllegalRequestDataException("Menu date is not equal to the current day");
        }
    }

    private void checkTimeForReVote(LocalTime voteTime) {
        if (voteTime.isAfter(TIME_THRESHOLD)) {
            throw new IllegalRequestDataException("Too late to change your vote.");
        }
    }

    private boolean isVoted(int userId) {
        return voteRepository.findByUserIdAndDate(userId, LocalDate.now()).isPresent();
    }

}
