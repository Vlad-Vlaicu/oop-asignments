package actions.commands;

import actions.Action;

public class RateCommand extends Action {
    private String user;
    private String title;
    private double grade;
    private int currentSeason;

    public RateCommand(int action_id, String action_type) {
        super(action_id, action_type);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(int currentSeason) {
        this.currentSeason = currentSeason;
    }

    @Override
    public String execute() {
        return null;
    }
}
