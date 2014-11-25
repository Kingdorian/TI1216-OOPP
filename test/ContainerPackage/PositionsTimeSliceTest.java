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
public class PositionsTimeSliceTest {
    
    public PositionsTimeSliceTest() {
    }

    /**
     * Test of setPlayersAlly method, of class PositionsTimeSlice.
     */
    @Test
    public void testSetGetPlayersAlly() {
        PlayerPosition p1 = new PlayerPosition(768, 1024);
        PositionsTimeSlice pTS = new PositionsTimeSlice();
        pTS.setPlayersAlly(p1, 0);
        assertTrue(pTS.getPlayersAlly(0) == p1);
    }

    /**
     * Test of setPlayersAdversary method, of class PositionsTimeSlice.
     */
    @Test
    public void testSetGetPlayersAdversary() {
        PlayerPosition p1 = new PlayerPosition(768, 1024);
        PositionsTimeSlice pTS = new PositionsTimeSlice();
        pTS.setPlayersAdversary(p1, 0);
        assertTrue(pTS.getPlayersAdversary(0) == p1);
    }

    
}
