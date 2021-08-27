package uz.pdp.app_hr_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_hr_management.entity.Task;
import uz.pdp.app_hr_management.payload.ApiResponse;
import uz.pdp.app_hr_management.payload.TaskDto;
import uz.pdp.app_hr_management.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("/add")
    public HttpEntity<?> addTask(@RequestBody TaskDto taskDto){
        ApiResponse apiResponse=taskService.addTask(taskDto);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail( @RequestParam String email,@RequestParam Integer taskId){
        ApiResponse apiResponse=taskService.verifyEmail(email, taskId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);

    }

    @GetMapping("/end/{taskId}")
    public HttpEntity<?> endTask(@PathVariable Integer taskId){
        ApiResponse apiResponse=taskService.endTask(taskId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/get")
    public HttpEntity<?> getTask(){
        List<Task> taskList = taskService.getTask();
        return  ResponseEntity.ok(taskList);
    }

    @GetMapping("/get/done")
    public HttpEntity<?> getTaskDone(){
        List<Task> taskList = taskService.getTaskDone();
        return  ResponseEntity.ok(taskList);
    }

    @GetMapping("/get/over")
    public HttpEntity<?> getTaskOver(){
        List<Task> taskList = taskService.getTaskOver();
        return  ResponseEntity.ok(taskList);
    }


}
