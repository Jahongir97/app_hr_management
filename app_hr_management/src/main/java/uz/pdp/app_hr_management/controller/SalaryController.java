package uz.pdp.app_hr_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.app_hr_management.entity.Salary;
import uz.pdp.app_hr_management.service.SalaryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    SalaryService salaryService;

    @GetMapping("/getByWorker/{id}")
    public HttpEntity<?> getByWorker(@PathVariable UUID id){
        List<Salary>  salaryList= salaryService.getByWorker(id);
        return  ResponseEntity.ok(salaryList);
    }

    @GetMapping("/getByMonth/{month}")
    public HttpEntity<?> getByMonth(@PathVariable Integer month){
        List<Salary>  salaryList= salaryService.getByMonth(month);
        return  ResponseEntity.ok(salaryList);
    }


}
