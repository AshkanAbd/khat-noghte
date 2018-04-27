package ir.ashkanabd;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

public class Main extends Application implements Update {

    static boolean start = false;

    public static String TEAM1COLOR = "#00f";
    public static String TEAM2COLOR = "#f00";
    public static int playerID;
    public static int aiID;

    private Controller controller;

//    private MySocket mySocket;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        controller = new Controller(this);
        loader.setController(controller);
        Parent root = loader.load();
        Scene myScene = new Scene(root, 720, 620);
        primaryStage.setTitle("JAshnvare harekat");
        primaryStage.setScene(myScene);
        primaryStage.setResizable(false);
        primaryStage.show();
        aiID = 1;
        playerID = 2;
        setTeamName(1, "Computer");
        setTeamName(2, "player");
        controller.startGame();
        Game game = new Game(this, aiID, playerID);
        controller.setOnReceive(game);
        /*try {
            mySocket = new MySocket(this);
            mySocket.start();
        } catch (Exception e) {
            controller.setError("Port is busy", Controller.FLAG_END , "#000");
        }*/
    }


    public static void main(String[] args) {
        launch(args);
    }

    //@Override
    public void setTeamName(int number, String teamName) {
        controller.setTeamName(number, teamName);
    }

    //@Override
    public void startTimer() {
        controller.setTime();
    }

    //@Override
    public void stopTimer() {
        controller.stopTimer();
    }

    //@Override
    public void setNode(int x, int y, String color) {
        controller.setNode(x, y, color);
    }

    //@Override
    public void setPlayerScore(int number) {
        controller.setScore(number);
    }

    //@Override
    public int getScore(int number) {
        return controller.getScore(number);
    }

    //@Override
    public String getTeam(int number) {
        return controller.getTeam(number);
    }

    //@Override
    public void setVisible(int x, int y, String color) {
        controller.setVisible(x, y, color);
    }

    //@Override
    public void setError(String massage, String color) {
        controller.setError(massage, Controller.FLAG_NONE, color);
    }

    //@Override
    public void stop() throws Exception {
        super.stop();
        System.exit(1);
    }
}
