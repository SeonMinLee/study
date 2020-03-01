package jwee0330.study.springrestapi.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Errors;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventValidator eventValidator;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, BindingResult errors) {
        if (hasErrors(errors)) return ResponseEntity.badRequest().body(errors);
        eventValidator.validate(eventDto, errors);
        if (hasErrors(errors)) return ResponseEntity.badRequest().body(errors);

        Event event = modelMapper.map(eventDto, Event.class);
        Event newEvent = eventRepository.save(eventRepository.save(event));
        URI createdUri = linkTo(EventController.class)
                .slash("{id}")
                .toUri();
        return ResponseEntity.created(createdUri)
                .body(newEvent);
    }

    private boolean hasErrors(BindingResult errors) {
        if (errors.hasErrors()) {
            return true;
        }
        return false;
    }
}
