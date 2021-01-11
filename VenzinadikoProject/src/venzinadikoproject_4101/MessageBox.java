/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package venzinadikoproject_4101;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Chrysanthi
 */
class MessageBox {
    public static void show (String message,String title){
        Stage stage = new Stage ();
        stage.setTitle(title);
        stage.setMinWidth(250);
        Label lbl = new Label ();
        lbl.setText(message);
        Button btnOK = new Button ("OK");
        btnOK.setOnAction(e-> stage.close());
        btnOK.setMinWidth(50);
        VBox pane = new VBox (20);
        pane.getChildren().addAll(lbl, btnOK);
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
