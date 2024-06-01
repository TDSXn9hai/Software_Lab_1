package test1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
//import java.util.zip.Inflater;
import java.util.Random;

public class WeightedDirectedGraph {
    private  Map<String, Map<String, Integer>> graph;//图是通过list实现的，每个节点用一个map表示，key是邻接节点，value是权重
    private  Map<String, Integer> graphnode;//用于建立节点名称和邻接矩阵编号对应关系
    public int[][] adjTable;
    public int[][] tempadjTable;
    int INF = Integer.MAX_VALUE;
    public WeightedDirectedGraph() {
        graph = new HashMap<>();
        graphnode = new HashMap<>();
    }
    //简单判断操作
    public int getSize() {   //返回图的节点数
        return graph.size();
    }
    public int getNodeIndex(String node) {   //返回节点在邻接矩阵中的索引
        if (graphnode.containsKey(node)) {
            return graphnode.get(node);
        } else {
            return -1;
        }
    }
    public String getNodeName(int index) {   //返回节点名称
        // System.out.println(graphnode.size());
        // System.out.println(graphnode.entrySet());
        // System.out.println(graphnode.keySet());
        for (Map.Entry<String, Integer> entry : graphnode.entrySet()) {
            // System.out.println(entry);
            // System.out.println(entry.getKey() + " " + entry.getValue());
            if (entry.getValue() == index) {
                return entry.getKey();
            }
    }
    return null;
}
    //节点度是否大于0
    public boolean isConnected(String node) {
        if (graph.containsKey(node)) {
            return graph.get(node).size() > 0;
        } else {
            return false;
        }
    }
     
    public boolean containsNode(String node) {
        return graph.containsKey(node);
    }

    public boolean hasPath(String source, String destination) {
        if (!graph.containsKey(source) || !graph.containsKey(destination)) {
            return false;
        }
        Map<String, Integer> edges = graph.get(source);
        if (edges.containsKey(destination)) {
            return true;
        }
        for (String node : edges.keySet()) {
            if (hasPath(node, destination)) {
                return true;
            }
    }
    return false;
}
    //基本插入删除节点操作

    public void addNode(String node) {
        if (!graph.containsKey(node)) {
            graph.put(node, new HashMap<>());
            graphnode.put(node, graph.size()-1);
        }
    }
 public String[] getNode(String node) {
    if (graph.containsKey(node)) {
        Map<String, Integer> edges = graph.get(node);
        if(edges.size()==0)
            return null;
        String[] keys = edges.keySet().toArray(new String[0]);
       // System.out.println(keys);
        return keys;
    } else {
        System.out.println("Node not found");
    return null;} 

 }  
 // 获取当前节点的随机邻居
 public String getRandomNeighbor(String current) {
    String[] neighbors =getNode(current);
    if (neighbors==null)
        return null;
    int randomIndex = new Random().nextInt(neighbors.length);
    return neighbors[randomIndex];

}
    public void addEdge(String source, String destination, int weight) {
        int w=getEdgeWeight(source, destination);
        if(w!=-1)
            graph.get(source).put(destination, w+weight);
            else{
        if (!graph.containsKey(source)) {
            addNode(source);
        }
        if (!graph.containsKey(destination)) {
            addNode(destination);
        }
        graph.get(source).put(destination, weight);
            }}

