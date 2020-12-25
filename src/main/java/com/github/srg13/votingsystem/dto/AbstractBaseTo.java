package com.github.srg13.votingsystem.dto;

import com.github.srg13.votingsystem.HasId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractBaseTo implements HasId {

    protected Integer id;

    public AbstractBaseTo(Integer id) {
        this.id = id;
    }
}
