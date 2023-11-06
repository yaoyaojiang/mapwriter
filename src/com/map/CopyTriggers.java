package com.map;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class CTriggers {
    public static void main(String[] args) throws InterruptedException, IOException {
        new CopyTriggers();
    }

}

class CopyTriggers extends JFrame {
    private JTextField foldf = new JTextField("在此填写来源map文件的目录地址");
    private JTextField foldt = new JTextField("在此填写被粘贴map文件的目录地址");
    private JFileChooser chooser = new JFileChooser("D:\\project\\mapreader");
    private JButton openf = new JButton("浏览");
    private JButton opent = new JButton("浏览");
    private JDialog dialog=new JDialog();
    private JButton okBut = new JButton("确定");
    private JButton copy = new JButton("复制");
    private JLabel success = new JLabel("复制成功");
    private JLabel direction=new JLabel("");
    private JPanel panel = new JPanel();
    private Font font = new Font("宋体", Font.PLAIN, 13);
    private CommonMethod m=new CommonMethod();
    private JTextField trigger1 = new JTextField("起始触发号");
    private JTextField trigger2 = new JTextField("结束触发号");
    private JTextField fuzzy = new JTextField("用于模糊查询的字符串");
    private JCheckBox ld = new JCheckBox("小队连带复制");
    private JTextField bl = new JTextField("局部变量递进");
    private JTextField ini = new JTextField("INI编号递进");
    private JTextField way = new JTextField("路径点递进");
    private JButton ok = new JButton("确定");
    private JButton cancel = new JButton("取消");
    private JLabel confirm = new JLabel("是否要新增事件？");
    int num = 1;

