package Models.User;

import Models.Audio.Audio;
import Models.Data.Database;
import Models.Genre;
import Models.Playlist;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class Listener extends User {
    private double credit;
    private ArrayList<Playlist> playlists;
    private Map<Audio, Integer> audiosPlayed;
    private LocalDate expirementDate;
    private ArrayList<Genre> favoriteGenres;

    public Listener(String username, String password, String name, String email, String phoneNumber, LocalDate dateOfBirth,
                    double credit, LocalDate expirementDate, ArrayList<Genre> favoriteGenres) {
        super(username, password, name, email, phoneNumber, dateOfBirth);
        this.credit = credit;
        if(this instanceof NormalListener) this.expirementDate = null;
        else this.expirementDate = expirementDate;
        this.playlists = new ArrayList<>();
        this.audiosPlayed = new HashMap<>();
        this.favoriteGenres = favoriteGenres;
    }
    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public Map<Audio, Integer> getAudiosPlayed() {
        return audiosPlayed;
    }

    public void setAudiosPlayed(Map<Audio, Integer> audiosPlayed) {
        this.audiosPlayed = audiosPlayed;
    }

    public LocalDate getExpirementDate() {
        return expirementDate;
    }

    public void setExpirementDate(LocalDate expirementDate) {
        this.expirementDate = expirementDate;
    }

    public ArrayList<Genre> getFavoriteGenres() {
        return favoriteGenres;
    }

    public void setFavoriteGenres(ArrayList<Genre> favoriteGenres) {
        this.favoriteGenres = favoriteGenres;
    }


    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder(super.toString());
        result.append("Credit : \n");
        result.append(this.credit);
        result.append("\n");
        return result.toString();
    }
}
