package uz.pdp.app_hr_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.app_hr_management.entity.Salary;
import uz.pdp.app_hr_management.entity.Tuniket;
import uz.pdp.app_hr_management.entity.User;
import uz.pdp.app_hr_management.payload.AboutWorkerDto;
import uz.pdp.app_hr_management.repository.SalaryRepository;
import uz.pdp.app_hr_management.repository.TuniketRepository;
import uz.pdp.app_hr_management.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class WorkerService {
    @Autowired
    SalaryRepository salaryRepository;
    @Autowired
    TuniketRepository tuniketRepository;
    @Autowired
    UserRepository userRepository;


    public List<Salary> getInfoWorkerSalary(UUID id, AboutWorkerDto aboutWorkerDto) {
        return salaryRepository.findByUserIdAndPeriod(id,aboutWorkerDto.getFromDate(),aboutWorkerDto.getToDate());
    }

    public List<Tuniket> getInfoWorkerTuniket(UUID id, AboutWorkerDto aboutWorkerDto) {
        return tuniketRepository.findByUserIdAndPeriod(id,aboutWorkerDto.getFromDate(),aboutWorkerDto.getToDate());
    }

    public List<User> getAllWorkers() {
        User userInSystem = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userInSystem.getRoles().contains(1)||userInSystem.getRoles().contains(2)){
            return userRepository.findAll();
        }
        return null;
    }
}
