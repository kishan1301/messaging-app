package com.photon.spring.github.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MessagingApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessagingApplication.class, args);
    }
}


@RestController
@Slf4j
class UserAuth {

    @GetMapping("user")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        log.debug("This is the {}", principal);
        return principal.getAttribute("name");
    }
}