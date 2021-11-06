package entities;

import entertainment.ActorsAwards;

import java.util.*;

public class Actor {
    private int id;
    private String name;
    private String career_description;
    private List<String> filmography;
    private Map<ActorsAwards,Integer> awards;

    public Actor(){
        filmography = new ArrayList<>();
        awards = new HashMap<>();
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

    public String getCareer_description() {
        return career_description;
    }

    public void setCareer_description(String career_description) {
        this.career_description = career_description;
    }

    public List<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(List<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return name.equals(actor.name) &&
                career_description.equals(actor.career_description) &&
                filmography.equals(actor.filmography) &&
                Objects.equals(awards, actor.awards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, career_description, filmography, awards);
    }
}
