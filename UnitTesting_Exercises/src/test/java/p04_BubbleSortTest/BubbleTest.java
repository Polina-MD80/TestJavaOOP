package p04_BubbleSortTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public
class BubbleTest {


    @Test
    public
    void sortUnsortedArray_ReturnSortedArray () {
        int[] arr = new int[]{6, -1, 3, 12, 4};
        Bubble.sort (arr);
        int[] expected = new int[]{-1, 3, 4, 6, 12};

        Assert.assertArrayEquals (expected, arr);
    }

    @Test
    public void whenTheArrayIsSorted_thenReturnTheSameArray(){
        int[] arr = new int[]{-1, 3, 4, 6, 12};
        Bubble.sort (arr);
        int[] expectedArr = new int[]{-1, 3, 4, 6, 12};
        Assert.assertArrayEquals (expectedArr,arr);
    }
}