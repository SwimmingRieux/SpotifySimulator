package Models;
public enum PremiumPlan {
    ONE_MONTH(30, 5), TWO_MONTHS(60, 9), SIX_MONTHS(180, 14);

    private final int days;
    private final int price;

    private PremiumPlan(int days, int price) {
        this.days = days;
        this.price = price;
    }

    public int getDays() {
        return days;
    }

    public int getPrice() {
        return price;
    }
}
