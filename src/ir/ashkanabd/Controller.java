package ir.ashkanabd;

import javafx.application.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

import java.util.*;

public class Controller {

    public static int FLAG_END = 1;
    public static int FLAG_NONE = 2;

    public static int TIMER_START = 1;
    public static int TIMER_STOP = 2;

    private Main main;
    private OnReceive onReceive;

    @FXML
    private Label player1, player2, player1Score, player2Score;
    @FXML
    private Label node11, node13, node15, node17, node31, node33, node35;
    @FXML
    private Label node37, node51, node53, node55, node57, node71, node73, node75, node77;
    @FXML
    private Label time, errorPaneInfo, startPaneInfo;
    @FXML
    private Ellipse node01, node03, node05, node07, node21, node23, node25, node27, node41, node43, node45, node47, node61;
    @FXML
    private Ellipse node63, node65, node67, node81, node83, node85, node87, node10, node12, node14, node16, node18, node30;
    @FXML
    private Ellipse node32, node34, node36, node38, node50, node52, node54, node56, node58, node70, node72, node74, node76;
    @FXML
    private Ellipse node78;
    @FXML
    private Button errorPaneOK, startPaneOK;
    @FXML
    private Pane errorPane, startPane;
    @FXML
    private GridPane mainPane;

    private List<Ellipse> nodeList;
    private List<Label> labelList;
    private int counter = 0, player1score = 0, player2score = 0, task = Controller.TIMER_START;

    public Controller(Main main) {
        this.main = main;
    }

    public void initialize() {
        getAllLabel();
        getAllEllipse();
        time.setText("00:00");
        player1.setText("NULL");
        player2.setText("NULL");
        for (Ellipse e : nodeList) {
            e.setVisible(true);
            e.setOnMouseClicked(this::userSelect);
        }
    }

    private void userSelect(MouseEvent event) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                if (Main.start) {
                    Ellipse e = (Ellipse) event.getSource();
                    onReceive.received(e.getId().substring(4, 5) + "_" + e.getId().substring(5), Main.aiID);
                }
            }
        },500);
    }

    void setOnReceive(OnReceive onReceive) {
        this.onReceive = onReceive;
    }

    public int getScore(int number) {
        if (number == 1)
            return player1score;
        else
            return player2score;
    }

    public String getTeam(int nubmer) {
        if (nubmer == 1)
            return player1.getText();
        else
            return player2.getText();
    }

    public void setVisible(int x, int y, String color) {
        Ellipse node = findNode(x, y);
        if (node.getRadiusX() != -1) {
            node.setVisible(true);
            node.setFill(Paint.valueOf(color));
        }
    }

    public void setScore(int number) {
        if (number == 2) {
            player2score++;
            player2Score.setText(player2score + "");
        } else {
            player1score++;
            player1Score.setText(player1score + "");
        }
    }

    public void setNode(int x, int y, String color) {
        Label node = findLabel(x, y);
        node.setStyle("-fx-background-color: " + color + "; -fx-alignment: center;-fx-text-alignment: center");
    }

    private Label findLabel(int x, int y) {
        Label node = new Label();
        String nodeID = "node" + x + y;
        for (Label label : labelList) {
            if (label.getId().equalsIgnoreCase(nodeID)) {
                node = label;
                break;
            }
        }
        return node;
    }

    private Ellipse findNode(int x, int y) {
        Ellipse node = new Ellipse(-1, -1);
        String nodeID = "node" + x + y;
        for (Ellipse ellipse : nodeList) {
            if (ellipse.getId().equalsIgnoreCase(nodeID)) {
                node = ellipse;
                break;
            }
        }
        return node;
    }

    public void handle(MouseEvent event) {
        startPane.setVisible(false);
        Main.start = true;
        main.startTimer();
        onReceive.start();
    }

    void startGame() {
        startPaneOK.setOnMouseClicked(this::handle);
    }

    public void stopTimer() {
        task = TIMER_STOP;
    }

    public void setTime() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (task == TIMER_START) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            counter++;
                            String s = "";
                            if ((counter / 60 + "").length() < 2) {
                                s += "0" + counter / 60 + ":";
                            } else {
                                s += counter / 60 + ":";
                            }
                            if ((counter % 60 + "").length() < 2) {
                                s += "0" + counter % 60;
                            } else {
                                s += counter % 60 + "";
                            }
                            time.setText(s);
                        }
                    });
                } else {
                    this.cancel();
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    public void setTeamName(int number, String teamName) {
        if (number == 1) {
            player1.setText(teamName);
        } else {
            player2.setText(teamName);
        }
    }

    public void setError(String error, int flag, String color) {
        errorPane.setVisible(true);
        errorPaneInfo.setText(error);
        errorPaneInfo.setTextFill(Paint.valueOf(color));
        errorPaneOK.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                errorPane.setVisible(false);
                if (flag == FLAG_END)
                    System.exit(1);
            }
        });
    }

    private void getAllLabel() {
        labelList = new ArrayList<>();
        labelList.add(node11);
        labelList.add(node13);
        labelList.add(node15);
        labelList.add(node17);
        labelList.add(node31);
        labelList.add(node33);
        labelList.add(node35);
        labelList.add(node37);
        labelList.add(node51);
        labelList.add(node53);
        labelList.add(node55);
        labelList.add(node57);
        labelList.add(node71);
        labelList.add(node73);
        labelList.add(node75);
        labelList.add(node77);
    }

    private void getAllEllipse() {
        nodeList = new ArrayList<>();
        nodeList.add(node01);
        nodeList.add(node03);
        nodeList.add(node05);
        nodeList.add(node07);
        nodeList.add(node21);
        nodeList.add(node23);
        nodeList.add(node25);
        nodeList.add(node27);
        nodeList.add(node41);
        nodeList.add(node43);
        nodeList.add(node45);
        nodeList.add(node47);
        nodeList.add(node61);
        nodeList.add(node63);
        nodeList.add(node65);
        nodeList.add(node67);
        nodeList.add(node81);
        nodeList.add(node83);
        nodeList.add(node85);
        nodeList.add(node87);
        nodeList.add(node10);
        nodeList.add(node12);
        nodeList.add(node14);
        nodeList.add(node16);
        nodeList.add(node18);
        nodeList.add(node30);
        nodeList.add(node32);
        nodeList.add(node34);
        nodeList.add(node36);
        nodeList.add(node38);
        nodeList.add(node50);
        nodeList.add(node52);
        nodeList.add(node54);
        nodeList.add(node56);
        nodeList.add(node58);
        nodeList.add(node70);
        nodeList.add(node72);
        nodeList.add(node74);
        nodeList.add(node76);
        nodeList.add(node78);
    }
}

