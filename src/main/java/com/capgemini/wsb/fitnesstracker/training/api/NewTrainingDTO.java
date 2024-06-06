package com.capgemini.wsb.fitnesstracker.training.api;



import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.UserDTO;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewTrainingDTO {
    private Long Id;
    private UserDTO userDto;
    private Date start;
    private Date end;
    private ActivityType activityType;
    private double distance;
    private double avgSpeed;
}
