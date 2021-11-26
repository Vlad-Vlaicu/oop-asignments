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
import java.util.Map;
import java.util.Optional;

public final class ActorService {
    private ActorDao actorDao;

    public ActorService() {
        Database database = Database.getInstance();
        actorDao = new ActorDao(database);
    }
    /** method creates an Actor object and attempts to insert it into the database
     * @param data is raw data used to create an Actor object
     * @return true if the new Actor instance was successfully inserted into database
     * and false in case of failure
     * */
    public boolean createActor(final ActorInputData data) {
        Actor actor = new Actor();
        actor.setName(data.getName());
        actor.setCareerDescription(data.getCareerDescription());
        actor.setFilmography(data.getFilmography());
        actor.setAwards(data.getAwards());
        return createActor(actor);
    }

    /** method atempts to insert an Actor object into the database
     * @param actor is the object to be inserted into database
     * @return true if the new Actor instance was successfully inserted into database
     * and false in case of failure
     * */
    public boolean createActor(final Actor actor) {
        if (!checkIfActorExists(actor)) {
            actorDao.save(actor);
            return true;
        }
        return false;
    }
    /** method searches the database for an Actor instance given as parameter
     * @param actor is the object searched into database
     * @return the id of the object if the object is found in the database,
     * or not_found constants in case of failure
     * */
    public int findActor(final Actor actor) {
        List<Actor> list = getAllActors();
        for (Actor a : list) {
            if (a.equals(actor)) {
                return a.getId();
            }
        }
        return Constants.NOT_FOUND;
    }

    /** method checks if the object given as parameter is persisted in the database
     * @param actor is the object the method looks after in the database
     * @return true if the object is found in the database and false in case of failure
     * */
    public boolean checkIfActorExists(final Actor actor) {
        int id = findActor(actor);
        return id != Constants.NOT_FOUND;
    }

    /** method attempts to remove the object given as parameter from the database
     * @param actor is the object to be removed
     * @return is true if the object was removed from the database and false otherwise
     * */
    public boolean removeActor(final Actor actor) {
        int id = findActor(actor);
        if (id == Constants.NOT_FOUND) {
            return false;
        }
        actorDao.delete(id);
        return true;
    }

    /** method selects all the Actor instances from the database
     * @return a list of Actor objects
     * */
    public List<Actor> getAllActors() {
        return actorDao.getAll();
    }

    /** method calculates the rating of the given instance of Actor
     * @param a is the Actor instance of which rating is calculated
     * @return a double as the rating of the given actor
     * */
    public double getRating(final Actor a) {
        int videos = 0;
        double score = 0;
        MovieService movieService = new MovieService();
        ShowService showService = new ShowService();

        List<String> filmography = a.getFilmography();
        for (String name : filmography) {
            Movie movie = movieService.findMovieByTitle(name);
            Show show = showService.findShowByTitle(name);

            Optional<Movie> movieBox = Optional.ofNullable(movie);
            Optional<Show> showBox = Optional.ofNullable(show);

            if (movieBox.isPresent()) {
                double movieScore = movieService.getRating(movie);
                if (movieScore != 0) {
                    videos++;
                    score += movieScore;
                }
            }

            if (showBox.isPresent()) {
                double showScore = showService.getRating(show);
                if (showScore != 0) {
                    videos++;
                    score += showScore;
                }
            }
        }
        if (videos == 0) {
            return 0;
        }
        return score / (double) videos;
    }

    /** method checks if the given actor has all the awards of the list given as parameter
     * @param actor is the object on which the check is made
     * @param awards is the list of awards checked on the actor
     * @return true if the actor has all the awards listed and false otherwise
     * */
    public boolean doesActorHaveAllAwards(final Actor actor, final List<ActorsAwards> awards) {
        for (ActorsAwards award : awards) {
            if (!actor.getAwards().containsKey(award)) {
                return false;
            }
        }
        return true;
    }

    /** method counts all the awards of the given actor
     * @param a is the actor from where the awards are counted
     * @return an integer representing the count of all the awards of the given actor
     * */
    public int getCountAwards(final Actor a) {
        Map<ActorsAwards, Integer> awards = a.getAwards();
        return awards.values().stream().mapToInt(s -> s).sum();
    }

}
