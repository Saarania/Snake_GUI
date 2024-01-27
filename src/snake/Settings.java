/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import javafx.scene.shape.Path;

/**
 *
 * @author Pocitac
 */
public class Settings {
    //konstanty
    public static final int FIELD_SIZE = 45; //px
    public static final int BOARD_WIDTH = 25;
    public static final int BOARD_HEIGHT = 15;
    public static final int MOVE_INTERVAL = 150;//v milisekundach pouziva se v casovaci
    public static final int RESET_IMAGE_ROTATION_SPEED = 20;//rychlost jak se bude otacet restart tlacitko
    
    public static int score = 0;
    public static int highScore = 0;
    
    public static boolean hraProbiha = false; //nastavenim na false se ukonci casovac, zaciname na false, dokud tlacitkem start nespustime hada
    public static boolean nezmensitSeTentoPohyb = false; //nastavi se na true pokazde, kdyz had sni jidlo a tim o jedno vzroste
    public static boolean spoustenoMimoNebeansSlozky = false; //je true pokud poustime samotny jar odjinud
    public static Direction aktualniSmer = Direction.LEFT;
    public static Direction predeslySmer = Direction.RIGHT; //vzdycky se nastavi na to co ted bylo
    public static String pathToLocDir  = System.getProperty("user.dir") + "\\src\\documents\\Score.txt"; //[cesta k dokumentu se skorem
}
