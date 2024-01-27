/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Pocitac
 */
public class Field {

    private Type type;
    private Rectangle rectangle;
    private ImageView edge;
    private int x;
    private int y;

    public Field(Type type, int x, int y) { //nastavi i sirku
        this.x = x;
        this.y = y;
        this.edge = new ImageView("images/edge.png");
        this.rectangle = new Rectangle();
        this.rectangle.setWidth(Settings.FIELD_SIZE);
        this.rectangle.setHeight(Settings.FIELD_SIZE);

        setType(type);

        Snake.root.getChildren().add(rectangle);
        Snake.root.getChildren().add(edge);
    }

    public Field() {
    }

    public Type getType() {
        return type;
    }

    public Field moveFoward() { //vraci null pokud volas na poli co neni had
        if (getType() != Type.HAD) {
            return null;
        }
        Settings.predeslySmer = Settings.aktualniSmer;
        Field budouciHad = new Field(); //misto, kde by mel byt budouci had
        if (!kontrolaPohybuDopredu()) { //zkontroluje a pokud je spatne spravne hada presune
            switch (Settings.aktualniSmer) {//nastavei budouciho mista
                case DOWN:
                    budouciHad = Controller.fields[x][y + 1];
                    break;
                case LEFT:
                    budouciHad = Controller.fields[x - 1][y];
                    break;
                case RIGHT:
                    budouciHad = Controller.fields[x + 1][y];
                    break;
                case UP:
                    budouciHad = Controller.fields[x][y - 1];
                    break;
            }
        }else {
            budouciHad = Controller.fields[x][y];
        }

        //kontrola, do ceho se vjede
        if (budouciHad.getType() == Type.JIDLO) { //pokud se vjede do jidla
            Controller.jidloByloSnezeno();
            budouciHad.setType(Type.HAD);
        } else {
            if (budouciHad.getType() == Type.PREKAZKA || budouciHad.getType() == Type.HAD) { //pokud se vjede do prekazky nebo na hada
                Controller.gameOver();
            } else {                                     // pokud se vjede na nic
                budouciHad.setType(Type.HAD);
            }
        }
        return budouciHad;
    }

    private boolean kontrolaPohybuDopredu() { //aby se nestalo ze vyjedeme z herni oblasti vraci true pokud vyjedeme z pole
        if (x == 0 && Settings.aktualniSmer == Direction.LEFT) {
            x = Settings.BOARD_WIDTH - 1;
            return true;
        }
        if (y == 0 && Settings.aktualniSmer == Direction.UP) {
            y = Settings.BOARD_HEIGHT - 1;
            return true;
        }
        if (x == Settings.BOARD_WIDTH - 1 && Settings.aktualniSmer == Direction.RIGHT) {
            x = 0;
            return true;
        }
        if (y == Settings.BOARD_HEIGHT - 1 && Settings.aktualniSmer == Direction.DOWN) {
            y = 0;
            return true;
        }
        return false;
    }

    public ImageView getEdge() {
        return edge;
    }

    public void setType(Type type) {
        this.type = type;
        if (type == null) {
            this.rectangle.setFill(Color.rgb(200, 200, 200)); //nastavi se na sedou pokud je null, protoze vsehno ostatni musi ve switchi nastat
        } else {
            switch (type) {
                case HAD:
                    this.rectangle.setFill(Color.GREEN);
                    break;
                case JIDLO:
                    this.rectangle.setFill(Color.YELLOW);
                    break;
                case PREKAZKA:
                    this.rectangle.setFill(Color.BLACK);
                    break;
            }
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setTranslateX(int x) {
        if (rectangle != null) {
            rectangle.setTranslateX(x);
            edge.setTranslateX(x);
        } else {
            System.out.println("NULL pointer exception by vysel rectangle je null!");
        }
    }

    public void setTranslateY(int y) {
        if (rectangle != null) {
            rectangle.setTranslateY(y);
            edge.setTranslateY(y);
        } else {
            System.out.println("NULL pointer exception by vysel rectangle je null!");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
