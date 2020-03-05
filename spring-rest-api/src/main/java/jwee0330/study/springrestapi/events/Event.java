package jwee0330.study.springrestapi.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jwee0330.study.springrestapi.accouts.Account;
import lombok.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.BindingResult;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class Event extends EntityModel<Event> {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;
    @ManyToOne
    @JsonSerialize(using = Account.AccountSerializer.class)
    private Account manager;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    public void update() {
        if (getBasePrice() == 0 && getMaxPrice() == 0) {
            setFree(true);
        } else {
            setFree(false);
        }
        if (Strings.isBlank(getLocation())) {
            setOffline(false);
        } else {
            setOffline(true);
        }
    }

    public void initialLink(EventDto eventDto, BindingResult errors, Link... links) throws JsonProcessingException {
        this.add(linkTo(methodOn(EventController.class).createEvent(eventDto, errors)).withSelfRel());
        for (Link link : links) {
            this.add(link);
        }
    }

    public void setManager(Account account) {

    }
}
