package uz.pdp.app_hr_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;


    @Column(nullable = false,unique = true)
    @Email
    private String email;


    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String emailCode;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;


    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private Timestamp createAt;

    @UpdateTimestamp
    private Timestamp updateAt;

    @ManyToMany()
    private Set<Role> roles;

    private boolean accountNonExpired=true;

    private boolean accountNonLocked=true;

    private boolean credentialsNonExpired=true;

    private boolean enabled=false;


    //Userdetailes ning methodlari


    //userning huquqlari
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }


    //email username o'rnida foydalanilyapti
    @Override
    public String getUsername() {
        return this.email;
    }

    //Akkountning muddati o'tmaganligi
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    //Akkaunt bloklanganligi
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }


    //Akkaunt ishonchliligi
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
