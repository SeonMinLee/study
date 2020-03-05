package jwee0330.study.springrestapi.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jwee0330.study.springrestapi.common.ErrorsResource;
import jwee0330.study.springrestapi.index.IndexController;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
//@RequestMapping(value = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventValidator eventValidator;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, BindingResult errors) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
        }
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
                new Link("/static/docs/index.html#resources-events-create", "profile"),
                linkTo(methodOn(EventController.class).createEvent(eventDto, errors)).withRel("query-events"),
                linkTo(methodOn(EventController.class).createEvent(eventDto, errors)).withRel("update-event")
        );

        return ResponseEntity.created(createdUri)
                .body(newEvent);
    }

    private ResponseEntity getResponseEntity(BindingResult errors) {
        return ResponseEntity.badRequest()
                .body(new ErrorsResource(errors, linkTo(methodOn(IndexController.class).index()).withRel("index")));
    }

    @GetMapping
    public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler,
                                      @AuthenticationPrincipal User user) {
        Page<Event> page = this.eventRepository.findAll(pageable);
        List<Event> events = page.getContent();

        for (Event event : events) {
            Integer eventId = event.getId();
            Link selfLink = linkTo(EventController.class).slash(eventId).withSelfRel();
            event.add(selfLink);
        }

        PageImpl result = new PageImpl(events, pageable, page.getTotalElements());

        PagedModel<EntityModel<Event>> entityModels = assembler.toModel(result);
//        return ResponseEntity.ok(this.eventRepository.findAll(pageable));
        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id) {
        Optional<Event> eventOptional = this.eventRepository.findById(id);
        if (eventOptional.isPresent() == false) {
            return ResponseEntity.notFound().build();
        }

        Event event = eventOptional.get();
        event.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
        event.add(new Link("/static/docs/index.html#resources-events-get").withRel("profile"));

        return ResponseEntity.ok(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity putEvent(@PathVariable Integer id, @RequestBody @Valid EventDto eventDto, BindingResult errors) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        if (optionalEvent.isPresent() == false) {
            return ResponseEntity.notFound().build();
        }

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorsResource(errors));
        }
        this.eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorsResource(errors));
        }

        Event existingEvent = optionalEvent.get();
        this.modelMapper.map(eventDto, existingEvent);
        Event changedEvent = this.eventRepository.save(existingEvent);

        changedEvent.add(linkTo(EventController.class).slash("{id}").withSelfRel());

        return ResponseEntity.ok(changedEvent);
    }
}
