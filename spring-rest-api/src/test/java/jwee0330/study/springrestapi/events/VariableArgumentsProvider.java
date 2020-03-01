package jwee0330.study.springrestapi.events;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class VariableArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<VariableSource> {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return null;
    }

    @Override
    public void accept(VariableSource variableSource) {

    }

    @Override
    public Consumer<VariableSource> andThen(Consumer<? super VariableSource> after) {
        return null;
    }
}
