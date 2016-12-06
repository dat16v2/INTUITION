package kea.intuition.model;

public class Reminder {
    private int id;
    private long date;
    private int noteId;

    public Reminder() {
        setId(-1);
        setDate(System.currentTimeMillis());
        setNoteId(-1);
    }

    public Reminder(int id, long date, int noteId) {
        setId(id);
        setDate(date);
        setNoteId(noteId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }
}
