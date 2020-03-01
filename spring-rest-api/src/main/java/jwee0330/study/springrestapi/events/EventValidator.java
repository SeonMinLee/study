package jwee0330.study.springrestapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, BindingResult bindingResult) {
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {
            bindingResult.rejectValue("basePrice", "wrongValue", "BasePrice is Wrong!");
            bindingResult.rejectValue("maxPrice", "wrongValue", "MaxPrice is Wrong!");
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
            endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            bindingResult.rejectValue("endEventDateTime", "wrongValue", "EndEventDateTime is Wrong!");
        }

        // TODO beginEventDateTime
        // TODO CloseEnrollmentDateTime
        // TODO BeginEnrollmentDateTime

    }
}
