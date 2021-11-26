package entities;

import entertainment.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public final class User {
    private int id;
    private String username;
    private Subscription subscription;
    private Map<String, Integer> history;
    private List<String> favouriteList;
    private Map<String, Double> ratings;

    public User() {
        history = new HashMap<>();
        favouriteList = new ArrayList<>();
        ratings = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(final Subscription subscription) {
        this.subscription = subscription;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(final Map<String, Integer> history) {
        this.history = history;
    }

    public List<String> getFavouriteList() {
        return favouriteList;
    }

    public void setFavouriteList(final List<String> favouriteList) {
        this.favouriteList = favouriteList;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(final Map<String, Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return username.equals(user.username)
               && subscription == user.subscription;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, subscription);
    }
}
