package Models.User;
import Models.Genre;

import java.time.LocalDate;
import java.util.*;
public class NormalListener extends Listener {
    private static final int SONGS_PER_PLAYLIST_LIMIT = 10;
    private static final int PLAYLIST_LIMIT = 3;

    public NormalListener(String username, String password, String name, String email, String phoneNumber,
                          LocalDate dateOfBirth, double credit, LocalDate expirementDate, ArrayList<Genre> favoriteGenres) {
        super(username, password, name, email, phoneNumber, dateOfBirth, credit, expirementDate, favoriteGenres);
    }
    public int getSongsPerPlaylistLimit() {
        return SONGS_PER_PLAYLIST_LIMIT;
    }

    public int getPlaylistLimit() {
        return PLAYLIST_LIMIT;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder(super.toString());
        result.append("Songs per playlist : ");
        result.append(SONGS_PER_PLAYLIST_LIMIT);
        result.append("\n");

        result.append("playlists count limit : ");
        result.append(PLAYLIST_LIMIT);
        result.append("\n");
        return  result.toString();
    }
}
