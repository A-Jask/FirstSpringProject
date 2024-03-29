package com.Alex.cloudstorage.Model;

public class Credentials {
    private Integer userid;
    private Integer credentialId;
    private String password;
    private String url;
    private String username;
    private String key;

    public Credentials() {
        this.url ="";
        this.username= "";
        this.key="";
        this.password ="";
    }

    public Credentials(Integer credentialId, String url, String username, String key, String password, Integer userid) {
        this.userid = userid;
        this.credentialId = credentialId;
        this.password = password;
        this.url = url;
        this.username = username;
        this.key = key;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
