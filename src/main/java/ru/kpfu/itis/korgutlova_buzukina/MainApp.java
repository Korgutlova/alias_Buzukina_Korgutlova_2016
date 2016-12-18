package ru.kpfu.itis.korgutlova_buzukina;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/sample.fxml"));
        primaryStage.setTitle("Alias");
        primaryStage.setScene(new Scene(root, 700, 650));
        primaryStage.show();
        Scanner scanner = new Scanner(System.in);
        int port = 3456;
        String host = "localhost";
        Socket s = new Socket(host, port);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter printWriter = new PrintWriter(s.getOutputStream(), true);
        while (true) {
            System.out.print("Введите сообщение: ");
            String outMessage = scanner.nextLine();
            printWriter.println(outMessage);
            while (bufferedReader.ready()) {
                String inMessage = bufferedReader.readLine();
                System.out.println(inMessage);
            }

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
