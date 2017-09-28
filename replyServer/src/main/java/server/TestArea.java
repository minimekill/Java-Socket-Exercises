package server;

public class TestArea {

    public static void main(String[] args) {
        String message = "etord#peter";
        String order = "";
        String word = "";

        for (int i = 0; i < message.length(); i++) {
            if (message.substring(i, i + 1).equals("#")) {

                order = message.substring(0, i);
                word = message.substring(i + 1, message.length());
            }
        }

    }
}