    public int getEdgeWeight(String source, String destination) {
        if (graph.containsKey(source) && graph.get(source).containsKey(destination)) {
            return graph.get(source).get(destination);
        } else {
            //hrow new IllegalArgumentException("Edge not found"); // 根据需求抛出异常
            return -1;
        }
    }
    // public String getNode(String source, String destination) {
    //     if ( graph.get(source).containsKey(destination)) {
    //         return source + "->" + destination + " weight: " + graph.get(source).get(destination);
    //     } else {
    //         //hrow new IllegalArgumentException("Edge not found"); // 根据需求抛出异常
    //         return "Edge not found";
    //     }
    // }
    //展示图
    public void displayGraph() {
        System.out.println();
        System.out.println("图："); 
        for (String source : graph.keySet()) {
            System.out.println( source + " --:");
            Map<String, Integer> edges = graph.get(source);
            for (Map.Entry<String, Integer> entry : edges.entrySet()) {
                System.out.println("  -> " + entry.getKey() + " weight: " + entry.getValue());
            }
        }
    }
    public void displayGraphMatrix() {
        System.out.println();
        System.out.println("邻接矩阵：");
        for (int i=0;i<adjTable.length;i++){
            for (int j=0;j<adjTable.length;j++){
                if (adjTable[i][j]==INF)
                    System.out.print("0"+" ");
                else    
                System.out.print(adjTable[i][j]+" ");
            }
            System.out.println();
        }
    }
    public void displayGraphPicture(String filePath) {
        if (filePath==null) filePath=".\\data.csv";
        D3api d3api=new D3api();
        System.out.println();
        System.out.println("图片："); 
        for (String source : graph.keySet()) {
            //System.out.println( source + " --:");
            Map<String, Integer> edges = graph.get(source);
            for (Map.Entry<String, Integer> entry : edges.entrySet()) {
                //System.out.println("  -> " + entry.getKey() + " weight: " + entry.getValue());
                d3api.addPoints(source,entry.getKey(),String.valueOf(entry.getValue()));
            }
        }
        d3api.show(filePath);
    }
    public void displayPathPicture(String filePath) {
        D3api d3api=new D3api();
        System.out.println();
        System.out.println("图片："); 
        for (String source : graph.keySet()) {
            //System.out.println( source + " --:");
            int i=getNodeIndex(source);
            Map<String, Integer> edges = graph.get(source);
            for (Map.Entry<String, Integer> entry : edges.entrySet()) {
                //System.out.println("  -> " + entry.getKey() + " weight: " + entry.getValue());
                int j=getNodeIndex(entry.getKey());
                String wei=String.valueOf(entry.getValue());
                if(istempadjTable(i,j)==1) { wei=wei.concat("SIG");}
                d3api.addPoints(source,entry.getKey(),String.valueOf(wei));
            }
        }
        d3api.show(filePath);
    }
    //改成邻接矩阵
    private void adjTable() {
       
        int size=graph.size();
        adjTable=new int[size][size];
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                adjTable[i][j]=INF;    }
            }

        for (String source : graph.keySet()) {
            Map<String, Integer> edges = graph.get(source);
            for (Map.Entry<String, Integer> entry : edges.entrySet()) {
                int weight=entry.getValue();
                int dest=graphnode.get(entry.getKey());
                adjTable[graphnode.get(source)][dest]=weight;
            }   
        }
    }
    //为了记录ij是否被访问过，用于高亮路径
    public void gettempadjTable() {
        int size=graph.size();
        tempadjTable=new int[size][size];
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                tempadjTable[i][j]=0;    }
            }
        }
    public void settempadjTable(int i,int j) {
        tempadjTable[i][j]=1;
    }
    public int istempadjTable(int i,int j) {
        return tempadjTable[i][j]==1?1:0;
    }
    public int[][] getadjTable() {//返回邻接矩阵,inf表示不存在边
        adjTable();
        
        return adjTable;
}
    public String findBridgeWords(String start,String end)//只返回第一个桥接词
    {
        int flag = 0;
        String[] brigeStrings = new String[100];
        int brigeNum = 0;
        if (containsNode(start)) {
            flag = 1;
        }
        if (containsNode(end)) {
            flag = flag + 2;
        }
        if (flag == 3) {
            for (String i : getNode(start)) {
                if (getEdgeWeight(i, end) != -1) {
                    brigeStrings[brigeNum++] = i;
                }
            }String x=new String();
            if (brigeNum !=0) {
                System.out.printf("The bridge words from \"%s\" to \"%s\" is: ", start, end);
                
                if(brigeNum == 1) {
                    System.out.printf("%s\n", brigeStrings[0]);
                    return brigeStrings[0];
                }
                
                for (int i = 0; i < brigeNum - 1; i++) {
                    System.out.printf("%s、", brigeStrings[i]);
                    x.concat(brigeStrings[i]);
                    x.concat("、");
                }
                System.out.printf("and %s\n", brigeStrings[brigeNum - 1]);
                x.concat("and ");
                x.concat(brigeStrings[brigeNum - 1]);
                return x;
            } else {
                System.out.println("No bridge words from \"" + start + "\" to \"" + end + "\" !");
            }
        } else {
            if (flag == 1) {
                System.out.println("No \"" + start + "\" in the graph!");
            } else if (flag == 2) {
                System.out.println("No \"" + end + "\" in the graph!");
            } else {
                System.out.println("No \"" + start + "\" and \"" + end + "\" in the graph!");
            }
        }
        return null;
    }
    public  String getbrige(String start, String end) {
        int flag = 0;        
        if (containsNode(start)) {
            flag = 1;
        }
        if (containsNode(end)) {
            flag = flag + 2;
        }
        if (flag != 3) {return null;}
        if (flag == 3) {
            for (String i : getNode(start)) {
                if (getEdgeWeight(i, end) != -1) {
                    return i;
                }
            }  
        } 
        return null;
    }
    public String generateText(String[] text) {
        System.out.println("生成新文本： ");
        int size=text.length;
        int i=0;
        int j=0;
        String[] newText=new String[2*size];
        if (size<2) return text.toString();
        while(i<size-1){
            newText[j++]=text[i];
            String brige=getbrige(text[i],text[i+1]);
            if(brige!=null){
                newText[j++]=brige;
                }
                i++;}
            newText[j++]=text[i];
            //打印旧文本
            System.out.print("原文本： ");
            for (String string : text) {                
                System.out.print(string+" ");
            }
            System.out.println();
            //打印新文本
            System.out.print("新文本： ");
            for (String string : newText) {
                System.out.print(string+" ");
                j--;
                if(j==0) break;
            }
            System.out.println();
            return Arrays.toString(newText);
}
//最短路
 public String shortestPath(String start, String end) {
        System.out.println("查询最短路径： ");
        int flag = 0;
        if (containsNode(start)) {
            flag = 1;            
        }
        if (containsNode(end)) {
            flag = flag + 2;
        }
        if (flag == 3) {
            int[] dist = new int[getSize()];
            boolean[] visited = new boolean[getSize()];
            int[] predecessors = new int[getSize()];
            Arrays.fill(dist, Integer.MAX_VALUE);
            Arrays.fill(visited, false);
            Arrays.fill(predecessors, -1);
            // if(!graph.hasPath(start, end)) {
            //     System.out.println("No path from \"" + start + "\" to \"" + end + "\" !");
            //     return;
            // }
            dist[getNodeIndex(start)] = 0;
            //System.out.println(graph.getNodeIndex(start));
            for (int i = 0; i < getSize(); i++) {
                int minDist = Integer.MAX_VALUE;
                int minIndex = -1;
                for (int j = 0; j < getSize(); j++) {
                    if (!visited[j] && dist[j] < minDist) {
                        minDist = dist[j];
                        minIndex = j;
                    }
                }
                if (minIndex == -1) {
                    break;
                }
                visited[minIndex] = true;
                for (int j = 0; j < getSize(); j++) {
                    //System.out.println(graph.getNodeName(minIndex) + "->" + graph.getNodeName(j) + ":" + graph.getEdgeWeight(graph.getNodeName(minIndex), graph.getNodeName(j)));
                    if (!visited[j] && getEdgeWeight(getNodeName(minIndex), getNodeName(j)) != -1) {
                        int newDist = dist[minIndex] + getEdgeWeight(getNodeName(minIndex), getNodeName(j));
                        if (newDist < dist[j]) {
                            dist[j] = newDist;
                            predecessors[j] = minIndex;
                        }
                    }
                }
            }
            if (dist[getNodeIndex(end)] == Integer.MAX_VALUE) {
                System.out.println("No path from \"" + start + "\" to \"" + end + "\" !");
            } else {
                System.out.printf("The shortest path from \"%s\" to \"%s\" is: %d\n", start, end, dist[getNodeIndex(end)]);
                //为了高亮路径
                gettempadjTable();
                String x=new String();
                printPath(predecessors, getNodeIndex(end),x);
                //生成csv文件
                displayPathPicture(".\\data.csv");
            return x;
            }
        } else {
            if (flag == 1) {
                System.out.println("No \"" + start + "\" in the graph!");
            } else if (flag == 2) {
                System.out.println("No \"" + end + "\" in the graph!");
            } else {
                System.out.println("No \"" + start + "\" and \"" + end + "\" in the graph!");
            }
        }
        return null;
    }
    
    private void printPath(int[] predecessors, int vertexIndex,String x) {
        if (vertexIndex == -1) {
            return;
        }
        printPath(predecessors, predecessors[vertexIndex],x);
        if(predecessors[vertexIndex]!=-1) settempadjTable(predecessors[vertexIndex], vertexIndex);
        System.out.print(getNodeName(vertexIndex) + " ");
        x.concat(getNodeName(vertexIndex));
        x.concat(" ");
    }
    //随机游走
    public String walkBian( String start) {//遇到第一个重复的边会停止
        System.out.println("随机游走： ");
        int flag = 0;
        if (containsNode(start)) {
            flag = 1;
        }
        if (flag == 1) {
            //维护一个visted边数组，记录访问过的边
            int[][] visited = getadjTable();
            String x=new String();
            
            System.out.print(start+" ");
            x.concat(start);
            x.concat(" ");
            int current = getNodeIndex(start);
            int last=current;
           
            while (true) {
                String neighbor=getRandomNeighbor(getNodeName(current));
                if(neighbor==null) break;
                current = getNodeIndex(neighbor);
                System.out.print(getNodeName(current)+" ");
                x.concat(getNodeName(current));
                x.concat(" ");
                //如果遇到重复的边，停止
                if (visited[last][current]==-1) {
                    break;}
                visited[last][current] = -1;
                last=current;
                    }
                    System.out.println();
                    return x;
        } else {
            if (flag == 0) {
                System.out.println("No \"" + start + "\" in the graph!");
               
            }
        } return null;
    }
}
