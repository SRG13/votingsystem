package com.github.srg13.votingsystem.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Vote extends BaseEntity {

    private LocalDate voteTime;

}
