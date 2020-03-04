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

<<<<<<< HEAD
=======
//            @Autowired
//            AppProperties appProperties;

>>>>>>> e645adf61ff2fd16f834adf551c878be2d8e0225
            @Override
            public void run(ApplicationArguments args) {
                Account account = Account.builder()
                        .email("jayden@email.com")
                        .password("pass")
                        .build();
<<<<<<< HEAD
=======

>>>>>>> e645adf61ff2fd16f834adf551c878be2d8e0225
                accountService.saveAccount(account);
            }
        };
    }

}
