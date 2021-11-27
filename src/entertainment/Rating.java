package entertainment;

public final class Rating {
    private String name;
    private double score;
    private double secondScore;

    public Rating() {
    }

    public Rating(final String name, final double score) {
        this.name = name;
        this.score = score;
    }

    public Rating(final String name, final double score, final double secondScore) {
        this.name = name;
        this.score = score;
        this.secondScore = secondScore;
    }

    public double getSecondScore() {
        return secondScore;
    }

    public void setSecondScore(final double secondScore) {
        this.secondScore = secondScore;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(final double score) {
        this.score = score;
    }

}
