package com.example.othello_game.model;

public class Board {
    private int[][] board;

    public Board() {
        board = new int[8][8];
        initializeBoard();
    }

    private void initializeBoard() {
        board[3][3] = 1; 
        board[3][4] = 2; 
        board[4][3] = 2;
        board[4][4] = 1;
    }

    public int[][] getBoard() {
        return board;
    }

    public void placeStone(int x, int y, int player) {
        board[x][y] = player;
    }

    public int[] calculateScores() {
        int whiteScore = 0;
        int blackScore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 1) whiteScore++;
                if (board[i][j] == 2) blackScore++;
            }
        }
        return new int[]{whiteScore, blackScore};
    }
}
