package com.github.srg13.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"vote_date", "user_id"}, name = "votes_unique_vote_date_user_id_idx")})
public class Vote extends BaseEntity {

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name ="vote_date", nullable = false, columnDefinition = "DATE")
    @NotNull
    private LocalDate voteDate = LocalDate.now();

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", menu=" + menu +
                ", user=" + user +
                ", voteDate=" + voteDate +
                '}';
    }

}
