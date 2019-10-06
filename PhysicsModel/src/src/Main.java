/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

//import java.math.double;
import java.math.BigInteger;
import java.math.MathContext;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Ralph McDougall 27/12/2017
 */
public class Main extends BasicGame {

    public static World world = new World();

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 1024;
    public static double timeAcc = 1;
    public int numSims = 1;
    private double SCALE = 1;

    public static int focus = -1;
    private Spaceship s;

    private PhysicsThread pt;

    private int trailLength = 200;
    private boolean hudVisible = true;

    //private Color[] colours = {Color.white, Color.gray, Color.yellow, Color.green, Color.red, Color.orange};
    public Main(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {

        pt = new PhysicsThread();

        int sim = 0;

        if (sim == 0) {
            SCALE = 1000000000;

            Body sun = new Body("Sun", Color.white, Double.parseDouble("1989000000000000000000000000000"), new Vector(0, 0), "Sun", 697000000);

            Body mercury = new Body("Mercury", Color.gray, Double.parseDouble("330000000000000000000000"), sun, Double.parseDouble("58000000000"), "Planet", 2439000);
            Body venus = new Body("Venus", Color.yellow, Double.parseDouble("4870000000000000000000000"), sun, Double.parseDouble("108000000000"), "Planet", 6052000);
            Body earth = new Body("Earth", Color.green, Double.parseDouble("5980000000000000000000000"), sun, Double.parseDouble("150000000000"), "Planet", 6378000);
            Body mars = new Body("Mars", Color.red, Double.parseDouble("642000000000000000000000"), sun, Double.parseDouble("228000000000"), "Planet", 3398000);

            Body jupiter = new Body("Jupiter", Color.orange, Double.parseDouble("189800000000000000000000000000"), sun, Double.parseDouble("778000000000"), "Planet", 71492000);

            Body io = new Body("Io", Color.yellow, Double.parseDouble("89000000000000000000000"), jupiter, Double.parseDouble("421700000"), "Moon", 1815000);
            Body europa = new Body("Europa", Color.white, Double.parseDouble("48000000000000000000000"), jupiter, Double.parseDouble("671034000"), "Moon", 2631000);
            Body ganymede = new Body("Ganymede", Color.lightGray, Double.parseDouble("148000000000000000000000"), jupiter, Double.parseDouble("1070412000"), "Moon", 1569000);
            Body callisto = new Body("Callisto", Color.gray, Double.parseDouble("108000000000000000000000"), jupiter, Double.parseDouble("1882709000"), "Moon", 2400000);

            Body moon = new Body("The Moon", Color.gray, Double.parseDouble("7347673090000000000000"), earth, Double.parseDouble("384400000"), "Moon", 1738000);
            Body phobos = new Body("Phobos", Color.lightGray, Double.parseDouble("10600000000000000"), mars, Double.parseDouble("5989000"), "Moon", 11000);
            Body deimos = new Body("Deimos", Color.white, Double.parseDouble("1476200000000000"), mars, Double.parseDouble("23463200"), "Moon", 6000);

            s = new Spaceship("EBFR", Color.yellow, Double.parseDouble("10000"), earth, 100000000, 10000);

            world.addBody(sun);

            world.addBody(mercury);
            world.addBody(venus);
            world.addBody(earth);
            world.addBody(moon);
            world.addBody(mars);
            world.addBody(phobos);
            world.addBody(deimos);

            world.addBody(jupiter);

            world.addBody(io);
            world.addBody(europa);
            world.addBody(ganymede);
            world.addBody(callisto);

            world.addBody(s);
        } else if (sim == 1) {
            SCALE = 10000000;

            Body jupiter = new Body("Jupiter", Color.orange, Double.parseDouble("189800000000000000000000000000"), new Vector(0, 0), "Planet", 71492000);

            Body io = new Body("Io", Color.yellow, Double.parseDouble("89000000000000000000000"), jupiter, 421700000, "Moon", 1815000);
            Body europa = new Body("Europa", Color.white, Double.parseDouble("48000000000000000000000"), jupiter, 671034000, "Moon", 2631000);
            Body ganymede = new Body("Ganymede", Color.lightGray, Double.parseDouble("148000000000000000000000"), jupiter, 1070412000, "Moon", 1569000);
            Body callisto = new Body("Callisto", Color.gray, Double.parseDouble("108000000000000000000000"), jupiter, 1882709000, "Moon", 2400000);

            world.addBody(jupiter);

            world.addBody(io);
            world.addBody(europa);
            world.addBody(ganymede);
            world.addBody(callisto);
        }

        pt.start();
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {

        Input input = gc.getInput();
        input.disableKeyRepeat();

        if (input.isKeyPressed(Input.KEY_SUBTRACT)) {
            SCALE = SCALE * 2;
        }
        if (input.isKeyPressed(Input.KEY_ADD)) {
            SCALE = SCALE / 2;
        }

        if (input.isKeyDown(Input.KEY_W)) {
            s.addForce(new Vector(0, s.thrust * -1));
        }
        if (input.isKeyDown(Input.KEY_A)) {
            s.addForce(new Vector(s.thrust * -1, 0));
        }
        if (input.isKeyDown(Input.KEY_S)) {
            s.addForce(new Vector(0, s.thrust));
        }
        if (input.isKeyDown(Input.KEY_D)) {
            s.addForce(new Vector(s.thrust, 0));
        }

        if (input.isKeyPressed(Input.KEY_RBRACKET)) {
            --focus;
            if (focus < 0) {
                focus = world.allBodies.size() - 1;
            }
        }
        if (input.isKeyPressed(Input.KEY_LBRACKET)) {
            ++focus;
            if (focus > world.allBodies.size() - 1) {
                focus = 0;
            }
        }

        if (input.isKeyPressed(Input.KEY_PERIOD)) {
            timeAcc = timeAcc * (2);
        }
        if (input.isKeyPressed(Input.KEY_COMMA)) {
            timeAcc = timeAcc / (2);
        }

        if (input.isKeyPressed(Input.KEY_LSHIFT)) {
            trailLength += 50;
        }
        if (input.isKeyPressed(Input.KEY_LCONTROL)) {
            trailLength -= 50;
            if (trailLength < 0) {
                trailLength = 0;
            }
        }
        if (input.isKeyPressed(Input.KEY_Q)) {
            hudVisible = !hudVisible;
        }
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        grphcs.setColor(Color.white);

        if (hudVisible) {
            grphcs.drawString("UPS: " + pt.ups, 10, 50);

            grphcs.drawString("SCALE: " + SCALE, 120, 10);
            grphcs.drawString("FOCUS: " + world.allBodies.get(focus).name, 120, 50);
            grphcs.drawString("TIME ACCELERATION: " + timeAcc, 120, 90);
            grphcs.drawString("TRAIL LENGTH: " + trailLength, 120, 130);

        }

        float w = 16;
        float h = 16;

        float yPos = 50;

        int num = 0;
        for (Body b1 : world.allBodies) {
            //System.out.println(b1.position.v);
            boolean toScale = false;
            if (b1.bodyClass.equals("Sun")) {
                w = 32;
                h = 32;
            }
            if (b1.bodyClass.equals("Planet")) {
                w = 16;
                h = 16;
            }
            if (b1.bodyClass.equals("Moon")) {
                w = 8;
                h = 8;
            }
            if (b1.bodyClass.equals("Spaceship")) {
                w = 4;
                h = 4;
            }

            float f = (float) (b1.radius / (SCALE));
            if (2 * f > w) {
                w = 2 * f;
                h = 2 * f;
                toScale = true;
            }

            grphcs.setColor(b1.colour);

            double bx = b1.position.v.get(0);
            double by = b1.position.v.get(1);

            float x = (float) ((bx - (world.allBodies.get(focus).position.v.get(0))) / (SCALE) + WIDTH / 2 - w / 2);
            float y = (float) ((by - (world.allBodies.get(focus).position.v.get(1))) / (SCALE) + HEIGHT / 2 - h / 2);

            b1.currSkipStep += 1;

            if (b1.currSkipStep == 10) {
                b1.currSkipStep = 0;
                Coordinate c = new Coordinate(bx, by);
                b1.trail.add(c);
                while (b1.trail.size() > trailLength) {
                    b1.trail.remove(0);
                    //System.out.println("REMOVED!");
                }
            }

            float tx1 = 0;
            float ty1 = 0;
            float tx2 = 0;
            float ty2 = 0;

            if (b1.trail.size() != 0) {
                for (int i = 0; i < b1.trail.size(); ++i) {
                    tx1 = (float) ((b1.trail.get(i).x - (world.allBodies.get(focus).position.v.get(0))) / (SCALE) + WIDTH / 2);
                    ty1 = (float) ((b1.trail.get(i).y - (world.allBodies.get(focus).position.v.get(1))) / (SCALE) + HEIGHT / 2);
                    if (i != 0) {
                        //System.out.println(tx1 + " " + ty1 + " " + tx2 + " " + ty2);
                        grphcs.drawLine(tx1, ty1, tx2, ty2);
                    }
                    tx2 = tx1;
                    ty2 = ty1;
                }

                grphcs.drawLine(tx2, ty2, x + w / 2, y + h / 2);
            }
            
            yPos += 180;

            if (toScale) {
                grphcs.fillOval(x, y, w, h);
            } else {
                grphcs.drawOval(x, y, w, h);
            }
            ++num;
        }
        //System.out.println("");

    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer appgc = new AppGameContainer(new Main("Physics Model"), WIDTH, HEIGHT, false);
        appgc.setShowFPS(true);
        appgc.start();
    }

}
