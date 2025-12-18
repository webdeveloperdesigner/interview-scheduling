package com.app.interview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeeklyAvailabilityRequest {
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private int capacity;
}
