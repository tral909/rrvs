package ru.regorov.rrvs.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.regorov.rrvs.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Modifying
    @Query("DELETE from Vote v WHERE v.id=:id AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    Vote save(Vote vote);

    @Query("SELECT v FROM Vote v JOIN v.user WHERE v.user.id=?1")
    List<Vote> getAll(int userId);

    @Query("SELECT v FROM Vote v JOIN v.user WHERE v.user.id=?1 AND v.date=?2")
    Optional<Vote> findByUserIdAndDate(Integer userId, LocalDate date);
}