package entertainment;

public class Rating {
    private String name;
    private double score;
    private double secondScore;

    public Rating() {
    }

    public Rating(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public Rating(String name, double score, double secondScore) {
        this.name = name;
        this.score = score;
        this.secondScore = secondScore;
    }

    public double getSecondScore() {
        return secondScore;
    }

    public void setSecondScore(double secondScore) {
        this.secondScore = secondScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
