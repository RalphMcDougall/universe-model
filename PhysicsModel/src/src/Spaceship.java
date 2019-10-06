/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

//import java.math.double;
import org.newdawn.slick.Color;

/**
 *
 * @author Ralph McDougal 28/12/2017
 */
public class Spaceship extends Body{
    
    public double thrust = 0; // In Newtons
    
    public Spaceship(String name, Color colour, double mass, Body parent, double radius, double thrust)
    {
        super(name, colour, mass, parent, radius, "Spaceship", 1);
        this.thrust = thrust;
        //System.out.println(this.thrust);
    }
    
}
