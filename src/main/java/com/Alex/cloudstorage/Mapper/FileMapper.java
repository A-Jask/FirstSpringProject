package com.Alex.cloudstorage.Mapper;

import com.Alex.cloudstorage.Model.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)" +
            "VALUES(#{filename}," + "#{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    Integer insert(File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userid = #{userId}")
    File getFilenameWithId(String filename, Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFilename(String filename);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileId(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int delete(Integer fileId);
}