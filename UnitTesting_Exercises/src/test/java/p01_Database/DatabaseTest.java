package p01_Database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

public
class DatabaseTest {
    private Integer[] elements;
    private Database database;

    @Before
    public
    void setUp () throws Exception {
        elements = new Integer[]{12, 42, 78, 69};
        database = new Database (elements);

    }

    @Test
    public
    void constructorCreatesCorrectlyDataBase () {

        Assert.assertEquals (elements.length, database.getElements ().length);
        Assert.assertArrayEquals (elements, database.getElements ());
    }

    @Test(expected = OperationNotSupportedException.class)
    public
    void whenElementsAreMoreThan16 () throws OperationNotSupportedException {
        new Database (new Integer[17]);
    }

    @Test(expected = OperationNotSupportedException.class)
    public
    void whenElementsAreZero () throws OperationNotSupportedException {
        new Database (new Integer[0]);
    }

    @Test
    public
    void addMethodAddsTheNewElementAtTheEndOfTheArray () throws OperationNotSupportedException {
        Integer newElement = 13;
        database.add (newElement);
        Assert.assertEquals (elements.length + 1, database.getElements ().length);
        Assert.assertEquals (newElement, database.getElements ()[database.getElements ().length-1]);

    }
    @Test(expected = OperationNotSupportedException.class)
    public void whenElementToAddIsNullExpectException() throws OperationNotSupportedException {
        Integer element = null;

        database.add (element);
    }

    @Test
    public
    void removeCorrectlyTheLastElement () throws OperationNotSupportedException {
        database.remove ();
        Assert.assertEquals (elements.length-1, database.getElements ().length);
        Assert.assertEquals (elements[elements.length - 2], database.getElements ()[database.getElements ().length -1]);
    }
    @Test(expected = OperationNotSupportedException.class)
    public void removeElementFromEmptyArray() throws OperationNotSupportedException {
        database = new Database (new Integer[1]);
        database.remove ();
        database.remove ();

    }


    @Test
    public
    void getElements () {
        Assert.assertArrayEquals (elements,database.getElements ());
    }
}