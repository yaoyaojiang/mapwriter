package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CTeamTypes {

    public static void main(String[] args) throws InterruptedException, IOException {
        new CopyTeamTypes();
    }
}
class CopyTeamTypes extends JFrame {
    private JTextField foldf = new JTextField("在此填写来源map文件的目录地址");
    private JTextField foldt = new JTextField("在此填写被粘贴map文件的目录地址");
    private JFileChooser chooser = new JFileChooser("D:\\project\\mapreader");
    private JButton openf = new JButton("浏览");
    private JButton opent = new JButton("浏览");
    private JDialog dialog=new JDialog();
    private final String[] items=new String[]{"不变","零级","一级","三级"};
    private JComboBox lv = new JComboBox(items);
    private JButton okBut = new JButton("确定");
    private JButton copy = new JButton("复制");
    private JLabel success = new JLabel("复制成功");
    private JLabel direction=new JLabel("");
    private JPanel panel = new JPanel();
    private Font font = new Font("宋体", Font.PLAIN, 13);
    private CommonMethod m=new CommonMethod();
    private JTextField team1 = new JTextField("起始作战小队");
    private JTextField team2 = new JTextField("结束作战小队");
    private JTextField tasknum = new JTextField("特遣部队递增数量");
    private JTextField name = new JTextField("重命名");
    private JButton ok = new JButton("确定");
    private JButton cancel = new JButton("取消");
    private JLabel confirm = new JLabel("是否要新增事件？");
    public CopyTeamTypes() throws InterruptedException {
        panel.setLayout(null);
        dialog.setBounds(400, 200, 350, 150);//设置弹出对话框的位置和大小
        dialog.setLayout(new FlowLayout());//设置弹出对话框的布局为流式布局
        dialog.add(confirm);
        dialog.add(success);
        dialog.add(ok);
        dialog.add(okBut);
        okBut.setVisible(false);
        success.setVisible(false);
        dialog.add(cancel);
        foldf.setBounds(70, 80, 220, 30);
        foldf.dispatchEvent(new FocusEvent(foldf, FocusEvent.FOCUS_GAINED, true));
        foldf.setName("foldf");
        foldt.setBounds(70, 120, 220, 30);
        foldt.dispatchEvent(new FocusEvent(foldf, FocusEvent.FOCUS_GAINED, true));
        foldt.setName("foldt");
        openf.setBounds(320, 80, 60, 30);
        openf.dispatchEvent(new FocusEvent(openf, FocusEvent.FOCUS_GAINED, true));
        openf.setName("openf");
        opent.setBounds(320, 120, 60, 30);
        opent.dispatchEvent(new FocusEvent(opent, FocusEvent.FOCUS_GAINED, true));
        opent.setName("opent");
        team1.setBounds(70, 160, 110, 30);
        team1.dispatchEvent(new FocusEvent(team1, FocusEvent.FOCUS_GAINED, true));
        team1.setName("team1");
        team2.setBounds(200, 160, 110, 30);
        team2.dispatchEvent(new FocusEvent(team2, FocusEvent.FOCUS_GAINED, true));
        team2.setName("team2");
        lv.setBounds(400, 160, 60, 30);
        lv.dispatchEvent(new FocusEvent(lv, FocusEvent.FOCUS_GAINED, true));
        lv.setName("lv");
        tasknum.setBounds(400, 210, 60, 30);
        tasknum.dispatchEvent(new FocusEvent(tasknum, FocusEvent.FOCUS_GAINED, true));
        tasknum.setName("tasknum");
        name.setBounds(70, 200, 200, 30);
        name.dispatchEvent(new FocusEvent(name, FocusEvent.FOCUS_GAINED, true));
        name.setName("name");
        copy.setBounds(300, 200, 60, 30);
        copy.dispatchEvent(new FocusEvent(copy, FocusEvent.FOCUS_GAINED, true));
        copy.setName("copy");
        direction.setBounds(560, 70, 200, 460);
        direction.setFont(font);
        direction.setForeground(Color.RED);
        m.JlabelSetText(direction,"复制小队重命名时，以%teamname%来带入来源小队名称，以%lv%代入小队等级");
        panel.add(direction);
        panel.add(foldf);
        panel.add(foldt);
        panel.add(team1);
        panel.add(team2);
        panel.add(openf);
        panel.add(opent);
        panel.add(copy);
        panel.add(tasknum);
        panel.add(name);
        panel.add(lv);
        addListener();
        this.add(panel);
        this.setBounds(300, 100, 800, 800);
        this.setPreferredSize(new Dimension(800, 600));
        this.pack();
        this.setVisible(true);
    }
    public void addListener()
    {
        m.fileChoose(openf, chooser, foldf);
        m.fileChoose(opent, chooser, foldt);
        m.dialogExit(okBut, dialog);
        m.dialogExit(cancel, dialog);
        copy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String fileName = foldf.getText();
                String fileName2 = foldt.getText();
                File file = new File(fileName);
                File file2 = new File(fileName2);
                try {
                    if (!m.lastName(file).equals("map")||!m.lastName(file2).equals("map")) {
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
                String fileName = foldf.getText();
                String fileName2 = foldt.getText();
                File file = new File(fileName);
                File file2 = new File(fileName2);
                try {
                    CommonMethod m=new CommonMethod();
                    mapList list=m.readMapList(foldf.getText());
                    mapList list2=m.readMapList(foldt.getText());
                    copyTeams(list,list2);

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


    private void copyTeams(mapList list,mapList list2) throws IOException {
        File file1 = new File(foldf.getText());
        File file2 = new File(foldt.getText());
        String startteam=team1.getText(),endteam=team2.getText();
        java.util.List<String> teamlist=Arrays.asList(list.getTeamTypes()).subList(0,list.getTeamLength());
        java.util.List<String> scriptlist= Arrays.asList(list.getScriptTypes()).subList(0,list.getScriptLength());
        java.util.List<String> tasklist= Arrays.asList(list.getTaskForces()).subList(0,list.getTaskForceLength());

        Collections.sort(teamlist);
        Collections.sort(scriptlist);
        Collections.sort(tasklist);
        String text="",lastteam="",firstteam="";
        String teamtext="";
        String listtext="";
        String[][] s=new String[10000][2];
        for(int i=0;i<10000;i++)
        {
            s[i][0]="";
            s[i][1]="";
        }
        String l=startteam;
        int pos=list.getTeamPosition(startteam),i=0;
        if (list2.getTeamTypes().length<1) listtext="[TeamTypes]\n";
        int code=Integer.parseInt(list2.maxnormalcode.substring(1))+1;
        int num=code,tnum=code;

        BufferedReader br=null;
        List<String> ldtext=new ArrayList<String>();
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        int row=0;
        int listlen=list2.getTeamLength();
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file1),"GB2312"));
        try {
            String  str=br.readLine();
            while((str=br.readLine())!=null) {
                if (row>0&&!str.contains("=")&&!str.contains("["))
                {
                    row=0;
                }
                if (str.equals("[TeamTypes]"))
                {
                    row=1;
                }
                else if(str.contains("["))
                {
                    String tcode=str.split("\\[")[1].split("]")[0];
                    if ((tcode.equals(startteam)||tcode.equals(endteam)||(tcode.compareTo(startteam)>0&&tcode.compareTo(endteam)<0))&&m.isNormalCode(tcode))
                    {
                        row=2;
                        teamtext+="\n[0"+String.valueOf(tnum)+"]\n";
                        tnum++;
                    }
                }
                if (row==1&&!str.equals("[TeamTypes]")) {
                    String tcode=str.split("=")[1];
                    if ((tcode.equals(startteam)||tcode.equals(endteam)||(tcode.compareTo(startteam)>0&&tcode.compareTo(endteam)<0))&&m.isNormalCode(tcode))
                    {
                        listtext += String.valueOf(listlen) + "=0" + String.valueOf(num) + "\n";
                        num++;
                        listlen++;
                    }
                }
                if (row==2&&!str.contains("[")) {
                    if (str.contains("Name="))
                    {
                        String nametext=str.split("=")[1];
                        teamtext+="Name=";
                        char[] a=name.getText().toCharArray();
                        int pn=0,k1=-1,k2=-1,comma=0;
                        String replacetype = null;
                        for(int j=0;j<a.length;j++)
                        {
                            if (a[j]=='%') {
                                if (pn%2==0) k1=j;
                                else k2=j;
                                //k2-k1==10表示为taskforce，否则为num，这是由英文单词长度决定
                                comma=1;
                                pn++;
                                if(k2-k1==9)
                                {
                                    replacetype="teamname";
                                }
                                else if (k2-k1==3)
                                {
                                    replacetype = "level";
                                }
                                if (pn%2==0)
                                {
                                    if (replacetype.equals("level"))
                                    {
                                        if (String.valueOf(lv.getSelectedItem()).equals("不变")) teamtext+="";
                                        else if (String.valueOf(lv.getSelectedItem()).equals("零级")) teamtext+="LV0";
                                        else if (String.valueOf(lv.getSelectedItem()).equals("一级")) teamtext+="LV1";
                                        else teamtext+="LV3";
                                    }
                                    else if (replacetype.equals("teamname"))
                                    {
                                        teamtext+=nametext;
                                    }
                                    comma=0;
                                }

                            }
                            else if(comma==0) teamtext+=a[j];
                        }
                        teamtext+="\n";
                    }
                    else if (str.contains("VeteranLevel="))
                    {
                        if (String.valueOf(lv.getSelectedItem()).equals("不变")) teamtext+=str+"\n";
                        else if (String.valueOf(lv.getSelectedItem()).equals("零级")) teamtext+="VeteranLevel=1\n";
                        else if (String.valueOf(lv.getSelectedItem()).equals("一级")) teamtext+="VeteranLevel=2\n";
                        else teamtext+="VeteranLevel=3\n";
                    }
                    else if (str.contains("TaskForce="))
                    {
                        String taskstr=str.split("=")[1];
                        int tasknumber=0;
                        if (m.isInteger(tasknum.getText(),false)) tasknumber=Integer.parseInt(tasknum.getText());
                        if (m.isNormalCode(taskstr))
                        {
                            teamtext+="TaskForce=0"+String.valueOf(Integer.parseInt(taskstr.substring(1))+tasknumber)+"\n";
                        }
                    }
                    else teamtext+=str+"\n";
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br!=null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
        System.out.println(listtext);
        printTeamtypes(list2,file1,file2,listtext,teamtext);
    }

    private void printTeamtypes(mapList list,File file1, File file2, String listtext, String teamtext) throws FileNotFoundException, UnsupportedEncodingException {
        BufferedReader br=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file2),"GB2312"));
        int i=1;
        try {
            for(String str=br.readLine();str!=null;str=br.readLine()) {
                if(i==list.getRow_TeamTypeLineList())
                    str=str.replaceAll(str,listtext);
                buff.append(str+line);
                i++;
            }
            buff.append(teamtext);
            OutputStreamWriter pw= new OutputStreamWriter(new FileOutputStream(file2),"gb2312");
            pw.write(String.valueOf(buff));
            if(pw!=null) {
                pw.close();
                dialog.setVisible(true);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br!=null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }
}
