package flaviodeangelis.u5w3d5.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateEventDTO(
        @NotEmpty(message = "Il titolo è un campo obbligatorio!")
        String title,
        @NotEmpty(message = "La descrizione è un campo obbligatorio!")
        String description,
        @NotEmpty(message = "La location è un campo obbligatorio!")
        String location,
        @NotNull(message = "La data è un campo obbligatorio")
        LocalDate date,
        @NotNull(message = "Il numero massimo di persone è un campo obbligatorio!")
        int maxNumberOfPeople) {
}
