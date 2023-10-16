package com.example.scoretracker.service.api;

import com.example.scoretracker.common.Constant;
import com.example.scoretracker.exception.AppException;
import com.example.scoretracker.model.common.ErrorResponse;
import com.example.scoretracker.model.dto.team.TeamReq;
import com.example.scoretracker.model.dto.team.TeamRes;
import com.example.scoretracker.model.entity.RoleEntity;
import com.example.scoretracker.model.entity.TeamEntity;
import com.example.scoretracker.repository.TeamRepository;
import com.example.scoretracker.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public TeamRes createTeam(TeamReq req) {
        TeamEntity teamEntity = new TeamEntity();

        if (req.getTeamName().isEmpty()){
            throw new AppException(new ErrorResponse("Team's name cannot be empty"));
        }

        teamEntity.setTeamName(req.getTeamName());
        teamEntity.setCreatedAt(LocalDateTime.now());
        teamEntity.setUpdatedAt(LocalDateTime.now());
        teamRepository.save(teamEntity);

        return convertToTeamRes(teamEntity);
    }

    private TeamRes convertToTeamRes(TeamEntity entity) {
        TeamRes dto = new TeamRes();

        dto.setId(entity.getId());
        dto.setTeamName(entity.getTeamName());
        dto.setCreatedAt(CommonUtils.convertToString(entity.getCreatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));
        dto.setUpdatedAt(CommonUtils.convertToString(entity.getUpdatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));

        return dto;
    }
    public TeamRes findTeamById(Long id) {
        TeamEntity teamEntity = teamRepository.findById(id).
                orElseThrow(() -> new AppException(new ErrorResponse("not found team with id[%s]", id)));
        return convertToTeamRes(teamEntity);
    }

    public List<TeamRes> findTeamList() {
        List<TeamEntity> teamEntityList = teamRepository.findAll();
        return teamEntityList.stream().map(this::convertToTeamRes).toList();
    }

    public TeamRes updateTeamById(Long id, TeamReq newTeam) {
        TeamEntity updatedTeam = teamRepository.findById(id).
                orElseThrow(() -> new AppException(new ErrorResponse("not found team with id[%s]", id)));

        updatedTeam.setTeamName(newTeam.getTeamName());
        updatedTeam.setUpdatedAt(LocalDateTime.now());
        teamRepository.save(updatedTeam);

        return convertToTeamRes(updatedTeam);
    }

    public void deleteTeamById(Long id) {
        teamRepository.deleteById(id);
    }

    public void validateTeamId(Long teamId) {
        if (teamRepository.findById(teamId).isEmpty()) {
            throw new AppException(new ErrorResponse("not found team with id[%s]",teamId));
        }
    }
}
