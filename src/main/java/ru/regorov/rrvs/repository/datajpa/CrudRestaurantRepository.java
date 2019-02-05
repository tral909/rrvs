package ru.regorov.rrvs.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.regorov.rrvs.model.Menu;
import ru.regorov.rrvs.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE from Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    Restaurant save(Restaurant restaurant);

    @Query("SELECT m FROM Restaurant r JOIN r.menus m WHERE r.id=?1")
    List<Menu> findByRestIdMenus(int id);

}