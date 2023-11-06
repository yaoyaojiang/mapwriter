package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class test extends Frame{
    private JLabel testLabel;
    private JPanel rootPanel;
    private JTextField textField1;
    private JButton NewTroop;


    public test() {
        testLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setBound();
            }
        });
        NewTroop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TextField text=new TextField();
                JPanel panel=new JPanel();
                JTextField textField=new JTextField();
                textField.setBounds(100,100,320,20);
                textField.setText("ddddd");
                textField.setMaximumSize(new Dimension(320,20));
                textField.setMinimumSize(new Dimension(320,20));
                rootPanel.add(text);
            }
        });
    }
    private void setBound()
    {
        rootPanel.setBounds(300,100,800,800);
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        new MyJFrame();
    }

}
class MyJFrame extends JFrame {
    private JButton button2 = new JButton("生成");
    private JButton okBut = new JButton("确定");
    private JButton open = new JButton("浏览");
    private JTextField fold = new JTextField("在此填写map文件的目录地址");
    private JTextField jTextField = new JTextField("在此填写特遣部队单位代码组合格式");
    private JTextField trooptype = new JTextField("在此填写特遣部队名称格式");
    private JTextField connector = new JTextField("在此填写连接符号");
    private JTextArea troops = new JTextArea(50,40);
    private JScrollPane scroll = new JScrollPane(troops);
    private JTextField rannum = new JTextField("随机生成队数");
    private JTextField rantype = new JTextField("随机生成单位种类");
    private JTextField rantop = new JTextField("单队数量下限，上限");
    private JTextField ranload = new JTextField("随机小队中必须出现的前提单位，数量（一般用于装载，仅能填一个）");
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel("修改成功");
    private JLabel label2 = new JLabel();
    private JCheckBox ran = new JCheckBox("随机生成");
    private Font font = new Font("宋体", Font.PLAIN, 13);
    private JFileChooser chooser = new JFileChooser("D:\\project\\mapreader");
    private JDialog dialog=new JDialog();
    private CommonMethod c=new CommonMethod();
    int num = 1;

