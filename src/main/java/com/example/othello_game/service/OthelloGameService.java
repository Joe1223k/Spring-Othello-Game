package com.example.othello_game.service;

import com.example.othello_game.model.Board;
import org.springframework.stereotype.Service;

@Service
public class OthelloGameService {
    private Board board;
    private int currentPlayer;
    private boolean gameFinished = false;
    private int winner = 0; // 0: playing, 1: white win, 2: black win, 3: tie

    public OthelloGameService() {
        board = new Board();
        currentPlayer = 1;  // player1: white
    }

    public int[][] getBoard() {
        return board.getBoard();
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int[] getScores() {
        return board.calculateScores();
    }

    public void fillBoard(int player) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.placeStone(i, j, player);
            }
        }
        gameFinished = true;
        endGame();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    public void placeStone(int x, int y) {
    	
        if (gameFinished) {
            return;  
        }

        if (board.getBoard()[x][y] == 0 && isValidMove(x, y, currentPlayer)) {
            board.placeStone(x, y, currentPlayer);
            flipStones(x, y, currentPlayer);
            switchPlayer();
        }

        if (isBoardFull()) {
            endGame();
        }
    }

    private boolean isBoardFull() {
        int[][] currentBoard = board.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentBoard[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void endGame() {
        int[] scores = board.calculateScores();
        int whiteScore = scores[0];
        int blackScore = scores[1];

        gameFinished = true;

        if (whiteScore > blackScore) {
            winner = 1; // white win
        } else if (blackScore > whiteScore) {
            winner = 2; // black win
        } else {
            winner = 3; // tie
        }
    }

    public void restartGame() {
        board = new Board();
        currentPlayer = 1;
        gameFinished = false;
        winner = 0;
    }

    private void flipStones(int x, int y, int player) {
        int opponent = (player == 1) ? 2 : 1;
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, // 上下左右
            {-1, -1}, {1, 1}, {-1, 1}, {1, -1} // 斜め
        };

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            if (isFlippable(x, y, dx, dy, player, opponent)) {
                int nx = x + dx;
                int ny = y + dy;
                while (board.getBoard()[nx][ny] == opponent) {
                    board.placeStone(nx, ny, player);
                    nx += dx;
                    ny += dy;
                }
            }
        }
    }

    private boolean isFlippable(int x, int y, int dx, int dy, int player, int opponent) {
        int nx = x + dx;
        int ny = y + dy;
        boolean foundOpponent = false;

        while (nx >= 0 && nx < 8 && ny >= 0 && ny < 8 && board.getBoard()[nx][ny] == opponent) {
            foundOpponent = true;
            nx += dx;
            ny += dy;
        }

        return foundOpponent && nx >= 0 && nx < 8 && ny >= 0 && ny < 8 && board.getBoard()[nx][ny] == player;
    }

    private boolean isValidMove(int x, int y, int player) {
        int opponent = (player == 1) ? 2 : 1;
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {1, 1}, {-1, 1}, {1, -1}
        };

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            if (isFlippable(x, y, dx, dy, player, opponent)) {
                return true;
            }
        }
        return false;
    }

    public void forceWin(int winner) {
        this.winner = winner;
        this.gameFinished = true;
    }

    public GameStatus getGameStatus() {
        return new GameStatus(gameFinished, winner);
    }
}
