package flaviodeangelis.u5w3d5.service;


import flaviodeangelis.u5w3d5.entities.Event;
import flaviodeangelis.u5w3d5.entities.User;
import flaviodeangelis.u5w3d5.exception.NotFoundException;
import flaviodeangelis.u5w3d5.repositories.EventRepository;
import flaviodeangelis.u5w3d5.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;


    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userRepository.findAll(pageable);
    }

    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) throws NotFoundException {
        User found = this.findById(id);
        userRepository.delete(found);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

    public String buyTicket(String userUsername, int eventId) {
        User userFound = userRepository.findByUsername(userUsername).orElseThrow(() -> new NotFoundException("utente con username " + userUsername + "non trovato"));
        Event eventFound = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
        if (eventFound.getMaxNumberOfPeople() == 0) {
            return "mi dispiace ma l'evento è già al completo";
        } else {
            userFound.getEvents().add(eventFound);
            eventFound.getUsers().add(userFound);
            eventFound.setMaxNumberOfPeople(eventFound.getMaxNumberOfPeople() - 1);
            userRepository.save(userFound);
            return "Biglietto acquistato con successo";
        }

    }

    public String removeTicket(String userUsername, int eventId) {
        User userFound = userRepository.findByUsername(userUsername).orElseThrow(() -> new NotFoundException("utente con username " + userUsername + "non trovato"));
        Event eventFound = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
        userFound.getEvents().remove(eventFound);
        eventFound.getUsers().remove(userFound);
        eventFound.setMaxNumberOfPeople(eventFound.getMaxNumberOfPeople() + 1);
        userRepository.save(userFound);
        return "Biglietto rimosso con successo";
    }


    public List<Event> getMyEvents(int id) {
        User found = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return found.getEvents();
    }
}
