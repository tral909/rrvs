package ru.regorov.rrvs.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class VoteTo extends BaseTo {

    @NotNull
    @Column(name = "date", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date;

    @JsonProperty("restaurant_id")
    private Integer restId;

    public VoteTo() {
    }

    public VoteTo(Integer id, Integer restId, LocalDate date) {
        super(id);
        this.restId = restId;
        this.date = date;
    }

    public Integer getRestId() {
        return restId;
    }

    public void setRestId(Integer restId) {
        this.restId = restId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", restId=" + restId +
                '}';
    }
}
