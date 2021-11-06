package entities;

import entertainment.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
}
