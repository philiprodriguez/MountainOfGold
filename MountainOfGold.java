import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Philip
 */
//Mountain of Gold Problem
public class MountainOfGold {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int numCases = scan.nextInt();
        for(int c = 1; c <= numCases; c++)
        {
            int numNodes = scan.nextInt();
            int numEdges = scan.nextInt();
            ArrayList<ArrayList<Edge>> adjList = new ArrayList<ArrayList<Edge>>();
            for(int n = 0; n < numNodes; n++)
                adjList.add(new ArrayList<Edge>());
            for(int e = 0; e < numEdges; e++)
            {
                int start = scan.nextInt();
                int end = scan.nextInt();
                int weight = scan.nextInt();
                adjList.get(start).add(new Edge(end, weight));
            }
            System.out.print("Case #" + c + ": ");
            ArrayList<Integer> result = hasNegativeCycle(adjList, 0);
            if (result.size() < 1)
            {
                System.out.println("not possible");
                continue;
            }
            else
            {
                boolean ans = false;
                for(Integer startPoint : result)
                {
                    if (hasPathToZero(adjList, startPoint))
                    {
                        ans = true;
                        break;
                    }
                }
                if (ans)
                {
                    System.out.println("possible");
                }
                else
                {
                    System.out.println("not possible");
                }
            }
        }
    }
    
    //Returns true iff there is a path from source to node 0, false otherwise.
    public static boolean hasPathToZero(ArrayList<ArrayList<Edge>> adjList, int source)
    {
        if (source == 0)
            return true;
        
        //BFS for it!
        boolean[] visited = new boolean[adjList.size()];
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(source);
        visited[source] = true;
        while(!q.isEmpty())
        {
            int node = q.poll();
            //System.out.println("Hit " + node);
            if (node == 0)
                return true;
            for(Edge e : adjList.get(node))
            {
                if (!visited[e.dest])
                {
                    q.add(e.dest);
                    visited[e.dest] = true;
                }
            }
        }
        return false;
    }
    
    //Returns null if there is no negative cycle, otherwise a list containing some
    //indices of nodes that belong to negative cycles in the graph. It is guaranteed
    //that at least one node form each negative cycle is in the list returned.
    public static final int oo = 999999999;
    private static ArrayList<Integer> hasNegativeCycle(ArrayList<ArrayList<Edge>> adjList, int start)
    {
        int[] dist = new int[adjList.size()];
        Arrays.fill(dist, oo);
        dist[start] = 0;
        for(int i = 0; i < adjList.size()-1; i++)
        {
            for(int v = 0; v < adjList.size(); v++)
            {
                for(int e = 0; e < adjList.get(v).size(); e++)
                {
                    //An edge from v to edge.dest
                    Edge edge = adjList.get(v).get(e);
                    if (dist[v]+edge.weight < dist[edge.dest])
                    {
                        dist[edge.dest] = dist[v]+edge.weight;
                    }
                }
            }
        }
        
        //look for every negative cycle
        ArrayList<Integer> inNegativeCycle = new ArrayList<Integer>();
        for(int v = 0; v < adjList.size(); v++)
        {
            for(int e = 0; e < adjList.get(v).size(); e++)
            {
                //An edge from v to edge.dest
                Edge edge = adjList.get(v).get(e);
                if (dist[v]+edge.weight < dist[edge.dest])
                {
                    dist[edge.dest] = dist[v]+edge.weight;
                    inNegativeCycle.add(edge.dest);
                }
            }
        }
        return inNegativeCycle;
    }
    
    private static class Edge
    {
        int weight;
        int dest;
        public Edge(int dest, int weight)
        {
            this.weight = weight;
            this.dest = dest;
        }
    }
}
