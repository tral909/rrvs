package ru.regorov.rrvs.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.regorov.rrvs.model.Menu;

@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE from Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    Menu save(Menu menu);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO menu_dish (menu_id, dish_id) VALUES (:menuId, :dishId)", nativeQuery = true)
    void appendDish(@Param("menuId") int menuId, @Param("dishId") int dishId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM menu_dish WHERE menu_id=:menuId AND dish_id=:dishId", nativeQuery = true)
    void deleteDish(@Param("menuId") int menuId, @Param("dishId") int dishId);
}
