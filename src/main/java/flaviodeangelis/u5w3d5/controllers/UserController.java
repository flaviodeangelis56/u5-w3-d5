package flaviodeangelis.u5w3d5.controllers;

import flaviodeangelis.u5w3d5.entities.Event;
import flaviodeangelis.u5w3d5.entities.User;
import flaviodeangelis.u5w3d5.exception.NotFoundException;
import flaviodeangelis.u5w3d5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService usersService;

    @GetMapping("")
    public Page<User> getUser(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String orderBy) {
        return usersService.getUsers(page, size, orderBy);
    }

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable int id) {
        return usersService.findById(id);
    }

    @GetMapping(value = "/{email}")
    public User findByEmail(@PathVariable String email) {
        return usersService.findByEmail(email);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        usersService.findByIdAndDelete(id);
    }

    @GetMapping("/me")
    public UserDetails getCurrentProfile(@AuthenticationPrincipal UserDetails currentUser) {
        return currentUser;
    }

    @GetMapping("/me/events")
    public List<Event> getCurrentProfile(@AuthenticationPrincipal User currentUser) {
        return usersService.getMyEvents(currentUser.getId());
    }

    @DeleteMapping("/me")
    public void deleteCurrent(@AuthenticationPrincipal User currentUser) {
        usersService.findByIdAndDelete(currentUser.getId());
    }

    @PutMapping("/buyTicket/{eventId}")
    public String buyTicket(@AuthenticationPrincipal UserDetails currentUser, @PathVariable int eventId) {
        return usersService.buyTicket(currentUser.getUsername(), eventId);
    }

    @PutMapping("/removeTicket/{eventId}")
    public String removeTicket(@AuthenticationPrincipal UserDetails currentUser, @PathVariable int eventId) {
        return usersService.removeTicket(currentUser.getUsername(), eventId);
    }
}
