package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("INSERT INTO CREDENTIALS (url, username, key, password, userid)" +
                            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userid}")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credentials credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credentials> getAllCredentials(Integer userid);

    @Select("SELECT key FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    String getKeyByCredentialId(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credentials getCredentialById(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialId = #{credentialId}")
    int updateCredentials(Credentials credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void deleteCredentials(Integer credentialId);

}