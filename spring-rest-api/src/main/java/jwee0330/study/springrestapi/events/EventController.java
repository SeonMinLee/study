package jwee0330.study.springrestapi.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jwee0330.study.springrestapi.common.ErrorsResource;
import jwee0330.study.springrestapi.index.IndexController;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventValidator eventValidator;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, BindingResult errors) throws JsonProcessingException {
        if (errors.hasErrors()) {
            return getResponseEntity(errors);
        }
        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return getResponseEntity(errors);
        }
        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
        Event newEvent = eventRepository.save(eventRepository.save(event));
        URI createdUri = linkTo(EventController.class)
                .slash("{id}")
                .toUri();

        newEvent.initialLink(eventDto, errors,
                new Link("/docs/index.html#resources-events-create", "profile"),
                linkTo(methodOn(EventController.class).createEvent(eventDto, errors)).withRel("events"),
                linkTo(methodOn(EventController.class).createEvent(eventDto, errors)).withRel("update")
        );

        return ResponseEntity.created(createdUri)
                .body(newEvent);
    }

    private ResponseEntity getResponseEntity(BindingResult errors) {
        return ResponseEntity.badRequest()
                .body(new ErrorsResource(errors, linkTo(methodOn(IndexController.class).index()).withRel("index")));
    }

}
