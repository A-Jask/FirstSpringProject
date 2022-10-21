package com.Alex.cloudstorage.Controller;

import com.Alex.cloudstorage.Model.Credentials;
import com.Alex.cloudstorage.services.*;
import com.Alex.cloudstorage.Model.Notes;
import com.Alex.cloudstorage.Model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/home")
public class HomeController {

    private final CredentialService credentialService;
    private final FileService fileService;
    private final NotesService notesService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public HomeController(FileService fileService, NotesService notesService, CredentialService credentialService,
                          UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.notesService = notesService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomeView(Authentication auth, Model model, @ModelAttribute("Notes") Notes notes, @ModelAttribute("Credentials") Credentials credentials) {
        User cur_User = userService.getCurrentUser(auth);
        Integer userId = cur_User.getUserId();
        model.addAttribute("files", fileService.getAllFiles(userId));
        model.addAttribute("notes", notesService.getAllNote(userId));
        model.addAttribute("credentials", credentialService.getAllCredentials(userId));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }
}
