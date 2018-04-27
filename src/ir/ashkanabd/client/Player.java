package ir.ashkanabd.client;

import ir.ashkanabd.player.Team;

import java.util.Scanner;

public class Player {

    private int myScore, oppScore;
    private Node[][] map;
    private int X, Y, myID, oppID;
    private Scanner scn;
    private Client client;

    public Player() {
        client = new Team();
    }

    void update(String map[][], int myScore, int oppScore, int myID, int oppID) {
        this.map = buildMap(map);
        this.myScore = myScore;
        this.oppScore = oppScore;
        this.myID = myID;
        this.oppID = oppID;
        X = 0;
        Y = 0;
        client.think(this);
    }

    public int getRowCount() {
        return 9;
    }

    public int getColumnCount() {
        return 9;
    }

    Node[][] buildMap(String map[][]) {
        Node nodes[][] = new Node[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                nodes[i][j] = new Node(i, j);
                if (map[i][j].equalsIgnoreCase("*"))
                    nodes[i][j].setSpot();
                else if (map[i][j].equalsIgnoreCase("#"))
                    nodes[i][j].setBlock();
                else if (map[i][j].equalsIgnoreCase("0")) {
                    nodes[i][j].setFree();
                } else if ((map[i][j].equalsIgnoreCase("1"))) {
                    nodes[i][j].setMarked();
                    nodes[i][j].setMark(1);
                } else {
                    nodes[i][j].setMarked();
                    nodes[i][j].setMark(2);
                }

            }
        }
        return nodes;
    }

    public String getXY() {
        return X + "_" + Y;
    }

    public void fill(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public void fill(Node node) {
        this.X = node.getX();
        this.Y = node.getY();
    }

    public int getOppScore() {
        return oppScore;
    }

    public int getMyScore() {
        return myScore;
    }

    public Node[][] getMap() {
        return map;
    }

    private String[][] rebuildMap(String map) {
        String result[][] = new String[9][9];
        String rows[] = map.split("_");
        String columns[];
        for (int i = 0; i < rows.length; i++) {
            columns = rows[i].split(",");
            System.arraycopy(columns, 0, result[i], 0, columns.length);
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (result[i][j].equals("@"))
                    result[i][j] = "#";
            }
        }
        return result;
    }

    public void message(String msg) {
        scn = new Scanner(msg);
        int myScore = Integer.parseInt(scn.nextLine());
        int oppScore = Integer.parseInt(scn.nextLine());
        int myID = Integer.parseInt(scn.nextLine());
        int oppID = Integer.parseInt(scn.nextLine());
        update(rebuildMap(scn.nextLine()), myScore, oppScore, myID, oppID);
    }

    public int getMyID() {
        return myID;
    }

    public int getOppID() {
        return oppID;
    }
}
