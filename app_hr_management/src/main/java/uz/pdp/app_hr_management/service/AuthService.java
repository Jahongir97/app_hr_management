package uz.pdp.app_hr_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.app_hr_management.entity.Role;
import uz.pdp.app_hr_management.entity.User;
import uz.pdp.app_hr_management.entity.enums.RoleName;
import uz.pdp.app_hr_management.payload.ApiResponse;
import uz.pdp.app_hr_management.payload.LoginDto;
import uz.pdp.app_hr_management.payload.ParolDto;
import uz.pdp.app_hr_management.payload.RegisterDto;
import uz.pdp.app_hr_management.repository.RoleRepository;
import uz.pdp.app_hr_management.repository.UserRepository;

import java.util.*;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;


    public ApiResponse registerUser(RegisterDto registerDto) {
        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("Bunday email ro'yxatga olib bo'lingan", false);
        User userInSystem = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userInSystem.getRoles().contains(RoleName.DIRECTOR)){
            User user=new User();
            user.setLastName(registerDto.getLastName());
            user.setFirstName(registerDto.getFirstName());
            user.setEmail(registerDto.getEmail());
            Set<Role> roleList = (Set)roleRepository.findAllById(registerDto.getRolesId());
            user.setRoles(roleList);

            user.setEmailCode(UUID.randomUUID().toString());

            userRepository.save(user);

            //emailga xabar yuborish
            sendEmail(user.getEmail(), user.getEmailCode());

            return new ApiResponse("Hodim ro'yxatga olindi. Akkaunt aktivlashishi uchun email yuborilga link orqali kirishi va o'z parolini o'rnatishi kerak bo'ladi",true);

        }else if (userInSystem.getRoles().contains(RoleName.MANAGER)){
            if (registerDto.getRolesId().contains(2)) {
                return new ApiResponse("Kechirasiz siz manager qo'sha olmaysiz",false);
            }
            User user=new User();
            user.setLastName(registerDto.getLastName());
            user.setFirstName(registerDto.getFirstName());
            user.setEmail(registerDto.getEmail());
            Set<Role> roleList = (Set)roleRepository.findAllById(registerDto.getRolesId());
            user.setRoles(roleList);

            user.setEmailCode(UUID.randomUUID().toString());

            userRepository.save(user);

            //emailga xabar yuborish
            sendEmail(user.getEmail(), user.getEmailCode());

            return new ApiResponse("Hodim ro'yxatga olindi. Akkaunt aktivlashishi uchun email yuborilga link orqali kirishi va o'z parolini o'rnatishi kerak bo'ladi",true);
        }
        return new ApiResponse("Siz hodim qo'sha olmaysiz",false);
    }


    public boolean sendEmail(String senderEmail, String emailCode){
        try {
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom("noreply@gmail.com");
            mailMessage.setTo(senderEmail);
            mailMessage.setSubject("Tasdiqlash kodi");
            mailMessage.setText("Tasdiqlang http://localhost:8080/api/auth/verifyEmail?emailCode="+emailCode+"&email="+senderEmail);
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception exception){
            return false;
        }

    }

    public ApiResponse verifyEmail(String emailCode, String email, ParolDto parolDto) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            user.setPassword(passwordEncoder.encode(parolDto.getPassword()));
            return  new ApiResponse("Akkount tasdiqlandi va parol o'rnatildi",true);
        }

return new ApiResponse("Akkaunt allaqachon tasdiqlangan", false);

    }

    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()));
            User user =(User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRoles());
            return new ApiResponse("Token",true,token);

        }catch (BadCredentialsException exception){
            return new ApiResponse("parol yoki login xato",false);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> optionalUser = userRepository.findByEmail(username);
//        if (optionalUser.isPresent()){
//            return optionalUser.get();
//        }
//        throw  new UsernameNotFoundException(username+" topilmadi");
        return userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username+" topilmadi"));
    }


}
