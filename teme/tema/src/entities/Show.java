package entities;

import entertainment.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Show {
    private int id;
    private String name;
    private int year;
    private List<String> cast;
    private List<Genre> genres;
    private int numberOfSeasons;
    private List<Season> seasons;

    public Show() {
        cast = new ArrayList<>();
        genres = new ArrayList<>();
        seasons = new ArrayList<>();
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

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return year == show.year &&
                numberOfSeasons == show.numberOfSeasons &&
                name.equals(show.name) &&
                cast.equals(show.cast) &&
                genres.equals(show.genres) &&
                Objects.equals(seasons, show.seasons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, cast, genres, numberOfSeasons, seasons);
    }
}
