package Models;

public enum Genre {
        ROCK("Rock"), POP("Pop") ,JAZZ("Jazz") ,HIPHOP("HipHop") ,
        COUNTRY("Country"),TRUE_CRIME("TrueCrime"), SOCIETY("Society"),
        INTERVIEW("Interview"), HISTORY("History");
        private final String asString;

    public String getAsString() {
        return asString;
    }

    private Genre(String asString)
        {
            this.asString = asString;
        }
        public static Genre fromString(String text) {
            Genre selectedGenre = null;
            for (Genre genre : Genre.values()) {
                if (genre.asString.equalsIgnoreCase(text)) {
                    selectedGenre = genre;
                }
            }
            return selectedGenre;
        }
}
