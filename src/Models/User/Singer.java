
package Models.User;
import Models.Album;

import java.util.ArrayList;
public class Singer extends Artist {
    private ArrayList<Album> albums;

    public Singer(String username, String password, String name, String email, String phoneNumber,
                  java.time.LocalDate dateOfBirth, double income, String biography) {
        super(username, password, name, email, phoneNumber, dateOfBirth, income, biography);
        this.albums = new ArrayList<>();
    }
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder(super.toString());
        result.append("Albums : \n");
        for(Album tmpAlbum : this.getAlbums())
        {
            result.append(tmpAlbum.toString());
        }
        return result.toString();
    }
}