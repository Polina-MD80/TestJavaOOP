package computers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public
class ComputerManagerTests {
    private ComputerManager computerManager;
    private Computer computer1;
    private Computer computer2;

    @Before
    public
    void setUp () {
        computerManager = new ComputerManager ();
        computer1 = new Computer ("A", "a", 350.50);
        computer2 = new Computer ("B", "b", 600.40);
    }

    @Test(expected = UnsupportedOperationException.class)
    public
    void testUnmodifiableList () {
     computerManager.addComputer (computer1);
     computerManager.addComputer (computer2);
     computerManager.getComputers ().remove (computer1);
    }
    @Test
    public void getCount(){
        Assert.assertEquals (0, computerManager.getCount ());
    }
    @Test(expected = IllegalArgumentException.class)
    public void tryToAddExistingComputer(){
        computerManager.addComputer (computer1);
        computerManager.addComputer (computer1);
    }
    // public Computer getComputer(String manufacturer, String model) {
    //        this.validateNullValue(manufacturer, CAN_NOT_BE_NULL_MESSAGE);
    //        this.validateNullValue(model, CAN_NOT_BE_NULL_MESSAGE);

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputerWithNullManufacturer(){
        computerManager.getComputer (null,"A");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetComputerWithNullModel(){
        computerManager.getComputer ("A",null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputerWithNullReturn(){
        computerManager.getComputer ("A","B");
    }
    @Test
    public void testGetComputerReturnsCorrect(){
        computerManager.addComputer (computer1);
        computerManager.addComputer (computer2);
        Computer computer = computerManager.getComputer (computer1.getManufacturer (), computer1.getModel ());
        Assert.assertEquals (computer1.getManufacturer (), computer.getManufacturer ());
        Assert.assertEquals (computer1.getModel (),computer.getModel ());
    }
    @Test
    public void testRemoveComputerReturnsCorrect(){
        computerManager.addComputer (computer1);
        computerManager.addComputer (computer2);
        Computer computer = computerManager.removeComputer (computer1.getManufacturer (), computer1.getModel ());
        Assert.assertEquals (computer1.getManufacturer (), computer.getManufacturer ());
        Assert.assertEquals (computer1.getModel (),computer.getModel ());
        Assert.assertEquals (1, computerManager.getCount ());
    }
    @Test
    public void testGetComputersByManufacturer(){
        computerManager.addComputer (computer1);
        computerManager.addComputer (computer2);
        List<Computer> compByMan = computerManager.getComputersByManufacturer (computer1.getManufacturer ());
        Assert.assertEquals (computer1.getManufacturer (), compByMan.get (0).getManufacturer ());
        Assert.assertEquals (computer1.getModel (), compByMan.get (0).getModel ());
        Assert.assertEquals (computer1.getPrice (), compByMan.get (0).getPrice (),0.00);
    }





}