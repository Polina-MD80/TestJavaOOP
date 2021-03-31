import java.util.*;
import java.util.stream.Collectors;

public
class ChainblockImpl implements Chainblock {
    Map<Integer, Transaction> transactionMap;

    public
    ChainblockImpl () {
        transactionMap = new LinkedHashMap<> ();
    }

    public
    Map<Integer, Transaction> getTransactionMap () {
        return transactionMap;
    }

    public
    int getCount () {
        return transactionMap.size ();
    }

    public
    void add (Transaction transaction) {
        if (!contains (transaction)){
            transactionMap.put (transaction.getId (), transaction);
        }
    }

    public
    boolean contains (Transaction transaction) {
        int id = transaction.getId ();
        return transactionMap.containsKey (id);
    }

    public
    boolean contains (int id) {
        return transactionMap.containsKey (id);
    }

    public
    void changeTransactionStatus (int id, TransactionStatus newStatus) {
        if (!transactionMap.containsKey (id)){
            throw new IllegalArgumentException ();
        }
        transactionMap.get (id).setStatus (newStatus);
    }

    public
    void removeTransactionById(int id) {
        if (!transactionMap.containsKey (id)){
            throw new IllegalArgumentException ();
        }
        transactionMap.remove (id);
    }

    public
    Transaction getById (int id) {
        if (!transactionMap.containsKey (id)){
            throw new IllegalArgumentException ();
        }
        return transactionMap.get (id);
    }

    public
    Iterable<Transaction> getByTransactionStatus (TransactionStatus status) {
       List<Transaction> transactions =  transactionMap.values ().stream ().filter (transaction -> transaction.getStatus ().equals (status))
                .sorted ((f,s)-> Double.compare (s.getAmount (), f.getAmount ()))
                .collect(Collectors.toList());
        checkLisEmptyThenThrowEx (transactions);
        return transactions;
    }

    private
    void checkLisEmptyThenThrowEx (List<Transaction> transactions) {
        if (transactions.isEmpty ()){
            throw new IllegalArgumentException ();
        }
    }

    public
    Iterable<String> getAllSendersWithTransactionStatus (TransactionStatus status) {
        List<String> fromList = transactionMap.values ().stream ().filter (t -> t.getStatus ().equals (status))
                .map (Transaction::getFrom).collect (Collectors.toList ());
        if (fromList.isEmpty ()){
            throw new IllegalArgumentException ();
        }
        return fromList;
    }

    public
    Iterable<String> getAllReceiversWithTransactionStatus (TransactionStatus status) {
        List<String> toList = transactionMap.values ().stream ().filter (t -> t.getStatus ().equals (status))
                .map (Transaction::getTo).collect (Collectors.toList ());
        if (toList.isEmpty ()){
            throw new IllegalArgumentException ();
        }
        return toList;
    }

    public
    Iterable<Transaction> getAllOrderedByAmountDescendingThenById () {
        return transactionMap.values ().stream ().sorted (Comparator.comparingInt (Transaction::getId))
                .sorted (Comparator.comparingDouble (Transaction::getAmount).reversed ())
                .collect(Collectors.toList());
    }

    public
    Iterable<Transaction> getBySenderOrderedByAmountDescending (String sender) {
        List<Transaction> transactionList = transactionMap.values ().stream ()
                          .filter (t-> t.getFrom ().equals (sender))
                .sorted (Comparator.comparingDouble (Transaction::getAmount).reversed ())
                .collect(Collectors.toList());
        checkLisEmptyThenThrowEx (transactionList);

        return transactionList;
    }

    public
    Iterable<Transaction> getByReceiverOrderedByAmountThenById (String receiver) {
        List<Transaction> transactionList = transactionMap.values ().stream ()
                .filter (t-> t.getTo ().equals (receiver))
                .sorted (Comparator.comparingInt (Transaction::getId))
                .sorted (Comparator.comparingDouble (Transaction::getAmount).reversed ())
                .collect(Collectors.toList());
        checkLisEmptyThenThrowEx (transactionList);
        return transactionList;
    }

    public
    Iterable<Transaction> getByTransactionStatusAndMaximumAmount (TransactionStatus status, double amount) {
        return transactionMap.values ().stream ()
                .filter (t-> t.getStatus ().equals (status)&& t.getAmount ()<=amount)
                .sorted (Comparator.comparingDouble (Transaction::getAmount).reversed ())
                .collect(Collectors.toList());
    }

    public
    Iterable<Transaction> getBySenderAndMinimumAmountDescending (String sender, double amount) {
        List<Transaction> transactionList = transactionMap.values ().stream ()
                          .filter (t->t.getFrom ().equals (sender)&&t.getAmount ()>amount)
                .sorted (Comparator.comparingDouble (Transaction::getAmount).reversed ())
                .collect(Collectors.toList());
        checkLisEmptyThenThrowEx (transactionList);
        return transactionList;
    }

    public
    Iterable<Transaction> getByReceiverAndAmountRange (String receiver, double lo, double hi) {
        List<Transaction> transactionList = transactionMap.values ().stream ()
                .filter (t->t.getTo ().equals (receiver)&&t.getAmount ()>=lo&&t.getAmount ()<hi)
                .sorted (Comparator.comparingInt (Transaction::getId))
                .sorted (Comparator.comparingDouble (Transaction::getAmount).reversed ())
                .collect(Collectors.toList());
       checkLisEmptyThenThrowEx (transactionList);
        return transactionList;
    }

//•	getAllInAmountRange(lo, hi) – returns all transactions within a range by insertion order (the range is inclusive)
// . Returns an empty collection if no such transactions were found.
    public
    Iterable<Transaction> getAllInAmountRange (double lo, double hi) {
        return transactionMap.values ().stream ()
                .filter (t-> t.getAmount ()>=lo && t.getAmount ()<=hi)
                .collect(Collectors.toList());
    }

    public
    Iterator<Transaction> iterator () {
        return transactionMap.values ().iterator ();
    }
}
