package com.Alex.cloudstorage.services;

import com.Alex.cloudstorage.Mapper.CredentialMapper;
import com.Alex.cloudstorage.Model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final HashService hashService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, HashService hashService){
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.hashService = hashService;
    }

    public int createCredentials(Credentials credentials){
        String password = credentials.getPassword();
        String encodedKey = generateKey();
        String hashedPassword = encryptionService.encryptValue(password, encodedKey);
        credentials.setKey(encodedKey);
        credentials.setPassword(hashedPassword);
        return credentialMapper.insert(credentials);
    }

    private String generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
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