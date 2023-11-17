package flaviodeangelis.u5w3d5.controllers;

import flaviodeangelis.u5w3d5.entities.User;
import flaviodeangelis.u5w3d5.exception.BadRequestException;
import flaviodeangelis.u5w3d5.payloads.LogInSuccesUserDTO;
import flaviodeangelis.u5w3d5.payloads.LogInUserDTO;
import flaviodeangelis.u5w3d5.payloads.NewUserDTO;
import flaviodeangelis.u5w3d5.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public LogInSuccesUserDTO login(@RequestBody LogInUserDTO body) {

        return new LogInSuccesUserDTO(authService.userAuthenticate(body));
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated NewUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return authService.save(body);
        }

    }
}
