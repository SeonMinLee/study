package jwee0330.study.springrestapi.events;

import junitparams.Parameters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EventTest {

    @Test
    public void builder_test() {
        //given
        Event event = Event.builder()
                .name("Spring REST API")
                .description("REST API development with Spring")
                .build();
        //when

        //then
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean_test() {
        //given
        String name = "Event";
        String description = "Spring";

        //when
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        //then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

}
