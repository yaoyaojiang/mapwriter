package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class RandomTerrain {
    public static void main(String[] args) throws InterruptedException, IOException {
        new RandomT();
    }

}

class RandomT extends JFrame {
    private JTextField fold = new JTextField("在此填写map文件的目录地址");
    private JFileChooser chooser = new JFileChooser("D:\\project\\mapreader");
    private JButton open = new JButton("浏览");
    private JTextField terrainlist = new JTextField("在此填写Terrain代码列表，用逗号隔开");
    private JTextField waypoint1 = new JTextField("指定格子坐标A，可用路径点代替");
    private JTextField waypoint2 = new JTextField("指定格子坐标B，可用路径点代替");
    private JTextField density = new JTextField("填写地形对象的间隔密度（0-1）");
    private JLabel direction=new JLabel("");
    private CommonMethod m=new CommonMethod();
    private JButton ok = new JButton("确定");
    private JButton create = new JButton("新增");
    private JButton okBut = new JButton("确定");
    private JButton cancel = new JButton("取消");
    private JLabel confirm = new JLabel("是否要填写地形对象？");
    private JLabel success = new JLabel("填写成功");
    private JPanel panel = new JPanel();
    private JDialog dialog=new JDialog();
    private Font font = new Font("宋体", Font.PLAIN, 13);
    public RandomT() throws InterruptedException {
        direction.setBounds(510, 50, 260, 520);
        direction.setFont(font);
        direction.setForeground(Color.RED);
        m.JlabelSetText(direction,"随机生成地形对象中，将选定地图中两个地形格作为平行四边形两个相对的顶点，并随机在其中填入地形对象。填写坐标参数时，以如果使用者能理解地形格RX和RY的意义，则可按照RX,RY的格式填写两个地形格坐标参数。如果不填写坐标，则可在地图编辑器中指定的两个顶点标上路径点，直接填写两个路径点的序号即可，在使用者不在参数中填写逗号的情况下，视为填写路径点序号。若不填写参数，则视为全地图随机填地形对象。密度标识指定平行四边形中每一格被填入地形对象的概率，必须在0到1之间。已在指定平行四边形内部填写过的地形对象将直接被清空。");
        panel.setLayout(null);
        dialog.setBounds(400, 200, 350, 150);//设置弹出对话框的位置和大小
        dialog.setLayout(new FlowLayout());//设置弹出对话框的布局为流式布局
        dialog.add(success);
        dialog.add(okBut);
        dialog.add(confirm);
        dialog.add(ok);
        dialog.add(cancel);
        fold.setBounds(130, 40, 220, 30);
        fold.dispatchEvent(new FocusEvent(fold, FocusEvent.FOCUS_GAINED, true));
        fold.setName("fold");
        terrainlist.setBounds(130, 80, 220, 30);
        terrainlist.dispatchEvent(new FocusEvent(terrainlist, FocusEvent.FOCUS_GAINED, true));
        terrainlist.setName("terrainlist");
        waypoint1.setBounds(130, 120, 100, 30);
        waypoint1.dispatchEvent(new FocusEvent(waypoint1, FocusEvent.FOCUS_GAINED, true));
        waypoint1.setName("waypoint1");
        waypoint2.setBounds(250, 120, 100, 30);
        waypoint2.dispatchEvent(new FocusEvent(waypoint2, FocusEvent.FOCUS_GAINED, true));
        waypoint2.setName("waypoint2");
        density.setBounds(130, 160, 100, 30);
        density.dispatchEvent(new FocusEvent(density, FocusEvent.FOCUS_GAINED, true));
        density.setName("density");
        create.setBounds(380, 300, 75, 30);
        create.dispatchEvent(new FocusEvent(create, FocusEvent.FOCUS_GAINED, true));
        create.setName("create");
        open.setBounds(380, 40, 75, 30);
        panel.add(direction);
        panel.add(fold);
        panel.add(open);
        panel.add(create);
        panel.add(terrainlist);
        panel.add(waypoint1);
        panel.add(waypoint2);
        panel.add(density);
        panel.add(create);
        this.add(panel);
        this.setBounds(300, 100, 800, 800);
        this.setPreferredSize(new Dimension(800, 600));
        this.pack();
        addListener();
        this.setVisible(true);
    }

