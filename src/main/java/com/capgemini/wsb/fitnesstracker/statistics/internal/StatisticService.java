package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDTO;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticService {

    private final StatisticsRepository statisticsRepository;
    private final UserProvider userProvider;
    private final StatisticsMapper statisticsMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Transactional
    public StatisticsDTO addStatistics(StatisticsDTO statisticsDTO) {
        User user = userProvider.getUser(statisticsDTO.userId())
                .orElseThrow(() -> {
                    return new UserNotFoundException("User not found");
                });

        Statistics statistics = statisticsMapper.toEntity(statisticsDTO);
        statistics.setUser(user);

        Statistics savedStatistics = statisticsRepository.save(statistics);
        return statisticsMapper.toDto(savedStatistics);
    }

    @Transactional
    public List<UserDTO> getUsersOlderThanProvided(LocalDate maxBirthdate) {
        List<User> allUsers = userRepository.findAll();
        List<User> olderUsers = allUsers.stream()
                .filter(user -> user.getBirthdate().isBefore(maxBirthdate))
                .collect(Collectors.toList());
        return olderUsers.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}


