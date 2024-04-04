package Models.Data;

import Models.Audio.Audio;
import Models.Report;
import Models.User.Admin;
import Models.User.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class Database {
    private static Database instance;
    private User logedInUser;
    private ArrayList<User> users;
    private ArrayList<Report> reports;
    private ArrayList<Audio> audios;
    public User getLogedInUser() {
        return logedInUser;
    }

    public void setLogedInUser(User logedInUser) {
        this.logedInUser = logedInUser;
    }
    public void updateUser(User user)
    {
        int tmpIndx = 0;
        for(User tmpUser : this.users)
        {
            if(tmpUser.getUsername().equals(user.getUsername()))
            {
                this.users.set(tmpIndx, user);
                break;
            }
            tmpIndx++;
        }
    }

    public void removeUser(User user)
    {
        int tmpIndx = 0;
        for(User tmpUser : this.users)
        {
            if(tmpUser.getUsername().equals(user.getUsername()))
                break;
            tmpIndx++;
        }
        this.users.remove(tmpIndx);
    }

    public void addUser(User user)
    {
        users.add(user);
    }
    public void addAudio(Audio audio)
    {
        audios.add(audio);
    }
    public void addReport(Report report)
    {
        reports.add(report);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }

    public ArrayList<Audio> getAudios() {
        return audios;
    }

    public void setAudios(ArrayList<Audio> audios) {
        this.audios = audios;
    }

    public static Database getInstance()
    {
        if(instance==null) instance = new Database();
        return instance;
    }

    public Database() {
        users = new ArrayList<User>();
        users.add(Admin.getInstance("admin", "admin", "admin", "admin@admin.admin", "09999999999", LocalDate.now()));
        reports = new ArrayList<Report>();
        audios = new ArrayList<Audio>();
    }
}
