package com.emergencyescape.dijkstra;

import com.emergencyescape.dijkstra.Dijkstra;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.junit.Assert.*;


public class DijkstraTest {

    // Undirected
    private class UndirectedGraph {
        final List<Graph.Vertex<Integer>> verticies = new ArrayList<Graph.Vertex<Integer>>();
        final Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
        final Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
        final Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
        final Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
        final Graph.Vertex<Integer> v5 = new Graph.Vertex<Integer>(5);
        final Graph.Vertex<Integer> v6 = new Graph.Vertex<Integer>(6);
        final Graph.Vertex<Integer> v7 = new Graph.Vertex<Integer>(7);
        final Graph.Vertex<Integer> v8 = new Graph.Vertex<Integer>(8);
        {
            verticies.add(v1);
            verticies.add(v2);
            verticies.add(v3);
            verticies.add(v4);
            verticies.add(v5);
            verticies.add(v6);
            verticies.add(v7);
            verticies.add(v8);
        }

        final List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>();
        final Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(7, v1, v2);
        final Graph.Edge<Integer> e1_3 = new Graph.Edge<Integer>(9, v1, v3);
        final Graph.Edge<Integer> e1_6 = new Graph.Edge<Integer>(14, v1, v6);
        final Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(10, v2, v3);
        final Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(15, v2, v4);
        final Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(11, v3, v4);
        final Graph.Edge<Integer> e3_6 = new Graph.Edge<Integer>(2, v3, v6);
        final Graph.Edge<Integer> e5_6 = new Graph.Edge<Integer>(9, v5, v6);
        final Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(6, v4, v5);
        final Graph.Edge<Integer> e1_7 = new Graph.Edge<Integer>(1, v1, v7);
        final Graph.Edge<Integer> e1_8 = new Graph.Edge<Integer>(1, v1, v8);
        {
            edges.add(e1_2);
            edges.add(e1_3);
            edges.add(e1_6);
            edges.add(e2_3);
            edges.add(e2_4);
            edges.add(e3_4);
            edges.add(e3_6);
            edges.add(e5_6);
            edges.add(e4_5);
            edges.add(e1_7);
            edges.add(e1_8);
        }

        final Graph<Integer> graph = new Graph<Integer>(verticies, edges);
    }

    // Directed
    private class DirectedGraph {
        final List<Graph.Vertex<Integer>> verticies = new ArrayList<Graph.Vertex<Integer>>();
        final Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
        final Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
        final Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
        final Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
        final Graph.Vertex<Integer> v5 = new Graph.Vertex<Integer>(5);
        final Graph.Vertex<Integer> v6 = new Graph.Vertex<Integer>(6);
        final Graph.Vertex<Integer> v7 = new Graph.Vertex<Integer>(7);
        final Graph.Vertex<Integer> v8 = new Graph.Vertex<Integer>(8);
        {
            verticies.add(v1);
            verticies.add(v2);
            verticies.add(v3);
            verticies.add(v4);
            verticies.add(v5);
            verticies.add(v6);
            verticies.add(v7);
            verticies.add(v8);
        }

        final List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>();
        final Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(7, v1, v2);
        final Graph.Edge<Integer> e1_3 = new Graph.Edge<Integer>(9, v1, v3);
        final Graph.Edge<Integer> e1_6 = new Graph.Edge<Integer>(14, v1, v6);
        final Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(10, v2, v3);
        final Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(15, v2, v4);
        final Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(11, v3, v4);
        final Graph.Edge<Integer> e3_6 = new Graph.Edge<Integer>(2, v3, v6);
        final Graph.Edge<Integer> e6_5 = new Graph.Edge<Integer>(9, v6, v5);
        final Graph.Edge<Integer> e6_8 = new Graph.Edge<Integer>(14, v6, v8);
        final Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(6, v4, v5);
        final Graph.Edge<Integer> e4_7 = new Graph.Edge<Integer>(16, v4, v7);
        final Graph.Edge<Integer> e1_8 = new Graph.Edge<Integer>(30, v1, v8);
        {
            edges.add(e1_2);
            edges.add(e1_3);
            edges.add(e1_6);
            edges.add(e2_3);
            edges.add(e2_4);
            edges.add(e3_4);
            edges.add(e3_6);
            edges.add(e6_5);
            edges.add(e6_8);
            edges.add(e4_5);
            edges.add(e4_7);
            edges.add(e1_8);
        }

        final Graph<Integer> graph = new Graph<Integer>(Graph.TYPE.DIRECTED, verticies, edges);
    }

