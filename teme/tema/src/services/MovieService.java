package services;

import common.Constants;
import dao.MovieDao;
import database.Database;
import entertainment.Genre;
import entities.Movie;
import fileio.MovieInputData;
import utils.Utils;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public final class MovieService {
    private MovieDao movieDao;

    public MovieService() {
        Database database = Database.getInstance();
        movieDao = new MovieDao(database);
    }
    /** method creates a Movie object and attempts to insert it into the database
     * @param data is raw data used to create a Movie object
     * @return true if the new Movie instance was successfully inserted into database
     * and false in case of failure
     * */
    public boolean createMovie(final MovieInputData data) {
        Movie movie = new Movie();
        movie.setDuration(data.getDuration());
        movie.setName(data.getTitle());
        movie.setYear(data.getYear());
        movie.setCast(data.getCast());

        List<Genre> genresList = data.getGenres().stream()
                .map(Utils::stringToGenre)
                .collect(Collectors.toList());
        movie.setGenres(genresList);
        return createMovie(movie);

    }
    /** method atempts to insert a Movie object into the database
     * @param movie is the object to be inserted into database
     * @return true if the new Movie instance was successfully inserted into database
     * and false in case of failure
     * */
    public boolean createMovie(final Movie movie) {
        if (!checkIfMovieExists(movie)) {
            movieDao.save(movie);
            return true;
        }
        return false;
    }
    /** method searches the database for a Movie instance given as parameter
     * @param movie is the object searched into database
     * @return the id of the object if the object is found in the database,
     * or not_found constants in case of failure
     * */
    public int findMovie(final Movie movie) {
        List<Movie> list = getAllMovies();
        for (Movie m : list) {
            if (m.equals(movie)) {
                return m.getId();
            }
        }
        return Constants.NOT_FOUND;
    }

    /** method searches the database for a Movie instance having its name
     * given as parameter
     * @param title is the String used to search in the database
     * @return an instance of class Movie if the object was found and a null
     * reference otherwise
     * */
    public Movie findMovieByTitle(final String title) {
        List<Movie> list = getAllMovies();
        for (Movie m : list) {
            String name = m.getName();
            if (name.equals(title)) {
                return m;
            }
        }
        return null;
    }

    /** method checks if the object given as parameter is persisted in the database
     * @param movie is the object the method looks after in the database
     * @return true if the object is found in the database and false in case of failure
     * */
    public boolean checkIfMovieExists(final Movie movie) {
        int id = findMovie(movie);
        return id != Constants.NOT_FOUND;
    }

    /** method attempts to remove the object given as parameter from the database
     * @param movie is the object to be removed
     * @return is true if the object was removed from the database and false otherwise
     * */
    public boolean removeMovie(final Movie movie) {
        int id = findMovie(movie);
        if (id == Constants.NOT_FOUND) {
            return false;
        }
        movieDao.delete(id);
        return true;
    }

    /** method rates the given movie with a grade
     * @param movie is the movie to be rated
     * @param grade is a double and represents the grade used in grading the movie
     * */
    public void rateMovie(final double grade, final Movie movie) {
        var ratings = movie.getRatings();
        ratings.add(grade);
    }

    /** method calculates the rating of the given instance of Movie
     * @param movie is the Movie instance of which rating is calculated
     * @return a double as the rating of the given movie
     * */
    public double getRating(final Movie movie) {
        OptionalDouble result = movie.getRatings().stream().mapToDouble(s -> s).average();
        if (result.isPresent()) {
            return result.getAsDouble();
        }
        return 0;
    }


    /** method selects all the Movie instances from the database
     * @return a list of Movie objects
     * */
    public List<Movie> getAllMovies() {
        return movieDao.getAll();
    }

}
