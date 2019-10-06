/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author ralph
 */
public class PhysicsThread extends Thread {
    
    public int ups = 0;
    
    private int updates = 0;
    private long lastChange = 0;
    
    public PhysicsThread()
    {
        
    }
    @Override
    public void run()
    {
        System.out.println("Starting Physics Thread");
        
        
        while (true)
        {
            ++updates;
            if (System.currentTimeMillis() - lastChange >= 1000)
            {
                ups = updates;
                updates = 0;
                lastChange = System.currentTimeMillis();
            }
            Main.world.applyGravitation();
            Main.world.updateBodies();
        }
        
    }
    
}
