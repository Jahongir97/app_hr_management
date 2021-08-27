package uz.pdp.app_hr_management.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_hr_management.payload.ApiResponse;
import uz.pdp.app_hr_management.payload.LoginDto;
import uz.pdp.app_hr_management.payload.ParolDto;
import uz.pdp.app_hr_management.payload.RegisterDto;
import uz.pdp.app_hr_management.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email, @RequestBody ParolDto parolDto){
        ApiResponse apiResponse=authService.verifyEmail(emailCode, email, parolDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);

    }

    @PostMapping("/login")
    public HttpEntity<?> loginUser(LoginDto loginDto){
        ApiResponse apiResponse=authService.login(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:400).body(apiResponse);
    }
}
