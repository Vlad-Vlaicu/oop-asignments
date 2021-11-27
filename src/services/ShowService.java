package services;

import common.Constants;
import dao.ShowDao;
import database.Database;
import entertainment.Genre;
import entities.Season;
import entities.Show;
import fileio.SerialInputData;
import utils.Utils;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public final class ShowService {
    private final ShowDao showDao;

    public ShowService() {
        Database database = Database.getInstance();
        showDao = new ShowDao(database);
    }
    /** method creates a Show object and attempts to insert it into the database
     * @param data is raw data used to create a Show object */
    public void createShow(final SerialInputData data) {
        Show show = new Show();
        show.setName(data.getTitle());
        show.setYear(data.getYear());
        show.setCast(data.getCast());
        show.setNumberOfSeasons(data.getNumberSeason());
        show.setSeasons(data.getSeasons());

        List<Genre> genreList = data.getGenres().stream()
                .map(Utils::stringToGenre)
                .collect(Collectors.toList());
        show.setGenres(genreList);
        createShow(show);
    }

    /** method atempts to insert a Show object into the database
     * @param show is the object to be inserted into database */
    public void createShow(final Show show) {
        if (!checkIfShowExists(show)) {
            showDao.save(show);
        }
    }

    /** method searches the database for a Show instance given as parameter
     * @param show is the object searched into database
     * @return the id of the object if the object is found in the database,
     * or not_found constants in case of failure
     * */
    public int findShow(final Show show) {
        List<Show> list = getAllShows();
        for (Show s : list) {
            if (s.equals(show)) {
                return s.getId();
            }
        }
        return Constants.NOT_FOUND;
    }

    /** method searches the database for a Show instance having its name
     * given as parameter
     * @param title is the String used to search in the database
     * @return an instance of class Show if the object was found and a null
     * reference otherwise
     * */
    public Show findShowByTitle(final String title) {
        List<Show> list = getAllShows();
        for (Show s : list) {
            String name = s.getName();
            if (name.equals(title)) {
                return s;
            }
        }
        return null;
    }

    /** method checks if the object given as parameter is persisted in the database
     * @param show is the object the method looks after in the database
     * @return true if the object is found in the database and false in case of failure
     * */
    public boolean checkIfShowExists(final Show show) {
        int id = findShow(show);
        return id != Constants.NOT_FOUND;
    }

    /** method attempts to remove the object given as parameter from the database
     * @param show is the object to be removed
     * @return is true if the object was removed from the database and false otherwise
     * */
    public boolean removeShow(final Show show) {
        int id = findShow(show);
        if (id == Constants.NOT_FOUND) {
            return false;
        }
        showDao.delete(id);
        return true;
    }

    /** method rates the given show, the season number and a grade
     * @param show is the show to be rated
     * @param season is an integer representing the season number
     * @param grade is a double and represents the grade used in grading the show
     * */
    public void rateShow(final double grade, final int season, final Show show) {
        Season s = show.getSeasons().get(season - 1);
        s.getRatings().add(grade);
    }

    /** method calculates and returns the grade of the given season instance
     * @param season is the season from where the rating is calculater
     * @return a double that represents the rating of the given season
     * */
    public double getSeasonRating(final Season season) {
        OptionalDouble optionalDouble = season.getRatings().stream().mapToDouble(s -> s).average();
        if (optionalDouble.isPresent()) {
            return optionalDouble.getAsDouble();
        }
        return 0;
    }

    /** method selects all the Show instances from the database
     * @return a list of Show objects
     * */
    public List<Show> getAllShows() {
        return showDao.getAll();
    }

    /** method calculates the rating of the given instance of Show
     * @param show is the Show instance of which rating is calculated
     * @return a double as the rating of the given show
     * */
    public Double getRating(final Show show) {
        int videos = show.getSeasons().size();
        double score = show.getSeasons()
                .stream()
                .map(this::getSeasonRating)
                .mapToDouble(s -> s)
                .sum();
        return score / (double) videos;
    }

    /** method calculates and returns the total length of the given show
     * @param show is the show from where the length is calculated
     * @return an integer representing the total length of the given show
     * */
    public Integer getShowLength(final Show show) {
        return show.getSeasons().stream()
                .mapToInt(Season::getDuration)
                .sum();
    }

    /** method searchrs the database for a Show instance having its name
     * given as parameter
     * @param name is the String used to search in the database
     * @return an instance of class Show if the object was found and a null
     * reference otherwise
     * */
    public Show getShowByName(final String name) {

        Optional<Show> optional = getAllShows().stream()
                .filter(s -> s.getName().equals(name))
                .findFirst();

        return optional.orElse(null);
    }
}
