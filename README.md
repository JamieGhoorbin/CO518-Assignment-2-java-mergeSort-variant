# Merge Sort Variant

> ### Second year university project for Algorithms.
> ### A variant of the merge sort algorithm.   

The standard merge sort operates by dividing an array into two parts, merge sorts them recursively, and merges them back together.  This implementation varies by avoiding a recursive call of the merge sort when a segment of the array is sorted, thus avoiding breaking the array into singletons. The algorithm continues by taking two lists from the queue, merges them together and places it back in the queue.

To test the algorithm, run the test method providing a size for the array. A helper function will initialise the array with random numbers and return the results in the console. 
