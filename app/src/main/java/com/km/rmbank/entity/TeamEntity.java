package com.km.rmbank.entity;

import com.km.rmbank.dto.UserDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class TeamEntity {
    private String teamName;
    private List<UserDto> userEntities;

    public TeamEntity(String teamName, List<UserDto> userEntities) {
        this.teamName = teamName;
        this.userEntities = userEntities;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<UserDto> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<UserDto> userEntities) {
        this.userEntities = userEntities;
    }
}
