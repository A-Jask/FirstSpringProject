package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Notes getNote(Integer NoteId);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Notes getNoteTitle(Integer noteid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Notes> getAllNotes(Integer userId);

    @Insert("INSERT INTO NOTES (noteid, notetitle, notedescription, userid)" +
            "VALUES(#{noteid}, #{notetitle}, #{notedescription}, #{noteid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteid =#{noteid}")
        int delete(Integer noteid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
        int update(Integer noteid, String noteTitle, String noteDescription);

    }

