package Views;

import Controllers.PodcasterController;
import Models.Genre;
import Models.User.User;

import java.util.ArrayList;
import java.util.Scanner;

public class PodcasterView {
    private PodcasterController podcasterController;
    public PodcasterView()
    {
        podcasterController = new PodcasterController();
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
            case "Publish":
                publishAudio(inputArray[2], Genre.fromString(inputArray[3]),
                        inputArray[4], inputArray[5], inputArray[6]);
                // inputArray[1] is useless since we already know we are in podcaster controller
                // inputArray[7] is also useless since we don't need albumId

                break;
            case "Logout":
                podcasterController.logout();
                return;
            case "AccountInfo":
                System.out.println(podcasterController.accountInfo());
                break;
            default:
                System.out.println("Invalid command");
        }
        getInput(jin);
    }

    private void publishAudio(String title, Genre genre, String caption, String link, String cover)
    {
        podcasterController.publishPodcast(title, genre, caption, link, cover);
    }
    private void calculateEarnings()
    {
        System.out.println(podcasterController.calculateIncome());
    }
    private void viewsStatistics()
    {
        System.out.println("Play counts :");
        System.out.println(podcasterController.getPlayedCount());
    }
    private void showFollowers()
    {

        ArrayList<User> followers = podcasterController.showFollowers();
        if(followers.isEmpty()) System.out.println("No followers found");
        for(User tmpUser : followers)
        {
            System.out.println(tmpUser.getName());
        }
    }
}
