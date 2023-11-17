package flaviodeangelis.u5w3d5.payloads;

import java.util.Date;
import java.util.List;

public record ListErrorsresponseDTO(String message,
                                    Date timestamp,
                                    List<String> errorsList) {
}
