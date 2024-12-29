package uglyluigi.zut.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uglyluigi.zut.entities.TicTacToeEntity;
import uglyluigi.zut.repositories.TicTacToeRepository;
import uglyluigi.zut.Util;
import uglyluigi.zut.gamebusiness.TicTacToe;
import uglyluigi.zut.gamebusiness.TicTacToe.VictoryCondition;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static uglyluigi.zut.gamebusiness.TicTacToe.TicTacToeSquareStatus.O;
import static uglyluigi.zut.gamebusiness.TicTacToe.TicTacToeSquareStatus.X;

@Controller
@RequestMapping("/g/ttt")
public class TicTacToeController {
    @Autowired
    private TicTacToeRepository tttRepo;

    @PostMapping(value = "/new/{o}")
    public ResponseEntity<String> init(@RequestHeader("ZUT-USERNAME") String username, @PathVariable String o) {
        if (username == null)
        {
            return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body("Set the request header ZUT-USERNAME.");
        }

        TicTacToeEntity entity = new TicTacToeEntity();
        entity.x_name = username;
        entity.o_name = o;
        entity.game_start_date = new Date();
        entity.most_recent_update_date = new Date();
        entity.board_state = Util.fromTicTacToeSquareStatus(TicTacToe.EMPTY_BOARD);
        entity.won = null;
        entity.abandoned = null;
        entity.turn = X;

        tttRepo.save(entity);
        return ResponseEntity.ok("game created with id " + entity.id);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TicTacToeEntity> getById(@PathVariable int id) {
        Optional<TicTacToeEntity> op = tttRepo.getEntityWithId(id);
        return op.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/look/{x}/{o}")
    public ResponseEntity<List<TicTacToeEntity>> getByPlayers(@PathVariable String x, @PathVariable String o) {
        List<TicTacToeEntity> entities = tttRepo.getEntityByPlayers(x, o);

        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(entities);
    }

    @PostMapping("/move/{id}/{x}/{y}")
    public ResponseEntity<String> makeMove(@RequestHeader(value="ZUT-USERNAME") String username, @PathVariable int id, @PathVariable int x, @PathVariable int y)
    {
        Optional<TicTacToeEntity> gameOpt = tttRepo.getEntityWithId(id);

        if (gameOpt.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        TicTacToeEntity game = gameOpt.get();

        if (game.won != null || game.abandoned != null)
        {
            return ResponseEntity.badRequest().body("This game has ended.");
        }

        if (!username.equals(game.o_name) && !username.equals(game.x_name))
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("You are not in this game.");
        }

        if ((username.equals(game.o_name) && game.turn != O) || (username.equals(game.x_name) && game.turn != X))
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("It is not your turn.");
        }

        TicTacToe ttt = new TicTacToe(Util.fromIntArray(game.board_state));
        boolean result = ttt.put(game.turn, x, y);

        if (result) {
            Optional<VictoryCondition> victoryConditionOptional = ttt.computeVictory();
            game.board_state = Util.fromTicTacToeSquareStatus(ttt.board);
            game.most_recent_update_date = new Date();

            if (victoryConditionOptional.isPresent())
            {
                game.won = victoryConditionOptional.get();
                game.turn = null;
                tttRepo.save(game);

                return ResponseEntity.ok(switch (game.won) {
                    case WIN_X -> "X wins!";
                    case WIN_O -> "O wins!";
                    case CAT -> "Draw.";
                });
            } else {
                game.turn = game.turn == X ? O : X;
                tttRepo.save(game);
                return ResponseEntity.ok("move recorded successfully.");
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/abandon/{id}")
    public ResponseEntity<String> abandonGame(@RequestHeader(value="ZUT-USERNAME") String username, @PathVariable int id) {
        Optional<TicTacToeEntity> gameOpt = tttRepo.getEntityWithId(id);

        if (gameOpt.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        TicTacToeEntity game = gameOpt.get();

        if (game.abandoned != null || game.won != null)
        {
            return ResponseEntity.badRequest().body("This game has ended.");
        }

        if (!username.equals(game.o_name) && !username.equals(game.x_name))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not in this game.");
        }

        game.abandoned = game.o_name.equals(username) ? O : X;
        game.turn = null;
        game.most_recent_update_date = new Date();
        tttRepo.save(game);
        return ResponseEntity.ok("game forfeited.");
    }
}
