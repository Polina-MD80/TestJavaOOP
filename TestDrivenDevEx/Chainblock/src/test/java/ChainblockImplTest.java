import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public
class ChainblockImplTest {
    private Chainblock chainBlockTest;
    private Transaction transactionDefault;

    @Before
    public
    void setUp () {
        chainBlockTest = new ChainblockImpl ();
        transactionDefault = new TransactionImpl (39, TransactionStatus.SUCCESSFUL, "From", "To", 56.5);
    }

    @Test
    public
    void getCount () {
        addTransactions ();
        Assert.assertEquals (8, chainBlockTest.getCount ());
    }

    @Test
    public
    void addTransactionWithUniqueIdAndDoesNotAddWithTheSameId () {
        chainBlockTest.add (transactionDefault);
        int expected = 1;
        int actual   = chainBlockTest.getCount ();
        Assert.assertEquals (expected, actual);
        chainBlockTest.add (transactionDefault);
        actual = chainBlockTest.getCount ();
        Assert.assertEquals (expected, actual);
        int id = transactionDefault.getId ();
        Assert.assertEquals (id, chainBlockTest.getTransactionMap ().keySet ().stream ().findFirst ().get ().intValue ());

    }

    @Test
    public
    void containsShouldReturnFalse () {
        Transaction transaction2        = new TransactionImpl (0, TransactionStatus.FAILED, "From", "To", 33);
        boolean     containsTransaction = chainBlockTest.contains (transaction2);
        Assert.assertFalse (containsTransaction);
    }

    @Test
    public
    void testContains () {
        Assert.assertFalse (chainBlockTest.getTransactionMap ().containsKey (2));
        chainBlockTest.add (transactionDefault);
        Assert.assertTrue (chainBlockTest.contains (transactionDefault.getId ()));
    }

    @Test(expected = IllegalArgumentException.class)
    public
    void changeTransactionStatusFailsWithInvalidTransaction () {
        chainBlockTest.add (transactionDefault);
        chainBlockTest.changeTransactionStatus (-1, TransactionStatus.ABORTED);

    }

    @Test
    public
    void changeTransactionStatusWithValidOne () {
        addTransactions ();
        int               id        = 0;
        TransactionStatus newStatus = TransactionStatus.UNAUTHORIZED;
        chainBlockTest.changeTransactionStatus (id, newStatus);
        Assert.assertEquals (newStatus, chainBlockTest.getTransactionMap ().get (id).getStatus ());
    }


    @Test(expected = IllegalArgumentException.class)
    public
    void removeTransactionByIdThrowsExceptionForInvalidId () {
        addTransactions ();
        chainBlockTest.removeTransactionById (-1);
    }

    @Test
    public
    void removeTransactionByIdRemovesTheExpectedTransaction () {
        addTransactions ();
        int transactionsCount = chainBlockTest.getCount ();
        chainBlockTest.removeTransactionById (0);
        Assert.assertEquals (transactionsCount - 1, chainBlockTest.getCount ());
        Assert.assertFalse (chainBlockTest.contains (0));
    }

    @Test(expected = IllegalArgumentException.class)
    public
    void getByIdThrowsExceptionForInvalidId () {
        addTransactions ();
        chainBlockTest.getById (-1);
    }

    @Test
    public
    void getByIdReturnsTheCorrectTransaction () {
        addTransactions ();
        chainBlockTest.add (transactionDefault);
        int id = transactionDefault.getId ();

        Assert.assertEquals (transactionDefault, chainBlockTest.getById (id));

    }

