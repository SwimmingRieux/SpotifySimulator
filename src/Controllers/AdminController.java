package Controllers;

import Models.Audio.Audio;
import Models.Data.Database;
import Models.Report;
import Models.User.Admin;
import Models.User.Artist;
import Models.User.User;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AdminController extends UserController{
    private static AdminController adminController;
    private Database database;
    private Admin adminModel;
    private AdminController()
    {
        database = Database.getInstance();
        adminModel = Admin.getInstance();
    }
    public static AdminController getAdminController()
    {
        if(adminController == null) adminController = new AdminController();
        return adminController;
    }
    public ArrayList<Audio> showMostLiked(int pages, int pageNumber)
    {
        ArrayList<Audio> result = database.getAudios();
        result = result.stream().sorted((aud1, aud2) -> aud1.getLikesCount() - aud2.getLikesCount())
                .collect(Collectors.toCollection(ArrayList::new));
        return paging(pages, pageNumber, result);
    }
    private Artist findArtistByUserName(String userName)
    {
        Artist selectedArtist = null;
        for(User tmpUser : database.getUsers()) if(tmpUser instanceof Artist)
        {
            Artist tmpArtist = (Artist) tmpUser;
            if(tmpArtist.getUsername().equals(userName)) selectedArtist = tmpArtist;
            break;
        }
        return selectedArtist;
    }
    public String showArtistInfo(String artistUserName)
    {
        Artist selectedArtist = findArtistByUserName(artistUserName);
        if(selectedArtist == null) return "No artist found";
        return selectedArtist.toString();
    }

    private Audio findAudioById(int audioId)
    {
        Audio selectedAudio = null;
        for(Audio tmpAudio : database.getAudios())
        {
            if(tmpAudio.getId() == audioId) selectedAudio = tmpAudio;
            break;
        }
        return selectedAudio;
    }
    public String showAudioInfo(int audioId)
    {
        Audio selectedAudio = findAudioById(audioId);
        if(selectedAudio == null) return "No audio found";
        return selectedAudio.toString();
    }
    public ArrayList<Artist> showArtists()
    {
        ArrayList<Artist> result = new ArrayList<>();
        for(User tmpUser : database.getUsers())
        {
            if(tmpUser instanceof Artist) result.add((Artist)tmpUser);
        }
        return result;
    }

    private ArrayList<Audio> paging(int pages, int pageNumber, ArrayList<Audio> audios)
    {
        ArrayList<Audio> result = new ArrayList<>();
        int audiosPerPage = audios.size() / pages;
        if(audios.size() % pages != 0) audiosPerPage++;
        for(int tmpIndx = (pageNumber-1)*audiosPerPage ; //start at zero
            audios.get(tmpIndx)!=null && tmpIndx < (pageNumber*audiosPerPage); tmpIndx++)
        {
            result.add(audios.get(tmpIndx));
        }
        return result;
    }
    public ArrayList<Report> showReports()
    {
        return database.getReports();
    }
    public ArrayList<Audio> showAudios(int pages, int pageNumber)
    {
        return paging(pages, pageNumber, database.getAudios());
    }
    public String showAdminInfo()
    {
        return adminModel.toString();
    }
}
