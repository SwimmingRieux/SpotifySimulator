package Models;

import Models.Audio.Song;

import java.util.ArrayList;

public class Album {
    private int id;
    private static int idCounter = 1;
    private String name;
    private String artistName;
    private ArrayList<Song> songs;

    public Album(String name, String artistName, ArrayList<Song> songs) {
        this.id = idCounter++;
        this.name = name;
        this.artistName = artistName;
        this.songs = songs;
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append("Name : ");
        result.append(this.name);
        result.append("\n");
        result.append("Artist : ");
        result.append(this.getArtistName());
        result.append("\n");
        return result.toString();
    }
}
