package org.example.tests;

import org.example.model.Frame;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrameTest {

    @Test
    void testAjouterLancerValide() {
        Frame frame = new Frame();
        int quilles = 15;
        frame.addShot(quilles, false);
        assertEquals(quilles, frame.getShots().get(0));
    }

}
