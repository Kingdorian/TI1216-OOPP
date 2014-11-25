/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContainerPackage;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author faris
 */
public class PlayerPositionTest {
    
    public PlayerPositionTest() {
    }

    /**
     * Test of getyPos method, of class Player.
     */
    @Test
    public void testGetyPos() {
        PlayerPosition p1 = new PlayerPosition(1024, 0);        
        assertTrue(p1.getyPos() == 0);
    }
    
        /**
     * Test of getyPos method, of class Player.
     */
    @Test
    public void testGetyPosRounding() {
        PlayerPosition p1 = new PlayerPosition(1024, 767);
        assertTrue(p1.getyPos() == 765);
    }

    /**
     * Test of getxPos method, of class Player.
     */
    @Test
    public void testGetxPos() {
        PlayerPosition p1 = new PlayerPosition(0, 768); 
        assertTrue(p1.getxPos() == 0);
    }
    
    /**
     * Test of getxPos method, of class Player.
     */
    @Test
    public void testGetxPosRounding() {
        PlayerPosition p1 = new PlayerPosition(1023, 768);
        assertTrue(p1.getxPos() == 1020);
    }

    /**
     * Test of setyPos method, of class Player.
     */
    @Test
    public void testSetyPos() {
        PlayerPosition p1 = new PlayerPosition();
        p1.setyPos(767);
        assertTrue(p1.getyPos() == 765);
    }

    /**
     * Test of setxPos method, of class Player.
     */
    @Test
    public void testSetxPos() {
        PlayerPosition p1 = new PlayerPosition();
        p1.setxPos(1023);
        assertTrue(p1.getxPos() == 1020);
    }
    
}
