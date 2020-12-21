package com.github.srg13.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"vote_date_time", "user_id"}, name = "votes_unique_vote_date_time_user_id_idx")})
public class Vote extends AbstractBaseEntity {

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "vote_date_time", nullable = false)
    @NotNull
    private LocalDateTime voteDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    public Vote(Integer id, @NotNull LocalDateTime voteDateTime) {
        super(id);
        this.voteDateTime = voteDateTime;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", voteDateTime=" + voteDateTime +
                '}';
    }

}
