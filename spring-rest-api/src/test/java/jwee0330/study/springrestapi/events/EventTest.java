package jwee0330.study.springrestapi.events;

import junitparams.JUnitParamsRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.runner.RunWith;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(JUnitParamsRunner.class)
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

//    @ParameterizedTest
//    @Parameters(method = "parametersForTestFree")
//    @VariableSource
    public void testFree(Arguments arguments) {
        //given
        Event event = Event.builder()
                .basePrice(Integer.valueOf(String.valueOf(arguments.get())))
                .maxPrice(Integer.valueOf(String.valueOf(arguments.get())))
                .build();

        //when
        event.update();

        //then
        assertThat(event.isFree()).isEqualTo(Boolean.valueOf(String.valueOf(arguments.get())));
    }

    static Stream<Arguments> arguments = Stream.of(
            Arguments.of(0, 0, true), // null strings should be considered blank
            Arguments.of(100, 0, false),
            Arguments.of(0, 100, false),
            Arguments.of(100, 200, false)
    );

    private Object[] parametersForTestFree() {
        return new Object[] {
                new Object[] {0, 0, true},
                new Object[] {100, 0, false},
                new Object[] {0, 100, false},
                new Object[] {100, 200, false}
        };
    }


}
