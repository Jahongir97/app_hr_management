package uz.pdp.app_hr_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app_hr_management.entity.Salary;
import uz.pdp.app_hr_management.repository.SalaryRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SalaryService {
    @Autowired
    SalaryRepository salaryRepository;


    public List<Salary> getByWorker(UUID id) {
        return salaryRepository.findAllByUserId(id);
    }

    public List<Salary> getByMonth(Integer month) {
        Date fromDate=new Date();
        fromDate.setMonth(month);
        fromDate.setDate(1);
        Date toDate=new Date();
        toDate.setMonth(month);
        toDate.setDate(31);
        return salaryRepository.findByPeriod(fromDate,toDate);
    }
}
