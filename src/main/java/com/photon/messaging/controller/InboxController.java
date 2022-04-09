package com.photon.messaging.controller;

import com.photon.messaging.folders.Folder;
import com.photon.messaging.folders.repository.FolderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Slf4j
public class InboxController {

    private final FolderRepository folderRepository;

    public InboxController(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @GetMapping(value = "/")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        log.info("Principal: {}", principal);
        if (principal == null || !StringUtils.hasText(principal.getAttribute("name")))
            return "index";
        String userId = principal.getAttribute("login");
        final var userFolders = folderRepository.findAllByUserId(userId);
        model.addAttribute("userFolders", userFolders);
        model.addAttribute("userId", userId);
        return "inbox-page";
    }

    @PostConstruct
    void init() {
        List<Folder> folderList = Stream.of(
                Folder.builder().color("Blue").label("Inbox").userId("kishan1301").build(),
                Folder.builder().color("Red").label("Spam").userId("kishan1301").build(),
                Folder.builder().color("Grey").label("Promotional").userId("kishan1301").build(),
                Folder.builder().color("Orange").label("Sent").userId("kishan1301").build()
        ).collect(Collectors.toList());
        folderRepository.saveAll(folderList);
    }
}
