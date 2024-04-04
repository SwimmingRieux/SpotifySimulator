package Models.User;
import java.util.ArrayList;
import java.time.LocalDate;

public class Artist extends User {
    private double income;
    private ArrayList<User> followers;
    private String biography;

    public Artist(String username, String password, String name, String email, String phoneNumber, LocalDate dateOfBirth,
                  double income, String biography) {
        super(username, password, name, email, phoneNumber, dateOfBirth);
        this.income = income;
        this.biography = biography;
        this.followers = new ArrayList<>();
    }
    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append(super.toString());

        result.append("Income : \n");
        result.append(this.getIncome());
        result.append("\n");

        result.append("Bio : \n");
        result.append(this.getBiography());
        result.append("\n");

        result.append("Followers : \n");
        for(User tmpFollower : this.getFollowers()) {
            result.append(tmpFollower.toString());
        }

        return result.toString();
    }
}

