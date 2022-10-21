package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper){
        this.fileMapper = fileMapper;
    }

    public Integer storeFile(MultipartFile file, Integer userId) throws IOException{
        String filename = file.getOriginalFilename();
        String fileSize = String.valueOf(file.getSize());
        String fileType = file.getContentType();
        return fileMapper.insert(new File(null, filename, fileType, fileSize, userId, file.getBytes()));
    }

    public boolean isFilenameAvailable(String filename, Integer userId){
        return this.fileMapper.getFilenameWithId(filename, userId) == null;
    }

    public List<File> getAllFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }

    public File getFilename(String fileName) {
        return fileMapper.getFilename(fileName);
    }

    public File getFileId(Integer fileId) {
        return fileMapper.getFileId(fileId);
    }

    public int deleteFile(Integer file_id) {
        return fileMapper.delete(file_id);
    }
}
