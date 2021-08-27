package uz.pdp.app_hr_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app_hr_management.entity.Tuniket;
import uz.pdp.app_hr_management.entity.User;
import uz.pdp.app_hr_management.payload.ApiResponse;
import uz.pdp.app_hr_management.payload.TuniketDto;
import uz.pdp.app_hr_management.repository.TuniketRepository;
import uz.pdp.app_hr_management.repository.UserRepository;

import java.util.Optional;

@Service
public class TuniketService {
    @Autowired
    TuniketRepository tuniketRepository;
    @Autowired
    UserRepository userRepository;

    public ApiResponse add(TuniketDto tuniketDto) {
        Optional<User> optionalUser = userRepository.findById(tuniketDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("Siz korxona hodimlari orasida yo'qsiz", false);
        Tuniket tuniket=new Tuniket();
        tuniket.setComeOrOut(tuniketDto.isComeOrOut());
        tuniket.setUser(optionalUser.get());
        return new ApiResponse("Tuniket muaffaqiyatli saqlandi",true);
    }
}
