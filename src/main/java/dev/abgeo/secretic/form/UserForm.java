package dev.abgeo.secretic.form;

public class UserForm {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private String passwordOld;

    private String passwordConfirm;

    private boolean agreedWithTerms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public boolean isAgreedWithTerms() {
        return agreedWithTerms;
    }

    public void setAgreedWithTerms(boolean agreedWithTerms) {
        this.agreedWithTerms = agreedWithTerms;
    }

}
