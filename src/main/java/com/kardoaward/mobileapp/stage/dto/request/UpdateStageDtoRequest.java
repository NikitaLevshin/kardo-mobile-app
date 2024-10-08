package com.kardoaward.mobileapp.stage.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "Сущность для обновления этапа.")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStageDtoRequest {
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String task;
}
