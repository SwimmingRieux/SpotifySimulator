package Models.User;

import java.time.LocalDate;

public abstract class User {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public User(String username, String password, String name, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append("UserName : \n");
        result.append(this.getUsername());
        result.append("\n");

        result.append("Name : \n");
        result.append(this.getName());
        result.append("\n");

        result.append("Email : \n");
        result.append(this.getEmail());
        result.append("\n");

        result.append("Birth Date : \n");
        result.append(this.getDateOfBirth().toString());
        result.append("\n");

        result.append("Phone : \n");
        result.append(this.getPhoneNumber());
        result.append("\n");

        return result.toString();
    }
}

