package jwee0330.study.springrestapi.common;

import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class ErrorsResource extends RepresentationModel<ErrorsResource> {

    private List<ObjectError> content;

    public ErrorsResource(BindingResult errors, Link... links) {
        this.content = errors.getAllErrors();
        for (Link link : links) {
            this.add(link);
        }
    }
}
