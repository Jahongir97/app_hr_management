package uz.pdp.app_hr_management.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class TuniketDto {
    @NotNull
    private boolean comeOrOut;

    @NotNull
    private UUID userId;

}

