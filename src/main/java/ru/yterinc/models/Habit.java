package ru.yterinc.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Habit {
    private final User owner;
    private String name;
    private String description;
    private String frequency; // "daily", "weekly"
    private final Date startDate;
    private List<Date> completionDates = new ArrayList<>();


    public Habit(User owner, String name, String description, String frequency) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.startDate = new Date();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public List<Date> getCompletionDates() {
        return completionDates;
    }

    public void setCompletionDates(List<Date> completionDates) {
        this.completionDates = completionDates;
    }

    public Date getStartDate() {
        return startDate;
    }

    public User getOwner() {
        return owner;
    }
}
