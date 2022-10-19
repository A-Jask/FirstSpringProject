package com.udacity.jwdnd.course1.cloudstorage.Controller;

import java.io.IOException;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    private final UserService userService;
    private Exception ex;

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/delete_file/{file_id}")
    public String delete_file(@PathVariable("file_id") Integer file_id, Model model) {
        try{
            fileService.deleteFile(file_id);
            model.addAttribute("isSuccess", true);
            model.addAttribute("successMsg", "Successfully deleted the file");
        }catch(Exception e) {
            model.addAttribute("isError", true);
            model.addAttribute("errorMsg", "File couldn't be deleted");
        }

        return "result";
    }

    @GetMapping(value = "/get_file/{file_id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> get_file(@PathVariable("file_id") Integer file_id) {
        File file = fileService.getFileId(file_id);
        ByteArrayResource resource = new ByteArrayResource(file.getFiledata());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(file.getContenttype())).body(resource);
    }

    @PostMapping("/new_file")
    public String upload_file(Authentication auth, @RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {
        Integer userId = userService.getUser(auth.getName()).getUserId();
        List<File> file_List = fileService.getAllFiles(userId);
        String err_msg = null;

        if (file.getBytes().length == 0) {
            err_msg = "File is empty.";
        }

        String filename = file.getOriginalFilename();
        for (File file_1: file_List){
            if (file_1.getFileId().equals(filename)) {
                err_msg = "File name \'" + filename + "\' already exists";
                break;
            }
        }

        if (err_msg == null) {
            try {
                fileService.storeFile(file, userId);
                model.addAttribute("isSuccess", true);
                model.addAttribute("successMsg", "File " + file.getOriginalFilename() + "  (Size: "+ file.getSize() + ")" + " has been uploaded");
            }catch(Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "File couldn't be uploaded");
            }
        }else {
            model.addAttribute("isError", true);
            model.addAttribute("errorMsg", err_msg);
        }
        return "result";
    }
}