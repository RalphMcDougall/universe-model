/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.ArrayList;

/**
 *
 * @author Ralph McDougall 27/12/2017
 */
public class World {
    
    public ArrayList<Body> allBodies = new ArrayList<Body>();
    
    public World()
    {
        
    }
    
    public void addBody(Body b)
    {
        allBodies.add(0, b);
        Main.focus++;
    }
    
    public void applyGravitation()
    {
        for (Body b1 : allBodies)
        {
            for (Body b2 : allBodies)
            {
                b1.affected(b2);
            }
        }
    }
    
    public void updateBodies()
    {
        for (Body b1 : allBodies)
        {
            b1.update();
        }
    }
    
}
