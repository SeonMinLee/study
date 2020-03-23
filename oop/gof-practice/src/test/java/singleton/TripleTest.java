package singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class TripleTest {

    @Test
    void singletonMethodTest() {
        Triple instance0 = Triple.getInstance(0);
        Triple instance1 = Triple.getInstance(1);
        Triple instance2 = Triple.getInstance(2);

        assertThat(instance0).isExactlyInstanceOf(Triple.class);
        assertThat(instance1).isExactlyInstanceOf(Triple.class);
        assertThat(instance2).isExactlyInstanceOf(Triple.class);
    }

    @Test
    public void anotherInstance() {
        //given
        Triple instance0 = Triple.getInstance(0);
        Triple instance1 = Triple.getInstance(1);

        //when & then
        assertThat(instance0.equals(instance1)).isFalse();
    }

    @Test
    public void checkSingletonInstance() {
        //given
        Triple instance0 = Triple.getInstance(0);
        Triple instance1 = Triple.getInstance(0);

        //when & then
        assertThat(instance0).isEqualTo(instance1);
    }

    @Test
    public void singletonTest() {
        for (int i = 0; i < 9; i++) {
            Triple triple = Triple.getInstance(i % 3);
            assertThat(triple.getId()).isEqualTo(i % 3);
        }
    }

}
