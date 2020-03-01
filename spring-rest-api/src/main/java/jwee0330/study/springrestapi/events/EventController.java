package jwee0330.study.springrestapi.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
public class EventController {

    @PostMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity createEvent(@RequestBody Event event) throws JsonProcessingException {

        URI createdUri = linkTo(EventController.class)
                .slash("{id}")
                .toUri();
        event.setId(10);
        return ResponseEntity.created(createdUri)
                .body(event);
    }
}
