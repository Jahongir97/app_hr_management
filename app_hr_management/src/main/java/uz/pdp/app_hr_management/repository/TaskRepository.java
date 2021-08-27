package uz.pdp.app_hr_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.app_hr_management.entity.Task;
import uz.pdp.app_hr_management.entity.enums.StatusTask;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Optional<Task> findByIdAndUserId(Integer id, UUID user_id);


    List<Task> findAllByUserId(UUID user_id);

    List<Task> findAllByStatusTask(StatusTask statusTask);

    @Query(value = "select * from task where end_time<current_date",nativeQuery = true)
    List<Task> findAllByDate();
}
