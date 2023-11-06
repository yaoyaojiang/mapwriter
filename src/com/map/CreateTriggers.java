package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

class NewTriggers {
    public static void main(String[] args) throws InterruptedException, IOException {
        new CreateTriggers();
    }
}
class CreateTriggers extends JFrame {
    private JTextField fold = new JTextField("在此填写map文件的目录地址");
    private JFileChooser chooser = new JFileChooser("D:\\project\\mapreader");
    private JButton open = new JButton("浏览");
    private JTextField name = new JTextField("在此填写触发名及命名规则");
    private JTextField owner = new JTextField("在此填写所属方的数字代码");
    private JTextField createnum = new JTextField("1");
    private final String[] items=new String[]{"一次","集体","重复"};
    private JComboBox repeat = new JComboBox(items);
    private JTextField bindingTriggers = new JTextField("在此填写关联触发代码及递增规则");
    private JCheckBox easy = new JCheckBox("简单");
    private JCheckBox normal = new JCheckBox("中等");
    private JCheckBox hard = new JCheckBox("困难");
    private JTextField easyper = new JTextField("简单概率");
    private JTextField normalper = new JTextField("中等概率");
    private JTextField hardper = new JTextField("困难概率");
    private JCheckBox ban = new JCheckBox("禁用");
    private JDialog dialog=new JDialog();
    private JPanel panel = new JPanel();
    private CommonMethod m=new CommonMethod();
    private JLabel direction=new JLabel("");
    private JLabel ge=new JLabel("个");
    private JButton ok = new JButton("确定");
    private JButton create = new JButton("新增");
    private JButton okBut = new JButton("确定");
    private JButton cancel = new JButton("取消");
    private JLabel confirm = new JLabel("是否要新增触发？");
    private JLabel success = new JLabel("新增成功");
    private Font font = new Font("宋体", Font.PLAIN, 13);

