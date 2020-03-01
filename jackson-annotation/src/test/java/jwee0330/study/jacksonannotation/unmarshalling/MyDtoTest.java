package jwee0330.study.jacksonannotation.unmarshalling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JsonTest
@AutoConfigureJsonTesters
class MyDtoTest {

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("Fail On Unknown Properties = true")
    @Test
    public void fail_on_unknown_properties_true() throws JsonProcessingException {
        //given
        String jsonAsString = "{\"stringValue\": \"a\"," +
                "\"intValue\":1," +
                "\"booleanValue\":true," +
                "\"stringValue2\": \"something\"}";

        //when
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        //then
        Assertions.assertThatThrownBy(() -> objectMapper.readValue(jsonAsString, MyDto.class))
                .isInstanceOf(UnrecognizedPropertyException.class);
    }

    @DisplayName("Fail On Unknown Properties = false")
    @Test
    public void fail_on_unknown_properties_false() throws JsonProcessingException {
        //given
        String jsonAsString = "{\"stringValue\": \"a\"," +
                "\"intValue\":1," +
                "\"booleanValue\":true," +
                "\"stringValue2\": \"something\"}";

        //when
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MyDto myDto = objectMapper.readValue(jsonAsString, MyDto.class);

        //then
        assertThat(myDto).isNotNull();
        assertThat(myDto.getStringValue()).isEqualTo("a");
        assertThat(myDto.getIntValue()).isEqualTo(1);
        assertThat(myDto.isBooleanValue()).isEqualTo(true);
    }
}
