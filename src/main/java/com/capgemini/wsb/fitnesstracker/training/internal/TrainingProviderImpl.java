package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.NewTrainingDTO;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainingProviderImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final UserProvider userProvider;
    private final TrainingMapper trainingMapper;

    @Override
    public Optional<Training> getTraining(Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    @Override
    public List<Training> getTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> findAllForUserId(Long trainingId) {
        return trainingRepository.findAll().stream().filter(
                training -> training.getUser().getId().equals(trainingId)).collect(Collectors.toList());
    }

    public Training createTraining(NewTrainingDTO newTrainingDTO) {
        User user = userProvider.getUser(newTrainingDTO.getId()).get();
        Training training = trainingMapper.toEntity(newTrainingDTO);
        training.setUser(user);
        trainingRepository.save(training);
        return training;
    }

}
