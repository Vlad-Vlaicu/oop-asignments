package services;

import common.Constants;
import dao.ShowDao;
import database.Database;
import entertainment.Genre;
import entities.Season;
import entities.Show;
import fileio.SerialInputData;
import utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class ShowService {
    private ShowDao showDao;

    public ShowService(){
        Database database = Database.getInstance();
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

    public Show findShowByTitle(String title){
        List<Show> list = getAllShows();
        for(Show s : list){
            String name = s.getName();
            if(name.equals(title)){
                return s;
            }
        }
        return null;
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

    public void rateShow(double grade, int season, Show show){
        Season s = show.getSeasons().get(season-1);
        s.getRatings().add(grade);
    }

    public double getSeasonRating(Season season){
        OptionalDouble optionalDouble = season.getRatings().stream().mapToDouble(s -> s).average();
        if(optionalDouble.isPresent())
            return optionalDouble.getAsDouble();
        return 0;
    }

    public List<Show> getAllShows(){
        return showDao.getAll();
    }

    public Double getRating(Show show){
        int videos = show.getSeasons().size();
        double score = show.getSeasons().stream().map(this::getSeasonRating).mapToDouble(s -> s).sum();

        return score/ (double) videos;
    }

    public Integer getShowLength(Show show){
        return show.getSeasons().stream()
                .mapToInt(Season::getDuration)
                .sum();
    }

    public Show getShowByName(String name){

        Optional<Show> optional = getAllShows().stream()
                        .filter(s -> s.getName().equals(name))
                        .findFirst();

        return optional.get();
    }
}
