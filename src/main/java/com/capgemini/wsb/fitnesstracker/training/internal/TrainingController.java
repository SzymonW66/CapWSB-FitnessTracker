package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.NewTrainingDTO;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDTO;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wsb/trainings")
public class TrainingController {

    private final TrainingProviderImpl trainingProvider;
    private final TrainingMapper trainingMapper;
    private final TrainingRepository trainingRepository;

    @GetMapping("/all")
    public ResponseEntity<List<TrainingDTO>> getAllTrainings() {
        List<TrainingDTO> trainings = trainingProvider.getTrainings().stream()
                .map(trainingMapper::toTrainingDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingDTO>> getTrainingByUserId(@PathVariable Long userId) {
        List<TrainingDTO> trainings = trainingProvider.findAllForUserId(userId).stream()
                .map(trainingMapper::toTrainingDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/activityType")
    public ResponseEntity<List<TrainingDTO>> getTrainingsForActivityType(@RequestParam("activityType") final ActivityType activityType) {
        List<TrainingDTO> trainings = trainingRepository.findByActivityType(activityType).stream()
                .map(trainingMapper::toTrainingDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/trainingId/{trainingId}")
    public ResponseEntity<List<TrainingDTO>> getTrainingsByTrainingId(@PathVariable ("trainingId") Long trainingId) {
        List<TrainingDTO> trainings = trainingRepository.findById(trainingId).stream()
                .map(trainingMapper::toTrainingDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/completed/{endDate}")
    public ResponseEntity<List<TrainingDTO>> getCompletedTrainings(@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<TrainingDTO> trainings = trainingRepository.findByEndTimeAfter(endDate).stream()
                .map(trainingMapper::toTrainingDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(trainings);
    }


    @PostMapping("/create")
    public ResponseEntity<TrainingDTO> addTraining(@RequestBody NewTrainingDTO trainingDTO) {
        Training savedEntity = trainingProvider.createTraining(trainingDTO);
        TrainingDTO responseDTO = trainingMapper.toTrainingDTO(savedEntity);
        URI location = URI.create(String.format("/wsb/trainings/train/%d", responseDTO.getId()));
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingDTO> updateTraining(@PathVariable Long trainingId, @RequestBody NewTrainingDTO newTrainingDTO) {
        Training training = trainingMapper.toUpdate(newTrainingDTO);
        training.setId(trainingId);
        trainingRepository.save(training);
        TrainingDTO responseDTO = trainingMapper.toTrainingDTO(training);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/distance/{distance}")
    public ResponseEntity <List<TrainingDTO>> getTrainingsByDistance(@PathVariable ("distance") double distance){
        List<TrainingDTO> findAllByDistance = trainingRepository
                .findAll().stream().filter(training -> training.getDistance() <= distance)
                .map(trainingMapper::toTrainingDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(findAllByDistance);
    }


}
