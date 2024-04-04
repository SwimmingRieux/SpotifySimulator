package Views;

import Controllers.NormalListenerController;
import Models.Audio.Audio;
import Models.Genre;
import Models.Playlist;
import Models.PremiumPlan;
import Models.User.Artist;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class NormalListenerView {
    private NormalListenerController normalListenerController;
    public NormalListenerView()
    {
        normalListenerController = new NormalListenerController();
    }
    public void getInput(Scanner jin)
    {

        String input = jin.nextLine();
        String[] inputArray = input.split(" -");
        switch (inputArray[0]){
            case "GetSuggestions":
                int n = Integer.parseInt(inputArray[1]);
                suggestedAudios(n);
                break;
            case "Artists":
                getArtists();
                break;
            case "Artist":
                getArtist(inputArray[1]);
                break;
            case "Follow":
                followArtist(inputArray[1]);
                break;
            case "Search":
                search(inputArray[1]);
                break;
            case "Sort":
                int type = 0;
                switch (inputArray[1]){
                    case "L":
                        type = 1;
                        break;
                    case "P":
                        type = 2;
                        break;
                }
                sort(type);
                break;
            case "Filter":
                switch (inputArray[1]){
                    case "A":
                        filter(inputArray[2], null, null, null);
                        break;
                    case "G":
                        filter(null, Genre.fromString(inputArray[2]), null, null);
                        break;
                    case "D":
                        filter(null, null, LocalDate.parse(inputArray[2], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                LocalDate.parse(inputArray[3], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        break;
                }
                break;
            case "Add":
                addToPlaylist(inputArray[1], Integer.parseInt(inputArray[2]));
                break;
            case "ShowPlaylists":
                showPlaylists();
                break;
            case "SelectPlaylist":
                selectPlaylist(inputArray[1]);
                break;
            case "Play":
                play(Integer.parseInt(inputArray[1]));
                break;
            case "Like":
                like(Integer.parseInt(inputArray[1]));
                break;
            case "Lyric":
                getLyric(Integer.parseInt(inputArray[1]));
                break;
            case "NewPlaylist":
                makePlaylist(inputArray[1]);
                break;
            case "Followings":
                showFollowings();
                break;
            case "Report":
                report(inputArray[1], inputArray[2]);
                break;
            case "IncreaseCredit":
                addCredit(Double.parseDouble(inputArray[1]));
                break;
            case "GetPremium":
                getPremium(PremiumPlan.valueOf(inputArray[1]));
                break;
            case "Logout":
                normalListenerController.logout();
                return;
            case "AccountInfo":
                System.out.println(normalListenerController.accountInfo());
                break;
            default:
                System.out.println("Invalid command");
        }
        getInput(jin);
    }
    private void getPremium(PremiumPlan plan)
    {
        System.out.println(normalListenerController.purchasePremium(plan));
    }
    private void addCredit(double value)
    {
        normalListenerController.addCredit(value);
    }
    private void report(String artistUserName, String description)
    {
        System.out.println(normalListenerController.reportArtist(artistUserName, description));
    }

    private void showFollowings()
    {
        ArrayList<Artist> artists = normalListenerController.getFollowings();
        System.out.println("Followings :");
        if(artists.size()==0) System.out.println("No followings found");
        for(Artist tmpArtist : artists)
        {
            System.out.println(tmpArtist.getUsername());
        }
    }
    private void makePlaylist(String name)
    {
        System.out.println(normalListenerController.makeNewPlaylist(name));
    }
    private void getLyric(int audioId)
    {
        System.out.println(normalListenerController.getLyric(audioId));
    }
    private void like(int audioId)
    {
        Audio tmpAudio = normalListenerController.findAudioById(audioId);
        System.out.println(normalListenerController.likeAudio(tmpAudio.getFileName()));
    }
    private void play(int audioId)
    {
        Audio tmpAudio = normalListenerController.findAudioById(audioId);
        System.out.println(normalListenerController.playAudio(tmpAudio.getFileName()));
    }
    private void selectPlaylist(String name)
    {
        System.out.println(normalListenerController.showPlaylist(name).toString());
    }
    private void showPlaylists()
    {
        ArrayList<Playlist> playlists = normalListenerController.showPlaylists();
        System.out.println("Your playlists : \n");
        if(playlists.size() == 0) System.out.println("You have no playlists");
        for(Playlist tmpPlaylist : playlists)
        {
            System.out.println(tmpPlaylist.toString());
        }
    }
    private void addToPlaylist(String playlistName, int audioId)
    {
        Audio requestedAudio = normalListenerController.findAudioById(audioId);
        System.out.println(normalListenerController.addToPlaylist(playlistName, requestedAudio));
    }
    private void filter(String artistName, Genre genre, LocalDate startDate, LocalDate endDate)
    {
        ArrayList<Audio> filteredAudios = normalListenerController.filterAudios(artistName, genre, startDate, endDate);
        System.out.println("Audios filtered: \n");
        if(filteredAudios.size() == 0) System.out.println("No Audio found\n");
        for(Audio tmpAudio : filteredAudios)
        {
            System.out.println(tmpAudio.toString());
        }
    }
    private void sort(int type)
    {
        ArrayList<Audio> sortedAudios = normalListenerController.sortAudios(type);
        System.out.println("Audios sorted: \n");
        if(sortedAudios.size() == 0) System.out.println("No Audio found\n");
        for(Audio tmpAudio : sortedAudios)
        {
            System.out.println(tmpAudio.toString());
        }
    }
    private void search(String value)
    {
        ArrayList<Audio> searchedAudios = normalListenerController.searchAudios(value);
        System.out.println("Search result : \n");
        if(searchedAudios.size() == 0) System.out.println("No Audio found\n");
        for(Audio tmpAudio : searchedAudios)
        {
            System.out.println(tmpAudio.toString());
        }
    }
    private void followArtist(String userName)
    {
        System.out.println(normalListenerController.followArtist(userName));
    }
    private void suggestedAudios(int n)
    {
        ArrayList<Audio> suggestedAudios = normalListenerController.suggestedAudios(n);
        System.out.println("Suggested musics:\n");
        for(Audio tmpAudio : suggestedAudios)
        {
            System.out.println(tmpAudio.toString());
            System.out.println("+++++++++++++++++++++++++++++++++");
        }
    }

    private void getArtists()
    {
        ArrayList<Artist> artists = normalListenerController.getArtistsList();
        System.out.println("Artists:\n");
        for(Artist tmpArtist : artists) {
            System.out.println(tmpArtist.toString());
            System.out.println("+++++++++++++++++++++++++++++++++");
        }
    }

    private void getArtist(String userName)
    {
        System.out.println(normalListenerController.getArtistInfo(userName));
    }
}

