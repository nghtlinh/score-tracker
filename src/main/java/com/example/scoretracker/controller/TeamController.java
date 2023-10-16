package com.example.scoretracker.controller;

import com.example.scoretracker.model.common.ResponseDto;
import com.example.scoretracker.model.dto.team.TeamReq;
import com.example.scoretracker.model.dto.team.TeamRes;
import com.example.scoretracker.service.api.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/new/createTeam")
    public ResponseDto<TeamRes> createTeam(@RequestBody TeamReq teamReq) {
        return new ResponseDto<>(teamService.createTeam(teamReq));
    }

    @GetMapping("/findTeam/{id}")
    public ResponseDto<TeamRes> findTeam(@PathVariable(name = "id") Long teamId) {
        return new ResponseDto<>(teamService.findTeamById(teamId));
    }

    @GetMapping("/getAllTeam/list")
    public ResponseDto<List<TeamRes>> findAllTeam() {
        return new ResponseDto<>(teamService.findTeamList());
    }

    @PutMapping("/edit/updateTeam/{id}")
    public ResponseDto<TeamRes> updateTeam(@PathVariable(name = "id") Long teamId,
                              @RequestBody TeamReq teamReq) {
        return new ResponseDto<>(teamService.updateTeamById(teamId, teamReq));
    }

    @DeleteMapping("/delete/deleteTeam/{id}")
    public void deleteTeam(@PathVariable(name = "id") Long teamId) {
        teamService.deleteTeamById(teamId);
    }
}
