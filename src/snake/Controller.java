/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import static snake.Snake.root;

/**
 *
 * @author Pocitac
 */
public class Controller {

    //herni pole
    public static Field[][] fields = new Field[Settings.BOARD_WIDTH][Settings.BOARD_HEIGHT];

    //pole obrazku ukazanych v hlavni nabidce
    private static ArrayList<Node> mainMenuImages = new ArrayList<>();
    public static ArrayList<Field> teloHada = new ArrayList<>(); //uchovava v sobe mista kde je had

    //tlacitko restart ma se otacet
    public static ImageView restartImage = new ImageView("images/restart.png");
    //text se scorem
    public static Text scoreText = new Text();
    public static Text highScoreText = new Text();

    //POMOCNE
    static Random random = new Random();

    //metoda, ktera se zavola pri startu programu
    public static void Inicializate() {
        try { //nastaveni nejvyssiho score
            Path pathToHightScore = Paths.get(Settings.pathToLocDir);
            Settings.highScore = (Integer.parseInt(Files.readAllLines(pathToHightScore).get(0)));
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } //nastavovani prvku v nabidce main menu
        ImageView background = new ImageView("images/background.png"), startImage = new ImageView("images/start.png"), quitImage = new ImageView("images/quit.png");
        highScoreText = new Text("Hight Score = " + Settings.highScore);
        mainMenuImages.add(background);
        mainMenuImages.add(quitImage);
        mainMenuImages.add(startImage);
        mainMenuImages.add(highScoreText);
        Snake.root.getChildren().add(background);
        Snake.root.getChildren().add(startImage);
        Snake.root.getChildren().add(quitImage);
        Snake.root.getChildren().add(highScoreText);

        highScoreText.setTranslateX(400);
        highScoreText.setTranslateY(-50);
        highScoreText.setFont(Font.font(40));
        highScoreText.setFill(Color.WHITE);
        quitImage.setTranslateY(200);
        quitImage.setTranslateX(-150);
        startImage.setTranslateY(200);
        startImage.setTranslateX(150);
        quitImage.setOnMousePressed((MouseEvent event) -> {
            Snake.stage.close();
        });
        startImage.setOnMousePressed((MouseEvent event) -> {
            for (int i = 0; i < mainMenuImages.size(); i++) {
                mainMenuImages.get(i).setVisible(false);
            }
            //nastaveni ze stackpainu na group
            Snake.root.setTranslateX(Snake.root.getTranslateX() - 1366 / 2 + 300);
            Snake.root.setTranslateY(Snake.root.getTranslateY() - 768 / 2 + 50);
            createGame();
        });
        
        createMainMenu();
    }

    //zavola se na zacatku a kdykoliv chceme ukazat nabidku
    public static void createMainMenu() {
        for (int i = 0; i < mainMenuImages.size(); i++) {
            mainMenuImages.get(i).setVisible(true);
        }
        
    }

    private static boolean spustenoPoprve = true; //kontroluje jestli se metoda pustla poprve

    public static void createGame() {
        //nastaveni hry
        for (int i = 0; i < Settings.BOARD_WIDTH; i++) {
            for (int j = 0; j < Settings.BOARD_HEIGHT; j++) {
                if (spustenoPoprve) {
                    fields[i][j] = new Field(null, i, j);
                    fields[i][j].setTranslateX(i * Settings.FIELD_SIZE - 250);
                    fields[i][j].setTranslateY(j * Settings.FIELD_SIZE);
                } else {
                    fields[i][j].getRectangle().setVisible(true);
                    fields[i][j].getEdge().setVisible(true);
                }
            }
        }
        spustenoPoprve = false;
        //nastaveni score textu
        scoreText = new Text("Score = " + Settings.score);
        scoreText.setFont(Font.font(40));
        scoreText.setTranslateX(950);
        scoreText.setTranslateY(400);
        scoreText.setFill(Color.WHITE);
        root.getChildren().add(scoreText);
        //rozmisteni hadoveho tela
        fields[15][4].setType(Type.HAD); //hlava
        fields[16][4].setType(Type.HAD);
        fields[17][4].setType(Type.HAD);
        teloHada.add(fields[15][4]);
        teloHada.add(fields[16][4]);
        teloHada.add(fields[17][4]);
        //rozmisteni prekazek a jidla
        fields[5][3].setType(Type.PREKAZKA);
        fields[18][8].setType(Type.PREKAZKA);
        fields[7][12].setType(Type.PREKAZKA);
        fields[16][3].setType(Type.PREKAZKA);
        fields[12][5].setType(Type.JIDLO);

        Settings.hraProbiha = true;
    }

