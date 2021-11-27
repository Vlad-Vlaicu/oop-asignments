package utils;

import common.Constants;
import entertainment.Rating;

import java.util.Comparator;
import java.util.List;

public final class SortingUtils {

    private SortingUtils() {

    }
    /** method sorts a list of ratings for the bestRecommendation action
     * @param list is a list of ratings to be sorted
     * */
    public static void bestRecommSort(final List<Rating> list) {
        Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore)
                                                .reversed();
        Comparator<Rating> secondComparator = Comparator.comparingDouble(Rating::getSecondScore);
        list.sort(firstComparator.thenComparing(secondComparator));
    }

    /** method sorts a list of ratings for the averageActor action
     * @param list is a list of ratings to be sorted
     * @param sortType indicates the type of sorting
     * */
    public static void avergActor(final List<Rating> list, final String sortType) {
        if (sortType.equals(Constants.DESC)) {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore)
                                                                .reversed();
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName)
                                                                .reversed();
            list.sort(firstComparator.thenComparing(secondComparator));
        } else {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
            list.sort(firstComparator.thenComparing(secondComparator));
        }
    }

    /** method sorts a list of ratings for the userQuery action
     * @param list is a list of ratings to be sorted
     * @param sortType indicates the type of sorting
     * */
    public static void userQuery(final List<Rating> list, final String sortType) {
        if (sortType.equals(Constants.DESC)) {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore)
                                                            .reversed();
           Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName)
                                                            .reversed();
            list.sort(firstComparator.thenComparing(secondComparator));

        } else {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
            list.sort(firstComparator.thenComparing(secondComparator));
        }
    }

    /** method sorts a list of ratings for the getActorsByAwards method
     * @param list is a list of ratings to be sorted
     * @param sortType indicates the type of sorting
     * */
    public static void awardActor(final List<Rating> list, final String sortType) {
        if (sortType.equals(Constants.DESC)) {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore)
                                                            .reversed();
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName)
                                                            .reversed();
            list.sort(firstComparator.thenComparing(secondComparator));
        } else {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
            list.sort(firstComparator.thenComparing(secondComparator));
        }

    }

    /** method sorts a list of ratings for the getMoviesByFavorite action
     * and getShowsByFavorite action
     * @param list is a list of ratings to be sorted
     * @param sortType indicates the type of sorting
     * */
    public static void videoFavorite(final List<Rating> list, final String sortType) {
        if (sortType.equals(Constants.DESC)) {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore)
                                                            .reversed();
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName)
                                                            .reversed();
            list.sort(firstComparator.thenComparing(secondComparator));
        } else {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
            list.sort(firstComparator.thenComparing(secondComparator));
        }
    }

    /** method sorts a list of ratings for the getMoviesByViews method and
     * getShowsByViews method
     * @param list is a list of ratings to be sorted
     * @param sortType indicates the type of sorting
     * */
    public static void videoMostViews(final List<Rating> list, final String sortType) {
        if (sortType.equals(Constants.DESC)) {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore)
                                                            .reversed();
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName)
                                                            .reversed();
            list.sort(firstComparator.thenComparing(secondComparator));
        } else {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
            list.sort(firstComparator.thenComparing(secondComparator));
        }
    }

    /** method sorts a list of ratings for the getMoviesByLength method and
     * getShowsByLength method
     * @param list is a list of ratings to be sorted
     * @param sortType indicates the type of sorting
     * */
    public static void videoLongest(final List<Rating> list, final String sortType) {
        if (sortType.equals(Constants.DESC)) {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore)
                                                            .reversed();
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName)
                                                            .reversed();
            list.sort(firstComparator.thenComparing(secondComparator));
        } else {
            Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
            Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
            list.sort(firstComparator.thenComparing(secondComparator));
        }
    }

    /** method sorts a list of ratings for the searchRecommendation action
     * @param list is a list of ratings to be sorted
     * */
    public static void videoSearch(final List<Rating> list) {
        Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore);
        Comparator<Rating> secondComparator = Comparator.comparing(Rating::getName);
        list.sort(firstComparator.thenComparing(secondComparator));
    }

    /** method sorts a list of ratings for the favouriteRecommendation action
     * @param list is a list of ratings to be sorted
     * */
    public static void videoFavRecomm(final List<Rating> list) {
        Comparator<Rating> firstComparator = Comparator.comparingDouble(Rating::getScore)
                                                        .reversed();
        Comparator<Rating> secondComparator = Comparator.comparingDouble(Rating::getSecondScore);
        list.sort(firstComparator.thenComparing(secondComparator));
    }
}
