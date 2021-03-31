package p03_IteratorTest;

import org.junit.Assert;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

public
class ListIteratorTest {

    @Test(expected = OperationNotSupportedException.class)
    public
    void passToConstructorNullElements_then_expectException () throws OperationNotSupportedException {
        new ListIterator (null);
    }

    @Test
    public
    void createListIteratorWihCorrectElements () throws OperationNotSupportedException {
        ListIterator listIterator = new ListIterator ("a", "b");
        Assert.assertTrue (listIterator.move ());
        Assert.assertFalse (listIterator.move ());
    }


    @Test(expected = IllegalStateException.class)
    public
    void tryToPrintEmptyIterator_expectException () throws OperationNotSupportedException {
        new ListIterator ().print ();
    }

    @Test
    public
    void printListIterator () throws OperationNotSupportedException {
        String[]     elements     = new String[]{"a", "b"};
        ListIterator listIterator = new ListIterator (elements);
        for (int i = 0; listIterator.hasNext (); listIterator.move (), i++) {

            Assert.assertEquals (elements[i], listIterator.print ());
        }
    }
}