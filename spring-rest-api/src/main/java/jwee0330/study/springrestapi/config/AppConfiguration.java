package jwee0330.study.springrestapi.config;

import jwee0330.study.springrestapi.accouts.Account;
import jwee0330.study.springrestapi.accouts.AccountRole;
import jwee0330.study.springrestapi.accouts.AccountService;
import jwee0330.study.springrestapi.common.AppProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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

            @Autowired
            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account admin = Account.builder()
                        .email(appProperties.getAdminUsername())
                        .password(passwordEncoder().encode(appProperties.getAdminPassword()))
                        .roles(Stream.of(AccountRole.ADMIN, AccountRole.USER).collect(Collectors.toSet()))
                        .build();
                accountService.saveAccount(admin);

                Account user = Account.builder()
                        .email(appProperties.getUserUsername())
                        .password(passwordEncoder().encode(appProperties.getUserPassword()))
                        .roles(Stream.of(AccountRole.ADMIN, AccountRole.USER).collect(Collectors.toSet()))
                        .build();
                accountService.saveAccount(user);
            }
        };
    }

}
