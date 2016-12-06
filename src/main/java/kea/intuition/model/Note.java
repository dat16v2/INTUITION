package kea.intuition.model;

public class Note {
    private int id;
    private int companyId;
    private String comment;
    private long timestamp;

    public Note(int id, int companyId, String comment, long timestamp) {
        setId(id);
        setCompanyId(companyId);
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
