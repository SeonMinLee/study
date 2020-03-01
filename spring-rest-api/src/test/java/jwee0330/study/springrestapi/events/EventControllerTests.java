package jwee0330.study.springrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("POST /api/events 201")
    @Test
    public void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 03, 02, 02, 00, 00))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 03, 02, 04, 00, 00))
                .beginEventDateTime(LocalDateTime.of(2020, 03, 05, 00, 00, 00))
                .endEventDateTime(LocalDateTime.of(2020, 03, 06, 00, 00, 00))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();
//        when(eventRepository.save(event))
//                .thenReturn(event);

        mockMvc.perform(post("/api/events/")
                .content(objectMapper.writeValueAsBytes(event))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("eventStatus").value(Matchers.not(EventStatus.PUBLISHED)))
        ;
    }

    @DisplayName("POST /api/events 400")
    @Test
    public void createEvent_badRequest() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 03, 02, 02, 00, 00))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 03, 02, 04, 00, 00))
                .beginEventDateTime(LocalDateTime.of(2020, 03, 05, 00, 00, 00))
                .endEventDateTime(LocalDateTime.of(2020, 03, 06, 00, 00, 00))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .eventStatus(EventStatus.PUBLISHED)
                .free(true)
                .build();

        mockMvc.perform(post("/api/events/")
                .content(objectMapper.writeValueAsBytes(event))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        //given
        EventDto eventDto = EventDto.builder().build();
        //when

        //then
        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(eventDto)))
                .andExpect(status().isBadRequest());

    }

    @DisplayName("POST /api/events 400 - business logic")
    @Test
    public void createEvent_time_logic_badRequest() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 03, 02, 02, 00, 00))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 03, 01, 04, 00, 00))
                .beginEventDateTime(LocalDateTime.of(2020, 03, 05, 00, 00, 00))
                .endEventDateTime(LocalDateTime.of(2020, 03, 06, 00, 00, 00))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .eventStatus(EventStatus.PUBLISHED)
                .free(true)
                .build();

        mockMvc.perform(post("/api/events/")
                .content(objectMapper.writeValueAsBytes(event))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }
}
