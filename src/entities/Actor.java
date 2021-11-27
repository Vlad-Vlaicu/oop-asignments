package entities;

import entertainment.ActorsAwards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Actor {
    private int id;
    private String name;
    private String careerDescription;
    private List<String> filmography;
    private Map<ActorsAwards, Integer> awards;

    public Actor() {
        filmography = new ArrayList<>();
        awards = new HashMap<>();
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

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public List<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final List<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(final Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Actor actor = (Actor) o;
        return name.equals(actor.name)
                && careerDescription.equals(actor.careerDescription)
                && filmography.equals(actor.filmography)
                && Objects.equals(awards, actor.awards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, careerDescription, filmography, awards);
    }
}
