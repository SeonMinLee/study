package jwee0330.study.springrestapi.config;

import jwee0330.study.springrestapi.accouts.Account;
import jwee0330.study.springrestapi.accouts.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

//            @Autowired
//            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) {
                Account account = Account.builder()
                        .email("jayden@email.com")
                        .password("pass")
                        .build();

                accountService.saveAccount(account);
            }
        };
    }

}
