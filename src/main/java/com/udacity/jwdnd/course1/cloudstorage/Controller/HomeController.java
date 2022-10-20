package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
