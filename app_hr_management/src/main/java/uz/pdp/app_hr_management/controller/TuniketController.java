package uz.pdp.app_hr_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.app_hr_management.payload.ApiResponse;
import uz.pdp.app_hr_management.payload.TuniketDto;
import uz.pdp.app_hr_management.service.TuniketService;

@RestController
@RequestMapping("/tuniket")
public class TuniketController {

    @Autowired
    TuniketService tuniketService;

    @PostMapping("/add")
    public HttpEntity<?> add(@PathVariable TuniketDto tuniketDto){
        ApiResponse apiResponse = tuniketService.add(tuniketDto);
        return  ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }


}
