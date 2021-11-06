package entities;

import entertainment.Subscription;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private int id;
    private String username;
    private Subscription subscription;
    private Map<String,Integer> history;
    private List<String> favouriteList;

    public User() {
        history = new HashMap<>();
        favouriteList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Integer> history) {
        this.history = history;
    }

    public List<String> getFavouriteList() {
        return favouriteList;
    }

    public void setFavouriteList(List<String> favouriteList) {
        this.favouriteList = favouriteList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) &&
                subscription == user.subscription;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, subscription);
    }
}
