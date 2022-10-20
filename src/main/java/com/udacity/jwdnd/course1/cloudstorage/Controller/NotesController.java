package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class NotesController {

    private final NotesService notesService;
    private final UserService userService;

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    public NotesController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @GetMapping("/delete_note/{noteid}")
    public String delete_note(@PathVariable("noteid") Integer noteid, Model model) {
        try{
            notesService.deleteNote(noteid);
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMsg", "Successfully deleted note");
        }catch(Exception e) {
            model.addAttribute("isError", true);
            model.addAttribute("errorMsg", "Note couldn't be deleted");
        }

        return "result";
    }

    @PostMapping("/add_note")
    public String add_or_update_note(Authentication auth, @ModelAttribute("Notes") Notes notes, Model model) {
        String username = (String) auth.getPrincipal();
        User user = userService.getUser(username);
        String error_Msg = null;

        if (notes.getNoteid() != null) {
            notesService.editNote(notes.getNoteid(), notes.getNoteTitle(), notes.getNoteDescription());
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMsg", "Note has been successfully updated");
        }else {
            try {
                notesService.createNote(new Notes(null, notes.getNoteTitle(), notes.getNoteDescription(), user.getUserId()));
                model.addAttribute("isSuccess", true);
                model.addAttribute("successMsg", "Note has been successfully created");
            }catch(Exception e) {
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "Something went wrong when creating the note, please try again");
            }
        }
        model.addAttribute("notes", notesService.getAllNote(user.getUserId()));

        return "result";
    }


}
