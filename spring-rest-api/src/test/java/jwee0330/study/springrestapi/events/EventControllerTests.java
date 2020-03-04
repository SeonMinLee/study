package jwee0330.study.springrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import jwee0330.study.springrestapi.common.RestDocsConfiguration;
import jwee0330.study.springrestapi.common.TestDescription;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ModelMapper modelMapper;

    @DisplayName("이벤트를 정상적으로 생성하는 테스트")
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
//                .andExpect(jsonPath("_links.profile").exists())
//                .andExpect(jsonPath("_links.self").exists())
//                .andExpect(jsonPath("_links.events").exists())
//                .andExpect(jsonPath("_links.update").exists())

                .andDo(document(
                        "create-event",
                        links(halLinks(),
                                linkWithRel("profile").description("Link to Profile"),
                                linkWithRel("self").description("Link to the created event"),
                                linkWithRel("events").description("Link to view all events"),
                                linkWithRel("update").description("Link to update the events")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        requestFields(
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                                fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("location").description("location of new event"),
                                fieldWithPath("basePrice").description("base price of new event"),
                                fieldWithPath("maxPrice").description("max price of new event"),
                                fieldWithPath("limitOfEnrollment").description("limit of enrolmment")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
//                        relaxedResponseFields(
                        responseFields(
                                fieldWithPath("id").description("identifier of new event"),
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
                                fieldWithPath("endEventDateTime").description("date time of end of new event"),
                                fieldWithPath("location").description("location of new event"),
                                fieldWithPath("basePrice").description("base price of new event"),
                                fieldWithPath("maxPrice").description("max price of new event"),
                                fieldWithPath("limitOfEnrollment").description("limit of enrolmment"),
                                fieldWithPath("free").description("it tells if this event is free or not"),
                                fieldWithPath("offline").description("it tells if this event is offline event or not"),
                                fieldWithPath("eventStatus").description("event status"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.events.href").description("link to query event list"),
                                fieldWithPath("_links.update.href").description("link to update existing event"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                ))
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

    @TestDescription("빈 객체로 요청했을때 에러가 발생하는 테스트")
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
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 03, 02, 02, 00, 00))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 03, 01, 04, 00, 00))
                .beginEventDateTime(LocalDateTime.of(2020, 02, 06, 00, 00, 00))
                .endEventDateTime(LocalDateTime.of(2020, 02, 05, 00, 00, 00))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();

        mockMvc.perform(post("/api/events/")
                .content(objectMapper.writeValueAsBytes(event))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("content.[0].objectName").exists())
                .andExpect(jsonPath("content.[0].field").exists())
                .andExpect(jsonPath("content.[0].defaultMessage").exists())
                .andExpect(jsonPath("content.[0].code").exists())
                .andExpect(jsonPath("content.[0].rejectedValue").exists())
                .andExpect(jsonPath("_links.index").exists())
        ;
    }

    @DisplayName("30개의 이벤트를 10개씩 두번째 페이지 조회하기")
    @Test
    public void queryEvent() throws Exception {
        //given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // when
        this.mockMvc.perform(get("/api/events")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
        .andExpect(jsonPath("page").exists())
        ;

    }

    private Event generateEvent(int index) {
        Event event = Event.builder()
                .name("event " + index)
                .description("test event")
                .build();
        return eventRepository.save(event);
    }

    @DisplayName("기존의 이벤트를 하나 조회하기")
    @Test
    public void getEvent() throws Exception {
        //given
        Event event = this.generateEvent(100);

        //when
        ResultActions perform = this.mockMvc.perform(get("/api/events/{id}", event.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE));

        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
            .andDo(document("get-an-event"))
        //todo 문서 생성
        ;
    }

    @DisplayName("없는 이벤트는 조회했을 때 404 응답 받기")
    @Test
    public void getEvent404() throws Exception {

        //when
        ResultActions perform = this.mockMvc.perform(get("/api/events/132423"));

        //then
        perform.andExpect(status().isNotFound());
    }

    @DisplayName("입력받은 id의 이벤트를 수정한다")
    @Test
    public void changeEvent() throws Exception {
        //given
        Event event = this.generateEvent(101);

        EventDto eventDto = EventDto.builder()
                .name("Jayden")
                .description("changed")
                .basePrice(0)
                .maxPrice(0)
                .location("location")
                .limitOfEnrollment(10)
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 03, 02, 02, 00, 00))
                .closeEnrollmentDateTime(LocalDateTime.of(2020, 03, 02, 04, 00, 00))
                .beginEventDateTime(LocalDateTime.of(2020, 03, 05, 00, 00, 00))
                .endEventDateTime(LocalDateTime.of(2020, 03, 06, 00, 00, 00))
                .build();

        //when
        ResultActions perform = this.mockMvc.perform(put("/api/events/{id}", event.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventDto)));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("name").value(Matchers.is("Jayden")))
                .andExpect(jsonPath("description").value(Matchers.is("changed")))
                .andExpect(jsonPath("_links.self").exists())
        ;
    }

    @DisplayName("존재하지 않는 이벤트 수정 실패")
    @Test
    public void updateEvent404() throws Exception {
        //given
        Event event = this.generateEvent(200);
        EventDto eventDto = this.modelMapper.map(event, EventDto.class);

        //when & then
        this.mockMvc.perform(put("/api/events/1231312", event.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventDto)))
        .andDo(print())
        .andExpect(status().isNotFound())
        ;
    }
}
