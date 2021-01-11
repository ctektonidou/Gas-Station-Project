/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package venzinadikoproject_4101;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Chrysanthi
 */
public class VenzinadikoProject_4101 extends Application {

    Label welcomeLabel = new Label("Καλώς ήρθατε");
    Label cardLbl = new Label("Συμπληρώστε τoν αριθμό της πιστωτικής σας κάρτας (10 ΝΟΎΜΕΡΑ)");
    Label resultLbl = new Label(" ");
    Label typosLbl = new Label("Επιλέξτε τι θα θέλατε να βάλετε");
    Label posoLbl = new Label("Πόσα χρήματα θα θέλατε να βάλετε");
    Label litraLbl = new Label("Λίτρα:");
    Label xrimaLbl = new Label("Ποσό:");

    TextField acountNumberText = new TextField("");
    TextField posoText = new TextField("");
    TextField Timer1Text = new TextField("0");
    TextField Timer2Text = new TextField("0");

    String cardNo;
    String type;
    String posoString;
    String posoStringnew;
    String lastFourDigits = "";
    public static double litra;
    public static double litranew;
    public static double posodouble;
    public static double posodoublenew;
    double timi;

    Button btnCard = new Button("Έλεγχος");
    Button btnfull = new Button("Γέμισμα");
    Button btnStop = new Button("Stop");

    ToggleGroup rb = new ToggleGroup();

    RadioButton radioBtn1 = new RadioButton("Πετρέλαιο");
    RadioButton radioBtn2 = new RadioButton("Βενζίνη");
    RadioButton radioBtn3 = new RadioButton("Αέριο");

    int fores;
    int speed;
    int speed2;
    double maxfores;
    int xrimafores;
    int moneytext;

    Timer Timer1 = new Timer();//Δημιουργία Timer
    Timer Timer2 = new Timer();//Δημιουργία Timer2

