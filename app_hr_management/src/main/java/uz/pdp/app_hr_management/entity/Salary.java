package uz.pdp.app_hr_management.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private double amount;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private Timestamp givedSalaryOfTime;

    @Column(nullable = false)
    private Date forPeriod;
}
