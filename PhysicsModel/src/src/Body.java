/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

//import java.math.double;
//import static java.math.double.ROUND_HALF_DOWN;
//import static java.math.double.ROUND_HALF_UP;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Queue;
import org.newdawn.slick.Color;

/**
 *
 * @author Ralph McDougall 27/12/2017
 */
public class Body {
    
    public double mass = 1;
    public Vector position = new Vector(0, 0);
    public Vector netForce = new Vector(0, 0);
    private Vector acceleration = new Vector(0, 0);
    public Vector velocity = new Vector(0, 0);
    
    private ArrayList<Vector> allForces = new ArrayList<Vector>(); 
    
    public String bodyClass = "Sun";
    public String name = "TEMP";
    public Color colour = Color.white;
    
    public ArrayList<Coordinate> trail = new ArrayList<Coordinate>();
    public int currSkipStep = 0;
    
    public double radius;
    
    public Body(String name, Color colour, double mass, Vector position, String bc, double r)
    {
        this.name = name;
        this.mass = mass;
        this.colour = colour;
        this.position = position;
        this.bodyClass = bc;
        this.radius = r;
    }
    
    public Body(String name, Color colour, double mass, Body attractor, double orbitRadius, String bc, double r)
    {
        this.name = name;
        this.mass = mass;
        this.bodyClass = bc;
        
        this.colour = colour;
        this.radius = r;
        
        this.position = new Vector(attractor.position.v.get(0), attractor.position.v.get(1) - (orbitRadius));
        this.velocity = new Vector(Math.pow(Constants.G * (attractor.mass) / (orbitRadius), 0.5) + (attractor.velocity.v.get(0)), (attractor.velocity.v.get(1)));
        
    }
    
    public void update()
    {
        netForce = new Vector(0, 0);
        
        for (int i = 0; i < allForces.size(); ++i)
        {
            netForce.add(allForces.get(i));
        }
        allForces.clear();
        
        double ax = acceleration.v.get(0);
        double ay = acceleration.v.get(1);
        
        acceleration = new Vector(netForce.v.get(0) / (mass), netForce.v.get(1) / (mass));
        ax = ax + (acceleration.v.get(0));
        ay = ay + (acceleration.v.get(1));
        
        ax = ax / 2;
        ay = ay / 2;
        
        velocity.v.set(0, velocity.v.get(0) + (acceleration.v.get(0) * (Main.timeAcc)));
        velocity.v.set(1, velocity.v.get(1) + (acceleration.v.get(1) * (Main.timeAcc)));
        
        double vs = velocity.v.get(0) * (velocity.v.get(0)) + (velocity.v.get(1) * (velocity.v.get(1)));
        
        if (vs > Constants.c * Constants.c)
        {
            double theta = Math.atan2(Double.parseDouble(velocity.v.get(1).toString()), Double.parseDouble(velocity.v.get(0).toString()));
            velocity.v.set(0, Constants.c * (Math.cos(theta)));
            velocity.v.set(1, Constants.c * (Math.sin(theta)));
            System.out.println("APPROACHING C!");
        }
        
        position.v.set(0, position.v.get(0) + (velocity.v.get(0) * (Main.timeAcc)));
        position.v.set(1, position.v.get(1) + (velocity.v.get(1) * (Main.timeAcc)));
        
    }
    
    public void addForce(Vector f)
    {
        allForces.add(f);
    }
    
    public void affected(Body b)
    {
        if (this.equals(b))
        {
            return;
        }
        double diffX = this.position.v.get(0) - (b.position.v.get(0));
        double diffY = this.position.v.get(1) - (b.position.v.get(1));
        
        // r squared
        double rs = (diffX * (diffX)) + (diffY * (diffY));
        
        //System.out.println("1: " + rs);
        //double r = sqrt(rs, -5);
        //System.out.println(r);
        
        double f = Constants.G * (this.mass) * (b.mass) / (rs);
        
        double theta = Math.atan2(diffY, diffX);
        
        allForces.add(new Vector(f * -1 * Math.cos(theta), f * Math.sin(theta) * -1));
        
    }
    
//    private double sqrt(double in, int scale)
//    {
//        double sqrt = new double(1);
//        sqrt.setScale(scale + 3, RoundingMode.FLOOR);
//        double store = new double(in.toString());
//        boolean first = true;
//        do
//        {
//            if (!first)
//            {
//                store = new double(sqrt.toString());
//            }
//            else
//            {
//                first = false;
//            }
//            store.setScale(scale + 3, RoundingMode.FLOOR);
//            sqrt = in.divide(store, scale + 3, RoundingMode.FLOOR).add(store).divide(double.valueOf(2), scale + 3, RoundingMode.FLOOR);
//        } while (!store.equals(sqrt));
//        return sqrt.setScale(scale, RoundingMode.FLOOR);
//    }
}
