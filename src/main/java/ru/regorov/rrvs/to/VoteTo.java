package ru.regorov.rrvs.to;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class VoteTo extends BaseTo {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    private Integer restId;

    public VoteTo() {
    }

    public VoteTo(Integer id, LocalDateTime dateTime, Integer restId) {
        super(id);
        this.dateTime = dateTime;
        this.restId = restId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getRestId() {
        return restId;
    }

    public void setRestId(Integer restId) {
        this.restId = restId;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", restId=" + restId +
                '}';
    }
}
