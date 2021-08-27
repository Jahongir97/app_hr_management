package uz.pdp.app_hr_management.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
public class AboutWorkerDto {

    @NotNull
    private Date fromDate;

    @NotNull
    private Date toDate;
}
