package Controllers;

import Models.Audio.Podcast;
import Models.Genre;
import Models.User.Podcaster;

import java.time.LocalDate;
import java.util.ArrayList;

public class PodcasterController extends ArtistController{

    public void publishPodcast(String title, Genre genre, String caption, String link, String cover)
    {
        Podcaster podcasterModel = (Podcaster) this.getArtistModel();
        Podcast podcast = new Podcast(title, this.getArtistModel().getName(), 0, 0, LocalDate.now(), genre, link, cover, caption);
        ArrayList<Podcast> podcasts = podcasterModel.getPodcasts();
        podcasts.add(podcast);
        this.getDatabase().getAudios().add(podcast);
    }

    public double calculateIncome()
    {
        int playedCount = getPlayedCount();
        this.getArtistModel().setIncome(0.5 * playedCount);
        return this.getArtistModel().getIncome();
    }
}
