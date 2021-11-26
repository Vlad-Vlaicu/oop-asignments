package utils;

import com.sun.source.doctree.SinceTree;
import common.Constants;
import entertainment.Rating;

import java.util.Comparator;
import java.util.List;

public class SortingUtils {
    public static void bestRecommSort(List<Rating> list) {
        Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore).reversed();
        Comparator<Rating> secondComparator = Comparator.comparingDouble(Rating::getSecondScore);
        list.sort(firstComparator.thenComparing(secondComparator));
    }

    public static void avergActor(List<Rating> list, String sortType) {
        if (sortType.equals(Constants.DESC)) {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore).reversed();
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName).reversed();
            list.sort(firstComparator.thenComparing(secondComparator));
        } else {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
            list.sort(firstComparator.thenComparing(secondComparator));
        }
    }

    public static void userQuery(List<Rating> list, String sortType) {
        if (sortType.equals(Constants.DESC)) {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore).reversed();
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName).reversed();
            list.sort(firstComparator.thenComparing(secondComparator));

        } else {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
            list.sort(firstComparator.thenComparing(secondComparator));
        }
    }

    public static void awardActor(List<Rating> list, String sortType) {
        if (sortType.equals(Constants.DESC)) {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore).reversed();
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName).reversed();
            list.sort(firstComparator.thenComparing(secondComparator));
        } else {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
            list.sort(firstComparator.thenComparing(secondComparator));
        }

    }

    public static void videoFavorite(List<Rating> list, String sortType) {
        if (sortType.equals(Constants.DESC)) {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore).reversed();
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName).reversed();
            list.sort(firstComparator.thenComparing(secondComparator));
        } else {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
            list.sort(firstComparator.thenComparing(secondComparator));
        }
    }

    public static void videoMostViews(List<Rating> list, String sortType) {
        if (sortType.equals(Constants.DESC)) {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore).reversed();
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName).reversed();
            list.sort(firstComparator.thenComparing(secondComparator));
        } else {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
            list.sort(firstComparator.thenComparing(secondComparator));
        }
    }

    public static void videoLongest(List<Rating> list, String sortType){
        if(sortType.equals(Constants.DESC)){
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore).reversed();
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName).reversed();
            list.sort(firstComparator.thenComparing(secondComparator));
        } else{
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
            list.sort(firstComparator.thenComparing(secondComparator));
        }
    }

    public static void videoSearch(List<Rating> list){
        Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
        Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
        list.sort(firstComparator.thenComparing(secondComparator));
    }

    public static void videoFavRecomm(List<Rating> list){
        Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore).reversed();
        Comparator<Rating> secondComparator = Comparator.comparingDouble(Rating::getSecondScore);
        list.sort(firstComparator.thenComparing(secondComparator));
    }
}
