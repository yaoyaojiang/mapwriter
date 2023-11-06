package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class TeamTypes {
        public static void main(String[] args) throws InterruptedException, IOException {
            new NewTeamTypes();
        }
}

class NewTeamTypes extends JFrame {
    private JTextField fold = new JTextField("在此填写map文件的目录地址");
    private JFileChooser chooser = new JFileChooser("D:\\project\\mapreader");
    private JButton open = new JButton("浏览");
    private JDialog dialog=new JDialog();
    private JButton okBut = new JButton("确定");
    private JButton create = new JButton("生成");
    private JLabel success = new JLabel("修改成功");
    private JLabel direction=new JLabel("");
    private CommonMethod m=new CommonMethod();
    private Font font = new Font("宋体", Font.PLAIN, 13);
    private JPanel panel = new JPanel();
    private JTextArea teamtype=new JTextArea(50,40);
    private JTextField teamname = new JTextField("在此填写小队命名规则");
    private JTextField createnum = new JTextField("在此填写生成小队的个数");
    private JScrollPane scroll = new JScrollPane(teamtype);
    public NewTeamTypes() throws InterruptedException, IOException {
        panel.setLayout(null);
        dialog.setBounds(400, 200, 350, 150);//设置弹出对话框的位置和大小
        dialog.setLayout(new FlowLayout());//设置弹出对话框的布局为流式布局
        dialog.add(success);
        dialog.add(okBut);
        fold.setBounds(130, 40, 220, 30);
        fold.dispatchEvent(new FocusEvent(fold, FocusEvent.FOCUS_GAINED, true));
        fold.setName("fold");
        teamname.setBounds(130, 80, 220, 30);
        teamname.dispatchEvent(new FocusEvent(teamname, FocusEvent.FOCUS_GAINED, true));
        teamname.setName("teamname");
        createnum.setBounds(370, 80, 80, 30);
        createnum.dispatchEvent(new FocusEvent(createnum, FocusEvent.FOCUS_GAINED, true));
        createnum.setName("createnum");
        open.setBounds(380, 40, 75, 30);
        create.setBounds(470, 40, 75, 30);
        direction.setBounds(510, 50, 260, 520);
        direction.setFont(font);
        direction.setForeground(Color.RED);
        teamtype.setName("teamtype");
        teamtype.setLineWrap(true);        //激活自动换行功能
        teamtype.setWrapStyleWord(true);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(130,120,320,340);
        m.JlabelSetText(direction,"作战小队中，脚本和特遣部队仅能以1为间隔递增。脚本递增规则为{初始脚本号，末尾脚本号，脚本重复次数}。脚本重复次数X表示连续X个作战小队中脚本都是这个脚本，到第X+1个作战小队时切换到下一个脚本。特遣部队格式与脚本相同。初始路径点和传送路径点使用阿拉伯数字递增表示。如{1,2,2}为{初始路径点，重复次数，间隔数（可能为负数）}，表示作战小队的初始路径点将依次为：1,1，3,3,5,5,……。小队单位等级仅有1-3三个等级，因此可能出现循环重复，其规则为{初始等级，重复次数，是否循环（0为否1为是）}。若不循环，则按照原本规则生成到最后一个小队后面仍需生成新小队时，新小队的单位等级均为三级。脚本、特遣部队、路径点也遵循这个一旦溢出则只生成最后那个数值的小队的原则，但是这三者并没有循环的机制。\n" +
                "在给作战小队命名时，%taskforce%表示本小队关联的特遣部队名称，%script%表示本小队关联的脚本名称，%house%表示本小队的所属方。小队名中出现的数字循环与初始路径点的递增方式相同。此外，难度也是一个可以循环的点。难度仅能隔一个难度循环，不能一次性由easy递增至hard。难度递增的参数是{初始难度，最终难度，递增(任一正整数）或递减（任一负整数）,重复次数}。");
        panel.add(direction);
        panel.add(fold);
        panel.add(open);
        panel.add(create);
        panel.add(scroll);
        panel.add(teamname);
        panel.add(createnum);
        addListener();
        this.add(panel);
        this.setBounds(300, 100, 800, 800);
        this.setPreferredSize(new Dimension(800, 600));
        this.pack();
        this.setVisible(true);
    }
    public void addListener()
    {
        m.fileChoose(open, chooser, fold);
        m.dialogExit(okBut,dialog);
        keyUpOrDown(fold,teamname,teamtype,createnum);
        create.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String fileName = fold.getText();
                int i,mode;
                File file = new File(fileName);
                try {
                    if (!m.lastName(file).equals("map")) {
                        throw new FileNotFoundException("后缀名必须为map");
                    }
                    CommonMethod m=new CommonMethod();
                    mapList list=m.readMapList_Name(fold.getText());
                    writeTeamTypes(list);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void writeTeamTypes(mapList list) throws Exception {
        int i;
        String teamlisttext="";
        String teamtext="";
        String fileName = fold.getText();
        File file = new File(fileName);
        int lsize=list.getMaxTeamType();
        if (lsize<0) teamlisttext="[TeamTypes]\n";
        int code=Integer.parseInt(list.maxnormalcode.substring(1))+1;
        java.util.List<String> slist=new ArrayList<String>();
        Scanner sc=new Scanner(teamtype.getText());
        while (sc.hasNextLine())
        {
            slist.add(sc.nextLine());
        }
        int num=Integer.parseInt(createnum.getText());
        String house;
        for(i=1;i<=num;i++)
        {
            teamlisttext+=Integer.toString(lsize+i)+"=0"+Integer.toString(code)+"\n";
            teamtext+="[0"+code+"]\n";
            house="";
            String taskname="";
            String scriptname="";
            String tagname="";
            for(int j=0;j< slist.size();j++)
            {
                String s=slist.get(j);
                String type=s.split("=")[0];
                if (type.equals("House"))
                {
                    house=s.split("=")[1];
                    teamtext+=s+"\n";

                }
                else if (type.equals("Script")&&s.contains("{"))
                {
                    String scripttype=s.split("=")[1].split("}")[0].substring(1);
                    String startscript=scripttype.split(",")[0];
                    String endscript=scripttype.split(",")[1];
                    int identity=Integer.parseInt(scripttype.split(",")[2]);
                    java.util.List<String> scriptlist=new ArrayList<>(Arrays.asList(list.getScriptTypes()));
                    int startpos=scriptlist.indexOf(startscript);
                    int endpos=scriptlist.indexOf(endscript);
                    if(endpos<0) endpos=10000;
                    int thispos=startpos+((i-1)/identity);
                    if (thispos>endpos) thispos=endpos;
                    String thisscript=scriptlist.get(thispos);
                    teamtext+="Script="+thisscript+"\n";
                    for(Map<String,String> m:list.getScriptTypesName())
                    {
                        if (m.get(thisscript)!=null)
                        {
                            scriptname=m.get(thisscript);
                            break;
                        }
                    }
                }
                else if (type.equals("TaskForce"))
                {
                    if (s.contains("{"))
                    {
                        String tasktype = s.split("=")[1].split("}")[0].substring(1);
                        String starttask = tasktype.split(",")[0];
                        String endstask = tasktype.split(",")[1];
                        int identity = Integer.parseInt(tasktype.split(",")[2]);
                        java.util.List<String> tasklist = new ArrayList<>(Arrays.asList(list.getTaskForces()));
                        int startpos = tasklist.indexOf(starttask);
                        int endpos = tasklist.indexOf(endstask);
                        if (endpos < 0) endpos = 10000;
                        int thispos = startpos + ((i - 1) / identity);
                        if (thispos > endpos) thispos = endpos;
                        String thisscript = tasklist.get(thispos);
                        for (Map<String, String> m : list.getTaskForcesName()) {
                            if (m.get(thisscript) != null) {
                                taskname = m.get(thisscript);
                                break;
                            }
                        }
                        teamtext += "TaskForce=" + thisscript + "\n";
                    }
                    else
                    {
                        String tasktype = s.split("=")[1];
                        java.util.List<String> tasklist = new ArrayList<>(Arrays.asList(list.getTaskForces()));
                        int startpos = tasklist.indexOf(tasktype);
                        int thispos = startpos ;
                        String thisscript = tasklist.get(thispos);
                        for (Map<String, String> m : list.getTaskForcesName()) {
                            if (m.get(thisscript) != null) {
                                taskname = m.get(thisscript);
                                break;
                            }
                        }
                        teamtext += "TaskForce=" + thisscript + "\n";
                    }
                }
                else if (type.equals("Tag")&&s.contains("{"))
                {
                    String tagtype=s.split("=")[1].split("}")[0].substring(1);
                    String starttag=tagtype.split(",")[0];
                    String endtag=tagtype.split(",")[1];
                    int identity=Integer.parseInt(tagtype.split(",")[2]);
                    java.util.List<String> taglist=list.getTags();
                    int startpos=taglist.indexOf(starttag);
                    int endpos=taglist.indexOf(endtag);
                    if(endpos<0) endpos=10000;
                    int thispos=startpos+((i-1)/identity);
                    if (thispos>endpos) thispos=endpos;
                    String thistag=taglist.get(thispos);
                    for(Map<String,String> m:list.getTagsName())
                    {
                        if (m.get(thistag)!=null)
                        {
                            tagname=m.get(thistag);
                            break;
                        }
                    }
                    teamtext+="Tag="+thistag+"\n";
                }
                else if (type.equals("Waypoint")||type.equals("TransportWaypoint"))
                {
                    int thispoint;
                    if(s.contains("{")) {
                        String waytype = s.split("=")[1].split("}")[0].substring(1);
                        String startway = waytype.split(",")[0];
                        int rtimes = Integer.parseInt(waytype.split(",")[1]);
                        int identity = Integer.parseInt(waytype.split(",")[2]);
                        thispoint = Integer.parseInt(startway) + ((i - 1) / rtimes) * identity;
                        teamtext+=type+"="+m.Waypoint_toAl(thispoint)+"\n";
                    }
                    else
                    {
                        teamtext+=type+"="+s.split("=")[1]+"\n";
                    }
                    //System.out.println(startway+","+i+","+identity+","+rtimes+","+thispoint);
                }
                else if (type.equals("VeteranLevel")&&s.contains("{"))
                {
                    String lvtype=s.split("=")[1].split("}")[0].substring(1);
                    int startlv=Integer.parseInt(lvtype.split(",")[0]);
                    int rtimes=Integer.parseInt(lvtype.split(",")[1]);
                    int loop=Integer.parseInt(lvtype.split(",")[2]);
                    int thislv=startlv+((i-1)/rtimes);
                    if (loop>0)
                        thislv=thislv%3;
                    else if (thislv>3) thislv=3;
                    teamtext+="VeteranLevel="+Integer.toString(thislv)+"\n";
                }
                else if (!type.equals("Name"))
                {
                    teamtext+=s+"\n";
                }
            }
            teamtext+="Name=";
            String name=teamname.getText();
            name=name.replaceAll("%taskforce%",taskname).replaceAll("%script%",scriptname).replaceAll("%house%",house);
            char[] a=name.toCharArray();
            int matchtype,ms=-1;
            String replace="";
            for (int k=0;k<a.length;k++)
            {
                matchtype=-1;
                if (a[k]=='{')
                {
                    ms=1;
                }
                else if (ms>0&&a[k]=='}')
                {
                    if (replace.contains("a")) matchtype=2;
                    else matchtype=1;
                    if (matchtype==1)
                    {
                        int startway=Integer.parseInt(replace.split(",")[0]);
                        int identity=Integer.parseInt(replace.split(",")[2]);
                        int rtimes=Integer.parseInt(replace.split(",")[1]);
                        int thisway=startway+(i/rtimes)*identity;
                        teamtext+=Integer.toString(thisway);
                    }
                    else if (matchtype==2)
                    {
                        String startd=replace.split(",")[0];
                        String endd=replace.split(",")[1];
                        String thisdiffs="";
                        int diffi1=0,diffi2=0,thisdiff=0;
                        if (startd.equals("easy")) diffi1=1;
                        else if (startd.equals("normal")) diffi1=2;
                        else if (startd.equals("hard")) diffi1=3;
                        if (endd.equals("easy")) diffi2=1;
                        else if (endd.equals("normal")) diffi2=2;
                        else if (endd.equals("hard")) diffi2=3;
                        int rtimes=Integer.parseInt(replace.split(",")[3]);
                        int loop=Integer.parseInt(replace.split(",")[2]);
                        if (loop>0) loop=1;
                        else if (loop<0) loop=-1;
                        thisdiff=diffi1+((i-1)/rtimes)*loop;
                        if (loop!=0) thisdiff=thisdiff%3;
                        else thisdiff=diffi2;
                        switch (Math.abs(thisdiff))
                        {
                            case 1:
                                thisdiffs="easy";
                                break;
                            case 2:
                                thisdiffs="normal";
                                break;
                            case 0:
                                thisdiffs="hard";
                                break;
                            default:
                                throw new Exception("难度填写错误");
                        }
                        teamtext+=thisdiffs;
                    }
                    ms=-1;
                    replace="";
                }
                else if (ms>0)
                {
                    replace+=a[k];
                }
                else
                {
                    teamtext+=a[k];
                }
            }
            code++;
            teamtext+="\n\n";
        }
        printTeamTypes(list,file,teamtext,teamlisttext);
        //System.out.println(teamlisttext);
        //System.out.println(teamtext);
    }
    public void printTeamTypes(mapList list,File file,String teamtext,String teamlisttext) throws IOException {
        BufferedReader br=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        int i=1;
        try {
            br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
            for(String str=br.readLine();str!=null;str=br.readLine()) {
                if(i==list.getRow_TeamTypeLineList())
                    str=str.replaceAll(str,teamlisttext);
                buff.append(str+line);
                i++;
            }
            buff.append(teamtext);
            OutputStreamWriter pw= new OutputStreamWriter(new FileOutputStream(file),"gb2312");
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

    static void keyUpOrDown(JTextField fold,JTextField teamname,JTextArea scroll,JTextField createnum)
    {
        fold.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_DOWN) {
                                        teamname.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
        teamname.addKeyListener(new KeyAdapter() {
                                      @Override
                                      public void keyReleased(KeyEvent e) {
                                          int keyCode = e.getKeyCode();
                                          if (keyCode == KeyEvent.VK_DOWN) {
                                              scroll.requestFocusInWindow();
                                          }
                                          else if (keyCode == KeyEvent.VK_UP) {
                                              fold.requestFocusInWindow();
                                          }
                                          super.keyReleased(e);
                                      }
                                  }
        );
        scroll.addKeyListener(new KeyAdapter() {
                                      @Override
                                      public void keyReleased(KeyEvent e) {
                                          int keyCode = e.getKeyCode();
                                          if (keyCode == KeyEvent.VK_UP) {
                                              teamname.requestFocusInWindow();
                                          }
                                          super.keyReleased(e);
                                      }
                                  }
        );
        createnum.addKeyListener(new KeyAdapter() {
                                     @Override
                                     public void keyReleased(KeyEvent e) {
                                         int keyCode = e.getKeyCode();
                                         if (keyCode == KeyEvent.VK_DOWN) {
                                             scroll.requestFocusInWindow();
                                         }
                                         super.keyReleased(e);
                                     }
                                 }
        );
    }

}