package rpg_lab;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public
class DummyTest {

    @Test
    public void attackedTargetLoosesHealth() {
        // Arrange
        Dummy dummy = new Dummy(10, 10);
        // Act
        dummy.takeAttack(5);
        // Assert
        assertEquals (5, dummy.getHealth ());
    }


}