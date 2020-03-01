package jwee0330.study.jacksonannotation.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd, yyyy HH:mm:ss", Locale.KOREA);

    public CustomLocalDateTimeSerializer() {
        this(null);
    }

    protected CustomLocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.format(dtf));
    }
}
