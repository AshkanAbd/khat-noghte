package ir.ashkanabd;

import ir.ashkanabd.client.Player;
import javafx.application.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game implements OnReceive {

    private String map[][];
    private String data = "";
    private int client = 1;
    private int round = 1;
    private Update update;
    private Queue<Boolean> clientQueue;
    private int input[] = new int[2];
    private boolean end = false, judge = false;
    private int player1ID, player2ID;
    private Player player;

    public Game(Update update, int player1ID, int player2ID) {
        this.update = update;
        map = firstSetup();
        this.player1ID = player1ID;
        this.player2ID = player2ID;
        clientQueue = new LinkedList<>();
        player = new Player();
    }

    void sendToAI() {
        data = "";
        data += (update.getScore(1) + "") + "\n";
        data += (update.getScore(0) + "") + "\n";
        data += player1ID + "\n";
        data += player2ID + "\n";
        data += prepareMap(map) + "\n";
        player.message(data);
    }

    private void startGame() {
        try {
            System.out.println("AI : " + round);
            if (round % 2 == player1ID) {
                if (judge)
                    Platform.runLater(this::judge);
                input[0] = -1;
                input[1] = -1;
                sendToAI();
                input[0] = -1;
                input[1] = -1;
                input = rebuildData(player.getXY());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!check(input, 2))
                round++;
            else {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("score");
                        startGame();
                    }
                }, 500);
            }
        }

    }

    @Override
    public void received(String request, int client) {
        System.out.println("player : " + round);
        if ((round % 2) + 2 == player2ID) {
            this.client = client;
            try {
                if (judge)
                    Platform.runLater(this::judge);
                input[0] = -1;
                input[1] = -1;
                input = rebuildData(request);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (!check(input, 1)) {
                    round++;
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startGame();
                        }
                    }, 500);
                } else {
                    System.out.println("Player scored");
                }
            }
//                TimeUnit.SECONDS.sleep(1);
        }
    }

    @Override
    public void start() {
        startGame();
    }

    private boolean check(int input[], int round) {
        int finalClient = round;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (input[0] != -1) {
                    if (finalClient % 2 == 1) {
                        if (isValid(input[0], input[1], map)) {
                            update.setVisible(input[0], input[1], Main.TEAM2COLOR);
                            map[input[0]][input[1]] = "2";
                        }
                    } else {
                        if (isValid(input[0], input[1], map)) {
                            update.setVisible(input[0], input[1], Main.TEAM1COLOR);
                            map[input[0]][input[1]] = "1";
                        }
                    }
                    scored(map, round + 1);
                }
            }
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endPoint(map);
        if (clientQueue.size() == 0) {
            Game.this.client++;
            return false;
        } else {
            clientQueue.poll();
            return true;
        }

    }

    private String[][] firstSetup() {
        String map[][] = new String[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i % 2 == 1 && j % 2 == 1)
                    map[i][j] = "#";
                else if (i % 2 == 0 && j % 2 == 0)
                    map[i][j] = "*";
                else
                    map[i][j] = "0";
            }
        }
        return map;
    }

    public int[] rebuildData(String data) {
        int[] line = new int[2];
        String s[] = data.split("_");
        line[0] = Integer.parseInt(s[0]);
        line[1] = Integer.parseInt(s[1]);
        return line;
    }

    public String prepareMap(String map[][]) {
        String result = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result += map[i][j];
                if (j != 8)
                    result += ",";
            }
            if (i != 8)
                result += "_";
        }
        return result;
    }

    private void scored(String map[][], int client) {
        for (int i = 1; i < 9; i += 2) {
            for (int j = 1; j < 9; j += 2) {
                if (map[i][j].equals("#") && !map[i - 1][j].equals("0") && !map[i][j - 1].equals("0")
                        && !map[i + 1][j].equals("0") && !map[i][j + 1].equals("0")) {
                    clientQueue.add(true);
                    map[i][j] = "@";
                    int finalI = i, finalJ = j;
                    if (client % 2 == 0) {
                        update.setPlayerScore(2);
                        update.setNode(finalJ, finalI, Main.TEAM2COLOR);
                    } else {
                        update.setPlayerScore(1);
                        update.setNode(finalJ, finalI, Main.TEAM1COLOR);
                    }
                }
            }
        }
    }

    private boolean isValid(int x, int y, String map[][]) {
        if (map[x][y].equals("0"))
            if ((x % 2 != 0 && y % 2 == 0) || (x % 2 == 0 && y % 2 != 0)) {
                return true;
            }
        return false;
    }

    private void endPoint(String map[][]) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (map[i][j].equals("0"))
                    return;
            }
        }
        judge = true;
    }

    private void judge() {
        end = true;
        update.stopTimer();
        if (update.getScore(1) > update.getScore(2)) {
            update.setError(update.getTeam(1) + " wins!!!", Main.TEAM1COLOR);
        } else if (update.getScore(1) < update.getScore(2)) {
            update.setError(update.getTeam(2) + " wins!!!", Main.TEAM2COLOR);
        } else {
            update.setError("Draw!!!", "#000");
        }
    }
}
