package flaviodeangelis.u5w3d5.service;

import flaviodeangelis.u5w3d5.entities.Role;
import flaviodeangelis.u5w3d5.entities.User;
import flaviodeangelis.u5w3d5.exception.BadRequestException;
import flaviodeangelis.u5w3d5.exception.UnauthorizedException;
import flaviodeangelis.u5w3d5.payloads.LogInUserDTO;
import flaviodeangelis.u5w3d5.payloads.NewUserDTO;
import flaviodeangelis.u5w3d5.repositories.UserRepository;
import flaviodeangelis.u5w3d5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String userAuthenticate(LogInUserDTO body) {
        User user = userService.findByEmail(body.email());

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

    public User save(NewUserDTO body) {
        userRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già utilizzata!");
        });

        User newUser = new User();
        if (body.role() == null) {
            throw new BadRequestException("Il ruolo del utente è un campo obbligatorio! Scegli tra UTENTE e ADMIN");
        } else if (body.role().trim().toUpperCase().contains("UTENTE")) {
            newUser.setUsername(body.username());
            newUser.setName(body.name());
            newUser.setLastName(body.lastname());
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setRole(Role.USER);
            return userRepository.save(newUser);
        } else if (body.role().trim().toUpperCase().contains("ADMIN")) {
            newUser.setUsername(body.username());
            newUser.setName(body.name());
            newUser.setLastName(body.lastname());
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setRole(Role.ADMIN);
            return userRepository.save(newUser);
        } else {
            throw new BadRequestException("valore inserito non valido o non di tipo stringa! Scegli tra UTENTE e ADMIN");
        }
    }
}
