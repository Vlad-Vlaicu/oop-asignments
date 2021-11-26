package entities;

import entertainment.Genre;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Movie {
    private int id;
    private String name;
    private int year;
    private int duration;
    private List<Genre> genres;
    private List<String> cast;
    private List<Double> ratings;

    public Movie() {
        genres = new ArrayList<>();
        cast = new ArrayList<>();
        ratings = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(final List<Genre> genres) {
        this.genres = genres;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(final List<String> cast) {
        this.cast = cast;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return year == movie.year
               && duration == movie.duration
               && name.equals(movie.name)
               && genres.equals(movie.genres)
               && cast.equals(movie.cast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, duration, genres, cast);
    }
}
