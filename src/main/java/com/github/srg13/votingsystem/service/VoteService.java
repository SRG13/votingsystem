package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.MenuDao;
import com.github.srg13.votingsystem.dao.RestaurantDao;
import com.github.srg13.votingsystem.dao.UserDao;
import com.github.srg13.votingsystem.dao.VoteDao;
import com.github.srg13.votingsystem.util.exception.IllegalRequestDataException;
import com.github.srg13.votingsystem.util.exception.NotFoundException;
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

    public List<Vote> getAll(int userId) {
        return voteRepository.findByUserIdOrderByVoteDateTimeDesc(userId);
    }

    public Vote create(Vote vote, int userId, int menuId) {
        checkRestaurantExist(menuId);
        if (isVoted(userId)) {
            checkTimeForReVote(vote.getVoteDateTime().toLocalTime());
            vote.setId(voteRepository.findByUserIdAndDate(userId, LocalDate.now()).get().getId());
        }

        vote.setRestaurant(restaurantRepository.getOne(menuId));
        vote.setUser(userRepository.getOne(userId));

        return voteRepository.save(vote);
    }

//    private void checkRestaurantExist(int restaurantId) {
//        restaurantRepository.findById(restaurantId).orElseThrow(
//                () -> new NotFoundException("Restaurant with id=" + restaurantId + " not found.")
//        );
//    }

    private void checkMenu(int menuId) {
        menuRepository.findByIdAndDate();
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
