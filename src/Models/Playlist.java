package Models;


import Models.Audio.Audio;

import java.util.ArrayList;

public class Playlist {
    private static int idUpdater = 0;
    private int id;
    private String playlistName;
    private String ownerUserName;
    private ArrayList<Audio> audioFiles;

    public Playlist(String playlistName, String ownerUserName) {
        this.id = idUpdater++;
        this.playlistName = playlistName;
        this.ownerUserName = ownerUserName;
        this.audioFiles = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public ArrayList<Audio> getAudioFiles() {
        return audioFiles;
    }
    public void addAudioFiles(Audio audio)
    {
        audioFiles.add(audio);
    }

    public void setAudioFiles(ArrayList<Audio> audioFiles) {
        this.audioFiles = audioFiles;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append("Name : ");
        result.append(this.getPlaylistName());
        result.append("\n");
        result.append("User : ");
        result.append(this.getOwnerUserName());
        result.append("\n");
        return result.toString();
    }
}
