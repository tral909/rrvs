package ru.regorov.rrvs.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class MenuTo extends BaseTo {

    private LocalDate date;

    @JsonProperty("restaurant_id")
    private Integer restId;

    public MenuTo() {
    }

    public MenuTo(Integer id, LocalDate date, Integer restId) {
        super(id);
        this.date = date;
        this.restId = restId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRestId() {
        return restId;
    }

    public void setRestId(Integer restId) {
        this.restId = restId;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
