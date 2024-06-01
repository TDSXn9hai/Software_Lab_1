
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import test1.WeightedDirectedGraph;

public class lab1 {
    static WeightedDirectedGraph graph;
    static void createGraph(String text) {//构图
        ParserWord parser = new ParserWord();
        ArrayList<String> wordslist = new ArrayList<>();
        wordslist.addAll(parser.parseWords(text));
        //图的构建
        graph = new WeightedDirectedGraph();
         while (wordslist.size() >= 2) {
            graph.addEdge(wordslist.get(0), wordslist.get(1), 1);
            wordslist.remove(0);
    }
        return ;}

        static void showDirectedGraph(WeightedDirectedGraph graph) {//展示有向图
            graph.displayGraphPicture(null);
        }
        static String queryBridgeWords(String word1, String word2) {//查询桥接词
            return graph.findBridgeWords(word1, word2);
        }
        static String generateNewText(String inputText) {//生成新文本
            ParserWord parser = new ParserWord();
            ArrayList<String> wordslist = new ArrayList<>();
            wordslist.addAll(parser.parseWords(inputText));
            return graph.generateText (wordslist.toArray(new String[wordslist.size()]));
        }   
        static String calcShortestPath(String word1, String word2) {//计算两个词的最短路径
            return graph.shortestPath(word1, word2);
        }
        static String randomWalk() {//随机游走
            int randomIndex = new Random().nextInt(graph.getSize());
            return graph.walkBian(graph.getNodeName(randomIndex));
        }
    public static void main(String[] args) {
        System.out.println("Hello, world!");
          String fileName ;

        if (args.length == 0) {fileName=".\\input.txt";
        } else {fileName=args[0];}

        try {
            byte[] bytes = Files.readAllBytes(Paths.get(fileName)); // 一次性将整个文件读入字节数组
            String text = new String(bytes); // 将字节数组转换为字符串
    
            createGraph(text);
            //创建图完毕
            //提示选择相应功能
            while (true) {
            System.out.println("请输入相应功能：");
            System.out.println("1. 展示有向图");
            System.out.println("2. 查询桥接词");
            System.out.println("3. 生成新文本");
            System.out.println("4. 计算两个词的最短路径");
            System.out.println("5. 随机游走");
            System.out.println("0. 退出");
            int choice = Integer.parseInt(System.console().readLine());
            switch (choice) {
                case 0:
                    System.exit(0); // 退出程序
                case 1:
                    showDirectedGraph(graph);
                    break;
                case 2:
                    System.out.println("请输入起始词：");
                    String start = System.console().readLine();
                    System.out.println("请输入终止词：");
                    String end = System.console().readLine();
                    queryBridgeWords(start.toLowerCase(), end.toLowerCase());
                    break;
                case 3:
                    System.out.println("请输入文本：");
                    String inputText = System.console().readLine();
                    generateNewText(inputText);
                    break;
                case 4:
                    System.out.println("请输入起始词：");
                    String start3 = System.console().readLine();
                    System.out.println("请输入终止词：");
                    String end3 = System.console().readLine();
                    calcShortestPath(start3.toLowerCase(), end3.toLowerCase());
                    break;
                case 5:
                    randomWalk();
                    break;
                default:
                    System.out.println("输入错误，请重新输入！");
                    break;
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   

}
class ParserWord {//完成文本划分为单词
    public List<String> parseWords(String input) {
        // 空格、制表符、换行符、回车符、换页符、以及标点符号作为分隔符
        String[] words = input.toLowerCase().split("[ \\t\\n\\x0B\\f\\r\\p{P}]+");
        return Arrays.asList(words);
    }
}