    @Test
    public
    void getByTransactionStatus () {
        TransactionStatus status = transactionDefault.getStatus ();
        addTransactions ();
        List<Transaction> expected = listOfTestTransactions ().stream ()
                .filter (transaction -> transaction.getStatus ().equals (status))
                .sorted ((t1, t2) -> Double.compare (t2.getAmount (), t1.getAmount ()))
                .collect (Collectors.toList ());
        List<Transaction> actual = (List<Transaction>) chainBlockTest.getByTransactionStatus (status);
        Assert.assertEquals (expected.size (), actual.size ());
        assertIdOfTheReturnedListsAreEqual (expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public
    void getByTransactionReturnsExceptionForNull () {
        TransactionStatus status = transactionDefault.getStatus ();
        chainBlockTest.getByTransactionStatus (status);
    }

    @Test(expected = IllegalArgumentException.class)
    public
    void getAllSendersWithTransactionStatusThrowExceptionForEmptyCollection () {
        TransactionStatus status = transactionDefault.getStatus ();
        chainBlockTest.getAllSendersWithTransactionStatus (status);
    }

    @Test
    public
    void getAllSendersWithTransactionStatus () {
        TransactionStatus status = transactionDefault.getStatus ();
        addTransactions ();
        List<String> expected = listOfTestTransactions ()
                .stream ().filter (t -> t.getStatus ().equals (status))
                .map (Transaction::getFrom).collect (Collectors.toList ());
        List<String> actual = (List<String>) chainBlockTest.getAllSendersWithTransactionStatus (status);
        for (int i = 0; i < expected.size (); i++) {
            Assert.assertEquals (expected.get (i), actual.get (i));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public
    void getAllReceiversWithTransactionStatusThrowExceptionForEmptyCollection () {
        TransactionStatus status = transactionDefault.getStatus ();
        chainBlockTest.getAllReceiversWithTransactionStatus (status);
    }

    @Test
    public
    void getAllReceiversWithTransactionStatus () {
        TransactionStatus status = transactionDefault.getStatus ();
        addTransactions ();
        List<String> expected = listOfTestTransactions ()
                .stream ().filter (t -> t.getStatus ().equals (status))
                .map (Transaction::getTo).collect (Collectors.toList ());
        List<String> actual = (List<String>) chainBlockTest.getAllReceiversWithTransactionStatus (status);
        for (int i = 0; i < expected.size (); i++) {
            Assert.assertEquals (expected.get (i), actual.get (i));
        }

    }

    @Test
    public
    void getAllOrderedByAmountDescendingThenByI () {
        List<Transaction> expected = listOfTestTransactions ().stream ()
                .sorted ((f, s) -> {
                    int result = Double.compare (s.getAmount (), f.getAmount ());
                    if (result == 0) {
                        result = Integer.compare (f.getId (), s.getId ());
                    }
                    return result;
                }).collect (Collectors.toList ());
        addTransactions ();
        List<Transaction> actual = (List<Transaction>) chainBlockTest.getAllOrderedByAmountDescendingThenById ();
        assertIdOfTheReturnedListsAreEqual (expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public
    void getBySenderOrderedByAmountDescendingThrowExceptionForInvalidSender () {
        String sender = transactionDefault.getFrom ();
        addTransactions ();
        chainBlockTest.getBySenderOrderedByAmountDescending (sender);
    }

    @Test
    public
    void getBySenderOrderedByAmountDescending () {
        addTransactions ();
        String sender = "Sender_1";
        List<Transaction> expected = listOfTestTransactions ()
                .stream ().filter (t -> t.getFrom ().equals (sender))
                .sorted (Comparator.comparingDouble (Transaction::getAmount).reversed ())
                .collect (Collectors.toList ());

        List<Transaction> actual = (List<Transaction>) chainBlockTest.getBySenderOrderedByAmountDescending (sender);
        assertIdOfTheReturnedListsAreEqual (expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public
    void getByReceiverOrderedByAmountThenByIdThrowsException () {
        addTransactions ();
        String receiver = transactionDefault.getTo ();
        chainBlockTest.getByReceiverOrderedByAmountThenById (receiver);
    }

    @Test
    public
    void getByReceiverOrderedByAmountThenById () {
        addTransactions ();
        String receiver = "Receiver_2";
        List<Transaction> expected = listOfTestTransactions ().stream ()
                .filter (s -> s.getTo ().equals (receiver))
                .sorted (Comparator.comparingInt (Transaction::getId))
                .sorted (Comparator.comparingDouble (Transaction::getAmount).reversed ())
                .collect (Collectors.toList ());
        List<Transaction> actual = (List<Transaction>) chainBlockTest.getByReceiverOrderedByAmountThenById (receiver);
        assertIdOfTheReturnedListsAreEqual (expected, actual);
    }

    @Test
    public
    void getByTransactionStatusAndMaximumAmount () {
        addTransactions ();
        chainBlockTest.add (transactionDefault);
        List<Transaction> testList = listOfTestTransactions ();
        testList.add (transactionDefault);
        TransactionStatus status    = transactionDefault.getStatus ();
        double            maxAmount = transactionDefault.getAmount ();
        List<Transaction> expected = testList.stream ().filter (transaction ->
                transaction.getStatus ().equals (status) && transaction.getAmount () <= maxAmount)
                .sorted (Comparator.comparingDouble (Transaction::getAmount).reversed ())
                .collect (Collectors.toList ());
        List<Transaction> actual = (List<Transaction>) chainBlockTest.getByTransactionStatusAndMaximumAmount (status, maxAmount);
        assertIdOfTheReturnedListsAreEqual (expected, actual);
    }

    @Test
    public
    void getByTransactionStatusAndMaximumAmountReturnsEmptyCollection () {
        TransactionStatus status    = transactionDefault.getStatus ();
        double            maxAmount = transactionDefault.getAmount ();
        List<Transaction> actual    = (List<Transaction>) chainBlockTest.getByTransactionStatusAndMaximumAmount (status, maxAmount);
        Assert.assertNotNull (actual);
        Assert.assertEquals (0, actual.size ());
    }


    @Test
    public
    void getBySenderAndMinimumAmountDescending () {
        String sender    = "Sender_1";
        double minAmount = 5.0;
        addTransactions ();
        List<Transaction> expected = listOfTestTransactions ().stream ()
                .filter (t -> t.getFrom ().equals (sender) && t.getAmount () > minAmount)
                .sorted (Comparator.comparingDouble (Transaction::getAmount).reversed ())
                .collect (Collectors.toList ());
        List<Transaction> actual = (List<Transaction>) chainBlockTest.getBySenderAndMinimumAmountDescending (sender, minAmount);
        assertIdOfTheReturnedListsAreEqual (expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public
    void getBySenderAndMinimumAmountDescendingThrowsExForEmptyCollection () {
        String sender    = "Sender_1";
        double minAmount = 5.0;
        chainBlockTest.add (transactionDefault);
        chainBlockTest.getBySenderAndMinimumAmountDescending (sender, minAmount);
    }

    //•	getByReceiverAndAmountRange(receiver, lo, hi) –
// returns all transactions with given receiver and amount between
// lo (inclusive) and hi (exclusive) ordered by amount descending then by id.
// If there are no such transactions throw IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public
    void getByReceiverAndAmountRangeThrowsExForEmptyCollection () {
        String receiver = transactionDefault.getTo ();
        double hi       = -1;
        double lo       = -200;
        addTransactions ();
        chainBlockTest.getByReceiverAndAmountRange (receiver, lo, hi);
    }

    @Test
    public
    void getByReceiverAndAmountRange () {
        String receiver = "Receiver_1";
        double hi       = 55;
        double lo       = 0;
        List<Transaction> expected = listOfTestTransactions ().stream ()
                .filter (t -> t.getTo ().equals (receiver) && t.getAmount () >= lo && t.getAmount () < hi)
                .sorted (Comparator.comparingInt (Transaction::getId))
                .sorted (Comparator.comparingDouble (Transaction::getAmount).reversed ())
                .collect (Collectors.toList ());

        addTransactions ();
        List<Transaction> actual = (List<Transaction>) chainBlockTest.getByReceiverAndAmountRange (receiver, lo, hi);

        assertIdOfTheReturnedListsAreEqual (expected, actual);
    }

    //•	getAllInAmountRange(lo, hi) – returns all transactions within a range by insertion order (the range is inclusive)
// . Returns an empty collection if no such transactions were found.
    @Test
    public
    void getAllInAmountRange () {
        addTransactions ();
        double lo = -200;
        double hi = -1;
        chainBlockTest.getAllInAmountRange (lo, hi);
        Assert.assertNotNull (chainBlockTest.getAllInAmountRange (lo, hi));
        lo = 5;
        hi = 55;
        double finalLo = lo;
        double finalHi = hi;
        List<Transaction> expected = listOfTestTransactions ().stream ()
                .filter (t -> t.getAmount () >= finalLo && t.getAmount () <= finalHi)
                .collect (Collectors.toList ());
        List<Transaction> actual = (List<Transaction>) chainBlockTest.getAllInAmountRange (lo,hi);
        assertIdOfTheReturnedListsAreEqual (expected,actual);
    }

    @Test
    public
    void iterator () {
        addTransactions ();
        Iterator<Transaction> iterator = chainBlockTest.iterator ();
        for (int i = 0; i < 8; i++) {
            Assert.assertTrue (iterator.hasNext ());
        }
    }

    private
    void assertIdOfTheReturnedListsAreEqual (List<Transaction> expected, List<Transaction> actual) {
        for (int i = 0; i < expected.size (); i++) {
            Assert.assertEquals (expected.get (i).getId (), actual.get (i).getId ());
        }
    }

    private
    List<Transaction> listOfTestTransactions () {
        List<Transaction> transactionList = new ArrayList<> ();
        transactionList.add (new TransactionImpl (0, TransactionStatus.FAILED, "Sender_1", "Receiver_1", 3.3));
        transactionList.add (new TransactionImpl (1, TransactionStatus.SUCCESSFUL, "Sender_2", "Receiver_1", 133));
        transactionList.add (new TransactionImpl (2, TransactionStatus.ABORTED, "Sender_1", "Receiver_1", 35.5));
        transactionList.add (new TransactionImpl (3, TransactionStatus.UNAUTHORIZED, "Sender_3", "Receiver_3", 66.0));
        transactionList.add (new TransactionImpl (4, TransactionStatus.ABORTED, "Sender_1", "Receiver_1", 35.5));
        transactionList.add (new TransactionImpl (5, TransactionStatus.SUCCESSFUL, "Sender_2", "Receiver_2", 55));
        transactionList.add (new TransactionImpl (6, TransactionStatus.SUCCESSFUL, "Sender_1", "Receiver_2", 55));
        transactionList.add (new TransactionImpl (7, TransactionStatus.UNAUTHORIZED, "Sender_3", "Receiver_1", 1.0));
        return transactionList;
    }

    private
    void addTransactions () {
        chainBlockTest.add (new TransactionImpl (0, TransactionStatus.FAILED, "Sender_1", "Receiver_1", 3.3));
        chainBlockTest.add (new TransactionImpl (1, TransactionStatus.SUCCESSFUL, "Sender_2", "Receiver_1", 133));
        chainBlockTest.add (new TransactionImpl (2, TransactionStatus.ABORTED, "Sender_1", "Receiver_1", 35.5));
        chainBlockTest.add (new TransactionImpl (3, TransactionStatus.UNAUTHORIZED, "Sender_3", "Receiver_3", 66.0));
        chainBlockTest.add (new TransactionImpl (4, TransactionStatus.ABORTED, "Sender_1", "Receiver_1", 35.5));
        chainBlockTest.add (new TransactionImpl (5, TransactionStatus.SUCCESSFUL, "Sender_2", "Receiver_2", 55));
        chainBlockTest.add (new TransactionImpl (6, TransactionStatus.SUCCESSFUL, "Sender_1", "Receiver_2", 55));
        chainBlockTest.add (new TransactionImpl (7, TransactionStatus.UNAUTHORIZED, "Sender_3", "Receiver_1", 1.0));
    }
}