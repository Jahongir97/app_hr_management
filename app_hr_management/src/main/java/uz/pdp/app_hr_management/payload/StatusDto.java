package uz.pdp.app_hr_management.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StatusDto {
    @NotNull
    private Integer password;
}

