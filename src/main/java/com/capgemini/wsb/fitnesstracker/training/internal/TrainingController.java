package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.NewTrainingDTO;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wsb/trainings")
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;
    private final TrainingRepository trainingRepository;

    @GetMapping("/train/all")
    public List<TrainingDTO> getAllTrainings() {
        return trainingService.getTrainings().stream().map(trainingMapper::toTrainingDTO).toList();
    }

    @GetMapping("/train/{userId}")
    public List<TrainingDTO> getTrainingByUserId(@PathVariable Long userId) {
        return trainingService.getTrainingsByUserId(userId).stream().map(trainingMapper::toTrainingDTO).toList();
    }

    @GetMapping("/train/activityType")
    public List<TrainingDTO> getTrainingsForActivityType(@RequestParam("activityType") final ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType).stream().map(trainingMapper::toTrainingDTO).collect(toList());
    }

    @GetMapping("/train/completed/{endDate}")
    public List<TrainingDTO> getCompletedTrainings(@PathVariable ("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate) {
        return trainingRepository.findByEndTimeAfter(endDate).stream().map(trainingMapper::toTrainingDTO).toList();
    }

    @PostMapping("/create")
    public TrainingDTO addTraining(@RequestBody NewTrainingDTO trainingDTO) {
        final Training savedEntity = trainingService.createTraining(trainingDTO);
        return trainingMapper.toTrainingDTO(savedEntity);
    }

    @PutMapping("/update/{trainingId}")
    public TrainingDTO updateTraining(@PathVariable Long trainingId, @RequestBody NewTrainingDTO newTrainingDTO) {
        Training training = trainingMapper.toUpdate(newTrainingDTO);
        training.setId(trainingId);
        trainingRepository.save(training);
        return trainingMapper.toTrainingDTO(training);
    }

}
