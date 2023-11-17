package flaviodeangelis.u5w3d5.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private String location;
    private Date date;
    private int maxNumberOfPeople;
    private String eventImg;
    @ManyToMany
    @JoinTable(name = "entity_usage",
            joinColumns = {
                    @JoinColumn(name = "id_user", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "id_event", referencedColumnName = "id")})
    private List<User> users;
}
