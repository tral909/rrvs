package ru.regorov.rrvs.to;

public class VoteTo extends BaseTo {

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
