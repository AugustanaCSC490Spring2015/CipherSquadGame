package edu.augustana.csc490.ratrace.game;

/**
 * Adapted from http://code.tutsplus.com/tutorials/android-sdk-create-an-arithmetic-game-high-scores-and-state-data--mobile-18825
 */
public class Score implements Comparable<Score> {
    private int scoreNum;
    private String uuid;
    private String initials;


    public Score(String uuid, String initials, int num) {
        scoreNum = num;
        this.uuid = uuid;
        this.initials = initials;
    }

    //Works opposite of normal compareTo method to create a descending list
    @Override
    public int compareTo(Score score) {
        //return 0 if equal
        //1 if passed greater than this
        //-1 if this greater than passed
        return score.scoreNum > scoreNum ? 1 : score.scoreNum < scoreNum ? -1 : 0;
    }

    public int getScore() {
        return scoreNum;
    }

    public String getScoreText() {
        return "" + scoreNum;
    }

    public String getUuid() {
        return uuid;
    }

    public String getInitials() {
        return initials;
    }
}
