package ir.ashkanabd;

interface Update {
    void setTeamName(int number, String teamName);

    void setError(String massage, String color);

    void startTimer();

    void stopTimer();

    void setNode(int x, int y, String color);

    void setVisible(int x, int y, String color);

    void setPlayerScore(int number);

    int getScore(int number);

    String getTeam(int number);
}