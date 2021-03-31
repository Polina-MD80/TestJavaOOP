package rpg_lab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public
class AxeTest {
    private static final int AXE_ATTACK = 10;
    private static final int AXE_DURABILITY = 1;
    private static final int DUMMY_HEALTH = 20;
    private static final int DUMMY_XP = 10;
    private static final int EXPECTED_DURABILITY = AXE_DURABILITY - 1;
    private Axe axe;
    private Dummy dummy;

    @Before
    public
    void initializeTestObjects () {
        this.axe = new Axe (AXE_ATTACK, AXE_DURABILITY);
        this.dummy = new Dummy (DUMMY_HEALTH, DUMMY_XP);
    }

    @Test
    public
    void weaponLosesDurabilityAfterAttack () {
        // Arrange

        // Act
        axe.attack (dummy);
        // Assert
        assertEquals (EXPECTED_DURABILITY, axe.getDurabilityPoints ());
    }

    @Test(expected = IllegalStateException.class) // Assert
    public
    void brokenWeaponCantAttack () {
        // Arrange

        // Act
        axe.attack (dummy);
        axe.attack (dummy);

    }

}