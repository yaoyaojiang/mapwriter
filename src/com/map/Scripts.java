package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Scripts {
    public static void main(String[] args) throws InterruptedException, IOException {
        new NewScript();
    }
}
class NewScript extends JFrame {
    private JTextField fold = new JTextField("在此填写map文件的目录地址");
    private JTextField scriptname = new JTextField("在此填写脚本命名规则");
    private JTextField createnum = new JTextField("生成个数");
    private JFileChooser chooser = new JFileChooser("D:\\project\\mapreader");
    private JButton open = new JButton("浏览");
    private JDialog dialog=new JDialog();
    private JButton okBut = new JButton("确定");
    private JButton create = new JButton("生成");
    private JLabel success = new JLabel("修改成功");
    private JLabel direction=new JLabel("");
    private JTextArea scripttype=new JTextArea("在此填写脚本内容及规则");
    private JPanel panel = new JPanel();
    private Font font = new Font("宋体", Font.PLAIN, 13);
    private CommonMethod m=new CommonMethod();

    public NewScript() throws InterruptedException, IOException {
        panel.setLayout(null);
        dialog.setBounds(400, 200, 350, 150);//设置弹出对话框的位置和大小
        dialog.setLayout(new FlowLayout());//设置弹出对话框的布局为流式布局
        dialog.add(success);
        dialog.add(okBut);
        dialog.setVisible(false);
        okBut.setBounds(100, 120, 100, 50);
        create.setBounds(250, 340, 75, 30);
        fold.setBounds(130, 40, 220, 30);
        fold.dispatchEvent(new FocusEvent(fold, FocusEvent.FOCUS_GAINED, true));
        fold.setName("fold");
        scriptname.setBounds(130,80,330,30);
        scriptname.dispatchEvent(new FocusEvent(scriptname, FocusEvent.FOCUS_GAINED, true));
        scriptname.setName("scriptname");
        createnum.setBounds(470,80,70,30);
        createnum.dispatchEvent(new FocusEvent(createnum, FocusEvent.FOCUS_GAINED, true));
        createnum.setName("createnum");
        scripttype.setBounds(130,120,320,200);
        scripttype.dispatchEvent(new FocusEvent(scripttype, FocusEvent.FOCUS_GAINED, true));
        scripttype.setName("scripttype");
        open.setBounds(380, 40, 75, 30);
        direction.setBounds(560, 70, 200, 460);
        direction.setFont(font);
        direction.setForeground(Color.RED);
        m.JlabelSetText(direction,"脚本名称使用%num%表示递增数字。在%num%后面添加{递增初始数字，递增额度}来表示其递增的具体设定。使用%taskforce%表示使用特遣部队名称代替。在%taskforce%后面添加{初始特遣部队编号,是否递增（0为否1为是，不填默认0）}，来将特遣部队名称命名到脚本名中。如Rush-%taskforce%{01000087,1}%num%{10,2}-%num%{11,2}。脚本内容为“序号=行为号，参数”，序号必须是从0开始以1为递增单位的整数，表示行为的顺序。行为号代表脚本行为的序号，仅有参数可递增。因此，一条可递增的内容是：0=39,%num%{3,2}");
        panel.add(direction);
        panel.add(fold);
        panel.add(scriptname);
        panel.add(createnum);
        panel.add(open);
        panel.add(create);
        panel.add(scripttype);
        addListener();
        setDefaultText();
        this.add(panel);
        this.setBounds(300, 100, 800, 800);
        this.setPreferredSize(new Dimension(800, 600));
        this.pack();
        this.setVisible(true);
    }
    public void addListener()
    {
        m.fileChoose(open, chooser, fold);
        keyUpOrDown(fold,scriptname,scripttype,createnum);
        dialogExit(okBut,dialog);
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
                    java.util.List<Script> scripts=list.getScripts();
                    writeNormalScript(list);
                    setDefaultConfig("DefaultConfig.txt");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    private void writeNormalScript(mapList list) {
        String fileName = fold.getText();
        File file = new File(fileName);
        int i,j=0,t,lsize=list.getMaxScriptType();
        String scriptlistText="";
        String scriptText="";
        int code=Integer.parseInt(list.maxnormalcode.substring(1))+1;
        String nametext;
        java.util.List<String> slist=new ArrayList<String>();
        if (lsize<1) scriptlistText="[ScriptTypes]\n";
        try {
            int num=Integer.parseInt(createnum.getText());
            nametext=scriptname.getText();
            Scanner sc=new Scanner(scripttype.getText());
            while (sc.hasNextLine())
            {
                slist.add(sc.nextLine());
            }
            for(t=1;t<=num;t++)
            {
                scriptlistText+=Integer.toString(lsize+t)+"=0"+Integer.toString(code)+"\n";
                scriptText+="[0"+code+"]\n";
                for(int n=0;n<slist.size();n++)
                {
                    String s=slist.get(n);
                    String idnum=s.split(",")[1];
                    if (idnum.contains("%num%"))
                    {
                        int base=Integer.parseInt(idnum.split("\\{")[1]),interval=Integer.parseInt(s.split(",")[2].split("}")[0]);
                        scriptText+=s.split(",")[0]+","+Integer.toString(base+(t-1)*interval)+"\n";
                    }
                    else
                    {
                        scriptText+=s+"\n";
                    }
                }
                scriptText+="Name=";
                if (nametext.contains("%taskforce%{")||nametext.contains("%num%{"))
                {
                    char[] a=nametext.toCharArray();
                    j=1;
                    int k1=-1,k2=-1,comma=-1,b1=-1,b2=-1,tnum=0,nnum=0;
                    String replacetype="none";
                    //以字符数组形式遍历名称字符串，以记录特殊符号的位置，以此作为substring的根据
                    for(i=0;i<a.length;i++)
                    {
                        if (a[i]=='%')
                        {
                            if (j%2==0) k2=i;
                            else k1=i;
                            //k2-k1==10表示为taskforce，否则为num，这是由英文单词长度决定
                            j++;
                            if(k2-k1==10)
                            {
                                replacetype="taskforce";
                                tnum++;
                            }
                            else if (k2-k1==4)
                            {
                                replacetype = "num";
                                nnum++;
                            }
                            //System.out.println("k1="+k1+",k2="+k2+",type="+replacetype);
                        }
                        else if (a[i]=='{')
                        {
                            if (replacetype.equals("taskforce")||replacetype.equals("num"))
                            {
                                b1=i;
                            }
                        }
                        else if (a[i]=='}')
                        {
                            if (replacetype.equals("taskforce"))
                            {
                                b2=i;
                                String tcode=nametext.substring(b1+1,comma);
                                //System.out.println(nametext+"|"+tcode);
                                int identity=Integer.parseInt(nametext.substring(comma+1,b2));
                                java.util.List<String> tlist=new ArrayList<>(Arrays.asList(list.getTaskForces()));
                                int tcodepos=-1;
                                for(int k=0;k<tlist.size();k++)
                                {
                                    if (tcode.equals(tlist.get(k))) {
                                        //System.out.println(tcode+",k="+k+",j="+j);
                                        tcodepos = k+identity*(t-1);
                                        if (tcodepos>=tlist.size()) tcodepos=tlist.size()-1;
                                        tcode=tlist.get(tcodepos);
                                        //System.out.println(tcode);
                                        break;
                                    }
                                }
                                //System.out.println(tlist);
                                String tname="";
                                for(Map<String,String> m:list.getTaskForcesName())
                                {
                                    if (m.get(tcode)!=null)
                                    {
                                        tname=m.get(tcode);
                                        break;
                                    }
                                }
                                scriptText+=tname;
                                k1=-1;k2=-1;b1=-1;b2=-1;comma=-1;replacetype="none";
                            }
                            else if (replacetype.equals("num"))
                            {
                                b2=i;
                                int startnum=Integer.parseInt(nametext.substring(b1+1,comma));
                                int identity=Integer.parseInt(nametext.substring(comma+1,b2));
                                scriptText+=Integer.toString(startnum+identity*(t-1));
                                k1=-1;k2=-1;b1=-1;b2=-1;comma=-1;replacetype="none";
                            }
                        }
                        else if (a[i]==','&&b1>=0)
                        {
                           if (replacetype.equals("taskforce")||replacetype.equals("num")) {
                               comma = i;
                           }
                        }
                        else if (k1<0&&k2<0&&b1<0&&b2<0&&comma<0)
                        {
                            scriptText+=a[i];
                        }
                    }
                }
                else scriptText+=nametext;
                scriptText+="\n\n";
                code++;

            }
            System.out.println(scriptlistText);
            printScriptTypes(list,file,scriptText,scriptlistText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void printScriptTypes(mapList list,File file,String scriptText,String scriptlistText) throws IOException {
        BufferedReader br=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        //InputStream fis = new FileInputStream(file);
        int row=0,written=0;
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));

        try {
            String  str=br.readLine();
            while((str=br.readLine())!=null) {
                if (str.equals("[ScriptTypes]")) row=1;
                if (row>0&&!str.equals("[ScriptTypes]")&&!str.contains("="))
                {
                    str = str.replaceAll(str, scriptlistText);
                    row=0;
                    written++;
                }
                buff.append(str+line);
            }
            if(written==0)  buff.append(scriptlistText+"\n");
            buff.append(scriptText);
            OutputStreamWriter pw= new OutputStreamWriter(new FileOutputStream(file),"gb2312");
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
    void setDefaultConfig(String filename) throws IOException {
        PrintWriter pw=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        int i=0,t=0;
        File file=new File(filename);
        String line=System.getProperty("line.separator");//平台换行!
        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                for (String str = br.readLine(); str != null; str = br.readLine())
                {
                    String type=str.split("=")[0];
                    String s="";
                    if (type.equals("DefaultScriptNameType"))
                    {
                        s=type+"="+scriptname.getText()+"\n";
                        t++;
                        buff.append(s);
                    }
                    else if (type.equals("DefaultScriptCreateNum"))
                    {
                        s=type+"="+createnum.getText()+"\n";
                        t++;
                        buff.append(s);
                    }
                    else
                    {
                        buff.append(str);
                    }
                    i++;
                }
                buff.append("\n");
                if (t==0) {
                    writeDefaultScriptConfig(file,buff,pw);
                }
                else {
                    buff.append(line);
                    pw = new PrintWriter(new FileWriter(file), true);
                    pw.println(buff);
                }
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
            writeDefaultScriptConfig(file,buff,pw);
        }
    }
    static void keyUpOrDown(JTextField fold,JTextField scriptname,JTextArea scripttype,JTextField createnum)
    {
        fold.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_DOWN) {
                                        scriptname.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
        scriptname.addKeyListener(new KeyAdapter() {
                                      @Override
                                      public void keyReleased(KeyEvent e) {
                                          int keyCode = e.getKeyCode();
                                          if (keyCode == KeyEvent.VK_DOWN) {
                                              scripttype.requestFocusInWindow();
                                          }
                                          else if (keyCode == KeyEvent.VK_UP) {
                                              fold.requestFocusInWindow();
                                          }
                                          super.keyReleased(e);
                                      }
                                  }
        );
        scripttype.addKeyListener(new KeyAdapter() {
                                      @Override
                                      public void keyReleased(KeyEvent e) {
                                          int keyCode = e.getKeyCode();
                                          if (keyCode == KeyEvent.VK_UP) {
                                              scriptname.requestFocusInWindow();
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
                                             scripttype.requestFocusInWindow();
                                         }
                                         super.keyReleased(e);
                                     }
                                 }
        );
    }
    static void dialogExit(JButton okBut, JDialog dialog)
    {
        okBut.addMouseListener(
            new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dialog.setVisible(false);
                    super.mouseClicked(e);
                }
            }
    );

    }
    void writeDefaultScriptConfig(File file,StringBuffer buff,PrintWriter pw) throws IOException {
        String line=System.getProperty("line.separator");
        buff.append("[addScriptTypes]\n");
        buff.append("DefaultScriptNameType=");
        buff.append(scriptname.getText()+"\n");
        buff.append("DefaultScriptCreateNum="+createnum.getText()+"\n");
        buff.append(line);
        pw = new PrintWriter(new FileWriter(file), true);
        pw.println(buff);
    }
    public void setDefaultText() throws IOException {
        Path path = Paths.get("DefaultConfig.txt");
        Scanner scanner = new Scanner(path);
        while(scanner.hasNextLine()) {
            String line=scanner.nextLine();
            String type=line.split("=")[0];
            if (type.equals("DefaultScriptNameType"))
            {
                scriptname.setText(line.substring(22));
            }
            else if (type.equals("DefaultScriptCreateNum"))
            {
                createnum.setText(line.substring(23));
            }
        }
    }
}
