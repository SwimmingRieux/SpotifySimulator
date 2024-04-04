package Extra;

import Models.Audio.Audio;
import Models.Data.Database;
import Models.User.Listener;
import Models.User.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AIRecommender {
    private Map<Listener, HashMap<Audio,Double>> usersRatings;
    private static AIRecommender aIrecommender;
    private Database database;
    private AIRecommender()
    {
        database = Database.getInstance();
        usersRatings = new HashMap<>();
    }

    public Database getDatabase() {
        return database;
    }

    public static AIRecommender getAIrecommender()
    {
        if(aIrecommender == null) aIrecommender = new AIRecommender();
        return aIrecommender;
    }
    public void addRating(Listener listener, Audio audio, double score)
    {
        if(!usersRatings.containsKey(listener))
        {
            usersRatings.put(listener, new HashMap<>());
        }
        Map<Audio, Double> userRating = usersRatings.get(listener);
        if(!userRating.containsKey(audio))
        {
            userRating.put(audio, 0.0);
        }
        int genreCoefficient = 1;
        if(listener.getFavoriteGenres().contains(audio.getGenre())) genreCoefficient *= 2;
        userRating.put(audio, userRating.get(audio) + score*genreCoefficient);
        // where to get scores?
    }

    public double computeSimilarity(Listener listener1, Listener listener2)
    {
        // Cosine similaritiy formula:
        // A.B / ||A||B||
        // we should calculate the above formula for each dimension and then sum them up
        //
        if(listener1.equals(listener2)) return 0.1; // reducing the chance of suggesting songs that user have already heard
        if(usersRatings.get(listener1) == null) usersRatings.put(listener1, new HashMap<>());
        if(usersRatings.get(listener2) == null) usersRatings.put(listener2, new HashMap<>());
        Map<Audio, Double> user1Ratings = usersRatings.get(listener1);
        Map<Audio, Double> user2Ratings = usersRatings.get(listener2);
        if(user1Ratings.size() == 0 || user2Ratings.size() == 0) return 0; // since one of them is a zero-vector, the formula gives us 0
        double avgRating1 = 0.0;
        double avgRating2 = 0.0;
        int cnt1 = 0;
        int cnt2 = 0;
        for(Audio tmpAudio : user1Ratings.keySet())
        {
            avgRating1 += user1Ratings.get(tmpAudio);
            cnt1++;
        }
        avgRating1 /= cnt1;

        for(Audio tmpAudio : user2Ratings.keySet())
        {
            avgRating2 += user2Ratings.get(tmpAudio);
            cnt2++;
        }
        avgRating2 /= cnt2;

        double dotProduct = 0.0;
        double powSum1 = 0.0;
        double powSum2 = 0.0;
        // iterating over all dimensions, each dimension is an audio.
        for(Audio tmpAudio : user1Ratings.keySet())
        {
            if(user2Ratings.containsKey(tmpAudio))
            {
                dotProduct += (user1Ratings.get(tmpAudio) - avgRating1) * (user2Ratings.get(tmpAudio) - avgRating2);
                powSum1 += (user1Ratings.get(tmpAudio) - avgRating1) * (user1Ratings.get(tmpAudio) - avgRating1);
                powSum2 += (user2Ratings.get(tmpAudio) - avgRating2) * (user2Ratings.get(tmpAudio) - avgRating2);
            }
        }
        return dotProduct / (Math.sqrt(powSum1) * Math.sqrt(powSum2));
    }

    public ArrayList<Audio> recommender(int n, Listener listener)
    {
        Map<Audio, Double> recommendedScores = new HashMap<>();
        for(User tmpUser : getDatabase().getUsers())
        {
            if(tmpUser instanceof Listener)
            {

                Listener otherListener = (Listener) tmpUser;
                //if(!otherListener.equals(listener))
                //{
                    double similarity = computeSimilarity(listener, otherListener);

                    Map<Audio, Double> otherListenerRatings = usersRatings.get(otherListener);

                    for (Audio tmpAudio : otherListenerRatings.keySet()) {
                        if (!recommendedScores.containsKey(tmpAudio)) {
                            recommendedScores.put(tmpAudio, 0.0);
                        }
                        recommendedScores.put(tmpAudio, recommendedScores.get(tmpAudio) +
                                otherListenerRatings.get(tmpAudio) * similarity);
                    }
                //}
            }
        }
        ArrayList<Map.Entry<Audio, Double>> scoresArray = new ArrayList<>(recommendedScores.entrySet());
        scoresArray = scoresArray.stream().sorted((entry1, entry2) -> Double.compare(entry1.getValue(), entry2.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Audio> finalResult = new ArrayList<>();
        int indx = 0;
        for(Map.Entry<Audio, Double> tmpEntry : scoresArray)
        {
            if((indx++) == n) break;
            finalResult.add(tmpEntry.getKey());
        }
        return finalResult;
    }
}
