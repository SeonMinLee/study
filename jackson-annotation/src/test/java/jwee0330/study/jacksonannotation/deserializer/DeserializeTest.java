package jwee0330.study.jacksonannotation.deserializer;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jwee0330.study.jacksonannotation.desirializer.CustomDateDeserializer;
import jwee0330.study.jacksonannotation.desirializer.CustomLocalDateTimeDeserializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JsonTest
@AutoConfigureJsonTesters
public class DeserializeTest {

    private static final Logger log = LoggerFactory.getLogger(DeserializeTest.class);

    @Autowired
    JacksonTester json;

    @Autowired
    ObjectMapper objectMapper;

    public static class Member {
        public int id;
        public String name;

        @JsonCreator
        public Member(@JsonProperty("id") int id, @JsonProperty("theName") String name) {
            this.id = id;
            this.name = name;
        }
    }

    @DisplayName("@JsonCreator 사용하기")
    @Test
    public void json_creator() throws JsonProcessingException {
        //given
        final String name = "Jayden";
        final String json = "{" +
                "\"id\" : 1,\n" +
                "\"theName\" : \"" + name + "\"\n" +
                "}";
        final ObjectMapper objectMapper = new ObjectMapper();

        //when
        Member member = objectMapper.readerFor(Member.class).readValue(json);

        //then
        assertThat(member.name).isEqualTo(name);
    }

    public static class Member2 {
        @JacksonInject
        public int id;
        public String name;
    }

    @DisplayName("@JacksonInject 사용하기")
    @Test
    public void json_inject() throws IOException {
        //given
        String json = "{\"name\": \"Jayden\"}";

        //when
        InjectableValues injectableValues = new InjectableValues.Std().addValue(int.class, 10);
        Member2 member = objectMapper.reader(injectableValues)
                .forType(Member2.class)
                .readValue(json);
        //then
        assertThat(member.id).isEqualTo(10);
    }

    public static class Event {
        public String name;

        @JsonDeserialize(using = CustomDateDeserializer.class)
        public Date eventDate;
    }

    @DisplayName("@JsonDeserialize 사용하기")
    @Test
    public void json_deserializer() throws IOException {
        //given
        String json = "{\"name\": \"Jayden\", \"eventDate\": \"01-03-2020 02:01:30\"}";


        //when
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Event event = objectMapper.readerFor(Event.class)
                .readValue(json);

        //then
        assertThat(simpleDateFormat.format(event.eventDate)).isEqualTo("01-03-2020 02:01:30");
    }

    public static class Event2 {
        public String name;
        @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
        public LocalDateTime eventDate;

        @Override
        public String toString() {
            return "Event2{" +
                    "name='" + name + '\'' +
                    ", eventDate=" + eventDate +
                    '}';
        }
    }

    @DisplayName("@JsonDeserialize")
    @Test
    public void unmarshalling_test() throws JsonProcessingException {
        //given
        String json = "{\"name\": \"Jayden\", \"eventDate\": \"01-03-2020 02:01:20\"}";

        //when
        Event2 event2 = objectMapper.readerFor(Event2.class)
                .readValue(json);


        log.debug(event2.toString());
        //then
        assertThat(event2.eventDate).isNotNull();
        assertThat(event2.name).isEqualTo("Jayden");

    }

    public static class Member3 {
        @JsonAlias({"name", "his_name", "her_name"})
        private String name;
        private String hobby;
    }

    @DisplayName("@JsonAlias")
    @Test
    public void json_alias() throws JsonProcessingException {
        //given
        String json1 = "{\"name\":\"Jayden\", \"hobby\":\"coding\"}";
        String json2 = "{\"his_name\":\"Jayden\", \"hobby\":\"coding\"}";
        String json3 = "{\"her_name\":\"Jayden\", \"hobby\":\"coding\"}";

        //when
        Member3 member1 = objectMapper.readerFor(Member3.class).readValue(json1);
        Member3 member2 = objectMapper.readerFor(Member3.class).readValue(json2);
        Member3 member3 = objectMapper.readerFor(Member3.class).readValue(json3);

        //then
        assertThat(member1.name).isEqualTo(member1.name);
        assertThat(member1.name).isEqualTo(member2.name);
        assertThat(member1.name).isEqualTo(member3.name);
    }

    @JsonIgnoreProperties({"id"})
    public static class Member4 {
        public int id;
        public String name;
        @JsonIgnore
        public LocalDateTime eventTime;

        public Member4(int id, String name, LocalDateTime eventTime) {
            this.id = id;
            this.name = name;
            this.eventTime = eventTime;
        }
    }

    @Test
    public void json_ignore_properties() throws JsonProcessingException {
        //given
        Member4 member4 = new Member4(1, "Jayden",
                LocalDateTime.of(2020, 03, 01, 17, 26, 30));

        //when
        String result = objectMapper.writeValueAsString(member4);

        //then
        assertThat(result.contains("1")).isFalse();
        assertThat(result.contains("eventTime")).isFalse();
        assertThat(result.contains("Jayden")).isTrue();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Member5 {
        public int id;
        public String name;

        public Member5(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @JsonInclude(JsonInclude.Include.ALWAYS)
    public static class Member6 {
        public int id;
        public String name;

        public Member6(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @DisplayName("@JsonInclude")
    @Test
    public void json_include() throws JsonProcessingException {
        //given
        Member5 includeNonNull = new Member5(1, null);
        Member6 includeNull = new Member6(1, null);

        //when
        String nonNullResult = objectMapper.writeValueAsString(includeNonNull);
        String nullResult = objectMapper.writeValueAsString(includeNull);

        //then
        assertThat(nonNullResult.contains("null")).isFalse();
        assertThat(nullResult.contains("null")).isTrue();
    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    public static class Member7 {
        @JsonProperty("my_name")
        private String name;
        @JsonProperty("my_hobby")
        private String hobby;

        public Member7() {
        }

        public Member7(String name, String hobby) {
            this.name = name;
            this.hobby = hobby;
        }
    }

    @Test
    public void json_property() throws JsonProcessingException {
        //given
        String json = "{\"my_name\":\"Jayden\", \"my_hobby\":\"coding\"}";

        //when
        Member7 member7 = objectMapper.readValue(json, Member7.class);

        //then
        assertThat(member7.name).isEqualTo("Jayden");
        assertThat(member7.hobby).isEqualTo("coding");
    }

    @Test
    public void disable_jackson() throws JsonProcessingException {
        //given
        String json = "{\"my_name\":\"Jayden\", \"my_hobby\":\"coding\"}";
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(MapperFeature.USE_ANNOTATIONS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //when
        Member7 member = objectMapper.readValue(json, Member7.class);

        //then
        assertThat(member.name).isNull();
        assertThat(member.hobby).isNull();
    }

    @Test
    public void dateTimeFormatter_test() {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        //when
        LocalDateTime dt = LocalDateTime.parse("01-03-2020 07:08:10", formatter);

        //then
        assertThat(dt.getMonthValue()).isEqualTo(3);
        assertThat(dt.getDayOfMonth()).isEqualTo(1);
        assertThat(dt.getYear()).isEqualTo(2020);
    }

}
