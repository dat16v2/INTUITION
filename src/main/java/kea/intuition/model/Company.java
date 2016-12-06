package kea.intuition.model;

public class Company {
    private int id;
    private String name;
    private double score;
    private String email;
    private String phoneNumber;
    private String phoneNumberPrefix;
    private String phoneNumberCountryCallingCode;
 
    public Company() {
        setId(-1);
        setName("");
        setScore(0.0);
        setEmail("");
        setPhoneNumber("");
        setPhoneNumberPrefix("00");
        setPhoneNumberCountryCallingCode("45");
    }

    public Company(int id, String name, String email, double score, String phoneNumberPrefix, String phoneNumberCountryCallingCode, String phoneNumber) {
        setId(id);
        setName(name);
        setScore(score);
        setEmail(email);
        setPhoneNumber(phoneNumber);

        if (phoneNumberPrefix.equals("-1")) {
            setPhoneNumberPrefix("00");
        } else {
            setPhoneNumberPrefix(phoneNumberPrefix);
        }

        if (phoneNumberCountryCallingCode.equals("-1")) {
            setPhoneNumberCountryCallingCode("45");
        } else {
            setPhoneNumberCountryCallingCode(phoneNumberCountryCallingCode);
        }
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumberPrefix() {
        return phoneNumberPrefix;
    }

    public void setPhoneNumberPrefix(String phoneNumberPrefix) {
        this.phoneNumberPrefix = phoneNumberPrefix;
    }

    public String getPhoneNumberCountryCallingCode() {
        return phoneNumberCountryCallingCode;
    }

    public void setPhoneNumberCountryCallingCode(String phoneNumberCountryCallingCode) {
        this.phoneNumberCountryCallingCode = phoneNumberCountryCallingCode;
    }

    public String getFormattedPhoneNumber() {
        if (this.phoneNumberPrefix.equals("-1")) {
            // Default to DK prefix
            this.phoneNumberPrefix = "00";
        }

        if (this.phoneNumberCountryCallingCode.equals("-1")) {
            // Default to DK country calling code
            this.phoneNumberCountryCallingCode = "45";
        }

        return String.format("+%s%s %s", this.phoneNumberPrefix, this.phoneNumberCountryCallingCode, this.phoneNumber);
    }
}