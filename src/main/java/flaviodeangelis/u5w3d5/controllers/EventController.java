package flaviodeangelis.u5w3d5.controllers;

import flaviodeangelis.u5w3d5.entities.Event;
import flaviodeangelis.u5w3d5.exception.BadRequestException;
import flaviodeangelis.u5w3d5.exception.NotFoundException;
import flaviodeangelis.u5w3d5.payloads.NewEventDTO;
import flaviodeangelis.u5w3d5.payloads.UpdateEventDTO;
import flaviodeangelis.u5w3d5.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/event")
@PreAuthorize("hasAuthority('ADMIN')")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Event save(@RequestBody @Validated NewEventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return eventService.save(body);
        }

    }

    @GetMapping("")
    public Page<Event> getUser(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String orderBy) {
        return eventService.getUsers(page, size, orderBy);
    }

    @GetMapping(value = "/{id}")
    public Event findById(@PathVariable int id) {
        return eventService.findById(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        eventService.findByIdAndDelete(id);
    }

    @PutMapping("/update/img/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String uploadImg(@RequestParam("avatar") MultipartFile file, @PathVariable int id) throws IOException {
        Event found = eventService.findById(id);
        return eventService.uploadImg(file, id, found);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Event uploadEvent(@RequestBody UpdateEventDTO body, @PathVariable int id) {
        return eventService.uploadEvent(body, id);
    }
}
