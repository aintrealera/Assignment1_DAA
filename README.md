# Assignment 1 — Divide-and-Conquer Algorithm Analysis

## 1. Project Overview

This project implements and analyzes four classic divide-and-conquer algorithms in Java:

1. MergeSort
2. QuickSort
3. Deterministic Select (Median-of-Medians)
4. Closest Pair of Points

The main goals of the assignment are to implement the algorithms, analyze their theoretical complexity, measure their practical performance, test their correctness, and compare the experimental results with the theoretical expectations.

The project also measures execution time, recursion depth, and an additional algorithm-specific operation metric. Experimental results are saved in CSV format.

---

## 2. Project Structure

```
Assignment1_DAA/
├── src/
│   ├── MergeSorter.java
│   ├── QuickSorter.java
│   ├── DeterministicSelector.java
│   ├── ClosestPairSolver.java
│   ├── Experiment.java
│   ├── Point.java
│   └── Main.java
│
├── tests/
│   ├── SortingTest.java
│   ├── DeterministicSelectTest.java
│   └── ClosestPairTest.java
│
├── docs/
│   ├── screenshots/
│   └── plots/
│
├── results/
│   └── results.csv
│
├── README.md
├── .gitignore
└── pom.xml
```

## 3. Algorithms

### 3.1 MergeSort

MergeSort is a divide-and-conquer sorting algorithm.

The algorithm divides the input array into two halves, recursively sorts both halves, and then merges the sorted halves using a linear merge operation.

The implementation uses a reusable auxiliary buffer and a small-input cutoff.

Complexity
Best case: Θ(n log n)
Average case: Θ(n log n)
Worst case: Θ(n log n)
Space complexity: O(n)
Recurrence

The recurrence is:

T(n) = 2T(n/2) + Θ(n)

Using the Master Theorem:

a = 2
b = 2
f(n) = Θ(n)

Therefore:

T(n) = Θ(n log n)

### 3.2 QuickSort

QuickSort uses a randomized pivot and performs in-place partitioning.

After partitioning, the implementation recursively processes the smaller partition and iteratively processes the larger partition. This helps control recursion depth.

Complexity
Typical/average running time: O(n log n)
Worst-case running time: O(n²)
Typical recursion depth: O(log n)
Space complexity: O(log n) for recursion in typical cases
Recurrence

For reasonably balanced partitions:

T(n) = 2T(n/2) + Θ(n)

which gives:

T(n) = Θ(n log n)

For highly unbalanced partitions:

T(n) = T(n-1) + Θ(n)

which gives:

T(n) = Θ(n²)

Randomized pivot selection reduces the likelihood of repeatedly obtaining highly unbalanced partitions.

### 3.3 Deterministic Select

Deterministic Select finds the k-th smallest element using the Median-of-Medians algorithm.

The input is divided into groups of five. The median of each group is found, and the median of those medians is used as the pivot.

Only the partition containing the required k-th element is processed recursively.

Complexity
Best case: O(n)
Average case: O(n)
Worst case: Θ(n)
Space complexity: depends on the in-place implementation and recursion
Recurrence

The Median-of-Medians method guarantees a sufficiently balanced partition.

Its recurrence can be represented conceptually as:

T(n) = T(n/5) + T(7n/10) + Θ(n)

The linear work is performed by grouping, finding medians, partitioning, and selecting the required side.

This recurrence gives:

T(n) = Θ(n)

Thus, unlike ordinary QuickSelect, Deterministic Select provides a worst-case linear-time guarantee.

### 3.4 Closest Pair of Points

The Closest Pair algorithm finds the two points with the smallest Euclidean distance.

The implementation first sorts the points by their x-coordinate. The points are then divided into two halves and processed recursively.

After finding the closest pair in each half, a strip around the dividing line is constructed. Points in the strip are checked in y-order to find a closer pair crossing the division.

Complexity
Time complexity: Θ(n log n)
Space complexity: O(n)
Recurrence

The main recursive work can be represented as:

T(n) = 2T(n/2) + Θ(n)

Using the Master Theorem:

T(n) = Θ(n log n)

The strip is processed efficiently because the points are maintained in y-order.

## 4. Experimental Setup

The program uses System.nanoTime() to measure execution time.

The experiments use multiple input sizes and four different input types:

