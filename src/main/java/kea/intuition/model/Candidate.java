package kea.intuition.model;

import kea.intuition.Tools;

public class Candidate {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String description;

    public Candidate() {
        setId(-1);
        setName("");
        setPhone("");
        setEmail("");
        setDescription("");
    }

    public Candidate( int id, String name, String phone, String email, String description ) {
        setId(id);
        setName(name);
        setPhone(phone);
        setEmail(email);
        setDescription(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHash() {
        return Tools.getCandidateHash(this);
    }
}
