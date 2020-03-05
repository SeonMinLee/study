package jwee0330.study.springrestapi.config;

import jwee0330.study.springrestapi.accouts.Account;
import jwee0330.study.springrestapi.accouts.AccountRepository;
import jwee0330.study.springrestapi.accouts.AccountRole;
import jwee0330.study.springrestapi.accouts.AccountService;
import jwee0330.study.springrestapi.common.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Test
    public void 인증토큰을_발급받는_테스트() throws Exception {
        //given
        String clientId = "myApp";
        String clientSecret = "pass";

        Account account = Account.builder()
                .email("jayden@email.com")
                .password("1234")
                .roles(Stream.of(AccountRole.ADMIN, AccountRole.USER).collect(Collectors.toSet()))
                .build();

        accountService.saveAccount(account);

        mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())
        ;
    }


}