    static boolean checkLuhn(String cardNo) {
        int nDigits = cardNo.length();
        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {
            int d = cardNo.charAt(i) - '0';
            if (isSecond == true) {
                d = d * 2;
            }
            nSum += d / 10;
            nSum += d % 10;
            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    @Override
    public void start(Stage primaryStage) {
        Application.setUserAgentStylesheet(getClass().getResource("oil_css_4101.css").toExternalForm());
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(welcomeLabel, 0, 0, 1, 1);
        welcomeLabel.setAlignment(Pos.TOP_LEFT);

        grid.add(cardLbl, 0, 1, 2, 1);
        cardLbl.setAlignment(Pos.TOP_LEFT);

        grid.add(acountNumberText, 0, 2, 2, 1);
        acountNumberText.setAlignment(Pos.TOP_CENTER);

        grid.add(btnCard, 1, 3, 1, 1);
        grid.setHalignment(btnCard, HPos.RIGHT);

        grid.add(resultLbl, 0, 4, 2, 1);
        grid.setHalignment(resultLbl, HPos.CENTER);
        
        //Έλεγχος κάρτας αν είναι έγκυρη
        btnCard.setOnAction(e12 -> {
            cardNo = acountNumberText.getText();
            if (cardNo.length() == 10) {
                if (checkLuhn(cardNo)) {
                    resultLbl.setText("Έγκυρη Κάρτα");
                    System.out.println("Έγκυρη Κάρτα");
                    lastFourDigits = cardNo.substring(cardNo.length() - 4);
                } else {
                    resultLbl.setText("Μη Έγκυρη Κάρτα");
                    System.out.println("Μη Έγκυρη Κάρτα");
                }
            } else {
                resultLbl.setText("Πρέπει να υπάρχουν 10 νούμερα");
                System.out.println("Πρέπει να υπάρχουν 10 νούμερα");
            }
        });

        grid.add(typosLbl, 0, 5, 1, 1);
        radioBtn1.setToggleGroup(rb);
        radioBtn2.setToggleGroup(rb);
        radioBtn3.setToggleGroup(rb);
        VBox rbs = new VBox(10, radioBtn1, radioBtn2, radioBtn3);
        grid.add(rbs, 0, 6, 1, 1);
        grid.add(posoLbl, 1, 5, 1, 1);
        grid.add(posoText, 1, 6, 1, 1);
        grid.setHalignment(posoText, HPos.CENTER);
        grid.add(btnfull, 1, 7, 1, 1);

        VBox vBox = new VBox(btnfull, litraLbl, Timer1Text);
        VBox vBox2 = new VBox(btnStop, xrimaLbl, Timer2Text);
        grid.add(vBox, 0, 8, 1, 1);
        grid.add(vBox2, 1, 8, 1, 1);

        fores = 0;
        xrimafores = 0;
        moneytext = 0;

        btnfull.setOnAction(e13 -> {

            posoString = posoText.getText();
            posoStringnew = Timer2Text.getText();
            moneytext = Integer.valueOf(posoText.getText());

            if (radioBtn1.isSelected()) {
                type = "Πετρέλαιο";
                posodouble = Double.parseDouble(posoString);
                timi = Double.parseDouble(new DecimalFormat("##").format(posodouble)); //στρογγυλοποίηση
                litra = posodouble / 1.15;
                maxfores = Double.parseDouble(new DecimalFormat("##").format(litra)); //στρογγυλοποίηση
                speed = 220;
                speed2 = 140;
            }
            if (radioBtn2.isSelected()) {
                type = "Βενζίνη";
                posodouble = Double.parseDouble(posoString);
                timi = Double.parseDouble(new DecimalFormat("##").format(posodouble)); //στρογγυλοποίηση
                litra = posodouble / 1.35;
                maxfores = Double.parseDouble(new DecimalFormat("##").format(litra)); //στρογγυλοποίηση
                speed = 280;
                speed2 = 100;
            }
            if (radioBtn3.isSelected()) {
                type = "Αέριο";
                posodouble = Double.parseDouble(posoString);
                timi = Double.parseDouble(new DecimalFormat("##").format(posodouble)); //στρογγυλοποίηση
                litra = posodouble / 0.70;
                maxfores = Double.parseDouble(new DecimalFormat("##").format(litra)); //στρογγυλοποίηση
                speed = 280;
                speed2 = 150;
            }

            TimerTask task1 = new TimerTask() {
                public void run() {
                    fores = Integer.valueOf(Timer1Text.getText());
                    fores++;
                    //Ο timer σταματάει όταν φράνει την τιμή των λίτρων
                    if (fores == maxfores) {
                        Timer1.cancel();
                    }
                    String foresstring = String.valueOf(fores);
                    Platform.runLater(() -> Timer1Text.setText(foresstring));
                }
            };

            TimerTask task2 = new TimerTask() {
                public void run() {
                    xrimafores = Integer.valueOf(Timer2Text.getText());
                    xrimafores++;
                    //Ο timer σταματάει ότανφτάσει την τιμή των λίτρων 
                    if (xrimafores == timi) {
                        Timer2.cancel();
                    }
                    String foresstring = String.valueOf(xrimafores);
                    Platform.runLater(() -> Timer2Text.setText(foresstring));

                }

            };

            Timer2.schedule(task2, 0, speed2);
            Timer1.schedule(task1, 0, speed);

            //απόδειξη με το πάτημα γέμισμα
            try {
                FileWriter writer = new FileWriter("card.txt", true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                bufferedWriter.write("xxxxxx" + lastFourDigits + "    "
                        + type + "    "
                        + litra + "    "
                        + posoText.getText() + "    "
                        + LocalTime.now() + "\n");

                bufferedWriter.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }

            String message = "************************************\n"
                    + "Αριθμός λογαριασμού: xxxxxx" + lastFourDigits + "\n"
                    + "Τύπος Καυσίμου: " + type + "\n"
                    + "Αριθμός λίτρων: " + litra + "\n"
                    + "Ποσό: " + posoText.getText() + "\n"
                    + "Ώρα: " + LocalTime.now() + "\n"
                    + "***********************************";
            MessageBox.show(message, "Απόδειξη");
        });

        btnStop.setOnAction(e -> {
            Timer1.cancel();
            Timer2.cancel();
            
            
            //διαγράφει την προηγούμενη καταγραφή  - κουμπί γέμισμα - δεν δουλεύει
            try {
                File inputFile = new File("card.txt");
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                FileWriter writer = new FileWriter("card.txt", true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                String lineToRemove = posoText.getText();
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    // trim newline when comparing with lineToRemove
                    String trimmedLine = currentLine.trim();
                    if (trimmedLine.equals(lineToRemove)) {
                        bufferedWriter.write("");
                        bufferedWriter.close();
                    }
                }
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            

            //δεύτερη απόδειξη - στοπ
            try {
                FileWriter writer = new FileWriter("card.txt", true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                bufferedWriter.write("xxxxxx" + lastFourDigits + "    "
                        + type + "    "
                        + Timer1Text.getText() + "    "
                        + Timer2Text.getText() + "    "
                        + LocalTime.now() + "\n");

                bufferedWriter.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }

            String message = "************************************\n"
                    + "Αριθμός λογαριασμού: xxxxxx" + lastFourDigits + "\n"
                    + "Τύπος Καυσίμου: " + type + "\n"
                    + "Αριθμός λίτρων: " + Timer1Text.getText() + "\n"
                    + "Ποσό: " + Timer2Text.getText() + "\n"
                    + "Ώρα: " + LocalTime.now() + "\n"
                    + "***********************************";
            MessageBox.show(message, "Απόδειξη");

        });

        Scene scene = new Scene(grid, 600, 575);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ΒΕΝΖΙΝΑΔΙΚΟ");
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
