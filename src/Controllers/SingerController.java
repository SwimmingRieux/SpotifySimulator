package Controllers;

import Models.Album;
import Models.Audio.Audio;
import Models.Audio.Song;
import Models.Genre;
import Models.User.Singer;
import Extra.Extra;

import java.time.LocalDate;
import java.util.ArrayList;

public class SingerController extends ArtistController {

    public void addAlbum(String albumName)
    {
        Singer singerModel = (Singer)this.getArtistModel();
        Album album = new Album(albumName, singerModel.getName(), new ArrayList<>());
        ArrayList<Album> singerModelAlbums = singerModel.getAlbums();
        singerModelAlbums.add(album);
    }
    public String addSong(String title, Genre genre, String lyrics, String link, String cover, int albumId)
    {
        Singer singerModel = (Singer)this.getArtistModel();
        ArrayList<Album> singerModelAlbums = singerModel.getAlbums();
        int tmpIndx = 0;
        for(Album tmpAlbum : singerModelAlbums)
        {
            if(tmpAlbum.getId() == albumId) break;
            tmpIndx++;
        }
        if(tmpIndx == singerModelAlbums.size()) return "No such album found";

        Album selectedAlbum = singerModelAlbums.get(tmpIndx);
        ArrayList<Song> selectedAlbumSongs = selectedAlbum.getSongs();
        Song song = new Song(title, this.getArtistModel().getName(), 0, 0, LocalDate.now(), genre,
                link, cover, lyrics);
        selectedAlbumSongs.add(song);
        this.getDatabase().getAudios().add(song);
        return "song added to the album successfully";
    }

    public double calculateIncome()
    {
        int playedCount = getPlayedCount();
        this.getArtistModel().setIncome(0.4 * playedCount);
        return this.getArtistModel().getIncome();
    }

}
