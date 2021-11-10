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

public class MovieService {
    private MovieDao movieDao;

    public MovieService(){
        Database database = Database.getInstance();
        movieDao = new MovieDao(database);
    }

    public boolean createMovie(MovieInputData data){
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

    public boolean createMovie(Movie movie){
        if(!checkIfMovieExists(movie)){
            movieDao.save(movie);
            return true;
        }
        return false;
    }

    public int findMovie(Movie movie){
        List<Movie> list = getAllMovies();
        for(Movie m : list){
            if(m.equals(movie)){
                return m.getId();
            }
        }
        return Constants.NOT_FOUND;
    }

    public Movie findMovieByTitle(String title){
        List<Movie> list = getAllMovies();
        for(Movie m : list){
            String name = m.getName();
            if(name.equals(title)){
                return m;
            }
        }
        return null;
    }

    public boolean checkIfMovieExists(Movie movie){
        int id = findMovie(movie);
        return id != Constants.NOT_FOUND;
    }

    public boolean removeMovie(Movie movie){
        int id = findMovie(movie);
        if(id == Constants.NOT_FOUND){
            return false;
        }
        movieDao.delete(id);
        return true;
    }

    public void rateMovie(double grade, Movie movie){
        var ratings = movie.getRatings();
        ratings.add(grade);
    }

    public double getRating(Movie movie){
        OptionalDouble result = movie.getRatings().stream().mapToDouble(s -> s).average();
        if(result.isPresent())
            return result.getAsDouble();
        return 0;
    }


    public List<Movie> getAllMovies() {
        return movieDao.getAll();
    }

}