    public CopyTriggers() throws InterruptedException {
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
        bl.setBounds(450, 160, 60, 30);
        bl.dispatchEvent(new FocusEvent(bl, FocusEvent.FOCUS_GAINED, true));
        bl.setName("bl");
        ini.setBounds(450, 240, 60, 30);
        ini.dispatchEvent(new FocusEvent(ini, FocusEvent.FOCUS_GAINED, true));
        ini.setName("ini");
        way.setBounds(450, 200, 60, 30);
        way.dispatchEvent(new FocusEvent(opent, FocusEvent.FOCUS_GAINED, true));
        way.setName("way");
        trigger1.setBounds(70, 160, 110, 30);
        trigger1.dispatchEvent(new FocusEvent(trigger1, FocusEvent.FOCUS_GAINED, true));
        trigger1.setName("trigger1");
        trigger2.setBounds(200, 160, 110, 30);
        trigger2.dispatchEvent(new FocusEvent(trigger2, FocusEvent.FOCUS_GAINED, true));
        trigger2.setName("trigger2");
        fuzzy.setBounds(200, 200, 110, 30);
        fuzzy.dispatchEvent(new FocusEvent(fuzzy, FocusEvent.FOCUS_GAINED, true));
        fuzzy.setName("fuzzy");
        ld.setBounds(40, 200, 140, 30);
        ld.dispatchEvent(new FocusEvent(ld, FocusEvent.FOCUS_GAINED, true));
        ld.setName("ld");
        copy.setBounds(340, 200, 80, 30);
        copy.dispatchEvent(new FocusEvent(copy, FocusEvent.FOCUS_GAINED, true));
        copy.setName("copy");
        direction.setBounds(560, 70, 200, 460);
        direction.setFont(font);
        direction.setForeground(Color.RED);
        m.JlabelSetText(direction,"复制触发可以一次性复制某个指定标准代码范围内或者名称中包含某特定字符串的触发内容。其中，包含字符串和代码范围的条件可以同时生效。此外，如果触发中的行为或事件包含ini号，且那个INI包含在输入的ini号范围内，则会自适应地按照原文件的触发顺序更换ini号。此外，使用者可填写递增数来表示复制后的路径点、局部变量号、ini号将会递增或递减多少数值。填写ini号递变值时，上文提到的ini号自适应变化无效。");
        panel.add(direction);
        panel.add(foldf);
        panel.add(foldt);
        panel.add(trigger1);
        panel.add(trigger2);
        panel.add(openf);
        panel.add(opent);
        panel.add(copy);
        panel.add(ld);
        panel.add(bl);
        panel.add(ini);
        panel.add(way);
        panel.add(fuzzy);
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
        keyUpOrDown(foldf,foldt,trigger1,trigger2);
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
                    mapList list=m.readMapList_Name(foldf.getText());
                    mapList list2=m.readMapList(foldt.getText());
                    copyTriggers(list,list2);

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
    private String mapping(String[][] s,String target,List<String> triggerlist)
    {
        if (!target.startsWith("010")||target.length()!=8||!triggerlist.contains(target)) return target;
        int i;
        for(i=0;i<s.length;i++)
        {
            if (s[i][0].equals(target)) return s[i][1];

        }
        return target;
    }
    private void copyTriggers(mapList list,mapList list2) throws IOException {
        File file1=new File(foldf.getText());
        File file2=new File(foldt.getText());
        String starttrigger=trigger1.getText(),endtrigger=trigger2.getText();
        String fuzz=fuzzy.getText();
        if(!m.isNormalCode(starttrigger)) starttrigger="01000000";
        if (!m.isNormalCode(endtrigger)) endtrigger=list.maxnormalcode;
        List<String> triggerlist=new ArrayList<String>();
        for(Map<String,String> map:list.getTriggersName())
        {
            for  (String k : map.keySet()){
                int code=Integer.parseInt(k.substring(1));
                String name=map.get(k);
                if(name.contains(fuzz)&&Integer.parseInt(starttrigger.substring(1))<=code&&Integer.parseInt(endtrigger.substring(1))>=code)
                {
                    triggerlist.add(k);
                }
            }
        }
        List<String> trlist=list.getTriggers();
        Collections.sort(trlist);
        Collections.sort(triggerlist);
        String triggerstext="";
        String tagstext="";
        String eventstext="";
        String actionstext="";
        String[][] s=new String[10000][2];
        for(int i=0;i<10000;i++)
        {
            s[i][0]="";
            s[i][1]="";
        }
        String l=starttrigger;
        int pos=list.getTriggerPosition(starttrigger,fuzz),i=0;
        if (list2.getTriggers().size()<1) triggerstext="[Triggers]\n";
        if (list2.getTags().size()<1) tagstext="[Tags]\n";
        if (list2.getActions().size()<1) actionstext="[Events]\n";
        if (list2.getEvents().size()<1) eventstext="[Actions]\n";
        int code=Integer.parseInt(list2.maxnormalcode.substring(1))+1;
        int num=0;
        if (starttrigger.equals(endtrigger)&&triggerlist.contains(starttrigger))
        {
            s[0][0]=starttrigger;
            s[0][1]="0"+String.valueOf(code);
            s[1][0]="0"+String.valueOf(Integer.parseInt(starttrigger.substring(1))+1);
            s[1][1]="0"+String.valueOf(code+1);
            num=1;
        }
        while(!l.equals(endtrigger)&&pos<list.getTriggers().size())
        {
            l=trlist.get(pos);
            s[i][0]=l;
            s[1+i][0]="0"+String.valueOf(Integer.parseInt(l.substring(1))+1);
            s[i][1]="0"+String.valueOf(code);
            s[1+i][1]="0"+String.valueOf(code+1);
            code=code+2;
            pos++;
            i=i+2;
            num++;

        }
        List<String> taglist=new ArrayList<String>();
        System.out.println(list.getTagsTriggers());
        for(Map<String,String>map:list.getTagsTriggers())
        {
            for  (String k : map.keySet()){
                String tcode=map.get(k);
                if(triggerlist.contains(tcode))
                {
                    taglist.add(k);
                }
            }
        }
        BufferedReader br=null;
        List<String> teamlist=new ArrayList<String>();
        List<String> ldtext=new ArrayList<String>();
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        int row=0;
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file1),"GB2312"));
        try {
            String  str=br.readLine();
            while((str=br.readLine())!=null) {
                if (row>0&&!str.contains("=")&&!str.contains("["))
                {
                    row=0;
                }
                if (str.equals("[Triggers]"))
                {
                    row=1;
                }
                if (str.equals("[Tags]")) row=2;
                if (str.equals("[Events]")) row=3;
                if (str.equals("[Actions]")) row=4;
                if (row==1&&!str.equals("[Triggers]")) {
                    String tcode=str.split("=")[0];
                    String tname=str.split("=")[1].split(",")[2];
                    int tnum=Integer.parseInt(tcode.substring(1));
                    String bindcode=str.split("=")[1].split(",")[1];
                    if (bindcode.startsWith("010")) bindcode=mapping(s,bindcode,triggerlist);
                    if ((tname.contains(fuzz)||tname.length()<1)&&tnum>=Integer.parseInt(starttrigger.substring(1))&&tnum<=Integer.parseInt(endtrigger.substring(1)))
                    {
                        if (bindcode.startsWith("010"))
                        {
                            triggerstext+=mapping(s,tcode,triggerlist)+"="+str.split("=")[1].split(",")[0]+","+bindcode;
                            int doupos=str.indexOf(",",str.indexOf(",")+1);
                            triggerstext+=","+str.substring(doupos+1)+"\n";
                        }
                        else triggerstext+=mapping(s,tcode,triggerlist)+str.substring(8)+"\n";
                    }
                }
                if (row==2&&!str.equals("[Tags]")) {
                    String tcode=str.split("=")[0];
                    int tnum=Integer.parseInt(tcode.substring(1));
                    int lastd=str.lastIndexOf(",");
                    String lcode=str.substring(lastd+1);
                    if (!mapping(s,lcode,triggerlist).equals(lcode))
                    {
                        String ss=str.substring(9);
                        ss=ss.substring(0,ss.lastIndexOf(","));
                        System.out.println(tcode+","+taglist);
                        tagstext+=mapping(s,tcode,taglist)+"="+ss+","+mapping(s,lcode,triggerlist)+"\n";
                    }
                }
                if (row==3&&!str.equals("[Events]")) {
                    String tcode=str.split("=")[0];

                    if (!mapping(s,tcode,triggerlist).equals(tcode))
                    {
                        eventstext+=mapping(s,tcode,triggerlist)+"=";
                        int eventnum,waynum=0,blnum=0;
                        String scode=str.split("=")[1];
                        String[] sa=scode.split(",");
                        if(m.isInteger(way.getText(),false)) waynum=Integer.parseInt(way.getText());
                        if(m.isInteger(bl.getText(),false)) blnum=Integer.parseInt(bl.getText());
                        for(int j=1;j<sa.length;j=j+3)
                        {
                            eventnum=Integer.parseInt(sa[j]);
                            if (eventnum==34&&waynum!=0)
                            {
                                if (Integer.parseInt(sa[j+2])+waynum<=0) sa[j+2]="0";
                                else sa[j+2]=String.valueOf(Integer.parseInt(sa[j+2])+waynum);
                            }
                            else if ((eventnum==36||eventnum==37)&&(blnum!=0))
                            {
                                if (Integer.parseInt(sa[j+2])+blnum<=0) sa[j+2]="0";
                                else sa[j+2]=String.valueOf(Integer.parseInt(sa[j+2])+blnum);
                            }
                        }
                        StringBuffer strb = new StringBuffer();
                        for (int j=0; j<sa.length; j++) {
                            if (j == sa.length-1) {
                                strb.append(sa[j]);
                            } else {
                                strb.append(sa[j]);
                                strb.append(",");
                            }
                        }
                        eventstext+= strb.toString()+"\n";
                    }
                }
                if (row==4&&!str.equals("[Actions]")) {
                    String tcode=str.split("=")[0];
                    int actnum,waynum=0,blnum=0,ininum=0;
                    if(m.isInteger(way.getText(),false)) waynum=Integer.parseInt(way.getText());
                    if(m.isInteger(bl.getText(),false)) blnum=Integer.parseInt(bl.getText());
                    if(m.isInteger(ini.getText(),false)) ininum=Integer.parseInt(ini.getText());
                    if (!mapping(s,tcode,triggerlist).equals(tcode))
                    {

                        String ss=str.split("=")[1];
                        String[] r=ss.split(",");
                        String st="";
                        for(int j=0;j<r.length;j++)
                        {
                            if (j%8==1)
                            {
                                actnum=Integer.parseInt(r[j]);
                                if ((actnum==56||actnum==57)&&blnum!=0)
                                {
                                    r[j+2]=String.valueOf(Integer.parseInt(r[j+2])+blnum);
                                }
                                if(actnum==17&&waynum!=0)
                                {
                                    r[j+2]=String.valueOf(Integer.parseInt(r[j+2])+waynum);
                                }
                                else if (m.isActionUsingWaypoint(actnum)&&waynum!=0)
                                {
                                    r[j+7]=m.Waypoint_toAl(m.Waypoint_toInt(r[j+7])+waynum);
                                }
                                if (m.actionType(actnum)==1||m.actionType(actnum)==2||m.actionType(actnum)==5)
                                {
                                    r[j+2]="0"+String.valueOf(Integer.parseInt(r[j+2].substring(1))+ininum);
                                }
                            }
                            if (m.isNormalCode(r[j])&&ininum==0)
                            {
                                st+=mapping(s,r[j],triggerlist);
                                if (ld.isSelected())
                                {
                                    if (r[j-1].equals("2")||r[j-1].equals("5"))
                                    {
                                        teamlist.add(r[j]);
                                    }
                                }
                            }
                            else st+=r[j];
                            if (j<r.length-1)st+=",";
                        }
                        actionstext+=mapping(s,tcode,triggerlist)+"="+st+"\n";
                    }
                }
            }
            if(teamlist.size()>0)
            {
                ldtext=CopyTeams(file1,teamlist);
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
        printTriggers(file1,file2,triggerstext,tagstext,eventstext,actionstext,ldtext);
    }

    private List<String> CopyTeams(File file1, List<String> teamlist) throws FileNotFoundException, UnsupportedEncodingException {
        List<String> list=new ArrayList<String>();
        BufferedReader br=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        Collections.sort(teamlist);
        int i=0;
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file1),"GB2312"));
        try{
            String  str;
            while((str=br.readLine())!=null) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void printTriggers(File file1, File file2,String triggerstext, String tagstext,String eventstext,String actionstext,List<String> ldtext) throws IOException {
        BufferedReader br=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        int row=0;
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file2),"GB2312"));
        int used=0;
        int tagrow=0,triggerrow=0,eventrow=0,actionrow=0;
        try {
            String  str=br.readLine();
            while((str=br.readLine())!=null) {
                if (str.equals("[Events]")) eventrow=1;
                if (str.equals("[Tags]")) tagrow=1;
                if (str.equals("[Actions]")) actionrow=1;
                if (str.equals("[Triggers]")) triggerrow=1;
                if (triggerrow>0&&!str.equals("[Triggers]")&&!str.contains("="))
                {
                    str = str.replaceAll(str, triggerstext);
                    triggerrow=0;
                }
                else if (tagrow>0&&!str.equals("[Tags]")&&!str.contains("="))  {
                    str = str.replaceAll(str, tagstext);
                    tagrow=0;
                }
                else if (actionrow>0&&!str.equals("[Actions]")&&!str.contains("="))  {
                    str = str.replaceAll(str, actionstext);
                    actionrow=0;
                }
                else if (eventrow>0&&!str.equals("[Events]")&&!str.contains("="))  {
                    str = str.replaceAll(str, eventstext);
                    eventrow=0;
                }
                buff.append(str+line);
            }
            OutputStreamWriter pw= new OutputStreamWriter(new FileOutputStream(file2),"gb2312");
            if(pw!=null) {
                pw.write(String.valueOf(buff));
                pw.close();
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
    static void keyUpOrDown(JTextField foldf,JTextField foldt,JTextField trigger1,JTextField trigger2)
    {
        foldf.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_DOWN) {
                                        foldt.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
        foldt.addKeyListener(new KeyAdapter() {
                                 @Override
                                 public void keyReleased(KeyEvent e) {
                                     int keyCode = e.getKeyCode();
                                     if (keyCode == KeyEvent.VK_DOWN) {
                                         trigger1.requestFocusInWindow();
                                     }
                                     else if (keyCode == KeyEvent.VK_UP) {
                                         foldf.requestFocusInWindow();
                                     }
                                     super.keyReleased(e);
                                 }
                             }
        );
        trigger1.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_UP) {
                                        foldt.requestFocusInWindow();
                                    }
                                    else if (keyCode == KeyEvent.VK_RIGHT) {
                                        trigger2.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
        trigger2.addKeyListener(new KeyAdapter() {
                                    @Override
                                    public void keyReleased(KeyEvent e) {
                                        int keyCode = e.getKeyCode();
                                        if (keyCode == KeyEvent.VK_UP) {
                                            foldt.requestFocusInWindow();
                                        }
                                        else if (keyCode == KeyEvent.VK_LEFT) {
                                            trigger1.requestFocusInWindow();
                                        }
                                        super.keyReleased(e);
                                    }
                                }
        );


    }
}
