package com.example.othello_game.controller;

import com.example.othello_game.service.GameStatus;
import com.example.othello_game.service.OthelloGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class OthelloController {
    private final OthelloGameService gameService;

    @Autowired
    public OthelloController(OthelloGameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/board")
    public int[][] getBoard() {
        return gameService.getBoard();
    }

    @GetMapping("/scores")
    public int[] getScores() {
        return gameService.getScores();
    }

    @PostMapping("/move")
    public ResponseEntity<String> placeStone(@RequestParam int x, @RequestParam int y) {
        gameService.placeStone(x, y);
        return ResponseEntity.ok("Move successful");
    }

    @GetMapping("/turn")
    public int getCurrentPlayer() {
        return gameService.getCurrentPlayer();
    }

    @PostMapping("/forceWin")
    public ResponseEntity<String> forceWin(@RequestParam int winner) {
        gameService.forceWin(winner);
        return ResponseEntity.ok("勝者を強制的に設定しました");
    }

    @PostMapping("/fillBoard")
    public ResponseEntity<String> fillBoard(@RequestParam int player) {
        gameService.fillBoard(player);
        return ResponseEntity.ok("盤面を塗りつぶしました");
    }

    @PostMapping("/restart")
    public ResponseEntity<String> restartGame() {
        gameService.restartGame();
        return ResponseEntity.ok("ゲームがリスタートされました");
    }

    @GetMapping("/status")
    public GameStatus getGameStatus() {
        return gameService.getGameStatus();
    }
}
