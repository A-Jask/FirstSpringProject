package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
    private final NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public List<Notes> getAllNote(Integer userId) {
        return notesMapper.getAllNotes(userId);
    }

    public Notes getNote(Integer noteid) {
        return notesMapper.getNote(noteid);
    }

    public int createNote(Notes note) {
        return notesMapper.insert(note);
    }

    public int editNote(Integer noteid, String noteTitle, String noteDescription) {
        return notesMapper.update(noteid, noteTitle, noteDescription);
    }
    public int deleteNote(Integer noteid) {
        return notesMapper.delete(noteid);
    }
}
