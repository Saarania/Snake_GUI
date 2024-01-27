/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Sara Praks
 * 
 * 7
 * 
 * Hra po hre "Osu" je celkem jednuducha na naprogramovani, je zajimava v nizkem
 * poctu potrebnych radku v souboru Controller
 */
public class Snake extends Application {

    public static Stage stage;
    public static StackPane root = new StackPane();
    public static Scene scene;

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        stage = primaryStage;

        scene = new Scene(root, 650, 700);
        
        //FileController.chceckPackages();
        Controller.Inicializate();

        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                //primaryStage.close();
            }
            if (event.getCode() == KeyCode.UP && Settings.predeslySmer != Direction.DOWN) {
                Settings.aktualniSmer = Direction.UP;
            }
            if (event.getCode() == KeyCode.DOWN && Settings.predeslySmer != Direction.UP) {
                Settings.aktualniSmer = Direction.DOWN;
            }
            if (event.getCode() == KeyCode.LEFT && Settings.predeslySmer != Direction.RIGHT) {
                Settings.aktualniSmer = Direction.LEFT;
            }
            if (event.getCode() == KeyCode.RIGHT && Settings.predeslySmer != Direction.LEFT) {
                Settings.aktualniSmer = Direction.RIGHT;
            }
        });
        
        scene.setFill(Color.BLACK);

        startThread();

        primaryStage.setTitle("Snake");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //muze se zavolat pouze jednou
    static boolean b = true; //promenna co to kontroluje

    public static void startThread() {
        if (b) {
            b = false;
            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(Settings.MOVE_INTERVAL);
                        Platform.runLater(() -> {
                            if (Settings.hraProbiha) { //kontrola, jestli hra probiha
                                Controller.timeChange();
                            }else {
                                if (Controller.restartImage != null) {
                                    Controller.restartImage.setRotate(Controller.restartImage.getRotate()-Settings.RESET_IMAGE_ROTATION_SPEED);
                                }
                            }
                        });
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

            });

            thread.setDaemon(true);
            thread.start();
        } else {
            System.out.println("TATO METODA startThread SE ZNOVA VOLAT NEMUZE!");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