    public MyJFrame() throws InterruptedException, IOException {
        panel.setLayout(null);
        dialog.setBounds(400, 200, 350, 150);//设置弹出对话框的位置和大小
        dialog.setLayout(new FlowLayout());//设置弹出对话框的布局为流式布局
        dialog.add(label);
        dialog.add(okBut);
        dialog.setVisible(false);
        button2.setBounds(380, 160, 75, 30);
        open.setBounds(380, 40, 75, 30);
        okBut.setBounds(100, 120, 100, 50);
        fold.setBounds(130, 40, 220, 30);
        fold.dispatchEvent(new FocusEvent(fold, FocusEvent.FOCUS_GAINED, true));
        fold.setName("fold");
        ran.setBounds(380, 200, 80, 30);
        ran.dispatchEvent(new FocusEvent(ran, FocusEvent.FOCUS_GAINED, true));
        ran.setName("ran");
        rannum.setBounds(380, 240, 80, 30);
        rannum.dispatchEvent(new FocusEvent(rannum, FocusEvent.FOCUS_GAINED, true));
        rannum.setName("rannum");
        rantop.setBounds(380, 280, 80, 30);
        rantop.dispatchEvent(new FocusEvent(rantop, FocusEvent.FOCUS_GAINED, true));
        rantop.setName("rantop");
        rantype.setBounds(380, 320, 180, 30);
        rantype.dispatchEvent(new FocusEvent(rantype, FocusEvent.FOCUS_GAINED, true));
        rantype.setName("rantype");
        ranload.setBounds(380, 360, 180, 30);
        ranload.dispatchEvent(new FocusEvent(ranload, FocusEvent.FOCUS_GAINED, true));
        ranload.setName("ranload");
        jTextField.setBounds(130, 80, 220, 30);
        jTextField.dispatchEvent(new FocusEvent(jTextField, FocusEvent.FOCUS_GAINED, true));
        jTextField.setName("nametype");
        trooptype.setBounds(130, 120, 220, 30);
        trooptype.dispatchEvent(new FocusEvent(trooptype, FocusEvent.FOCUS_GAINED, true));
        trooptype.setName("nametype2");
        connector.setBounds(380, 80, 100, 30);
        connector.dispatchEvent(new FocusEvent(connector, FocusEvent.FOCUS_GAINED, true));
        connector.setName("connector");
        troops.dispatchEvent(new FocusEvent(troops, FocusEvent.FOCUS_GAINED, true));
        troops.setName("troops");
        troops.setLineWrap(true);        //激活自动换行功能
        troops.setWrapStyleWord(true);
        troops.setText("按照APOC,3,HTNK,4的格式输入，每个特遣部队都占一行");
        label2.setBounds(560, 0, 200, 300);
        label2.setFont(font);
        label2.setForeground(Color.RED);
        c.JlabelSetText(label2,"规定单位组合格式时，使用%unitcode%,%unitnum%来代替单位代码（如APOC）和单位数量，然后使用连接符号来连接。组合完成的单位组合名称用%unitcons%来代替,并可将其写入特遣部队名称格式栏中。此外，可在特遣部队名称格式栏中使用%num%{初始数字，递增（减）额度}参数来表示一个递增的数字。使用随机生成时，应输入所有可能出现的单位代码、单队特遣部队的单位总数上限和特遣部队的数量。单位代码种类用APOC,V3,HTNK的格式填写，填写后点击生成按钮随机生成特种部队，命名规则同正常生成。");

        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(30,160,330,340);
        panel.add(scroll);
        panel.add(connector);
        panel.add(button2);
        panel.add(ran);
        panel.add(rannum);
        panel.add(rantype);
        panel.add(rantop);
        panel.add(ranload);
        panel.add(open);
        panel.add(jTextField);
        panel.add(trooptype);
        panel.add(fold);
        panel.add(label2);
        addListener();
        setDefaultText();
        this.add(panel);
        this.setBounds(300, 100, 800, 800);
        this.setPreferredSize(new Dimension(800, 600));
        this.pack();
        this.setVisible(true);
    }
    public void setDefaultText() throws IOException {
        Path path = Paths.get("DefaultConfig.txt");
        Scanner scanner = new Scanner(path);
        while(scanner.hasNextLine()) {
            String line=scanner.nextLine();
            String type=line.split("=")[0];
            if (type.equals("DefaultCons"))
            {
                jTextField.setText(line.substring(12));
            }
            else if (type.equals("DefaultConnector"))
            {
                connector.setText(line.substring(17));
            }
            else if (type.equals("DefaultTroopNameType"))
            {
                trooptype.setText(line.substring(21));
            }

        }
    }
    public void addListener() {
        CommonMethod m =new CommonMethod();
        okBut.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        dialog.setVisible(false);
                        super.mouseClicked(e);
                    }
                }
        );
        jTextField.addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        int keyCode = e.getKeyCode();
                        if (keyCode == KeyEvent.VK_UP) {
                            fold.requestFocusInWindow();
                        } else if (keyCode == KeyEvent.VK_DOWN) {
                            troops.requestFocusInWindow();
                        }
                        super.keyReleased(e);
                    }
                }
        );
        fold.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_DOWN) {
                                        jTextField.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
        button2.addMouseListener(new MouseAdapter() {
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
                    mapList list=m.readMapList(fold.getText());
                    if (ran.isSelected()) writeRandomTaskForces(list);
                    else writeNormalTaskForces(list);
                    setDefaultConfig("DefaultConfig.txt");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        m.fileChoose(open, chooser, fold);
    }

    void setDefaultConfig(String filename) throws IOException {
        PrintWriter pw=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        int t1=0,t2=0,t3=0,t4=0,title=0;
        File file=new File(filename);
        String line=System.getProperty("line.separator");//平台换行!
        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                for (String str = br.readLine(); str != null; str = br.readLine())
                {
                    String type=str.split("=")[0];
                    if(type.equals("[addTaskForces]")) title++;
                    else if (type.equals("DefaultCons"))
                    {
                        str=type+"="+jTextField.getText();
                        t1++;
                    }
                    else if (type.equals("DefaultConnector"))
                    {
                        str=type+"="+connector.getText();
                        t2++;
                    }
                    else if (type.equals("DefaultTroopNameType"))
                    {
                        str=type+"="+trooptype.getText();
                        t3++;
                    }
                    else if (type.equals("DefaultMapFile"))
                    {
                        str=type+"="+fold.getText();
                        t3++;
                    }
                    buff.append(str+line);
                }
                if (t4==0)buff.append("DefaultMapFile="+fold.getText()+"\n\n");
                if (title==0) buff.append("[addTaskForces]\n");
                if (t1==0)buff.append("DefaultCons="+jTextField.getText()+"\n");
                if (t2==0)buff.append("DefaultConnector="+connector.getText()+"\n");
                if (t3==0)buff.append("DefaultTroopNameType="+trooptype.getText()+"\n");
                buff.append("\n");
                pw = new PrintWriter(new FileWriter(file), true);
                pw.println(buff);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null)
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (pw != null) {
                    pw.close();
                }
            }
        }
        else
        {
            buff.append("DefaultMapFile="+fold.getText()+"\n\n");
            buff.append("[addTaskForces]\n");
            buff.append("DefaultCons=");
            buff.append(jTextField.getText()+"\n");
            buff.append("DefaultConnector="+connector.getText()+"\n");
            buff.append("DefaultTroopNameType="+trooptype.getText()+"\n\n");
            pw = new PrintWriter(new FileWriter(file), true);
            pw.println(buff);
        }
    }
    void writeNormalTaskForces(mapList list) {
        String fileName = fold.getText();
        File file = new File(fileName);
        int i,j=list.getMaxtaskForce()+1,t=0;
        String tasktxt="";
        String trooptxt="";
        int code=Integer.parseInt(list.maxnormalcode.substring(1))+1,num=1;
        if (list.getMaxtaskForce()<0) tasktxt="[TaskForces]\n";
        String connetor=connector.getText();
        String unitcons,nametext;
        String trooptext=troops.getText();
        Scanner sc=new Scanner(trooptext);
        try {
            while (sc.hasNextLine())
            {
                String jt=sc.nextLine();
                if(!jt.contains(",")) break;
                unitcons="";
                nametext=trooptype.getText();
                String s[]=jt.split(",");
                trooptxt+="[0"+Integer.toString(code)+"]\n";
                t=0;
                for(i=0;i<s.length;i=i+2)
                {
                    trooptxt+=Integer.toString(i/2)+"="+s[i+1]+","+s[i]+"\n";
                    if (t>0) unitcons+=connetor;
                    unitcons+=jTextField.getText().replaceAll("%unitcode%",s[i]).replaceAll("%unitnum%",s[i+1]);
                    t++;
                }
                tasktxt+=Integer.toString(j)+"=0"+Integer.toString(code)+"\n";
                nametext=nametext.replaceAll("%unitcons%",unitcons).replaceAll("%num%",Integer.toString(num));
                trooptxt+="Name=";
                trooptxt+=nametext;
                trooptxt+="\n";
                trooptxt+="Group=-1";
                trooptxt+="\n\n";
                num++;
                code++;
                j++;
            }
            printTaskForces(list,file,trooptxt,tasktxt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void writeRandomTaskForces(mapList list) {
        String fileName = fold.getText();
        File file = new File(fileName);
        int i,j=list.getMaxtaskForce()+1,t=0;
        String tasktxt="";
        String trooptxt="";
        int code=Integer.parseInt(list.maxnormalcode.substring(1))+1,num=1;
        if (list.getMaxtaskForce()<0) tasktxt="[TaskForces]\n";
        String connetor=connector.getText();
        String unitcons,nametext;
        int k=0,teamnum,randomnum1,randomnum2,rnum=Integer.parseInt(rannum.getText()),typenum=rantype.getText().split(",").length;
        String tt=rantop.getText();
        int ranloadnum=1;
        String[] ranl=ranload.getText().split(",");
        if(ranl.length>1) ranloadnum=Integer.parseInt(ranl[1]);
        String ranload=ranl[0];
        String jttext;
        int top,down;
        if(tt.contains(","))
        {
            top=Integer.parseInt(tt.split(",")[1]);
            down=Integer.parseInt(tt.split(",")[0]);
        }
        else
        {
            top=Integer.parseInt(tt);
            down=1;
        }
        String[] types=rantype.getText().split(",");
        java.util.List<String> jtlist=new ArrayList<String>();
        Random random=new Random();
        String jtext;
        int typeuse=0;
        try {
            while (k<rnum) {
                jttext="";
                unitcons="";
                nametext=trooptype.getText();
                teamnum=0;typeuse=0;
                if(isBig(ranload))
                {
                    jttext+=ranload+","+String.valueOf(ranloadnum)+",";
                }
                int trynum=0;
                while(teamnum<top&&typeuse<typenum)
                {
                    randomnum1=random.nextInt(top)+1;
                    randomnum2=random.nextInt(typenum);
                    System.out.println(types[randomnum2]+","+randomnum1);
                    if(jttext.contains(types[randomnum2])) continue;
                    if(teamnum+randomnum1>=top&&isContain(jtlist,jttext))
                    {
                        trynum++;
                        if(trynum>=10)
                        {
                            jttext="";
                            if(isBig(ranload))
                            {
                                jttext+=ranload+","+String.valueOf(ranloadnum)+",";
                            }
                            trynum=0;
                            typeuse=0;
                            teamnum=0;
                        }
                        continue;
                    }
                    if (teamnum+randomnum1>top) {
                        if(teamnum<down)
                        {
                            continue;
                        }
                        else {
                            jtlist.add(jttext);
                            break;
                        }
                    }
                    else if (teamnum+randomnum1==top) {
                        if(teamnum>0) jttext+=",";
                        String ltext=jttext+types[randomnum2]+","+String.valueOf(randomnum1);
                        if(jtlist.contains(ltext))
                        {
                            continue;
                        }
                        else jttext+=types[randomnum2]+","+String.valueOf(randomnum1);
                        typeuse++;
                        teamnum=teamnum+randomnum1;
                        jtlist.add(jttext);
                    }
                    else
                    {
                        if(teamnum>0) jttext+=",";
                        jttext+=types[randomnum2]+","+String.valueOf(randomnum1);
                        teamnum=teamnum+randomnum1;
                        typeuse++;
                    }
                }
                String s[]=jttext.split(",");
                trooptxt+="[0"+Integer.toString(code)+"]\n";
                t=0;
                for(i=0;i<s.length;i=i+2)
                {
                    trooptxt+=Integer.toString(i/2)+"="+s[i+1]+","+s[i]+"\n";
                    if (t>0) unitcons+=connetor;
                    unitcons+=jTextField.getText().replaceAll("%unitcode%",s[i]).replaceAll("%unitnum%",s[i+1]);
                    t++;
                }
                tasktxt+=Integer.toString(j)+"=0"+Integer.toString(code)+"\n";
                nametext=nametext.replaceAll("%unitcons%",unitcons).replaceAll("%num%",Integer.toString(num));
                trooptxt+="Name=";
                trooptxt+=nametext;
                trooptxt+="\n";
                trooptxt+="Group=-1";
                trooptxt+="\n\n";
                num++;
                code++;
                j++;
                k++;
            }
            System.out.println(trooptxt);
            System.out.println(tasktxt);
            printTaskForces(list,file,trooptxt,tasktxt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void printTaskForces(mapList list,File file,String trooptext,String tasktext) throws IOException {
        BufferedReader br=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        int i=1;
        try {
            br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
            for(String str=br.readLine();str!=null;str=br.readLine()) {
                if(i==list.getRow_TaskLineList())
                    str=str.replaceAll(str,tasktext);
                buff.append(str+line);
                i++;
            }
            buff.append(trooptext);
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
    public boolean isBig(String s) {
        if(s.length()==0) return false;
        return s.matches("^[A-Z]*");
    }
    public boolean isContain(java.util.List<String> list,String s)
    {
        if (list.contains(s)) return true;
        String[] a=s.split(",");
        String[] c = new String[a.length/2],d = new String[a.length/2];
        for(int i=0;i<a.length-1;i=i+2)
        {
            c[i/2]=a[i]+","+a[i+1];
        }
        java.util.List<String> list3=Arrays.asList(c);
        Collections.sort(list3);
        for(String str:list)
        {
            String[] b=str.split(",");
            if (b.length!=a.length) continue;
            for(int i=0;i<a.length-1;i=i+2)
            {
                d[i/2]=b[i]+","+b[i+1];
            }
            java.util.List<String> list1= Arrays.asList(d);
            Collections.sort(list1);
            if(list1.equals(list3)) return true;
        }
        return false;
    }
}
