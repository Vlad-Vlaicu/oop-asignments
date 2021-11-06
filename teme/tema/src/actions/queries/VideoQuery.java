package actions.queries;

import entertainment.Genre;
import utils.Utils;

public class VideoQuery extends Query{
    private int year;
    private Genre genre;

    public VideoQuery(int action_id, String action_type) {
        super(action_id, action_type);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setGenre(String genre) {
        this.genre = Utils.stringToGenre(genre);
    }

    @Override
    public String execute() {
        return null;
    }
}
