package ru.regorov.rrvs.to;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoteTo extends BaseTo {

    @JsonProperty("restaurant_id")
    private Integer restId;

    public VoteTo() {
    }

    public VoteTo(Integer id, Integer restId) {
        super(id);
        this.restId = restId;
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
                ", restId=" + restId +
                '}';
    }
}
