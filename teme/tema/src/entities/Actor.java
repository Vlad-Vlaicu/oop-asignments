package entities;

import entertainment.ActorsAwards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Actor {
    private String name;
    private String career_description;
    private List<String> filmography;
    private Map<ActorsAwards,Integer> awards;

    public Actor(){
        filmography = new ArrayList<>();
        awards = new HashMap<>();
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
}
