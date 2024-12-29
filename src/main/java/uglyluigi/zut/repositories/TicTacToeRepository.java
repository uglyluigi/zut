package uglyluigi.zut.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uglyluigi.zut.entities.TicTacToeEntity;

import java.util.List;
import java.util.Optional;

public interface TicTacToeRepository extends JpaRepository<TicTacToeEntity, Integer> {

    @Query("SELECT e FROM TicTacToeEntity e WHERE ID = ?1")
    Optional<TicTacToeEntity> getEntityWithId(int id);

    @Query("SELECT e FROM TicTacToeEntity e WHERE x_name = :x_name AND o_name = :o_name")
    List<TicTacToeEntity> getEntityByPlayers(@Param("x_name") String x_name, @Param("o_name") String o_name);
}
