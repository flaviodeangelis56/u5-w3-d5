package flaviodeangelis.u5w3d5.payloads;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record NewEventDTO(
        @NotEmpty(message = "Il titolo è un campo obbligatorio!")
        String title,
        @NotEmpty(message = "La descrizione è un campo obbligatorio!")
        String description,
        @NotEmpty(message = "La location è un campo obbligatorio!")
        String location,
        @NotEmpty(message = "La data è un campo obbligatorio!")
        LocalDate date,
        int maxNumberOfPeople) {
}
