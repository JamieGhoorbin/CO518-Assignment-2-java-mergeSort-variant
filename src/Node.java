
/**
 * Standard Cons-cells
 *
 * @author Stefan Kahrs
 * @version 1
 * <p>
 * <p>
 * type parameter T
 * Below is an explanation for the fancy stuff
 * attached to that type parameter.
 * For the assessment you do not need to know this,
 * but if you are curious: read on!
 * <p>
 * Because we want to be able to compare the elements
 * of the list with one another, we require that
 * class T implements the Comparable interface.
 * That interface has itself a type parameter, which
 * gives you what these values can be compared to.
 * The reason this is (in the most general case) not just T
 * itself is the following scenario:
 * class X implements the interface,
 * so we can compare Xs with Xs, then we define a subclass Y of X,
 * so it inherits the compareTo method from X,
 * but Ys are now compared with Xs.
 */

/**
 * type parameter T
 * Below is an explanation for the fancy stuff
 * attached to that type parameter.
 * For the assessment you do not need to know this,
 * but if you are curious: read on!
 *
 * Because we want to be able to compare the elements
 * of the list with one another, we require that
 * class T implements the Comparable interface.
 * That interface has itself a type parameter, which
 * gives you what these values can be compared to.
 * The reason this is (in the most general case) not just T
 * itself is the following scenario:
 * class X implements the interface,
 * so we can compare Xs with Xs, then we define a subclass Y of X,
 * so it inherits the compareTo method from X,
 * but Ys are now compared with Xs.
 */

import java.util.Queue;
import java.util.Random;
import java.util.LinkedList;

public class Node<T extends Comparable<? super T>> {
    protected T head;
    protected Node<T> tail;

    public Node(T h, Node<T> t) {
        head = h;
        tail = t;
    }

    public String toString() {
        if (tail == null) return "[" + head + "]";
        return "[" + head + tail.tailString();
    }

    private String tailString() {
        String initialPart = "," + head;
        if (tail == null) return initialPart + "]";
        return initialPart + tail.tailString();
    }

    public int length() {
        int result = 1;
        for (Node<T> n = tail; n != null; n = n.tail) {
            result++;
        }
        return result;
    }

    public Queue<Node<T>> queueSortedSegments() {
        Queue<Node<T>> subList = new LinkedList<>();
        Node currentNode = this;
        Node nodeStart = this;
        while (currentNode.tail != null) {
            if (currentNode.head.compareTo(currentNode.tail.head) <= 0) {
                currentNode = currentNode.tail;
            } else {
                subList.add(nodeStart);
                nodeStart = currentNode.tail;
                currentNode.tail = null;
                currentNode = nodeStart;
            }
        }
        subList.add(nodeStart);
        return subList;
    }

    public boolean isSorted() {
        Node currentNode = this;
        if (currentNode == null || currentNode.tail == null) return true;
        while (currentNode.tail != null) {
            if (currentNode.head.compareTo(currentNode.tail.head) > 0) return false;
            currentNode = currentNode.tail;
        }
        return true;
    }

    public Node<T> merge(Node<T> another) {
        assert isSorted();
        assert another == null || another.isSorted();
        Node<T> thisList = this; //this list
        Node<T> anotherList = another; //another list
        Node<T> finalList = null; //the final list to return once sorted
        Node<T> tempList = null; //iterative node
        //if one list is empty, return the other.
        if (thisList == null) return anotherList;
        if (anotherList == null) return thisList;
        //adds first lowest element to queue
        if(thisList.head.compareTo(anotherList.head) <= 0) 
        {
            finalList = thisList;
            thisList = thisList.tail;
        }
        else
        {
            finalList = anotherList;
            anotherList = anotherList.tail;
        }
        tempList = finalList; //assigns to tempList for iterating
        while(thisList != null && anotherList !=null) 
        {
            if(thisList.head.compareTo(anotherList.head) <= 0)
            {
                tempList.tail = thisList;
                thisList = thisList.tail;
            }
            else 
            {
                tempList.tail = anotherList;
                anotherList = anotherList.tail;
            }
            tempList = tempList.tail;
        }
        // if one list is null, append the other 
        if(thisList == null)
            tempList.tail = anotherList;
        else
            tempList.tail = thisList;
        return finalList;
    }

    public Node<T> mergeSort() {
        Queue<Node<T>> theQueue = this.queueSortedSegments();
        while (theQueue.size() > 1) {
            theQueue.add(theQueue.remove().merge(theQueue.remove()));
        }
        return theQueue.remove(); //keep compiler happy
    }

    static public Node<Integer> randomList(int n) {
        Random r = new Random();
        Node<Integer> result = null;
        int k = n;
        while (k > 0) {
            result = new Node<Integer>(r.nextInt(n), result);
            k--;
        }
        return result;
    }

    static public void test(int n) {
        Node<Integer> rList = randomList(n);
        System.out.println(rList.isSorted() ? rList + " <-This is the original list. \nisSorted() returns: true\n": rList + " <-This is the original list. \nisSorted() returns: false \n");
        rList = rList.mergeSort();
        System.out.println(rList.isSorted() ? rList + " <-This is the list after mergeSort is called. \nisSorted() returns: true": rList + " <-This is the sorted list. \nisSorted() returns: false");
    }
}