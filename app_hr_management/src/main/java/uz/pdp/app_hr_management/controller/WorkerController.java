package uz.pdp.app_hr_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_hr_management.entity.Salary;
import uz.pdp.app_hr_management.entity.Tuniket;
import uz.pdp.app_hr_management.entity.User;
import uz.pdp.app_hr_management.payload.AboutWorkerDto;
import uz.pdp.app_hr_management.service.WorkerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;

    @GetMapping("/getAll")
    public HttpEntity<?> getAllWorkers(){
        List<User> userList=workerService.getAllWorkers();
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/infoSalary/{id}")
    public HttpEntity<?> getInfoWorkerSalary(@PathVariable UUID id, @RequestBody AboutWorkerDto aboutWorkerDto){
        List<Salary> salaryList=workerService.getInfoWorkerSalary(id,aboutWorkerDto);
        return ResponseEntity.ok(salaryList);
    }

    @PostMapping("/infoTuniket/{id}")
    public HttpEntity<?> getInfoWorkerTuniket(@PathVariable UUID id, @RequestBody AboutWorkerDto aboutWorkerDto){
        List<Tuniket> tunikets=workerService.getInfoWorkerTuniket(id,aboutWorkerDto);
        return ResponseEntity.ok(tunikets);
    }


}
