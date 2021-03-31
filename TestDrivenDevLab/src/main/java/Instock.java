import jdk.jshell.spi.ExecutionControl;

import java.util.Iterator;

public
class Instock implements ProductStock {


    @Override
    public
    Integer getCount () {
        return null;
    }

    @Override
    public
    Boolean contains (Product product) {
        return null;
    }

    @Override
    public
    void add (Product product) {

    }

    @Override
    public
    void changeQuantity (String product, int quantity) {

    }

    @Override
    public
    Product find (int index) {
        return null;
    }

    @Override
    public
    Product findByLabel (String label) {
        return null;
    }

    @Override
    public
    Iterable<Product> findFirstByAlphabeticalOrder (int count) {
        return null;
    }

    @Override
    public
    Iterable<Product> findAllInRange (double lo, double hi) {
        return null;
    }

    @Override
    public
    Iterable<Product> findAllByPrice (double price) {
        return null;
    }

    @Override
    public
    Iterable<Product> findFirstMostExpensiveProducts (int count) {
        return null;
    }

    @Override
    public
    Iterable<Product> findAllByQuantity (int quantity) {
        return null;
    }

    @Override
    public
    Iterator<Product> iterator () {
        return null;
    }
}