    // Directed with negative weights
    private class DirectedWithNegativeWeights {
        final List<Graph.Vertex<Integer>> verticies = new ArrayList<Graph.Vertex<Integer>>();
        final Graph.Vertex<Integer> v1 = new Graph.Vertex<Integer>(1);
        final Graph.Vertex<Integer> v2 = new Graph.Vertex<Integer>(2);
        final Graph.Vertex<Integer> v3 = new Graph.Vertex<Integer>(3);
        final Graph.Vertex<Integer> v4 = new Graph.Vertex<Integer>(4);
        {
            verticies.add(v1);
            verticies.add(v2);
            verticies.add(v3);
            verticies.add(v4);
        }

        final List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>();
        final Graph.Edge<Integer> e1_4 = new Graph.Edge<Integer>(2, v1, v4);  // w->z
        final Graph.Edge<Integer> e2_1 = new Graph.Edge<Integer>(6, v2, v1);  // x->w
        final Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(3, v2, v3);  // x->y
        final Graph.Edge<Integer> e3_1 = new Graph.Edge<Integer>(4, v3, v1);  // y->w
        final Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(5, v3, v4);  // y->z
        final Graph.Edge<Integer> e4_2 = new Graph.Edge<Integer>(-7, v4, v2); // z->x
        final Graph.Edge<Integer> e4_3 = new Graph.Edge<Integer>(-3, v4, v3); // z->y
        {
            edges.add(e1_4);
            edges.add(e2_1);
            edges.add(e2_3);
            edges.add(e3_1);
            edges.add(e3_4);
            edges.add(e4_2);
            edges.add(e4_3);
        }

        final Graph<Integer> graph = new Graph<Integer>(Graph.TYPE.DIRECTED, verticies, edges);
    }

