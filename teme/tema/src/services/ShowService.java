package services;

import common.Constants;
import dao.ShowDao;
import database.Database;
import entertainment.Genre;
import entities.Show;
import fileio.SerialInputData;
import utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class ShowService {
    private ShowDao showDao;

    public ShowService(Database database){
        showDao = new ShowDao(database);
    }

    public boolean createShow(SerialInputData data){
        Show show = new Show();
        show.setName(data.getTitle());
        show.setYear(data.getYear());
        show.setCast(data.getCast());
        show.setNumberOfSeasons(data.getNumberSeason());
        show.setSeasons(data.getSeasons());

        List<Genre> genreList = data.getGenres().stream()
                                    .map(Utils::stringToGenre)
                                    .collect(Collectors.toList());
        show.setGenres(genreList);
        return createShow(show);
    }

    public boolean createShow(Show show){
        if(!checkIfShowExists(show)){
            showDao.save(show);
            return true;
        }
        return false;
    }

    public int findShow(Show show){
        List<Show> list = getAllShows();
        for(Show s : list){
            if(s.equals(show)){
                return s.getId();
            }
        }
        return Constants.NOT_FOUND;
    }

    public boolean checkIfShowExists(Show show){
        int id = findShow(show);
        return id != Constants.NOT_FOUND;
    }

    public boolean removeShow(Show show){
        int id = findShow(show);
        if(id == Constants.NOT_FOUND){
            return false;
        }
        showDao.delete(id);
        return true;
    }

    public List<Show> getAllShows(){
        return showDao.getAll();
    }
}
