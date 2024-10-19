package com.example.othello_game.service;

public class GameStatus {
    private boolean finished;
    private int winner;

    public GameStatus(boolean finished, int winner) {
        this.finished = finished;
        this.winner = winner;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getWinner() {
        return winner;
    }
}

