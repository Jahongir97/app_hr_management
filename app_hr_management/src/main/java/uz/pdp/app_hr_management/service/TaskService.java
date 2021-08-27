package uz.pdp.app_hr_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.app_hr_management.entity.Task;
import uz.pdp.app_hr_management.entity.User;
import uz.pdp.app_hr_management.entity.enums.RoleName;
import uz.pdp.app_hr_management.entity.enums.StatusTask;
import uz.pdp.app_hr_management.payload.ApiResponse;
import uz.pdp.app_hr_management.payload.TaskDto;
import uz.pdp.app_hr_management.repository.TaskRepository;
import uz.pdp.app_hr_management.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    JavaMailSender javaMailSender;

    public ApiResponse addTask(TaskDto taskDto) {
        Optional<User> optionalUser = userRepository.findById(taskDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("Bunday hodim yo'q", false);
        User userInSystem = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userInSystem.getRoles().contains(RoleName.DIRECTOR)){
            Task task=new Task();
            task.setStatusTask(StatusTask.NEW);
            task.setDescription(taskDto.getDescription());
            task.setEndTime(taskDto.getEndTime());
            task.setUser(optionalUser.get());
            task.setName(taskDto.getName());

            taskRepository.save(task);

            //emailga xabar yuborish
            sendEmail(optionalUser.get().getEmail(),task.getId());

            return new ApiResponse("Vazifa hodimga yuborildi.",true);

        }else if (userInSystem.getRoles().contains(RoleName.MANAGER)){
            if (optionalUser.get().getRoles().contains(2)) {
                return new ApiResponse("Kechirasiz siz manager vazifa bera olmaysiz",false);
            }
            Task task=new Task();
            task.setStatusTask(StatusTask.NEW);
            task.setDescription(taskDto.getDescription());
            task.setEndTime(taskDto.getEndTime());
            task.setUser(optionalUser.get());
            task.setName(taskDto.getName());

            taskRepository.save(task);

            //emailga xabar yuborish
            sendEmail(optionalUser.get().getEmail(),task.getId());

            return new ApiResponse("Vazifa hodimga yuborildi.",true);
        }
        return new ApiResponse("Siz hodimlarga vazifa berolmaysiz, chunki o'ziz oddiy ishchisiz",false);
    }

    public boolean sendEmail(String senderEmail,Integer taskId){
        try {
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom("HPManagement.com");
            mailMessage.setTo(senderEmail);
            mailMessage.setSubject("Tasdiqlash kodi");
            mailMessage.setText("Ushbu link orqali sizga yuborilgan vazifani jarayonda ekanligini tasdiqlang http://localhost:8080/api/task/verifyEmail?email="+senderEmail+"&taskId="+taskId);
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception exception){
            return false;
        }

    }

    public ApiResponse verifyEmail(String email, Integer taskId) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent())
            return new ApiResponse("Bunday hodim yo'q.",false);

        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(taskId, optionalUser.get().getId());
        if (!optionalTask.isPresent()){
            return new ApiResponse("Bunday vaizifa yo'q.",false);
        }
        Task task = optionalTask.get();
        task.setStatusTask(StatusTask.IN_PROGRESS);
        taskRepository.save(task);
        return new ApiResponse("Vazifa bajarish jarayonida",true);
    }

    public ApiResponse endTask(Integer taskId) {
        User userInSystem =(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(taskId,userInSystem.getId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Sizda bunday vaizifa yo'q",false);
        Task task = optionalTask.get();
        task.setStatusTask(StatusTask.DONE);
        taskRepository.save(task);

        Optional<User> optionalUser = userRepository.findById(task.getCreatedBy());
        sendEmailAboutTaskStatus(optionalUser.get().getEmail(),task.getId());

        return new ApiResponse("Vazifa bajarish yakunlandi",true);
    }

    public boolean sendEmailAboutTaskStatus(String senderEmail,Integer taskId){
        try {
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom("HPManagement.com");
            mailMessage.setTo(senderEmail);
            mailMessage.setSubject("Vazifa holati haqida");
            mailMessage.setText(taskId+" id vaziafa bajarilgan");
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception exception){
            return false;
        }

    }

    public List<Task> getTask() {
        User userInSystem = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return taskRepository.findAllByUserId(userInSystem.getId());
    }

    public List<Task> getTaskDone() {
        return taskRepository.findAllByStatusTask(StatusTask.DONE);
    }

    public List<Task> getTaskOver() {
        return taskRepository.findAllByDate();
    }
}