    public void addListener() {
        m.fileChoose(open, chooser, fold);
        m.dialogExit(okBut, dialog);
        m.dialogExit(cancel, dialog);
        create.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String fileName = fold.getText();
                File file = new File(fileName);
                try {
                    if (!m.lastName(file).equals("map")) {
                        throw new FileNotFoundException("后缀名必须为map");
                    }
                    okBut.setVisible(false);
                    success.setVisible(false);
                    ok.setVisible(true);
                    cancel.setVisible(true);
                    confirm.setVisible(true);
                    dialog.setVisible(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        ok.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String fileName = fold.getText();
                File file = new File(fileName);
                try {
                    CommonMethod m=new CommonMethod();
                    //readTiles(fileName);
                    mapList list=m.readMapList_Name(fold.getText());
                    createTerrain(list);

                    okBut.setVisible(true);
                    success.setVisible(true);
                    ok.setVisible(false);
                    cancel.setVisible(false);
                    confirm.setVisible(false);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void createTerrain(mapList list) throws Exception {
        java.util.List<Map<String,String>> terlist=list.getTerrains();
        java.util.List<Map<String,String>> waylist=list.getWaypoint();
        String text="";
        if (terlist.size()==0) text="[Terrain]\n";
        String way1=waypoint1.getText(),way2=waypoint2.getText();
        Double per=Double.valueOf(density.getText())*100;
        String[] ter=terrainlist.getText().split(",");
        if(per<0||per>100)
        {
            throw new Exception("概率为0-1");
        }
        int x1,y1,x2,y2;
        if (way1.contains(","))
        {
            x1=Integer.parseInt(way1.split(",")[0]);
            y1=Integer.parseInt(way1.split(",")[1]);
        }
        else if (m.isInteger(way1,true))
        {
            String s="";
            for(Map<String,String> m:waylist)
            {
                if (m.get(way1)!=null)
                {
                    s=m.get(way1);
                }
            }
            x1=Integer.parseInt(s.substring(s.length()-3,s.length()));
            y1=Integer.parseInt(s.substring(0,s.length()-3));
        }
        else
        {
            x1=1;
            y1=list.getWidth();
        }
        if(way2.contains(","))
        {
            x2 = Integer.parseInt(way2.split(",")[0]);
            y2 = Integer.parseInt(way2.split(",")[1]);
        }
        else if (m.isInteger(way2,true))
        {
            String s="";
            for(Map<String,String> m:waylist)
            {
                if (m.get(way2)!=null)
                {
                    s=m.get(way2);
                }
            }
            x2=Integer.parseInt(s.substring(s.length()-3,s.length()));
            y2=Integer.parseInt(s.substring(0,s.length()-3));
        }
        else
        {
            x2=list.getWidth()+list.getHeight()-1;
            y2=list.getHeight();
        }
        int i,j;
        int u=0;
        java.util.List<String> klist=new ArrayList<String>();
        for(Map<String,String> map:terlist)
        {
            i=0;
            map.forEach((key,value)->{
                klist.add(key);
            });
        }

        for(i=min(x1,x2);i<=max(x1,x2);i++)
        {
            for(j=min(y1,y2);j<=max(y1,y2);j++)
            {
                String sx,sy;
                if(i<10) sx="00"+String.valueOf(i);
                else if (i<100) sx="0"+String.valueOf(i);
                else sx=String.valueOf(i);
                sy=String.valueOf(j);
                String str=sy+sx;
                if(!klist.contains(str))
                {
                    Random random=new Random();
                    Double rnum=random.nextDouble()*100;
                    int pos=random.nextInt(ter.length);
                    if(rnum<per)
                    {
                        text+=str+"="+ter[pos]+"\n";
                    }
                }
            }
        }
        System.out.println(text);
        printNewTerrain(text);
    }

    public void printNewTerrain(String text) throws IOException {
        File file=new File(fold.getText());
        BufferedReader br=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
        int tagrow=0,written=0;
        try {
            String str;
            while ((str = br.readLine()) != null) {
                if (str.equals("[Terrain]")) tagrow=1;
                if (tagrow>0&&!str.equals("[Terrain]")&&!str.contains("="))
                {
                    str = str.replaceAll(str, text);
                    tagrow=0;
                    written++;
                }
                buff.append(str+line);
            }
            if(written==0)  buff.append(text+"\n");
            OutputStreamWriter pw= new OutputStreamWriter(new FileOutputStream(file),"gb2312");
            if(pw!=null) {
                pw.write(String.valueOf(buff));
                pw.close();
                dialog.setVisible(true);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
}
int min(int x1,int x2)
{
    if (x1>x2) return x2;
    else return x1;
}
int max(int x1,int x2)
{
    if (x1>x2) return x1;
    else return x2;
}
}
