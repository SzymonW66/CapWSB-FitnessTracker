package com.capgemini.wsb.fitnesstracker.training.internal;


import com.capgemini.wsb.fitnesstracker.training.api.NewTrainingDTO;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDTO;


import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final UserMapper userMapper;
    private final UserProvider userProvider;

    TrainingDTO toTrainingDTO(Training training) {
        return new TrainingDTO(
                training.getId(),
                userMapper.toDto(training.getUser()),
                training.getEndTime(),
                training.getStartTime(),
                training.getDistance(),
                training.getAverageSpeed(),
                training.getActivityType()
        );
    }

    Training toEntity(NewTrainingDTO trainingTO) {
        return new Training(
                trainingTO.getStart(),
                trainingTO.getEnd(),
                trainingTO.getActivityType(),
                trainingTO.getDostance(),
                trainingTO.getAvgSpeed());
    }

    public Training toUpdate(NewTrainingDTO newTrainingDTO) {
        return new Training(
                newTrainingDTO.getId(),
                userMapper.saveEntity(userMapper.toDto(userProvider.getUser(newTrainingDTO.getId()).get())),
                newTrainingDTO.getStart(),
                newTrainingDTO.getEnd(),
                newTrainingDTO.getActivityType(),
                newTrainingDTO.getDostance(),
                newTrainingDTO.getAvgSpeed()
                );
    }
}

