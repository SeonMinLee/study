package jwee0330.study.jacksonannotation.serializer;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JsonTest
@AutoConfigureJsonTesters
class SerializerTest {

    private static final Logger log = LoggerFactory.getLogger(SerializerTest.class);

    @Autowired
    JacksonTester json;

    @Autowired
    GsonTester gson;

    public static class Member {
        public String name;
        private Map<String, String> properties = new HashMap<>();

        public Member(String name) {
            this.name = name;
        }

        @JsonAnyGetter
        public Map<String, String> getProperties() {
            return properties;
        }

        public String add(String attr, String val) {
            return properties.put(attr, val);
        }
    }

    @DisplayName("@JsonAnyGetter")
    @Test
    void json_any_getter() throws IOException {
        Member member = new Member("Jayden");
        member.add("favorite", "chicken");
        member.add("hobby", "coding");

        String result = json.write(member).getJson();

        log.debug(result);
        assertThat(result).contains("favorite");
        assertThat(result).contains("hobby");
    }

    public static class Member2 {
        public int id;
        private String name;
        private String theName;

        public Member2(int id, String name, String theName) {
            this.id = id;
            this.name = name;
            this.theName = theName;
        }

        @JsonGetter("name")
        public String getTheName() {
            return name;
        }
    }

    @DisplayName("@JsonGetter")
    @Test
    void json_getter() throws IOException {
        Member2 member2 = new Member2(1, "Jayden", "theName");

        String result = json.write(member2).getJson();

        log.debug(result);
        assertThat(result).contains("1", "Jayden");
    }

    @JsonPropertyOrder({"name", "id"})
    public static class Member3 {
        public int id;
        public String name;

        public Member3(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @DisplayName("@JsonPropertyOrder 직렬화 속성의 순서")
    @Test
    public void json_property_order() throws IOException {
        //given
        Member3 member3 = new Member3(1, "Jayden");

        //when
        String result = json.write(member3).getJson();

        //then
        assertThat(result).isEqualTo("{\"name\":\"Jayden\",\"id\":1}");
    }

    public static enum TypeEnumWithValue {
        TYPE1(1, "맥북"), TYPE2(2, "아이맥");

        private Integer id;
        private String name;

        TypeEnumWithValue(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }
    }

    @DisplayName("@JsonValue 가 없으면 ENUM 변수명이 value로 출력된다.")
    @Test
    public void json_value() throws IOException {
        //given
        TypeEnumWithValue type = TypeEnumWithValue.TYPE1;

        //when
        String result = this.json.write(type).getJson();

        log.debug(result);
        //then
        assertThat(result).contains(type.name);
    }

    @JsonRootName(value = "member")
    public static class Member4 {
        public int id;
        public String name;

        public Member4(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @DisplayName("@JsonRootName")
    @Test
    public void json_root_name() throws JsonProcessingException {
        //given
        Member4 member = new Member4(1, "Jayden");

        //when
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String result = mapper.writeValueAsString(member);
        log.info(result);

        //then
        assertThat(result).contains("member");
        assertThat(result).contains("1");
        assertThat(result).contains("Jayden");
    }


    public static class Event {
        public String name;
        @JsonSerialize(using = CustomDateSerializer.class)
        public Date date;
        @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
        public LocalDateTime dateTime;

        public Event(String name, Date date) {
            this.name = name;
            this.date = date;
        }

        public Event(String name, Date date, LocalDateTime dateTime) {
            this.name = name;
            this.date = date;
            this.dateTime = dateTime;
        }
    }

    @DisplayName("@JsonSerialize CustomSerializer extends StdSerializer")
    @Test
    public void json_serialize() throws ParseException, IOException {
        //given
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String target = "01-03-2020 17:02:30";
        String marshallingTarget = "03-01, 2020 17:02:30";
        Date date = dateFormat.parse(target);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("Asia/Seoul"));

        log.debug(date.toString());
        Event event = new Event("study", date, localDateTime);

        //when
        String result = new ObjectMapper().writeValueAsString(event);

        //then
        log.debug(result);
        assertThat(result.contains(marshallingTarget)).isTrue();
    }

}
