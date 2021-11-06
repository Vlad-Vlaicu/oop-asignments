package entities;

import entertainment.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie {
    private int id;
    private String name;
    private int year;
    private int duration;
    private List<Genre> genres;
    private List<String> cast;

    public Movie() {
        genres = new ArrayList<>();
        cast = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year &&
                duration == movie.duration &&
                name.equals(movie.name) &&
                genres.equals(movie.genres) &&
                cast.equals(movie.cast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, duration, genres, cast);
    }
}
