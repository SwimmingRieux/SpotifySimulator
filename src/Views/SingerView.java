package Views;

import Controllers.SingerController;
import Models.Album;
import Models.Audio.Song;
import Models.Genre;
import Models.User.User;

import java.util.ArrayList;
import java.util.Scanner;

public class SingerView {
    private SingerController singerController;
    public SingerView()
    {
        singerController = new SingerController();
    }
    public void getInput(Scanner jin) {

        String input = jin.nextLine();
        String[] inputArray = input.split(" -");
        switch (inputArray[0]) {
            case "Followers":
                showFollowers();
                break;
            case "ViewsStatistics":
                viewsStatistics();
                break;
            case "CalculateEarnings":
                calculateEarnings();
                break;
            case "NewAlbum":
                makeNewAlbum(inputArray[1]);
                break;
            case "Publish":
                publishAudio(inputArray[2], Genre.fromString(inputArray[3]),
                        inputArray[4], inputArray[5], inputArray[6],
                        Integer.parseInt(inputArray[7])); // inputArray[1] is useless since we already know we are in singer controller
                break;
            case "Logout":
                singerController.logout();
                return;
            case "AccountInfo":
                System.out.println(singerController.accountInfo());
                break;
            default:
                System.out.println("Invalid command");
        }
        getInput(jin);
    }
    private void publishAudio(String title, Genre genre, String lyrics, String link, String cover, int albumId)
    {
        singerController.addSong(title, genre, lyrics, link, cover, albumId);
    }
    private void makeNewAlbum(String albumName)
    {
        singerController.addAlbum(albumName);
    }
    private void calculateEarnings()
    {
        System.out.println(singerController.calculateIncome());
    }
    private void viewsStatistics()
    {
        System.out.println("Play counts :");
        System.out.println(singerController.getPlayedCount());
    }
    private void showFollowers()
    {
        ArrayList<User> followers = singerController.showFollowers();
        if(followers.isEmpty()) System.out.println("No followers found");
        for(User tmpUser : followers)
        {
            System.out.println(tmpUser.getName());
        }
    }
}
