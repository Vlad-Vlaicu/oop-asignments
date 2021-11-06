package services;

import common.Constants;
import dao.ActorDao;
import database.Database;
import entities.Actor;
import fileio.ActorInputData;

import java.util.List;


public class ActorService {
    private ActorDao actorDao;

    public ActorService(Database database) {
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

}
