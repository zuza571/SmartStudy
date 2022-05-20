package android.smartstudy;

import java.util.List;

public class User {
    private int id;
    private String name;
    private String surname;
    private String university;
    private String login;
    private String password;

    public User() {}

    public User(String name, String surname, String university, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.university = university;
        this.login = login;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUniversity() {
        return university;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public User current_user (User currentUser, List<String> data){
        int x = Integer.parseInt(data.get(0));
        currentUser.setId(x);
        currentUser.setName(data.get(1));
        currentUser.setSurname(data.get(2));
        currentUser.setUniversity(data.get(3));
        currentUser.setLogin(data.get(4));
        currentUser.setPassword(data.get(5));
        return currentUser;
    }
}
