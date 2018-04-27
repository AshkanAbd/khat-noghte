package ir.ashkanabd;

public interface OnReceive {
    void received(String request, int client);

    void start();
}
