************************************************************************
CS 6301.002.  Implementation of Advanced Data Structures and Algorithms
Spring 2016
Short Project 2
Submission by: Darshan Narayana Reddy and Mahesh Venkateswaran

************************************************************************

There are 8 source(.java) files and >10 test files which are in .txt format:

1. Graph.java - Provided by the Professor. Modified to include a method to print the graph
2. Vertex.java - Provided by the Professor. Modified to include fields required for our algorithms
3. Edge.java - Provided by the Professor.

4. TopologicalOrdering.java - contains the solution to problem a. 
	Contains two methods implementing different algorithms for topological ordering.
	Prints the vertices of a directed graph in topological order.

5. TreeDiameter.java - contains solution to problem b.
	Provides a method which takes a tree as input and returns the length of the longest diameter of the tree.
	Assumption - The graph provided is connected.

6. StronglyConnectedComponents.java - contains solution to problem c.
	Provides a method to assign component numbers to each vertex that belongs to a strongly connected component of a directed graph.

7. OddLengthCycle.java - contains a partial solution to problem d.
	Provides a method which takes an undirected graph as input and returns tests if the graph contains a cycle of odd length.
	If it does not contain such a cycle, it concludes that the graph is bipartite.

8. Eulerian.java - contains the solutions to problems e.
	Provides a method that tests a graph if it contains a Euler circuit or an Euler path.
	Possible outputs:
	Graph is Eulerian.
	Graph has an Eulerian Path between vertices ?? and ??.
	Graph is not connected.
	Graph is not Eulerian.  It has ?? vertices of odd degree.


For every problem, the graph is input using the readGraph() method. Any input may be given under the standard format and assumptions