Random
Sorted
Reverse-sorted
Duplicate-heavy

For each experiment, the following metrics are recorded:

Execution time
Maximum recursion depth
Additional algorithmic operations

The results are saved to:

results/results.csv

The experiment data contains measurements for all four implemented algorithms.

## 5. Experimental Results

The experimental results are stored in results/results.csv.

The project contains two main plots:

Time vs. n

The plot shows how execution time changes as the input size increases.

Recursion Depth vs. n

The plot shows how the measured recursion depth changes with increasing input size.

The complete experimental data for all algorithms and input types can be found in:

results/results.csv

## 6. Testing

The project includes separate correctness tests in the tests directory.

Sorting Tests

SortingTest.java compares MergeSort and QuickSort against Java's:

Arrays.sort()

The tests include:

Random arrays
Sorted arrays
Reverse-sorted arrays
Duplicate-heavy arrays
Empty arrays
Single-element arrays

The tests passed successfully.

Deterministic Select Tests

DeterministicSelectTest.java performs 100 random tests.

For each test, the result of Deterministic Select is compared with:

Arrays.sort(a)[k]

All 100 tests passed successfully.

Closest Pair Tests

ClosestPairTest.java verifies the Closest Pair implementation using several point configurations, including:

A simple point set
Two points
Duplicate points

The tests passed successfully.


## 7. Discussion

Do the results match the theoretical complexity?

The experimental results generally demonstrate the expected growth patterns of the algorithms. However, measured execution time does not exactly equal theoretical complexity because practical performance is affected by many additional factors.

These include JVM optimization, CPU performance, memory access, caching, garbage collection, and measurement overhead.

How does input structure affect performance?

Input structure can affect algorithms differently.

Sorted, reverse-sorted, random, and duplicate-heavy inputs can produce different partitioning behavior for QuickSort and different numbers of operations.

MergeSort is less sensitive to input ordering because its divide-and-merge structure remains similar for different input types.

Why does smaller-first recursion help QuickSort?

Recursing on the smaller partition limits the amount of stack space required by recursive calls.

The larger partition is processed iteratively, which prevents unnecessary growth of the recursion stack.

This helps keep the recursion depth controlled even when partitions are unbalanced.

Why does Median-of-Medians guarantee O(n)?

Median-of-Medians chooses a pivot using groups of five.

The pivot provides a guaranteed amount of progress during partitioning, preventing the algorithm from repeatedly making extremely unbalanced partitions.

As a result, the total amount of work across recursive levels remains linear:

Θ(n)
Why is divide-and-conquer Closest Pair faster than O(n²) for large inputs?

A brute-force solution compares every pair of points, requiring approximately:

O(n²)

comparisons.

The divide-and-conquer solution divides the points into smaller sets and only performs a limited number of comparisons in the strip around the dividing line.

This reduces the theoretical complexity to:

Θ(n log n)

which scales better for large datasets.

What practical factors affect performance?

Practical execution time can be affected by:

JVM JIT compilation
CPU performance
CPU cache behavior
Memory allocation
Garbage collection
Operating system processes
Java runtime overhead
Input generation
Measurement overhead from System.nanoTime()

Therefore, experimental measurements may differ from theoretical predictions.

## 8. Reflection

This assignment provided practical experience with implementing and analyzing divide-and-conquer algorithms. The main challenge was not only implementing the algorithms, but also handling edge cases and measuring their practical behavior. Testing different input types helped demonstrate that theoretical complexity and actual execution time are related but not identical.

Another important part of the assignment was implementing the Closest Pair algorithm and correctly managing the recursive division and strip processing. The testing process also helped verify the correctness of the implementations, especially for duplicate values, empty arrays, and different input structures.

## 9. Screenshots

The following screenshots document the implementation and experimental results:

Project structure
Main program output
Sorting tests
Deterministic Select tests
Closest Pair tests
Execution-time plot
Recursion-depth plot
Experimental results

Screenshots are stored in:
```
docs/screenshots/
```
## 10. Conclusion

The project implements four divide-and-conquer algorithms and evaluates both their correctness and practical performance.

The experimental results, correctness tests, plots, and CSV measurements provide a comparison between theoretical algorithmic complexity and observed execution behavior.

All required algorithms, tests, experimental measurements, results, plots, and documentation are included in the project repository.
