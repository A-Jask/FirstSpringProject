package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService){
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public void createCredentials(Credentials credentials){
        credentials.setKey(this.encryptionService.generateKey());
        credentials.setPassword(this.encryptPassword(credentials));
        this.credentialMapper.insert(credentials);
    }

    public String encryptPassword(Credentials credentials){
        return this.encryptionService.encryptValue(credentials.getPassword(), credentials.getKey());
    }

    public String decryptPassword(Credentials credentials){
        return this.encryptionService.decryptValue(credentials.getPassword(), credentials.getKey());
    }

    public void updateCredentials(Credentials credentials){
        String key = this.credentialMapper.getKeyByCredentialId(credentials.getCredentialId());
        String encodedPassword = this.encryptionService.encryptValue(credentials.getPassword(), key);
        credentials.setPassword(encodedPassword);
        this.credentialMapper.updateCredentials(credentials);
    }

    public List<Credentials> getAllCredentials(Integer userId){
        return credentialMapper.getAllCredentials(userId);
    }

    public void deleteCredentials(Integer credentialId){
        credentialMapper.deleteCredentials(credentialId);
    }

    public Credentials getCredentialsByItsId(Integer credentialid){
        return credentialMapper.getCredentialById(credentialid);
    }


}