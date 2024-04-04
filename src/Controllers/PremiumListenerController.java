package Controllers;

import Models.Audio.Audio;
import Models.Playlist;
import Models.PremiumPlan;
import Models.User.NormalListener;
import Models.User.PremiumListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PremiumListenerController extends ListenerController{

    public PremiumListenerController()
    {
        super();
        daySubtracter();
    }
    public String makeNewPlaylist(String name)
    {
        ArrayList<Playlist> playlists = this.getListenerModel().getPlaylists();
        playlists.add(new Playlist(name, this.getListenerModel().getUsername()));
        this.getListenerModel().setPlaylists(playlists);
        this.getDatabase().updateUser(this.getListenerModel());
        return "Playlist added successfully";
    }


    public String addToPlaylist(String playlistName, Audio audio)
    {
        ArrayList<Playlist> playlists = this.getListenerModel().getPlaylists();
        for(Playlist tmpPlaylist : playlists)
        {
            if(tmpPlaylist.getPlaylistName().equals(playlistName))
            {
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
            PremiumListener tmpListener = (PremiumListener) this.getListenerModel(); // assign the model listener to a new variable so we can use it after deletion
            // Updating the model
            PremiumListener premiumModel = ((PremiumListener) this.getListenerModel());
            premiumModel.setExpirementDate(premiumModel.getExpirementDate().plusDays(plan.getDays()));
            premiumModel.setRemainingDays((premiumModel).getRemainingDays() + plan.getDays());
            this.setListenerModel(premiumModel);
            // Updating the Database
            this.getDatabase().updateUser(premiumModel);
            return "Premium Account updated successfully";
        }
        return "Your account credit is not enough";
    }

    public void repurchasePremium()
    {
        PremiumListener premiumModel = (PremiumListener) this.getListenerModel();
        this.getDatabase().removeUser(this.getListenerModel());
        NormalListener normalModel = (NormalListener) this.getListenerModel();
        normalModel.setExpirementDate(null);
        this.getDatabase().addUser(normalModel);
        this.getDatabase().setLogedInUser(normalModel);
    }

    private void daySubtracter()
    {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1); // 1 : number of threads in the thread pool
        executor.scheduleAtFixedRate(() -> {
            PremiumListener premiumModel = (PremiumListener) this.getListenerModel();
            if(premiumModel.getRemainingDays() == 0)
            {
                executor.shutdown();
                repurchasePremium();
                return;
            }
            premiumModel.setRemainingDays(premiumModel.getRemainingDays()-1);
            this.setListenerModel(premiumModel);
            this.getDatabase().updateUser(premiumModel);
        }, 1 , 1, TimeUnit.DAYS); // 1 = starts one day after run, 1 day distance, Timeunit = day
    }
}
