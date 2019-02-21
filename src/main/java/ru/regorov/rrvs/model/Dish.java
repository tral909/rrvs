package ru.regorov.rrvs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "dish")
public class Dish extends AbstractNamedEntity {

    @NotNull
    @Range(min = 1, max = 10000)
    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToMany(mappedBy = "dishes", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Menu> menus;

    public Dish() {
    }

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
