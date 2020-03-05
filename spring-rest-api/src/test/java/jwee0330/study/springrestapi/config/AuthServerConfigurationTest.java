package jwee0330.study.springrestapi.config;

import jwee0330.study.springrestapi.accouts.Account;
import jwee0330.study.springrestapi.accouts.AccountRepository;
import jwee0330.study.springrestapi.accouts.AccountRole;
import jwee0330.study.springrestapi.accouts.AccountService;
import jwee0330.study.springrestapi.common.AppProperties;
import jwee0330.study.springrestapi.common.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthServerConfigurationTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppProperties appProperties;

    @Test
    @Transactional
    public void 인증토큰을_발급받는_테스트() throws Exception {
        String username = "j@name.com";
        String password = "1234";

        Set<AccountRole> roles = new HashSet<>();
        roles.add(AccountRole.USER);
        roles.add(AccountRole.ADMIN);
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(roles)
                .build();

        accountService.saveAccount(account);

        mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())
        ;
    }


}