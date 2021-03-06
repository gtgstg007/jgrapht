/*
 * (C) Copyright 2018, by Lukas Harzenetter and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package org.jgrapht.graph;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class AsUnweightedGraphTest
{

    private DefaultWeightedEdge loop;
    private DefaultWeightedEdge e12;
    private DefaultWeightedEdge e23;
    private DefaultWeightedEdge e24;
    private String v1 = "v1";
    private String v2 = "v2";
    private String v3 = "v3";
    private String v4 = "v4";
    private Graph<String, DefaultWeightedEdge> unweightedGraph;

    /**
     * Similar set up as created by {@link AsUndirectedGraphTest}.
     */
    @Before public void setUp()
    {
        Graph<String, DefaultWeightedEdge> undirectedWeightedGraph =
            new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);
        this.unweightedGraph = new AsUnweightedGraph<>(undirectedWeightedGraph);

        undirectedWeightedGraph.addVertex(v1);
        undirectedWeightedGraph.addVertex(v2);
        undirectedWeightedGraph.addVertex(v3);
        undirectedWeightedGraph.addVertex(v4);
        e12 = Graphs.addEdge(undirectedWeightedGraph, v1, v2, 6d);
        e23 = Graphs.addEdge(undirectedWeightedGraph, v2, v3, 456d);
        e24 = Graphs.addEdge(undirectedWeightedGraph, v2, v4, 0.587d);
        loop = Graphs.addEdge(undirectedWeightedGraph, v4, v4, 6781234453486d);
    }

    @Test public void getEdgeWeightOfE12()
    {
        assertEquals(Graph.DEFAULT_EDGE_WEIGHT, this.unweightedGraph.getEdgeWeight(e12), 0);
    }

    @Test public void getEdgeWeightOfE23()
    {
        assertEquals(Graph.DEFAULT_EDGE_WEIGHT, this.unweightedGraph.getEdgeWeight(e23), 0);
    }

    @Test public void getEdgeWeightOfE24()
    {
        assertEquals(Graph.DEFAULT_EDGE_WEIGHT, this.unweightedGraph.getEdgeWeight(e24), 0);
    }

    @Test public void getEdgeWeightOfLoop()
    {
        assertEquals(Graph.DEFAULT_EDGE_WEIGHT, this.unweightedGraph.getEdgeWeight(loop), 0);
    }

    @Test public void setEdgeWeight()
    {
        try {
            this.unweightedGraph.setEdgeWeight(e23, 81);
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), is("Edge weight is not supported"));
        }
    }

    @Test public void getType()
    {
        assertFalse(this.unweightedGraph.getType().isWeighted());
    }

    @Test public void failOnCreationOfUnweightedGraph()
    {
        try {
            new AsUnweightedGraph<>(null);
            fail("Expected an NullPointerException to be thrown");
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
    }
}
