package com.kardoaward.mobileapp.events.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventDtoRequest {
    private String name;
    private String description;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;
}
