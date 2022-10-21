package com.Alex.cloudstorage.Controller;

import com.Alex.cloudstorage.Model.Credentials;
import com.Alex.cloudstorage.Model.User;
import com.Alex.cloudstorage.services.CredentialService;
import com.Alex.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
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

    @PostMapping("/newCredentials")
    public String createCredentials(@ModelAttribute("credentials") Credentials credentials, Authentication auth, Model model){
        User user = userService.getUser(auth.getPrincipal().toString());
        credentials.setUserid(user.getUserId());

        if(credentials.getCredentialId() == null) {
            try {
                credentialService.createCredentials(credentials);
                model.addAttribute("isSuccess", true);
                model.addAttribute("successMsg", "Successfully created new credentials.");
            } catch (Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "Credentials couldn't be created.");
            }
        }else{
            try {
                credentialService.updateCredentials(credentials);
                model.addAttribute("isSuccess", true);
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