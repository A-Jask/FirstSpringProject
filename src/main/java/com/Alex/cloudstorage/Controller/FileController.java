package com.Alex.cloudstorage.Controller;

import com.Alex.cloudstorage.Model.File;
import com.Alex.cloudstorage.services.FileService;
import com.Alex.cloudstorage.services.UserService;
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

import java.io.IOException;


@Controller
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    private final UserService userService;

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
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
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

        if (file.getBytes().length == 0) {
            model.addAttribute("isError", true);
            model.addAttribute("errorMsg", "No file selected.");
            return "result";
        }

        if(!fileService.isFilenameAvailable(file.getOriginalFilename(), userId)){
            model.addAttribute("isError", true);
            model.addAttribute("errorMsg", "File with the same filename already exists");
            return "result";
        }

        try {
                fileService.storeFile(file, userId);
                model.addAttribute("isSuccess", true);
                model.addAttribute("successMsg", "File " + file.getOriginalFilename() + "  (Size: "+ file.getSize() + ")" + " has been uploaded");
                return "result";
            }catch(Exception e) {
                logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                model.addAttribute("isError", true);
                model.addAttribute("errorMsg", "File couldn't be uploaded");
                return "result";
            }
    }
}