package org.example.tests;

import org.example.model.Frame;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FrameTest {


    @Test
    void testAjouterLancerValide(){
        Frame frame = new Frame();
        frame.addShot(10,false);
        assertEquals(10, frame.getShots().get(0));
    }

}
