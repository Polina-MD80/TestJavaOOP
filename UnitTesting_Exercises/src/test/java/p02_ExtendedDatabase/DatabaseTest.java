package p02_ExtendedDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

public
class DatabaseTest {
    private static final Person[] PEOPLE = new Person[]{
            new Person (1, "A"),
            new Person (2, "B"),
            new Person (3, "C")
    };
    private Database database;

    @Before
    public
    void setUp () throws Exception {
        database = new Database (PEOPLE);
    }
    @Test
    public void createDatabase(){
        Assert.assertEquals (PEOPLE.length, database.getElements ().length);
        Assert.assertArrayEquals (PEOPLE,database.getElements ());
    }
    @Test(expected = OperationNotSupportedException.class)
    public void ifPeopleSizeIsMoreThen16ExpectException() throws OperationNotSupportedException {
        new Database (new Person[17]);
    }
    @Test(expected = OperationNotSupportedException.class)
    public
    void whenElementsAreZero () throws OperationNotSupportedException {
        new Database ();
    }

    @Test
    public
    void add () throws OperationNotSupportedException {
        Person person = new Person (4, "D");
        database.add (person);
        Assert.assertEquals (PEOPLE.length + 1, database.getElements ().length);
        Assert.assertEquals (person, database.getElements ()[database.getElements ().length-1]);
        Assert.assertEquals (person.getId (), database.getElements ()[database.getElements ().length-1].getId ());
        Assert.assertEquals (person.getUsername (), database.getElements ()[database.getElements ().length-1].getUsername ());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void tryToAddNullPersonAndCatchException() throws OperationNotSupportedException {

        database.add (null);
    }

    @Test
    public
    void remove () throws OperationNotSupportedException {
        database.remove ();
        Assert.assertEquals (PEOPLE.length-1, database.getElements ().length);
        Assert.assertEquals (PEOPLE[PEOPLE.length-2], database.getElements ()[database.getElements ().length-1]);

    }
    @Test(expected = OperationNotSupportedException.class)
    public void tryToRemoveFromEmptyDatabase_ThenReturnException() throws OperationNotSupportedException {
        database.remove ();
        database.remove ();
        database.remove ();
        database.remove ();
    }

    @Test
    public
    void getElements () {
        Assert.assertArrayEquals (PEOPLE, database.getElements ());
    }
    @Test(expected = OperationNotSupportedException.class)
    public void tryToFindPersonWithNullUsername_ThenCatchException() throws OperationNotSupportedException {

        database.findByUsername (null);
    }

    @Test
    public
    void findByUsername () throws OperationNotSupportedException {
        String userName = "C";
        Person person = database.findByUsername (userName);
        Assert.assertEquals (PEOPLE[2], person);
    }
    @Test(expected = OperationNotSupportedException.class)
    public void findWithInvalidUserName_ExpectException() throws OperationNotSupportedException {
        database.findByUsername ("K");
    }

    @Test
    public
    void findById () throws OperationNotSupportedException {
        long userId = 1;
        Person person = database.findById (userId);
        Assert.assertEquals (PEOPLE[0], person);
    }
    @Test (expected = OperationNotSupportedException.class)
    public void tryToFindInvalidId_ThenReturnException() throws OperationNotSupportedException {
        database.findById (5);
    }
    @Test(expected = OperationNotSupportedException.class)
    public void find2PeopleWithTheSameName_ExpectException() throws OperationNotSupportedException {
        database = new Database (
              new   Person(1,"A"),
                new Person (2,"A")
        );
        database.findByUsername ("A");
    }
    @Test(expected = OperationNotSupportedException.class)
    public void find2PeopleWithTheSameId_ExpectException() throws OperationNotSupportedException {
        database = new Database (
                new   Person(1,"A"),
                new Person (1,"B")
        );
        database.findById (1);
    }

}