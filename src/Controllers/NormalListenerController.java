package Controllers;

import Models.Audio.Audio;
import Models.Audio.Podcast;
import Models.Audio.Song;
import Models.Playlist;
import Models.PremiumPlan;
import Models.User.NormalListener;
import Models.User.Podcaster;
import Models.User.PremiumListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class NormalListenerController extends ListenerController {

    public String makeNewPlaylist(String name)
    {
        ArrayList<Playlist> playlists = this.getListenerModel().getPlaylists();
        if(playlists.size() < 3)
        {
            playlists.add(new Playlist(name, this.getListenerModel().getUsername()));
            this.getListenerModel().setPlaylists(playlists);
            this.getDatabase().updateUser(this.getListenerModel());
            return "Playlist added successfully";
        }
        return "You should upgrade your account to premium in order to add more than 3 Playlists";
    }

    public String addToPlaylist(String playlistName, Audio audio)
    {
        ArrayList<Playlist> playlists = this.getListenerModel().getPlaylists();
        for(Playlist tmpPlaylist : playlists)
        {
            if(tmpPlaylist.getPlaylistName().equals(playlistName))
            {
                if(tmpPlaylist.getAudioFiles().size() > 10)
                    return "You must upgrade your account in order to add more than 10 Audios in a playlist";
                tmpPlaylist.addAudioFiles(audio);
                this.getListenerModel().setPlaylists(playlists);
                this.getDatabase().updateUser(this.getListenerModel());
                return "Audio added successfully";
            }
        }
        return "No such playlist found";
    }

    public String purchasePremium(PremiumPlan plan)
    {
        if(this.getListenerModel().getCredit() >= plan.getPrice())
        {
            this.getListenerModel().setCredit(this.getListenerModel().getCredit() - plan.getPrice()); // subtract credit
            LocalDate subscriptionEndDate = LocalDate.now();
            subscriptionEndDate = subscriptionEndDate.plusDays(plan.getDays()); // end date of subscription
            NormalListener tmpListener = (NormalListener) this.getListenerModel(); // assign the model listener to a new variable so we can use it after deletion
            this.getDatabase().removeUser(this.getListenerModel());

            PremiumListener tmpPremium = new PremiumListener(tmpListener.getUsername(), tmpListener.getPassword(),
                    tmpListener.getName(), tmpListener.getEmail(), tmpListener.getPhoneNumber(), tmpListener.getDateOfBirth(),
                    tmpListener.getCredit(), subscriptionEndDate, plan.getDays(), tmpListener.getFavoriteGenres());
            this.setListenerModel(tmpPremium);
            this.getDatabase().addUser(tmpPremium);
            this.getDatabase().setLogedInUser(tmpPremium);
            return "Premium Account purchased successfully";
        }
        return "Your account credit is not enough";
    }
}
