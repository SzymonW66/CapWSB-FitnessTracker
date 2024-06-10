package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class StatisticsMapper {





        public Statistics toEntity(StatisticsDTO statisticsDTO) {
            Statistics statistics = new Statistics();
            statistics.setTotalTrainings(statisticsDTO.totalTrainings());
            statistics.setTotalDistance(statisticsDTO.totalDistance());
            statistics.setTotalCaloriesBurned(statisticsDTO.totalCaloriesBurned());
            return statistics;
        }

        public StatisticsDTO toDto(Statistics statistics) {
            return new StatisticsDTO(
                    statistics.getUser().getId(),
                    statistics.getTotalTrainings(),
                    statistics.getTotalDistance(),
                    statistics.getTotalCaloriesBurned()
            );
        }
}
