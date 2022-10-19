package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.Notes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NotesMapper {
    @Select("SSELECT * FROM NOTES WHERE notes = #{notetitle}")
    Notes getNoteTitle(String noteTitle);

    @Insert("INSERT INTO NOTES (noteid, notetitle, notedescription, userid) VALUES(#{noteid}, #{notetitle}, #{notedescription}, #{noteid})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insert(Notes notes);
}
