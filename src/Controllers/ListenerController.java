package Controllers;

import Extra.AIRecommender;
import Models.*;
import Models.Audio.Audio;
import Models.Audio.Podcast;
import Models.Audio.Song;
import Models.Data.Database;
import Models.PremiumPlan;
import Models.User.Artist;
import Models.User.Listener;
import Models.User.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public abstract class ListenerController extends UserController{
    private Database database;
    private AIRecommender aIrecommender;

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
    private Listener listenerModel;

    public Listener getListenerModel() {
        return listenerModel;
    }

    public void setListenerModel(Listener listenerModel) {
        this.listenerModel = listenerModel;
    }
    public ListenerController()
    {
        this.aIrecommender = AIRecommender.getAIrecommender();
        this.database = Database.getInstance();
        listenerModel = (Listener) this.database.getLogedInUser();
    }
    public void addCredit(double value) {
        listenerModel.setCredit(listenerModel.getCredit() + value);
        database.updateUser(this.listenerModel);
    }

    public ArrayList<Audio> searchAudios(String value)
    {
        ArrayList<Audio> audios = database.getAudios();
        ArrayList<Audio> result = audios.stream()
                .filter(aud -> aud.getArtistName().contains(value) || aud.getFileName().contains(value))
                .collect(Collectors.toCollection(ArrayList::new));
        return result;
    }
    public ArrayList<Audio> sortAudios(int type)
    {
        ArrayList<Audio> audios = database.getAudios();
        ArrayList<Audio> result = new ArrayList<>();
        switch (type)
        {
            case 1:
                result = audios.stream()
                        .sorted((aud1, aud2) -> aud1.getLikesCount() - aud2.getLikesCount())
                        .collect(Collectors.toCollection(ArrayList::new));
                break;
            case 2:
                result = audios.stream()
                        .sorted((aud1, aud2) -> aud1.getPlayCount() - aud2.getPlayCount())
                        .collect(Collectors.toCollection(ArrayList::new));
                break;
        }
        return result;
    }

    public ArrayList<Audio> filterAudios(String artistName, Genre genre, LocalDate startDate, LocalDate endDate)
    {
        ArrayList<Audio> result = database.getAudios();
        if(artistName != null)
        {
           result = result.stream()
                    .filter(aud -> aud.getArtistName().equals(artistName))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        if(genre != null)
        {
            result = result.stream()
                    .filter(aud -> aud.getGenre().equals(genre))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        if(startDate != null && endDate != null)
        {
            result = result.stream()
                    .filter(aud -> aud.getPublishDate().isBefore(endDate) && aud.getPublishDate().isAfter(startDate))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return result;
    }

    public ArrayList<Audio> suggestedAudios(int n)
    {
        return aIrecommender.recommender(n, this.getListenerModel());
    }

    public String playAudio(String audioName)
    {
        for(Audio tmpAudio : database.getAudios())
        {
            if(tmpAudio.getFileName().equals(audioName))
            {
                aIrecommender.addRating(this.getListenerModel(), tmpAudio, 0.2);
                Map<Audio, Integer> playCountMap = this.getListenerModel().getAudiosPlayed();
                if(playCountMap.containsKey(tmpAudio))
                {
                    int current = playCountMap.get(tmpAudio);
                    playCountMap.put(tmpAudio, ++current);
                }
                else
                {
                    playCountMap.put(tmpAudio, 1);
                }
                tmpAudio.setLikesCount(tmpAudio.getLikesCount()+1);
                return tmpAudio.getAudioLink();
            }
        }
        return "Audio file not found";
    }

    public String likeAudio(String audioName)
    {
        for(Audio audio : database.getAudios())
        {
            if(audio.getFileName().equals(audioName))
            {
                aIrecommender.addRating(this.getListenerModel(), audio, 1);

                audio.setLikesCount(audio.getLikesCount()+1);
                return "Audio liked";
            }
        }
        return "Audio file not found";
    }

    public ArrayList<Artist> getFollowings()
    {
        ArrayList<Artist> result = new ArrayList<>();
        for(User tmpUser : database.getUsers())
        {
            if(tmpUser instanceof Artist)
            {
                Artist tmpArtist = (Artist) tmpUser;
                if(tmpArtist.getFollowers().contains(this.getListenerModel()))
                    result.add(tmpArtist);
            }
        }
        return result;
    }

    private Artist findArtistByUserName(String userName)
    {
        Artist selectedArtist = null;
        for(User tmpUser : database.getUsers()) if(tmpUser instanceof Artist)
        {
            Artist tmpArtist = (Artist) tmpUser;
            if(tmpArtist.getUsername().equals(userName))
            {
                selectedArtist = tmpArtist;
                break;
            }
        }
        return selectedArtist;
    }

    public String reportArtist(String artistUserName, String description)
    {
        Artist reportedArtist = findArtistByUserName(artistUserName);
        if(reportedArtist == null) return "No Artist found";
        database.addReport(new Report(this.getListenerModel(), reportedArtist,  description));
        return "Report Added successfully";
    }

    public ArrayList<Artist> getArtistsList()
    {
        ArrayList<Artist> result = new ArrayList<>();
        for(User tmpUser : database.getUsers())
        {
            if(tmpUser instanceof Artist)
            {
                result.add((Artist)tmpUser);
            }
        }
        return result;

    }

    public String getArtistInfo(String userName)
    {
        Artist selectedArtist = findArtistByUserName(userName);
        if(selectedArtist == null) return "No artist found";
        return selectedArtist.toString();
    }

    public ArrayList<Audio> getArtistAudios(String artistUserName)
    {
        ArrayList<Audio> artistAudios = new ArrayList<>();
        Artist selectedArtist = findArtistByUserName(artistUserName);
        if(selectedArtist == null) return artistAudios;
        for(Audio tmpAudio : database.getAudios())
        {
            if(tmpAudio.getArtistName().equals(artistUserName)) artistAudios.add(tmpAudio);
        }
        return artistAudios;
    }

    public String getLyric(int audioId)
    {
        Audio tmpAudio = findAudioById(audioId);
        if(tmpAudio == null) return "No audio found\n";
        if(tmpAudio instanceof Song) {
            Song tmpSong = (Song) tmpAudio;
            return tmpSong.getLyrics();
        }
        else
        {
            Podcast tmpPodcast = (Podcast) tmpAudio;
            return tmpPodcast.getCaption();
        }
    }

    public String followArtist(String userName)
    {
        Artist selectedArtist = findArtistByUserName(userName);
        if(selectedArtist == null) return "No Artist found";
        ArrayList<User> followers = selectedArtist.getFollowers();
        followers.add(this.getListenerModel());
        return "Artist followed by you";
    }

    public ArrayList<Playlist> showPlaylists()
    {
        return this.getListenerModel().getPlaylists();
    }

    public Playlist showPlaylist(String playlistName)
    {
        for(Playlist tmpPlaylist : this.getListenerModel().getPlaylists())
        {
            if(tmpPlaylist.getPlaylistName().equals(playlistName)) return tmpPlaylist;
        }
        return null; // in case playlistName was invalid
    }
    public String showUserInformation()
    {
        return this.getListenerModel().toString();
    }
    public abstract String makeNewPlaylist(String name);
    public abstract String addToPlaylist(String playistName, Audio audio);
    public abstract String purchasePremium(PremiumPlan plan);

    public Audio findAudioById(int audioId)
    {
        Optional<Audio> result = database.getAudios().stream().filter(aud -> aud.getId() == audioId)
                .findFirst();
        if(result.isPresent()) return result.get();
        else return null;

    }

}
