package uz.pdp.app_hr_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.app_hr_management.entity.Role;
import uz.pdp.app_hr_management.entity.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(RoleName roleName);

}
