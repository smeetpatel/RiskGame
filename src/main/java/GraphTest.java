package main.java;

import java.util.Iterator;

import org.jgrapht.*;
import org.jgrapht.graph.*;
//import org.jgrapht.io.*;
import org.jgrapht.traverse.*;
import org.jgrapht.alg.connectivity.*;

public class GraphTest {
	
	public static void main(String[] args) {
		Graph<String, DefaultEdge> mapGraph;
		mapGraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
		mapGraph.addVertex("17");
		mapGraph.addVertex("18");
		mapGraph.addVertex("19");
		mapGraph.addVertex("20");
		mapGraph.addVertex("21");
		mapGraph.addVertex("22");
		mapGraph.addEdge("17", "20");
		mapGraph.addEdge("17", "19");
		mapGraph.addEdge("17", "18");
		mapGraph.addEdge("18", "19");
		mapGraph.addEdge("18", "21");
		mapGraph.addEdge("18", "22");
		mapGraph.addEdge("19", "18");
		mapGraph.addEdge("19", "20");
		mapGraph.addEdge("19", "22");
		mapGraph.addEdge("20", "21");
		mapGraph.addEdge("20", "19");
		mapGraph.addEdge("20", "17");
		mapGraph.addEdge("21", "18");
		mapGraph.addEdge("21", "20");
		mapGraph.addEdge("22", "18");
		mapGraph.addEdge("22", "19");
		
		
		ConnectivityInspector<String, DefaultEdge> ci = new ConnectivityInspector<>(mapGraph);
		if(ci.isConnected())
			System.out.println("Connected");
		else
			System.out.println("Not connected");
		Iterator<String> iterator = new DepthFirstIterator<>(mapGraph, "21");
        while (iterator.hasNext()) {
            String uri = iterator.next();
            System.out.println(uri);
	}
}
}