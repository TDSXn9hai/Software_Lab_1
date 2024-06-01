package test1;

import java.io.File;
import java.io.BufferedWriter;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
/**
 * 坐标点参数实体类
 */
class PointsParamDto {
    /**
     * 坐标点头部
     */
    private String pointId;

    /**
     * X 坐标点
     */
    private String x;

    /**
     * X 坐标点
     */
    private String y;

    public PointsParamDto(){}

    public PointsParamDto(String pointId,String x,String y){
        this.pointId = pointId;
        this.x = x;
        this.y = y;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String toRow(){
        return String.format("%s,%s,%s",this.pointId,this.x,this.y);
    }
}
public class D3api {
/**
 * 生成csv文件
 * @param pointsList
 * @return
 */
private List<PointsParamDto> pointsList;
public D3api(){
	pointsList = new ArrayList<PointsParamDto>();
}
private void PointsToCsvFile(String filePath ){
    if (pointsList!=null && pointsList.size() > 0){
        // 表格头
        String[] headArr = new String[]{"source", "target", "type"};
        //检查输入文件路径是不是csv
        if (!filePath.endsWith(".csv")) {
            filePath += ".csv";
        }
        //String filePath = "D:\\VScode\\java实验\\test1"; //CSV文件路径
       // String fileName = "data.csv";//CSV文件名称
        File csvFile = null;
        BufferedWriter csvWriter = null;
        try {
            csvFile = new File(filePath);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);

            // 写入文件头部标题行
            csvWriter.write(String.join(",", headArr));
            csvWriter.newLine();

            // 写入文件内容
            for (PointsParamDto points : pointsList) {
                csvWriter.write(points.toRow());
                csvWriter.newLine();
            }
            csvWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
public void addPoints(String pointId,String x,String y){
	pointsList.add(new PointsParamDto(pointId,x,y));
}
public void show(String filePath){
    PointsToCsvFile(filePath);
    try {
        // 执行二进制文件
        @SuppressWarnings("deprecation")
        Process process = Runtime.getRuntime().exec(".\\RUN.bat");
    
        // 等待命令执行完成
        process.waitFor();
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
    
}
public static void main(String[] args) {
    D3api d3api = new D3api();
    d3api.addPoints("1","10","20");
    d3api.addPoints("2","30","40");
    d3api.addPoints("3","50","60"); 
    d3api.show("D:\\VScode\\java实验\\test1\\data.csv");
}
}