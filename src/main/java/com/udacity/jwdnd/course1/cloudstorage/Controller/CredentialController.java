package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @ModelAttribute("credentials")
    public Credentials getCredential(){
        return new Credentials();
    }

    @PostMapping("/newCredentials")
    public String createCredentials(Credentials credentials, Authentication auth, Model model){
        User user = userService.getUser(auth.getPrincipal().toString());
        credentials.setUserid(user.getUserId());

        if(credentials.getCredentialId() == null) {
            try {
                System.out.println("Creating new credentials!!!!");
                credentialService.createCredentials(credentials);
                model.addAttribute("is Success", true);
                model.addAttribute("successMsg", "Successfully created new credentials");
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "Credentials couldn't be created.");
            }
        }else{
            try {
                System.out.println("Updating Credentials!!!!");
                credentialService.updateCredentials(credentials);
                model.addAttribute("is Success", true);
                model.addAttribute("successMsg", "Successfully updated the selected credentials");
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "Credentials couldn't be updated.");
            }

        }
        return "result";
    }

    @GetMapping("/deleteCredendtials/{credentialid}")
    public String deleteCredentials(@PathVariable("credentialid") Integer credentialid, Model model) {
        try{
            credentialService.deleteCredentials(credentialid);
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMsg", "Successfully deleted the credential.");
        }catch(Exception e) {
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            model.addAttribute("isError", true);
            model.addAttribute("errorMsg", "Credential couldn't be deleted.");
        }

        return "result";
    }
}
