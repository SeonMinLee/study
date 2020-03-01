package jwee0330.study.jacksonannotation.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomDateSerializer extends StdSerializer<Date> {
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.KOREA);

    protected CustomDateSerializer() {
        this(null);
    }

    protected CustomDateSerializer(Class<Date> type) {
        super(type);
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(formatter.format(value));
    }
}
