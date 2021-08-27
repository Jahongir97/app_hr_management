package uz.pdp.app_hr_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.app_hr_management.entity.Tuniket;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TuniketRepository extends JpaRepository<Tuniket, Integer> {
    @Query(value = "select * from tuniket where user_id=:userId and date between fromdate=:fromDate and todate=:toDate", nativeQuery = true)
    List<Tuniket> findByUserIdAndPeriod(UUID userId, Date fromDate, Date toDate);

}


