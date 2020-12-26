package com.github.srg13.votingsystem.service;

import com.github.srg13.votingsystem.dao.RestaurantDao;
import com.github.srg13.votingsystem.dao.UserDao;
import com.github.srg13.votingsystem.dao.VoteDao;
import com.github.srg13.votingsystem.exception.IllegalRequestDataException;
import com.github.srg13.votingsystem.exception.NotFoundException;
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

    private final RestaurantDao restaurantRepository;

    private final UserDao userRepository;

    @Autowired
    public VoteService(VoteDao voteRepository, RestaurantDao restaurantRepository, UserDao userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public List<Vote> getAll(int userId) {
        return voteRepository.findByUserIdOrderByVoteDateTimeDesc(userId);
    }

    public Vote create(Vote vote, int userId, int restaurantId) {
        checkRestaurantExist(restaurantId);
        if (isVoted(userId)) {
            checkTimeForReVote(vote.getVoteDateTime().toLocalTime());
            vote.setId(voteRepository.findByUserIdAndDate(userId, LocalDate.now()).get().getId());
        }

        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        vote.setUser(userRepository.getOne(userId));

        return voteRepository.save(vote);
    }

    private void checkRestaurantExist(int restaurantId) {
        restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new NotFoundException("Restaurant with id=" + restaurantId + " not found.")
        );
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
