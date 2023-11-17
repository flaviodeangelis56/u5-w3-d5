package flaviodeangelis.u5w3d5.repositories;

import flaviodeangelis.u5w3d5.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findByTitle(String title);
}
