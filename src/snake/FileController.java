/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

/**
 *
 * @author Pocitac
 */
public class FileController {
    
    
    static PrintWriter writer;//na score
    public static String pathToLocalDirectory;
    public static String pathToSoundForAudioPlayer;

    public static void chceckPackages() throws URISyntaxException {
        if (!System.getProperty("user.dir").substring(System.getProperty("user.dir").lastIndexOf('\\') + 1).equals("Snake")) { //podminka, ktera zjistuje, jeslti to nezpoustime odjinud
            Settings.spoustenoMimoNebeansSlozky= true;
            pathToLocalDirectory = FileController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("Snake.jardocuments", "");
            System.out.println(pathToLocalDirectory);
            /*File docDir = new File(pathToLocalDirectory + "/documents");
            if (!docDir.exists()) {                 //kdyz neexistuje slozka documents
                if (docDir.mkdir()) {
                    System.out.println("Documents dir was created.");
                } else {
                    System.out.println("Documents dir wasnt created.");
                }
                try {
                    //pro score
                    writer = new PrintWriter(docDir.getPath() + "\\Score.txt", "UTF-8");
                    writer.write("0");
                    writer.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }*/

            //nastavit kde je slozka hudbya dokumentu
            Settings.pathToLocDir = pathToLocalDirectory + "documents//";
            /*System.out.println(Settings.pathToDocDir);*/
        }
    }

}