    public static ArrayList<Field> najdiPrazdneMista() {
        ArrayList<Field> prazdneMista = new ArrayList<>();
        for (int i = 0; i < Settings.BOARD_WIDTH; i++) {
            for (int j = 0; j < Settings.BOARD_HEIGHT; j++) {
                if (fields[i][j].getType() == null) {
                    prazdneMista.add(fields[i][j]);
                }
            }
        }
        return prazdneMista;
    }

    public static void timeChange() {
        Field newHlava = teloHada.get(0).moveFoward();//pokazde posune a nastavi se nova hlava
        teloHada.add(0, newHlava);
        if (!Settings.nezmensitSeTentoPohyb) { //pokud se snedlo jidlo, had se nakonci nezmensi
            teloHada.get(teloHada.size() - 1).setType(null);
            teloHada.remove(teloHada.size() - 1);
        }
        Settings.nezmensitSeTentoPohyb = false;
    }

    public static void jidloByloSnezeno() { //provede se kdyz se sni jidlo ale to je asi z nazvu jasne :D
        Settings.nezmensitSeTentoPohyb = true;
        ArrayList<Field> mozneMistaNaPolozeniJidla = najdiPrazdneMista();
        mozneMistaNaPolozeniJidla.get(random.nextInt(mozneMistaNaPolozeniJidla.size())).setType(Type.JIDLO);
        scoreText.setText("Score = " + ++Settings.score);
    }

    //metoda zavola se na konci hry
    public static void gameOver() {
        Settings.hraProbiha = false;
        ImageView gameOverImage = new ImageView("images/gameOver.png");
        root.getChildren().add(gameOverImage);
        gameOverImage.setTranslateX(1366 / 2 - 300);
        gameOverImage.setTranslateY(768 / 2 - 50);
        restartImage = new ImageView("images/restart.png");
        root.getChildren().add(restartImage);
        restartImage.setTranslateX(gameOverImage.getTranslateX() - 50);
        restartImage.setTranslateY(gameOverImage.getTranslateY() + 100);
        restartImage.setOnMousePressed((MouseEvent event) -> { //nastaveni zmacknuti tlacikta restart
            eraseImageView(gameOverImage);
            root.setTranslateX(0); //umisteni vseho doprostred
            root.setTranslateY(0);
            eraseImageView(restartImage);
            teloHada.clear();
            scoreText = null;
            ulozNejvyssiScore();
            backToMainMenu();
        });
    }

    //zavola se na konci hry
    private static void ulozNejvyssiScore() {
        if (Settings.score > Settings.highScore) {
            PrintWriter writer = null;
            try {
                //urcovani noveho skore
                writer = new PrintWriter(Settings.pathToLocDir);
                writer.print(Settings.score);
                writer.close();
                Settings.highScore = Settings.score;
                highScoreText.setText("High Score = " + Settings.highScore);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                writer.close();
            }
        }
    }

    //zavola se pokud se zmackne tlacitko reset, vynulovani a schovani herniho pole
    private static void backToMainMenu() {
        for (int i = 0; i < Settings.BOARD_WIDTH; i++) {
            for (int j = 0; j < Settings.BOARD_HEIGHT; j++) {
                fields[i][j].setType(null);
                fields[i][j].getRectangle().setVisible(false);
                fields[i][j].getEdge().setVisible(false);
                createMainMenu();
            }
        }
    }

    public static void eraseImageView(ImageView iv) {
        iv.setVisible(false);
        iv = null;
    }
}
