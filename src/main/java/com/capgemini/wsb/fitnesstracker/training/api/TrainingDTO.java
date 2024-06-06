package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.UserDTO;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDTO {
    private Long id;
    private UserDTO user;
    private Date endTime;
    private Date startTime;
    private double distance;
    private double averageSpeed;
    private ActivityType activityType;
}
