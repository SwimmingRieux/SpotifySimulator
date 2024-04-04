package Views;

import Controllers.AdminController;
import Models.Audio.Audio;
import Models.Report;
import Models.User.Artist;
import java.util.ArrayList;
import java.util.Scanner;
public class AdminView {
    private AdminController adminController;
    public AdminView()
    {
        adminController = AdminController.getAdminController();
    }
    public void getInput(Scanner jin)
    {

        String input = jin.nextLine();
        String[] inputArray = input.split(" -");
        switch (inputArray[0]){
            case "Statistics":
                showStatistics(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2]));
                break;
            case "Audios":
                showAudios(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2]));
                break;
            case "Audio":
                getAudio(Integer.parseInt(inputArray[1]));
                break;
            case "Artists":
                showArtists();
                break;
            case "Artist":
                getArtist(inputArray[1]);
                break;
            case "Reports":
                showReports();
                break;
            case "Logout":
                adminController.logout();
                return;
            case "AccountInfo":
                System.out.println(adminController.accountInfo());
                break;
            default:
                System.out.println("Command not found");
        }
        getInput(jin);
    }
    private void showReports()
    {
        ArrayList<Report> reports = adminController.showReports();
        if(reports.isEmpty()) System.out.println("No reports found");
        for(Report tmpReport : reports)
        {
            System.out.println(tmpReport.toString());
        }
    }
    private void getArtist(String userName)
    {
        System.out.println(adminController.showArtistInfo(userName));
    }
    private void showArtists()
    {
        ArrayList<Artist> artists = adminController.showArtists();
        if(artists.isEmpty()) System.out.println("No Artists found");
        for(Artist tmpArtist : artists)
        {
            System.out.println(tmpArtist.toString());
        }
    }
    private void getAudio(int audioId)
    {
        System.out.println(adminController.showAudioInfo(audioId));
    }
    private void showStatistics(int pages, int pageNumber)
    {
        ArrayList<Audio> audios = adminController.showMostLiked(pages, pageNumber);
        System.out.println("Audios by likes:");
        if(audios.isEmpty()) System.out.println("No Audios");
        for(Audio tmpAudio : audios)
        {
            System.out.println(tmpAudio.toString());
        }
    }
    private void showAudios(int pages, int pageNumber)
    {
        ArrayList<Audio> audios = adminController.showAudios(pages, pageNumber);
        System.out.println("Audios :");
        if(audios.isEmpty()) System.out.println("No Audios");
        for(Audio tmpAudio : audios)
        {
            System.out.println(tmpAudio.toString());
        }
    }
}
