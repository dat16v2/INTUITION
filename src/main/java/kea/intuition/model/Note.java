package kea.intuition.model;

public class Note {
    private int id;
    private int companyId;
    private int userId;
    private String comment;
    private long timestamp;

    public Note() {
        setId(-1);
        setCompanyId(-1);
        setUserId(-1);
        setComment("");
        setTimestamp(0);
    }

    public Note(int id, int companyId, int userId, String comment, long timestamp) {
        setId(id);
        setCompanyId(companyId);
        setUserId(userId);
        setComment(comment);
        setTimestamp(timestamp);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int company_id) {
        this.companyId = company_id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