    // Ideal undirected path
    private Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> getIdealUndirectedPath(UndirectedGraph undirected) {
        final HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> idealUndirectedPath = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
        {
            final int cost = 11;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_3);
            list.add(undirected.e3_6);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v6, path);
        }
        {
            final int cost = 20;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_3);
            list.add(undirected.e3_6);
            list.add(new Graph.Edge<Integer>(9, undirected.v6, undirected.v5));
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v5, path);
        }
        {
            final int cost = 9;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_3);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v3, path);
        }
        {
            final int cost = 20;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_3);
            list.add(undirected.e3_4);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v4, path);
        }
        {
            final int cost = 7;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_2);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v2, path);
        }
        {
            final int cost = 0;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v1, path);
        }
        {
            final int cost = 1;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_7);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v7, path);
        }
        {
            final int cost = 1;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(undirected.e1_8);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealUndirectedPath.put(undirected.v8, path);
        }
        return idealUndirectedPath;
    }

    // Ideal undirected PathPair
    private Graph.CostPathPair<Integer> getIdealUndirectedPathPair(UndirectedGraph undirected) {
        final int cost = 20;
        final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
        list.add(undirected.e1_3);
        list.add(undirected.e3_6);
        list.add(new Graph.Edge<Integer>(9, undirected.v6, undirected.v5));
        return (new Graph.CostPathPair<Integer>(cost, list));
    }

    // Ideal directed path
    private Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> getIdealDirectedPath(DirectedGraph directed) {
        final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> idealDirectedPath = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
        {
            final int cost = 11;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_3);
            list.add(directed.e3_6);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v6, path);
        }
        {
            final int cost = 20;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_3);
            list.add(directed.e3_6);
            list.add(new Graph.Edge<Integer>(9, directed.v6, directed.v5));
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v5, path);
        }
        {
            final int cost = 36;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_3);
            list.add(directed.e3_4);
            list.add(directed.e4_7);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v7, path);
        }
        {
            final int cost = 9;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_3);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v3, path);
        }
        {
            final int cost = 20;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_3);
            list.add(directed.e3_4);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v4, path);
        }
        {
            final int cost = 7;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_2);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v2, path);
        }
        {
            final int cost = 0;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v1, path);
        }
        {
            final int cost = 25;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directed.e1_3);
            list.add(directed.e3_6);
            list.add(directed.e6_8);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedPath.put(directed.v8, path);
        }
        return idealDirectedPath;
    }

    // Ideal directed Path Pair
    private Graph.CostPathPair<Integer> getIdealPathPair(DirectedGraph directed) {
        final int cost = 20;
        final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
        list.add(directed.e1_3);
        list.add(directed.e3_6);
        list.add(new Graph.Edge<Integer>(9, directed.v6, directed.v5));
        return (new Graph.CostPathPair<Integer>(cost, list));
    }

    // Ideal directed with negative weight path
    private Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> getIdealDirectedNegWeight(DirectedWithNegativeWeights directedWithNegWeights) {
        final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> idealDirectedNegWeight = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
        {
            final int cost = -2;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directedWithNegWeights.e1_4);
            list.add(directedWithNegWeights.e4_2);
            list.add(directedWithNegWeights.e2_3);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedNegWeight.put(directedWithNegWeights.v3, path);
        }
        {
            final int cost = 2;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directedWithNegWeights.e1_4);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedNegWeight.put(directedWithNegWeights.v4, path);
        }
        {
            final int cost = -5;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            list.add(directedWithNegWeights.e1_4);
            list.add(directedWithNegWeights.e4_2);
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedNegWeight.put(directedWithNegWeights.v2, path);
        }
        {
            final int cost = 0;
            final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
            final Graph.CostPathPair<Integer> path = new Graph.CostPathPair<Integer>(cost, list);
            idealDirectedNegWeight.put(directedWithNegWeights.v1, path);
        }
        return idealDirectedNegWeight;
    }

    // Ideal pair
    private Graph.CostPathPair<Integer> getIdealDirectedWithNegWeightsPathPair(DirectedWithNegativeWeights directedWithNegWeights) {
        final int cost = -2;
        final List<Graph.Edge<Integer>> list = new ArrayList<Graph.Edge<Integer>>();
        list.add(directedWithNegWeights.e1_4);
        list.add(directedWithNegWeights.e4_2);
        list.add(directedWithNegWeights.e2_3);
        return (new Graph.CostPathPair<Integer>(cost, list));
    }

    @Test
    public void testDijstraUndirected() {
        final UndirectedGraph undirected = new UndirectedGraph();
        final Graph.Vertex<Integer> start = undirected.v1;
        final Graph.Vertex<Integer> end = undirected.v5;
        {   // UNDIRECTED GRAPH
            final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map1 = Dijkstra.getShortestPaths(undirected.graph, start);

            // Compare results
            for (Graph.Vertex<Integer> v : map1.keySet()) {
                final Graph.CostPathPair<Integer> path1 = map1.get(v);
                final Graph.CostPathPair<Integer> path2 = getIdealUndirectedPath(undirected).get(v);
                assertTrue("Dijstra's shortest path error. path1="+path1+" path2="+path2, path1.equals(path2));
            }

            final Graph.CostPathPair<Integer> pair1 = Dijkstra.getShortestPath(undirected.graph, start, end);
            assertTrue("No path from " + start.getValue() + " to " + end.getValue(), (pair1 != null));

            assertTrue("Dijstra's shortest path error. pair="+pair1+" pair="+getIdealUndirectedPathPair(undirected), pair1.equals(getIdealUndirectedPathPair(undirected)));
        }
    }



    @Test
    public void testDijkstraDirected() {
        final DirectedGraph directed = new DirectedGraph();
        final Graph.Vertex<Integer> start = directed.v1;
        final Graph.Vertex<Integer> end = directed.v5;
        final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map1 = Dijkstra.getShortestPaths(directed.graph, start);

        // Compare results
        for (Graph.Vertex<Integer> v : map1.keySet()) {
            final Graph.CostPathPair<Integer> path1 = map1.get(v);
            final Graph.CostPathPair<Integer> path2 = getIdealDirectedPath(directed).get(v);
            assertTrue("Dijstra's shortest path error. path1="+path1+" path2="+path2, path1.equals(path2));
        }

        final Graph.CostPathPair<Integer> pair1 = Dijkstra.getShortestPath(directed.graph, start, end);
        assertTrue("No path from "+start.getValue()+" to "+end.getValue(), (pair1!=null));

        // Compare pair
        assertTrue("Dijstra's shortest path error. pair1="+pair1+" idealPathPair="+getIdealPathPair(directed), pair1.equals(getIdealPathPair(directed)));
    }


    /*
     * Makes a zero weighted directed graph, so that there is an edge between two vertices if the difference between the
     * vertices values is >= K
     */
    private final Graph<Integer> makeDirectedGraph(int N, int K, int[] values) {
        final List<Graph.Vertex<Integer>> vertices = new ArrayList<Graph.Vertex<Integer>>(values.length);
        for (int i=0; i<values.length; i++)
            vertices.add(new Graph.Vertex<Integer>(values[i], 0));

        final List<Graph.Edge<Integer>> edges = new ArrayList<Graph.Edge<Integer>>(values.length);
        for (int i=0; i<values.length; i++) {
            final Graph.Vertex<Integer> vi = vertices.get(i);
            for (int j=i+1; j<values.length; j++) {
                final Graph.Vertex<Integer> vj = vertices.get(j);
                final int diff = Math.abs(vi.getValue() - vj.getValue());
                if (diff >= K) {
                    final Graph.Edge<Integer> eij = new Graph.Edge<Integer>(diff, vi, vj);
                    edges.add(eij);
                }
            }
        }

        final Graph<Integer> g = new Graph<Integer>(Graph.TYPE.DIRECTED, vertices, edges);
        return g;
    }
}


