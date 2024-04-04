package Models.User;

import Models.Genre;

import java.util.ArrayList;
import java.time.LocalDate;

public class PremiumListener extends Listener {
    private int remainingDays;

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public PremiumListener(String username, String password, String name, String email, String phoneNumber, LocalDate dateOfBirth, double accountCredit, LocalDate subscriptionEndDate, int remainingDays, ArrayList<Genre> favoriteGenres) {
        super(username, password, name, email, phoneNumber, dateOfBirth, accountCredit, subscriptionEndDate, favoriteGenres);
        this.remainingDays = remainingDays;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder(super.toString());
        result.append("Premium remaining days : ");
        result.append(this.getRemainingDays());
        result.append("\n");
        return result.toString();
    }
}
