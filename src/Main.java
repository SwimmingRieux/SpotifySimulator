import Models.Data.Database;
import Models.User.Admin;
import Views.AccountView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        AccountView.getUserView().getInput(new Scanner(System.in));
    }
}
