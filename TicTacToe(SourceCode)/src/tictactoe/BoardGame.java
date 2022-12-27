/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author husse
 */
class BoardGame {

    List<Point> availablePoints;
    int[][] boardGame = new int[3][3];

    public BoardGame() {
    }

    public boolean isGameOver() {

        return (isXWon() || isOWon() || getAvailableStates().isEmpty());
    }

    public boolean isXWon() {
        if ((boardGame[0][0] == boardGame[1][1] && boardGame[0][0] == boardGame[2][2] && boardGame[0][0] == 1) || (boardGame[0][2] == boardGame[1][1] && boardGame[0][2] == boardGame[2][0] && boardGame[0][2] == 1)) {
            //x win
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if (((boardGame[i][0] == boardGame[i][1] && boardGame[i][0] == boardGame[i][2] && boardGame[i][0] == 1)
                    || (boardGame[0][i] == boardGame[1][i] && boardGame[0][i] == boardGame[2][i] && boardGame[0][i] == 1))) {
                //  x win
                return true;
            }
        }
        return false;
    }

    public boolean isOWon() {
        if ((boardGame[0][0] == boardGame[1][1] && boardGame[0][0] == boardGame[2][2] && boardGame[0][0] == 2) || (boardGame[0][2] == boardGame[1][1] && boardGame[0][2] == boardGame[2][0] && boardGame[0][2] == 2)) {
            //o win
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((boardGame[i][0] == boardGame[i][1] && boardGame[i][0] == boardGame[i][2] && boardGame[i][0] == 2)
                    || (boardGame[0][i] == boardGame[1][i] && boardGame[0][i] == boardGame[2][i] && boardGame[0][i] == 2)) {
                //  o win
                return true;
            }
        }

        return false;
    }

    public List<Point> getAvailableStates() {
        availablePoints = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (boardGame[i][j] == 0) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }

        return availablePoints;
    }

    public void placeAMove(Point point, int player) {
        boardGame[point.x][point.y] = player;   //player = 2 for X, 1 for O
    }

    public void displayBoard() {
        System.out.println();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                System.out.print(boardGame[i][j] + " ");
            }
            System.out.println();

        }
    }

    Point computersMove;

    public int minimax(int depth, int turn) {
        if (isXWon()) {
            return +1; //X won
        }
        if (isOWon()) {
            return -1; // O won
        }
        List<Point> pointsAvailable = getAvailableStates();
        if (pointsAvailable.isEmpty()) {
            return 0; //draw
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < pointsAvailable.size(); ++i) {
            Point point = pointsAvailable.get(i);
            if (turn == 1) { // Ai , O 
                placeAMove(point, turn);
                int currentScore = minimax(depth + 1, 2);
                max = Math.max(currentScore, max);
                // if(depth == 0)System.out.println("position ("+pointsAvailable.get(i).x+", "+pointsAvailable.get(i).y+") = "+currentScore);
                if (currentScore >= 0 && depth == 0) {
                    computersMove = point;
                } //if he can win or tie in this move save it 
                if (currentScore == 1) {
                    boardGame[point.x][point.y] = 0;
                    break;
                } //he restes the predictions and break
                if (i == pointsAvailable.size() - 1 && max < 0) {
                    if (depth == 0) {
                        computersMove = point;
                    }
                } //if it's the last place and he can't win he put the point anyway
            } else if (turn == 2) { // player , X
                placeAMove(point, turn);
                int currentScore = minimax(depth + 1, 1);
                min = Math.min(currentScore, min);
                if (currentScore == -1) {
                    boardGame[point.x][point.y] = 0;
                    break;
                } //he restes the predictions and break
            }
            boardGame[point.x][point.y] = 0; //Reset this point
        }
        return turn == 1 ? max : min; //if(turn==1) return max else return min
    }

    //Functions for GUI
    public int returnNextMove() {
        if (isGameOver()) {
            return -1;
        }
        minimax(0, 1);
        return computersMove.x * 3 + computersMove.y;
    }

    public void resetBoard() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                boardGame[i][j] = 0;
            }
        }
    }
}
