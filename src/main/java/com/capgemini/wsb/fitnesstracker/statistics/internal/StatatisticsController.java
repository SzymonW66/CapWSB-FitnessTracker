package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.user.api.UserDTO;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class StatatisticsController {

    private final StatisticService statisticService;
    private final StatisticsMapper statisticsMapper;
    private final UserMapper userMapper;

    @PostMapping("/createStats")
    public ResponseEntity<StatisticsDTO> addStatistics(@RequestBody StatisticsDTO statisticsDTO) {
        StatisticsDTO createdStatistics = statisticService.addStatistics(statisticsDTO);
        return ResponseEntity.ok(createdStatistics);
    }

    @GetMapping("/getOlderThan/{bir")
    public ResponseEntity<List<UserDTO>> getStatisticForOlder(@RequestParam LocalDate birthdate){
        List<UserDTO> statisticsForOlderThan = statisticService.getUsersOlderThanProvided(birthdate);
        statisticsForOlderThan.stream()
                .map(userMapper::toEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(statisticsForOlderThan);
    }


}
