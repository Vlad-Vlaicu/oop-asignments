package services;

import common.Constants;
import dao.ActorDao;
import database.Database;
import entertainment.ActorsAwards;
import entities.Actor;
import entities.Movie;
import entities.Show;
import fileio.ActorInputData;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


public class ActorService {
    private ActorDao actorDao;

    public ActorService() {
        Database database = Database.getInstance();
        actorDao = new ActorDao(database);
    }

    public boolean createActor(ActorInputData data){
        Actor actor = new Actor();
        actor.setName(data.getName());
        actor.setCareer_description(data.getCareerDescription());
        actor.setFilmography(data.getFilmography());
        actor.setAwards(data.getAwards());
        return createActor(actor);
    }

    public boolean createActor(Actor actor){
        if(!checkIfActorExists(actor)){
            actorDao.save(actor);
            return true;
        }
        return false;
    }

    public int findActor(Actor actor){
        List<Actor> list = getAllActors();
        for(Actor a : list){
            if(a.equals(actor)){
                return a.getId();
            }
        }
        return Constants.NOT_FOUND;
    }

    public boolean checkIfActorExists(Actor actor){
        int id = findActor(actor);
        return id != Constants.NOT_FOUND;
    }

    public boolean removeActor(Actor actor){
        int id = findActor(actor);
        if(id == Constants.NOT_FOUND){
            return false;
        }
        actorDao.delete(id);
        return true;
    }

    public List<Actor> getAllActors(){
        return actorDao.getAll();
    }

    public double getRating(Actor a){
        int videos = 0;
        double score = 0;
        MovieService movieService = new MovieService();
        ShowService showService = new ShowService();

        List<String> filmography = a.getFilmography();
        for(String name : filmography){
            Movie movie = movieService.findMovieByTitle(name);
            Show show = showService.findShowByTitle(name);

            Optional<Movie> movieBox = Optional.ofNullable(movie);
            Optional<Show> showBox = Optional.ofNullable(show);

            if(movieBox.isPresent()){
                double movieScore = movieService.getRating(movie);
                if(movieScore != 0){
                    videos++;
                    score += movieScore;
                }
            }

            if(showBox.isPresent()){
                double showScore = showService.getRating(show);
                if(showScore != 0){
                    videos++;
                    score += showScore;
                }
            }
        }
        return score / (double)videos;
    }

    public int getActorPrestigeStrict(Actor actor, List<ActorsAwards> awards){
        int awardsCount = 0;
        for(ActorsAwards award : awards){
            if(actor.getAwards().containsKey(award)){
                awardsCount += actor.getAwards().get(award);
            }else
                return 0;

        }
        return awardsCount;
    }

    public boolean doesActorHaveDescription(Actor actor, List<String> keywords){
        String actorDescription = actor.getCareer_description();
        boolean isEligibe = true;
        for(String word : keywords){
           boolean res =  Pattern.compile(Pattern.quote(word),Pattern.CASE_INSENSITIVE)
                   .matcher(actorDescription)
                   .find();
           isEligibe = isEligibe && res;


        }
        return isEligibe;
    }
    
}
