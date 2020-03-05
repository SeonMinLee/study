package jwee0330.study.springrestapi.accouts;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.util.Set;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

    public static class AccountSerializer extends JsonSerializer<Account> {

        @Override
        public void serialize(Account account, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeNumberField("id", account.getId());
            gen.writeEndObject();
        }
    }
}