    public CreateTriggers() throws InterruptedException, IOException{
        dialog.setBounds(400, 200, 350, 150);//设置弹出对话框的位置和大小
        dialog.setLayout(new FlowLayout());//设置弹出对话框的布局为流式布局
        dialog.add(confirm);
        dialog.add(success);
        dialog.add(ok);
        dialog.add(okBut);
        okBut.setVisible(false);
        success.setVisible(false);
        dialog.add(cancel);
        fold.setBounds(70, 80, 220, 30);
        fold.dispatchEvent(new FocusEvent(fold, FocusEvent.FOCUS_GAINED, true));
        fold.setName("fold");
        open.setBounds(320, 80, 60, 30);
        open.dispatchEvent(new FocusEvent(open, FocusEvent.FOCUS_GAINED, true));
        open.setName("open");
        name.setBounds(70, 120, 300, 30);
        name.dispatchEvent(new FocusEvent(name, FocusEvent.FOCUS_GAINED, true));
        name.setName("name");
        createnum.setBounds(210, 320, 40, 30);
        createnum.dispatchEvent(new FocusEvent(createnum, FocusEvent.FOCUS_GAINED, true));
        createnum.setName("createnum");
        ge.setBounds(255, 320, 30, 30);
        ge.dispatchEvent(new FocusEvent(ge, FocusEvent.FOCUS_GAINED, true));
        ge.setName("ge");
        owner.setBounds(70, 160, 120, 30);
        owner.dispatchEvent(new FocusEvent(owner, FocusEvent.FOCUS_GAINED, true));
        owner.setName("owner");
        create.setBounds(70, 320, 120, 30);
        create.dispatchEvent(new FocusEvent(create, FocusEvent.FOCUS_GAINED, true));
        create.setName("create");
        repeat.setBounds(270, 160, 100, 30);
        repeat.dispatchEvent(new FocusEvent(repeat, FocusEvent.FOCUS_GAINED, true));
        repeat.setName("repeat");
        direction.setBounds(510, 50, 260, 520);
        direction.setFont(font);
        direction.setForeground(Color.RED);
        bindingTriggers.setBounds(70, 200, 300, 30);
        bindingTriggers.dispatchEvent(new FocusEvent(bindingTriggers, FocusEvent.FOCUS_GAINED, true));
        bindingTriggers.setName("bindingTriggers");
        easy.setBounds(70, 240, 70, 30);
        easy.dispatchEvent(new FocusEvent(easy, FocusEvent.FOCUS_GAINED, true));
        easy.setName("easy");
        easy.doClick();
        ban.setBounds(310, 240, 70, 30);
        ban.dispatchEvent(new FocusEvent(ban, FocusEvent.FOCUS_GAINED, true));
        ban.setName("ban");
        normal.setBounds(150, 240, 70, 30);
        normal.dispatchEvent(new FocusEvent(normal, FocusEvent.FOCUS_GAINED, true));
        normal.setName("normal");
        normal.doClick();
        hard.setBounds(230, 240, 70, 30);
        hard.dispatchEvent(new FocusEvent(hard, FocusEvent.FOCUS_GAINED, true));
        hard.setName("hard");
        hard.doClick();
        easyper.setBounds(70, 280, 70, 30);
        easyper.dispatchEvent(new FocusEvent(easyper, FocusEvent.FOCUS_GAINED, true));
        easyper.setName("easyper");
        normalper.setBounds(150, 280, 70, 30);
        normalper.dispatchEvent(new FocusEvent(normalper, FocusEvent.FOCUS_GAINED, true));
        normalper.setName("normalper");
        hardper.setBounds(230, 280, 70, 30);
        hardper.dispatchEvent(new FocusEvent(hardper, FocusEvent.FOCUS_GAINED, true));
        hardper.setName("hardper");
        m.JlabelSetText(direction,"批量新增触发时，仅能递增触发名和关联触发代码。触发命名可以将%teamtype%作为一个命名因子，其递增规则是在%teamtype%后增加{小队编码,递增（减）额度},如%teamtype%{01000010,1}。%taskforce%,%script%的递增规则和%teamtype%相同。需要注意的是，%difficulty%是一个不可递增的命名因子，它表示当前触发在哪个难度下有效。如果有效的难度不止一个，则该因子会将所有有效难度以“、”连接，比如“简单、困难”。难度下面的概率表示批量触发中各个触发会被勾选的概率（填写0-1），默认为1，若填写该数值且不为1，则按照概率勾选此难度。考虑到一个触发至少必须填写一个有效的难度，若三个难度均不打勾时，实际概率会高于指定概率。");
        panel.add(repeat);
        panel.add(direction);
        panel.setLayout(null);
        panel.add(createnum);
        panel.add(owner);
        panel.add(ge);
        panel.add(ban);
        panel.add(create);
        panel.add(name);
        panel.add(fold);
        panel.add(open);
        panel.add(easy);
        panel.add(normal);
        panel.add(hard);
        panel.add(easyper);
        panel.add(normalper);
        panel.add(hardper);
        panel.add(bindingTriggers);
        addListener();
        this.add(panel);
        this.setBounds(300, 100, 800, 800);
        this.setPreferredSize(new Dimension(800, 600));
        this.pack();
        this.setVisible(true);
    }
    public void addListener() {
        m.fileChoose(open, chooser, fold);
        m.dialogExit(okBut, dialog);
        m.dialogExit(cancel, dialog);
        keyUpOrDown(fold,name,owner,bindingTriggers,easy,normal,hard);
        ok.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String fileName = fold.getText();
                int i,mode;
                File file = new File(fileName);
                try {
                    CommonMethod m=new CommonMethod();
                    mapList list=m.readMapList_Name(fold.getText());
                    writeTriggers(list);

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
    }

    private void writeTriggers(mapList list) throws Exception {
        int i;
        String triggerstext="";
        String tagstext="";
        String nametext="";
        String fileName = fold.getText();
        File file = new File(fileName);
        if (list.getTriggers().size()<1) triggerstext="[Triggers]\n";
        if (list.getTags().size()<1) tagstext="[Tags]\n";
        int code=Integer.parseInt(list.maxnormalcode.substring(1))+1;
        java.util.List<String> slist=new ArrayList<String>();
        java.util.List<String> triggerlist=list.getTriggers();
        java.util.List<String> taglist=list.getTags();
        String[] country=list.getCountries();
        List<Map<String, String>> scriptlist=list.getScriptTypesName();
        List<Map<String, String>> trooplist=list.getTaskForcesName();
        List<Map<String, String>> teamlist=list.getTeamTypesName();
        String nametype=name.getText();
        int ownernum=Integer.parseInt(owner.getText());
        int num=Integer.parseInt(createnum.getText());
        String bindcode=bindingTriggers.getText();
        for(i=1;i<=num;i++)
        {
            nametext=nametype;
            triggerstext+="0"+code+"="+country[ownernum]+",";
            tagstext+="0"+String.valueOf(code+1)+"=";
            if (bindcode.contains("{"))
            {
                int bcode=Integer.parseInt(bindcode.substring(2).split(",")[0]);
                int bnum=Integer.parseInt(bindcode.split(",")[1].split("}")[0]);
                triggerstext+="0"+String.valueOf(bcode+bnum*(i-1))+",";
            }
            else if (m.isNormalCode(bindcode))
            {
                triggerstext+=bindcode+",";
            }
            else triggerstext+="<none>,";
            String difftext="";
            boolean selected1,selected2,selected3;
            if(m.isDecimal(easyper.getText()))
            {
                Double per=Double.parseDouble(easyper.getText());
                Random random=new Random();
                Double ran=random.nextDouble();
                if(ran<per)
                {
                    difftext+="1,";
                    selected1=true;
                }
                else
                {
                    difftext+="0,";
                    selected1=false;
                }
            }
            else if (easy.isSelected())
            {
                difftext+="1,";
                selected1=true;
            }
            else
            {
                difftext+="0,";
                selected1=false;
            }

            if(m.isDecimal(normalper.getText()))
            {
                Double per=Double.parseDouble(normalper.getText());
                Random random=new Random();
                Double ran=random.nextDouble();
                if(ran<per)
                {
                    difftext+="1,";
                    selected2=true;
                }
                else
                {
                    difftext+="0,";
                    selected2=false;
                }
            }
            else if (normal.isSelected())
            {
                difftext+="1,";
                selected2=true;
            }
            else
            {
                difftext+="0,";
                selected2=false;
            }
            if(m.isDecimal(hardper.getText()))
            {
                Double per=Double.parseDouble(hardper.getText());
                Random random=new Random();
                Double ran=random.nextDouble();
                if(ran<per)
                {
                    difftext+="1,0\n";
                    selected3=true;
                }
                else
                {
                    difftext+="0,0\n";
                    selected3=false;
                }
            }
            else if (hard.isSelected())
            {
                difftext+="1,0\n";
                selected3=true;
            }
            else
            {
                difftext+="0,0\n";
                selected3=false;
            }
            if(!selected1&&!selected2&&!selected3)
            {
                Double per1=Double.parseDouble(easyper.getText());
                Double per2=Double.parseDouble(normalper.getText());
                Double per3=Double.parseDouble(hardper.getText());
                Double totalper=per1+per2+per3;
                Random random=new Random();
                Double rannum=random.nextDouble();
                if(rannum<(per1/totalper))
                {
                    selected1=true;
                    difftext="1,0,0,0\n";
                }
                else if (rannum>=(per1/totalper)&&rannum<(per2+(per1/totalper)))
                {
                    selected2=true;
                    difftext="1,0,0,0\n";
                }
                else
                {
                    selected3=true;
                    difftext="0,0,1,0\n";
                }
            }
            String diff="";
            if (selected1) diff+="简单";
            if (selected2)
            {
                if (selected1) diff+="、";
                diff+="中等";
            }
            if (selected3)
            {
                if (selected1||selected2) diff+="、";
                diff+="困难";
            }
            nametext=nametext.replaceAll("%owner%",country[ownernum]);
            nametext=nametext.replaceAll("%difficulty%",diff);
            String nametext2="";
            char[] a=nametext.toCharArray();
            int j=0,k1=-1,k2=-1,pn=0,b1=-1,b2=-1,comma=-1;
            String replacetype = null;
            for(j=0;j<a.length;j++)
            {
                if (a[j]=='%') {
                    if (pn%2==0) k1=j;
                    else k2=j;
                    //k2-k1==10表示为taskforce，否则为num，这是由英文单词长度决定
                    pn++;
                    if(k2-k1==10)
                    {
                        replacetype="taskforce";
                    }
                    else if (k2-k1==4)
                    {
                        replacetype = "num";
                    }
                    else if (k2-k1==9)
                    {
                        replacetype = "teamtype";
                    }
                    else if (k2-k1==7)
                    {
                        replacetype = "script";
                    }
                }
                else if (a[j]=='{')
                {
                    b1=j;
                }
                else if (a[j]==','&&b1>=0)
                {
                    comma = j;
                }
                else if (a[j]=='}')
                {
                    if (replacetype.equals("num"))
                    {
                        b2=j;
                        int startnum=Integer.parseInt(nametext.substring(b1+1,comma));
                        int identity=Integer.parseInt(nametext.substring(comma+1,b2));
                        nametext2+=Integer.toString(startnum+identity*(i-1));
                        k1=-1;k2=-1;b1=-1;b2=-1;comma=-1;replacetype="none";
                    }
                    else if (replacetype.equals("taskforce")||replacetype.equals("teamtype")||replacetype.equals("script"))
                    {
                        b2=j;
                        String tcode=nametext.substring(b1+1,comma);
                        //System.out.println(nametext+"|"+tcode);
                        int identity=Integer.parseInt(nametext.substring(comma+1,b2));
                        java.util.List<String> tlist;
                        List<Map<String,String>> mlist;
                        if (replacetype.equals("taskforce")) {
                            tlist = new ArrayList<>(Arrays.asList(list.getTaskForces()));
                            mlist=list.getTaskForcesName();
                        }
                        else if (replacetype.equals("teamtype")){
                            tlist = new ArrayList<>(Arrays.asList(list.getTeamTypes()));
                            mlist=list.getTeamTypesName();
                        }
                        else{
                            tlist = new ArrayList<>(Arrays.asList(list.getScriptTypes()));
                            mlist=list.getScriptTypesName();
                        }
                        int tcodepos=-1;
                        for(int k=0;k<tlist.size();k++)
                        {
                            if (tcode.equals(tlist.get(k))) {
                                //System.out.println(tcode+",k="+k+",j="+j);
                                tcodepos = k+identity*(i-1);
                                if (tcodepos>=tlist.size()) tcodepos=tlist.size()-1;
                                tcode=tlist.get(tcodepos);
                                //System.out.println(tcode);
                                break;
                            }
                        }
                        //System.out.println(tlist);
                        String tname="";

                        for(Map<String,String> m:mlist)
                        {
                            if (m.get(tcode)!=null)
                            {
                                tname=m.get(tcode);
                                break;
                            }
                        }
                        nametext2+=tname;
                        k1=-1;k2=-1;b1=-1;b2=-1;comma=-1;replacetype="none";
                    }
                }
                else if (k1<0&&k2<0&&b1<0&&b2<0&&comma<0)
                {
                    nametext2+=a[j];
                }
            }
            triggerstext+=nametext2+",";
            if (ban.isSelected()) triggerstext+="1,";
            else triggerstext+="0,";
            triggerstext+=difftext;
            if (String.valueOf(repeat.getSelectedItem()).equals("一次")) tagstext+="0,";
            else if (String.valueOf(repeat.getSelectedItem()).equals("集体")) tagstext+="1,";
            else tagstext+="2,";
            tagstext+=nametext2+" 1,";
            tagstext+="0"+code+"\n";
            code=code+2;
        }
        printNewTriggers(list,file,triggerstext,tagstext,num);
    }

    public void printNewTriggers(mapList list,File file,String triggerstext,String tagstext,int num) throws IOException {
        BufferedReader br=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        //InputStream fis = new FileInputStream(file);
        int i=1,row1=list.getRow_TriggerLineList(),row2=list.getRow_TagLineList();
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
        int tagrow=0,triggerrow=0;
        try {
            String  str;
            while((str=br.readLine())!=null) {
                if (str.equals("[Tags]")) tagrow=1;
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
                buff.append(str+line);
                i++;
            }
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
    static void keyUpOrDown(JTextField fold,JTextField name,JTextField owner,JTextField bindingTriggers,JCheckBox easy,JCheckBox normal,JCheckBox hard) {
        fold.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_DOWN) {
                                        name.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
        name.addKeyListener(new KeyAdapter() {
                                    @Override
                                    public void keyReleased(KeyEvent e) {
                                        int keyCode = e.getKeyCode();
                                        if (keyCode == KeyEvent.VK_DOWN) {
                                            owner.requestFocusInWindow();
                                        }
                                        else if (keyCode == KeyEvent.VK_UP) {
                                            fold.requestFocusInWindow();
                                        }
                                        super.keyReleased(e);
                                    }
                                }
        );
        owner.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_DOWN) {
                                        bindingTriggers.requestFocusInWindow();
                                    }
                                    else if (keyCode == KeyEvent.VK_UP) {
                                        name.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
        bindingTriggers.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_DOWN) {
                                        normal.requestFocusInWindow();
                                    }
                                    else if (keyCode == KeyEvent.VK_UP) {
                                        owner.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
        easy.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_UP) {
                                        bindingTriggers.requestFocusInWindow();
                                    }
                                    else if (keyCode == KeyEvent.VK_RIGHT) {
                                        normal.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
        normal.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_UP) {
                                        bindingTriggers.requestFocusInWindow();
                                    }
                                    else if (keyCode == KeyEvent.VK_LEFT) {
                                        easy.requestFocusInWindow();
                                    }
                                    else if (keyCode == KeyEvent.VK_RIGHT) {
                                        hard.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
        hard.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_UP) {
                                        bindingTriggers.requestFocusInWindow();
                                    }
                                    else if (keyCode == KeyEvent.VK_LEFT) {
                                        normal.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
    }
}
