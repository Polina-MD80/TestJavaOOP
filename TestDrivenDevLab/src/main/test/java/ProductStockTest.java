import com.sun.jdi.IntegerValue;
import org.junit.Test;

import static org.junit.Assert.*;

public
class ProductStockTest {

    @Test
    public
    void getCountShouldReturn2when2ProductsAreAdded () {
        ProductStock inStock = new Instock ();
        inStock.add (new Product ("Label_1", 100, 1));
        inStock.add (new Product ("Label_2", 100, 1));
        assertEquals (Integer.valueOf (2), inStock.getCount ());
    }

    @Test
    public
    void getCountShouldReturnOWhenThereIsNoProducts () {
        ProductStock inStock = new Instock ();
        assertEquals (Integer.valueOf (0), inStock.getCount ());

    }


}
