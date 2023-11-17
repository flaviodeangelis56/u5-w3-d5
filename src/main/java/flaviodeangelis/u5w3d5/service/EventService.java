package flaviodeangelis.u5w3d5.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import flaviodeangelis.u5w3d5.entities.Event;
import flaviodeangelis.u5w3d5.exception.BadRequestException;
import flaviodeangelis.u5w3d5.exception.NotFoundException;
import flaviodeangelis.u5w3d5.payloads.NewEventDTO;
import flaviodeangelis.u5w3d5.payloads.UpdateEventDTO;
import flaviodeangelis.u5w3d5.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private Cloudinary cloudinary;

    public Event save(NewEventDTO body) {
        eventRepository.findByTitle(body.title()).ifPresent(event -> {
            throw new BadRequestException("evento con titolo " + body.title() + "già esistente");
        });

        Event newEvent = new Event();
        newEvent.setEventImg("...");
        newEvent.setDate(body.date());
        newEvent.setDescription(body.description());
        newEvent.setLocation(body.location());
        newEvent.setTitle(body.title());
        newEvent.setMaxNumberOfPeople(body.maxNumberOfPeople());
        return eventRepository.save(newEvent);
    }

    public Page<Event> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return eventRepository.findAll(pageable);
    }

    public Event findById(int id) {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) throws NotFoundException {
        Event found = this.findById(id);
        eventRepository.delete(found);
    }

    public String uploadImg(MultipartFile file, int id, Event body) throws IOException {
        Event found = this.findById(id);
        String imgURL = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setId(id);
        found.setDate(body.getDate());
        found.setDescription(body.getDescription());
        found.setLocation(body.getLocation());
        found.setTitle(body.getTitle());
        found.setMaxNumberOfPeople(body.getMaxNumberOfPeople());
        found.setEventImg(imgURL);
        eventRepository.save(found);
        return imgURL;
    }

    public Event uploadEvent(UpdateEventDTO body, int id) {
        Event found = this.findById(id);
        found.setId(id);
        found.setDate(body.date());
        found.setDescription(body.description());
        found.setLocation(body.location());
        found.setTitle(body.title());
        found.setMaxNumberOfPeople(body.maxNumberOfPeople());
        return eventRepository.save(found);
    }
}
