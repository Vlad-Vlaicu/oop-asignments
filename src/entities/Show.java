package entities;

import entertainment.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Show {
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

    public List<String> getCast() {
        return cast;
    }

    public void setCast(final List<String> cast) {
        this.cast = cast;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(final List<Genre> genres) {
        this.genres = genres;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(final int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(final List<Season> seasons) {
        this.seasons = seasons;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Show show = (Show) o;
        return year == show.year
               && numberOfSeasons == show.numberOfSeasons
               && name.equals(show.name)
               && cast.equals(show.cast)
               && genres.equals(show.genres)
               && Objects.equals(seasons, show.seasons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, cast, genres, numberOfSeasons, seasons);
    }
}
