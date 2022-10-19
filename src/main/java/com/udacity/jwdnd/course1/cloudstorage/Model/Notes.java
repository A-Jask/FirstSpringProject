package com.udacity.jwdnd.course1.cloudstorage.Model;

public class Notes {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;



    public Notes(Integer noteId, String noteTitle, String noteDescription) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

        public Integer getNoteId() {
            return noteId;
        }

        public void setNoteId(Integer noteId) {
            this.noteId = noteId;
        }

        public String getNoteTitle() {
            return noteTitle;
        }

        public void setNoteTitle(String noteTitle) {
            this.noteTitle = noteTitle;
        }

        public String getNoteDescription() {
            return noteDescription;
        }

        public void setNoteDescription(String noteDescription) {
            this.noteDescription = noteDescription;
        }

    }