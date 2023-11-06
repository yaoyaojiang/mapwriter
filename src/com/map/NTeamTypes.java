package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class NTeamTypes {
    public static void main(String[] args) throws InterruptedException, IOException {
        new TeamType();
    }
}

class TeamType extends JFrame {
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
    private JTextField teamname = new JTextField("在此填写小队命名规则");
    private JTextField createnum = new JTextField("在此填写生成小队的个数");
    private JCheckBox lv0 = new JCheckBox("等级0");
    private JCheckBox lv1 = new JCheckBox("等级1");
    private JCheckBox lv3 = new JCheckBox("等级3");
    private JTextField priority = new JTextField("优先级");
    private JTextField owner = new JTextField("所属方代号");
    private JTextField techlevel = new JTextField("科技等级");
    private JTextField most = new JTextField("最多");
    private JTextField group = new JTextField("分组");
    private JTextField waypoint = new JTextField("路径点");
    private JTextField waypoint2 = new JTextField("传送路径点");
    private final String[] items=new String[]{"随机","加入小队","放进部队回收站","放入生化反应炉","寻敌","没有行为"};
    private JComboBox controlaction = new JComboBox(items);
    private JTextField script = new JTextField("脚本代码");
    private JTextField troop = new JTextField("特遣部队代码");
    private JTextField tag = new JTextField("标签代码");
    private JCheckBox Loadable = new JCheckBox("可装载");
    private JCheckBox Droppod = new JCheckBox("空降部队");
    private JCheckBox Autocreate = new JCheckBox("自动建造");
    private JCheckBox Full = new JCheckBox("初始装载");
    private JCheckBox Whiner = new JCheckBox("哀叫效果");
    private JCheckBox Prebuild = new JCheckBox("预先建造");
    private JCheckBox AreTeamMembersRecruitable = new JCheckBox("小队成员可重组");
    private JCheckBox Annoyance = new JCheckBox("烦恼效果");
    private JCheckBox LooseRecruit = new JCheckBox("解散重组");;
    private JCheckBox Reinforce = new JCheckBox("成员自动补充");
    private JCheckBox TransportsReturnOnUnload = new JCheckBox("运载单位返回");
    private JCheckBox GuardSlower = new JCheckBox("防卫减缓");
    private JCheckBox Aggressive = new JCheckBox("侵略性的");
    private JCheckBox OnTransOnly= new JCheckBox("仅为传送");
    private JCheckBox IsBaseDefense= new JCheckBox("基地防御");;
    private JCheckBox Recruiter= new JCheckBox("征兵");
    private JCheckBox Suicide= new JCheckBox("自杀");
    private JCheckBox AvoidThreats= new JCheckBox("避开威胁");
    private JCheckBox OnlyTargetHouseEnemy= new JCheckBox("仅攻击敌方");
    public TeamType() throws InterruptedException, IOException {
        panel.setLayout(null);
        dialog.setBounds(400, 200, 350, 150);//设置弹出对话框的位置和大小
        dialog.setLayout(new FlowLayout());//设置弹出对话框的布局为流式布局
        dialog.add(success);
        dialog.add(okBut);
        fold.setBounds(70, 40, 220, 30);
        fold.dispatchEvent(new FocusEvent(fold, FocusEvent.FOCUS_GAINED, true));
        fold.setName("fold");
        Loadable.setBounds(70, 310, 100, 30);
        Loadable.dispatchEvent(new FocusEvent(Loadable, FocusEvent.FOCUS_GAINED, true));
        Loadable.setName("Loadable");
        panel.add(Loadable);
        Droppod.setBounds(180, 310, 100, 30);
        Droppod.dispatchEvent(new FocusEvent(Droppod, FocusEvent.FOCUS_GAINED, true));
        Droppod.setName("Droppod");
        panel.add(Droppod);
        Autocreate.setBounds(290, 310, 100, 30);
        Autocreate.dispatchEvent(new FocusEvent(Autocreate, FocusEvent.FOCUS_GAINED, true));
        Autocreate.setName("Autocreate");
        panel.add(Autocreate);
        Full.setBounds(70, 350, 100, 30);
        Full.dispatchEvent(new FocusEvent(Full, FocusEvent.FOCUS_GAINED, true));
        Full.setName("Full");
        panel.add(Full);
        Whiner.setBounds(180, 350, 100, 30);
        Whiner.dispatchEvent(new FocusEvent(Whiner, FocusEvent.FOCUS_GAINED, true));
        Whiner.setName("Whiner");
        panel.add(Whiner);
        Prebuild.setBounds(290, 350, 100, 30);
        Prebuild.dispatchEvent(new FocusEvent(Prebuild, FocusEvent.FOCUS_GAINED, true));
        Prebuild.setName("Prebuild");
        panel.add(Prebuild);
        AreTeamMembersRecruitable.setBounds(400, 350, 100, 30);
        AreTeamMembersRecruitable.dispatchEvent(new FocusEvent(AreTeamMembersRecruitable, FocusEvent.FOCUS_GAINED, true));
        AreTeamMembersRecruitable.setName("AreTeamMembersRecruitable");
        panel.add(AreTeamMembersRecruitable);
        Annoyance.setBounds(70, 390, 100, 30);
        Annoyance.dispatchEvent(new FocusEvent(Annoyance, FocusEvent.FOCUS_GAINED, true));
        Annoyance.setName("Annoyance");
        panel.add(Annoyance);
        LooseRecruit.setBounds(180, 390, 100, 30);
        LooseRecruit.dispatchEvent(new FocusEvent(LooseRecruit, FocusEvent.FOCUS_GAINED, true));
        LooseRecruit.setName("LooseRecruit");
        panel.add(LooseRecruit);
        Reinforce.setBounds(290, 390, 100, 30);
        Reinforce.dispatchEvent(new FocusEvent(Reinforce, FocusEvent.FOCUS_GAINED, true));
        Reinforce.setName("Reinforce");
        panel.add(Reinforce);
        TransportsReturnOnUnload.setBounds(400, 390, 100, 30);
        TransportsReturnOnUnload.dispatchEvent(new FocusEvent(TransportsReturnOnUnload, FocusEvent.FOCUS_GAINED, true));
        TransportsReturnOnUnload.setName("TransportsReturnOnUnload");
        panel.add(TransportsReturnOnUnload);
        GuardSlower.setBounds(70, 430, 100, 30);
        GuardSlower.dispatchEvent(new FocusEvent(GuardSlower, FocusEvent.FOCUS_GAINED, true));
        GuardSlower.setName("GuardSlower");
        panel.add(GuardSlower);
        Aggressive.setBounds(180, 430, 100, 30);
        Aggressive.dispatchEvent(new FocusEvent(Aggressive, FocusEvent.FOCUS_GAINED, true));
        Aggressive.setName("Aggressive");
        panel.add(Aggressive);
        OnTransOnly.setBounds(290, 430, 100, 30);
        OnTransOnly.dispatchEvent(new FocusEvent(OnTransOnly, FocusEvent.FOCUS_GAINED, true));
        OnTransOnly.setName("OnTransOnly");
        panel.add(OnTransOnly);
        IsBaseDefense.setBounds(400, 430, 100, 30);
        IsBaseDefense.dispatchEvent(new FocusEvent(IsBaseDefense, FocusEvent.FOCUS_GAINED, true));
        IsBaseDefense.setName("IsBaseDefense");
        panel.add(IsBaseDefense);
        Recruiter.setBounds(70, 470, 100, 30);
        Recruiter.dispatchEvent(new FocusEvent(Recruiter, FocusEvent.FOCUS_GAINED, true));
        Recruiter.setName("Recruiter");
        panel.add(Recruiter);
        Suicide.setBounds(180, 470, 100, 30);
        Suicide.dispatchEvent(new FocusEvent(Suicide, FocusEvent.FOCUS_GAINED, true));
        Suicide.setName("Suicide");
        panel.add(Suicide);
        AvoidThreats.setBounds(290, 470, 100, 30);
        AvoidThreats.dispatchEvent(new FocusEvent(AvoidThreats, FocusEvent.FOCUS_GAINED, true));
        AvoidThreats.setName("AvoidThreats");
        panel.add(AvoidThreats);
        OnlyTargetHouseEnemy.setBounds(400, 470, 100, 30);
        OnlyTargetHouseEnemy.dispatchEvent(new FocusEvent(OnlyTargetHouseEnemy, FocusEvent.FOCUS_GAINED, true));
        OnlyTargetHouseEnemy.setName("OnlyTargetHouseEnemy");
        panel.add(OnlyTargetHouseEnemy);
        teamname.setBounds(70, 80, 220, 30);
        teamname.dispatchEvent(new FocusEvent(teamname, FocusEvent.FOCUS_GAINED, true));
        teamname.setName("teamname");
        createnum.setBounds(370, 80, 80, 30);
        createnum.dispatchEvent(new FocusEvent(createnum, FocusEvent.FOCUS_GAINED, true));
        createnum.setName("createnum");
        lv0.setBounds(70, 110, 70, 30);
        lv0.dispatchEvent(new FocusEvent(lv0, FocusEvent.FOCUS_GAINED, true));
        lv0.setName("lv0");
        lv0.doClick();
        lv1.setBounds(150, 110, 70, 30);
        lv1.dispatchEvent(new FocusEvent(lv1, FocusEvent.FOCUS_GAINED, true));
        lv1.setName("lv1");
        lv3.setBounds(230, 110, 70, 30);
        lv3.dispatchEvent(new FocusEvent(lv3, FocusEvent.FOCUS_GAINED, true));
        lv3.setName("lv3");
        priority.setBounds(230, 150, 70, 30);
        priority.dispatchEvent(new FocusEvent(priority, FocusEvent.FOCUS_GAINED, true));
        priority.setName("priority");
        owner.setBounds(70, 150, 70, 30);
        owner.dispatchEvent(new FocusEvent(owner, FocusEvent.FOCUS_GAINED, true));
        owner.setName("owner");
        techlevel.setBounds(70, 190, 70, 30);
        techlevel.dispatchEvent(new FocusEvent(techlevel, FocusEvent.FOCUS_GAINED, true));
        techlevel.setName("techlevel");
        most.setBounds(70, 230, 70, 30);
        most.dispatchEvent(new FocusEvent(most, FocusEvent.FOCUS_GAINED, true));
        most.setName("most");
        group.setBounds(310, 150, 70, 30);
        group.dispatchEvent(new FocusEvent(group, FocusEvent.FOCUS_GAINED, true));
        group.setName("group");
        waypoint.setBounds(230, 190, 70, 30);
        waypoint.dispatchEvent(new FocusEvent(waypoint, FocusEvent.FOCUS_GAINED, true));
        waypoint.setName("waypoint");
        waypoint2.setBounds(230, 230, 70, 30);
        waypoint2.dispatchEvent(new FocusEvent(waypoint2, FocusEvent.FOCUS_GAINED, true));
        waypoint2.setName("waypoint2");
        controlaction.setBounds(310, 230, 100, 30);
        controlaction.dispatchEvent(new FocusEvent(controlaction, FocusEvent.FOCUS_GAINED, true));
        controlaction.setName("controlaction");
        script.setBounds(70, 270, 120, 30);
        script.dispatchEvent(new FocusEvent(script, FocusEvent.FOCUS_GAINED, true));
        script.setName("script");
        troop.setBounds(200, 270, 120, 30);
        troop.dispatchEvent(new FocusEvent(troop, FocusEvent.FOCUS_GAINED, true));
        troop.setName("troop");
        tag.setBounds(330, 270, 120, 30);
        tag.dispatchEvent(new FocusEvent(tag, FocusEvent.FOCUS_GAINED, true));
        tag.setName("tag");
        open.setBounds(380, 40, 75, 30);
        create.setBounds(470, 40, 75, 30);
        direction.setBounds(510, 50, 260, 520);
        direction.setFont(font);
        direction.setForeground(Color.RED);
        m.JlabelSetText(direction,"在此处批量新增第二版作战小队时，特遣部队、脚本、标签是以{初始编号,递增（减）额度}的格式表示递增。在为作战小队命名时，可以以%taskforce%,%script%,%tag%来表示特遣部队、脚本、标签的原本名称并代入作战小队命名。在命名时%lv%可以表示作战小队的等级，%waypoint%和%transwaypoint%分别表示路径点和传送路径点，%house%表示该小队的作战方名称。然后，%num%{初始数字,递增（减）额度}可以用来表示一个数字的递增。此外，难度也是一个可以循环的点。难度仅能隔一个难度循环，不能一次性由easy递增至hard。难度递增的参数是{初始难度，最终难度，递增(任一正整数）或递减（任一负整数）,重复次数}，这与第一版的作战小队命名仍相同。" +
                "需注意的是，路径点和传送路径点的两个栏目可以直接代入脚本参数来自动填写，其填写方式为%script%{脚本序号，差值}。脚本序号表示本小队的脚本中的第几个行为（第一个行为序号为0，以此类推），差值表示本栏目应填写的数值与该序号对应的脚本行为的参数的差值为多少。");
        panel.add(direction);
        panel.add(fold);
        panel.add(techlevel);
        panel.add(most);
        panel.add(priority);
        panel.add(open);
        panel.add(create);
        panel.add(teamname);
        panel.add(createnum);
        panel.add(lv0);
        panel.add(lv1);
        panel.add(lv3);
        panel.add(owner);
        panel.add(group);
        panel.add(waypoint);
        panel.add(waypoint2);
        panel.add(controlaction);
        panel.add(tag);
        panel.add(troop);
        panel.add(script);
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
        m.dialogExit(okBut,dialog);
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
                    setDefaultConfig("DefaultConfig.txt",list);
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
        int num=Integer.parseInt(createnum.getText());
        String house;
        String[] country=list.getCountries();
        int ownernum=Integer.parseInt(owner.getText());
        String thisscript="",thistask="",thistag="";
        int lcode=lsize+1;
        int VeteranLevel=0;
        if (lv0.isSelected()) VeteranLevel++;
        if (lv1.isSelected()) VeteranLevel++;
        if (lv3.isSelected()) VeteranLevel++;
        for(i=1;i<=num;i++)
        {
            for(int j=1;j<=VeteranLevel;j++) {
                teamlisttext += Integer.toString(lcode) + "=0" + Integer.toString(code) + "\n";
                lcode++;
                teamtext += "[0" + code + "]\n";
                String taskname = "";
                String scriptname = "";
                String tagname = "";
                String grouptext = group.getText();
                if (m.isInteger(grouptext, false)) grouptext = "-1";
                teamtext += "Max=" + most.getText() + "\n";
                teamtext += "Group=" + grouptext + "\n";
                teamtext += "House=" + country[ownernum] + "\n";
                house = country[ownernum];
                teamtext += "Script=";
                /*脚本部分*/
                if (script.getText().contains("{")) {
                    if (script.getText().startsWith("%random%")) {
                        String scripttype = script.getText().split("\\{")[1].split("}")[0];
                        String startscript = scripttype.split(",")[0];
                        String endscript = scripttype.split(",")[1];
                        Random random = new Random();
                        java.util.List<String> tasklist = new ArrayList<>(Arrays.asList(list.getTaskForces()));
                        int startpos = tasklist.indexOf(startscript);
                        int endpos = tasklist.indexOf(endscript);
                        int thispos = random.nextInt(endpos - startpos) + startpos;
                        thisscript = tasklist.get(thispos);
                    } else {
                        String scripttype = script.getText().split("}")[0].substring(1);
                        String startscript = scripttype.split(",")[0];
                        int identity = Integer.parseInt(scripttype.split(",")[1]);
                        java.util.List<String> scriptlist = new ArrayList<>(Arrays.asList(list.getScriptTypes()));
                        int startpos = scriptlist.indexOf(startscript);
                        int thispos = startpos + ((i - 1) / identity);
                        thisscript = scriptlist.get(thispos);
                    }
                    teamtext += thisscript + "\n";
                } else {
                    teamtext += script.getText() + "\n";
                    thisscript = script.getText();
                }
                for (Map<String, String> m : list.getScriptTypesName()) {
                    if (m.get(thisscript) != null) {
                        scriptname = m.get(thisscript);
                        break;
                    }
                }
                teamtext += "TaskForce=";
                /*特遣部队部分*/
                if (troop.getText().contains("{")) {
                    if (troop.getText().startsWith("%random%")) {
                        String tasktype = troop.getText().split("\\{")[1].split("}")[0];
                        String starttask = tasktype.split(",")[0];
                        String endtask = tasktype.split(",")[1];
                        Random random = new Random();
                        java.util.List<String> tasklist = new ArrayList<>(Arrays.asList(list.getTaskForces()));
                        int startpos = tasklist.indexOf(starttask);
                        int endpos = tasklist.indexOf(endtask);
                        int thispos = random.nextInt(endpos - startpos) + startpos;
                        thistask = tasklist.get(thispos);
                    } else {
                        String tasktype = troop.getText().split("}")[0].substring(1);
                        String starttask = tasktype.split(",")[0];
                        int identity = Integer.parseInt(tasktype.split(",")[1]);
                        java.util.List<String> tasklist = new ArrayList<>(Arrays.asList(list.getTaskForces()));
                        int startpos = tasklist.indexOf(starttask);
                        int thispos = startpos + ((i - 1) / identity);
                        thistask = tasklist.get(thispos);
                    }
                    teamtext += thistask + "\n";
                } else {
                    teamtext += troop.getText() + "\n";
                    thistask = troop.getText();
                }
                for (Map<String, String> m : list.getTaskForcesName()) {
                    if (m.get(thistask) != null) {
                        taskname = m.get(thistask);
                        break;
                    }
                }
                teamtext += "";
                /*标签部分*/
                if (tag.getText().contains("{")) {
                    String tagtype = tag.getText().split("}")[0].substring(1);
                    String starttag = tagtype.split(",")[0];
                    int identity = Integer.parseInt(tagtype.split(",")[1]);
                    List<String> taglist = list.getTags();
                    int startpos = taglist.indexOf(starttag);
                    int thispos = startpos + ((i - 1) / identity);
                    thistag = taglist.get(thispos);
                    teamtext += "Tag=" + thistag + "\n";
                } else if (tag.getText().length() > 0) {
                    teamtext += "Tag=" + tag.getText() + "\n";
                }
                /*路径点和传送路径点*/
                int thispoint;
                String s = waypoint.getText();
                String waytext1, waytext2;
                if (s.contains("{")) {
                    if (s.contains("%script%")) {
                        thispoint=getScriptPara(list,thisscript,s);
                    }
                    else {
                        String waytype = s.split("}")[0].substring(1);
                        String startway = waytype.split(",")[0];
                        int identity = Integer.parseInt(waytype.split(",")[1]);
                        thispoint = Integer.parseInt(startway) + (i - 1) * identity;
                   }
                    teamtext += "Waypoint=" + m.Waypoint_toAl(thispoint) + "\n";
                    waytext1 = String.valueOf(thispoint);
                } else {
                    teamtext += "Waypoint=" + m.Waypoint_toAl(Integer.parseInt(s))+ "\n";
                    waytext1=String.valueOf(s);
                }
                s = waypoint2.getText();
                if (s.contains("{")) {
                    if (s.contains("%script%")) {
                        thispoint=getScriptPara(list,thisscript,s);
                    }
                    else {
                        String waytype = s.split("}")[0].substring(1);
                        String startway = waytype.split(",")[0];
                        int identity = Integer.parseInt(waytype.split(",")[1]);
                        thispoint = Integer.parseInt(startway) + (i - 1) * identity;
                    }

                    teamtext += "TransportWaypoint=" + m.Waypoint_toAl(thispoint) + "\n";
                    waytext2 = String.valueOf(thispoint);
                } else {
                    teamtext += "TransportWaypoint=" + m.Waypoint_toAl(Integer.parseInt(s)) + "\n";
                    waytext2=String.valueOf(s);
                }
                /*小队等级部分*/
                teamtext +="VeteranLevel=";
                String nowlv="Lv0";
                if(j==1)
                {
                    if(lv0.isSelected()) {
                        teamtext += "1\n";
                        nowlv="Lv0";
                    }
                    else if (lv1.isSelected()) {
                        teamtext+="2\n";
                        nowlv="Lv1";
                    }
                    else if (lv3.isSelected()) {
                        teamtext+="3\n";
                        nowlv="Lv3";
                    }
                }
                else if (j==2)
                {
                    if(lv0.isSelected()&&lv1.isSelected()) {
                        teamtext+="2\n";
                        nowlv="Lv1";
                    }
                    else if (lv0.isSelected()&&lv3.isSelected()){
                        teamtext+="3\n";
                        nowlv="Lv3";
                    }
                    else if (lv1.isSelected()&&lv3.isSelected()) {
                        teamtext+="3\n";
                        nowlv="Lv3";
                    }
                }
                else if (j==3)
                {
                    teamtext+="3\n";
                    nowlv="Lv3";
                }
                teamtext += "Priority="+priority.getText()+"\n";
                teamtext += "TechLevel="+techlevel.getText()+"\n";
                /*勾选部分*/
                if (Loadable.isSelected()) teamtext += "Loadable=yes\n";
                else teamtext += "Loadable=no\n";
                if (Droppod.isSelected()) teamtext += "Droppod=yes\n";
                else teamtext += "Droppod=no\n";
                if (Autocreate.isSelected()) teamtext += "Autocreate=yes\n";
                else teamtext += "Autocreate=no\n";
                if (Full.isSelected()) teamtext += "Full=yes\n";
                else teamtext += "Full=no\n";
                if (Whiner.isSelected()) teamtext += "Whiner=yes\n";
                else teamtext += "Whiner=no\n";
                if (Prebuild.isSelected()) teamtext += "Prebuild=yes\n";
                else teamtext += "Prebuild=no\n";
                if (AreTeamMembersRecruitable.isSelected()) teamtext += "AreTeamMembersRecruitable=yes\n";
                else teamtext += "AreTeamMembersRecruitable=no\n";
                if (Annoyance.isSelected()) teamtext += "Annoyance=yes\n";
                else teamtext += "Annoyance=no\n";
                if (LooseRecruit.isSelected()) teamtext += "LooseRecruit=yes\n";
                else teamtext += "LooseRecruit=no\n";
                if (Reinforce.isSelected()) teamtext += "Reinforce=yes\n";
                else teamtext += "Reinforce=no\n";
                if (TransportsReturnOnUnload.isSelected()) teamtext += "TransportsReturnOnUnload=yes\n";
                else teamtext += "TransportsReturnOnUnload=no\n";
                if (GuardSlower.isSelected()) teamtext += "GuardSlower=yes\n";
                else teamtext += "GuardSlower=no\n";
                if (Aggressive.isSelected()) teamtext += "Aggressive=yes\n";
                else teamtext += "Aggressive=no\n";
                if (OnTransOnly.isSelected()) teamtext += "OnTransOnly=yes\n";
                else teamtext += "OnTransOnly=no\n";
                if (IsBaseDefense.isSelected()) teamtext += "IsBaseDefense=yes\n";
                else teamtext += "IsBaseDefense=no\n";
                if (Recruiter.isSelected()) teamtext += "Recruiter=yes\n";
                else teamtext += "Recruiter=no\n";
                if (Suicide.isSelected()) teamtext += "Suicide=yes\n";
                else teamtext += "Suicide=no\n";
                if (AvoidThreats.isSelected()) teamtext += "AvoidThreats=yes\n";
                else teamtext += "AvoidThreats=no\n";
                if (OnlyTargetHouseEnemy.isSelected()) teamtext += "OnlyTargetHouseEnemy=yes\n";
                else teamtext += "OnlyTargetHouseEnemy=no\n";
                teamtext +="MindControlDecision=";
                if((String.valueOf(controlaction.getSelectedItem()).equals("随机"))) teamtext +="0\n";
                else if ((String.valueOf(controlaction.getSelectedItem()).equals("加入小队"))) teamtext +="1\n";
                else if ((String.valueOf(controlaction.getSelectedItem()).equals("放进部队回收站"))) teamtext +="2\n";
                else if ((String.valueOf(controlaction.getSelectedItem()).equals("放入生化反应炉"))) teamtext +="3\n";
                else if ((String.valueOf(controlaction.getSelectedItem()).equals("寻敌"))) teamtext +="4\n";
                else if ((String.valueOf(controlaction.getSelectedItem()).equals("没有行为"))) teamtext +="5\n";
               /* if (String.valueOf(repeat.getSelectedItem()).equals("一次")) tagstext+="0,";
                else if (String.valueOf(repeat.getSelectedItem()).equals("集体")) tagstext+="1,";
                else tagstext+="2,";*/
                /*命名部分*/
                teamtext += "Name=";
                String name = teamname.getText();
                name = name.replaceAll("%taskforce%", taskname).replaceAll("%script%", scriptname).replaceAll("%house%", house).replaceAll("%lv%", nowlv).replaceAll("%waypoint%", waytext1).replaceAll("%transwaypoint%", waytext2);
                char[] a = name.toCharArray();
                int matchtype, ms = -1;
                String replace = "";
                for (int k = 0; k < a.length; k++) {
                    matchtype = -1;
                    if (a[k] == '{') {
                        ms = 1;
                    } else if (ms > 0 && a[k] == '}') {
                        if (replace.contains("a")) matchtype = 2;
                        else matchtype = 1;
                        if (matchtype == 1) {
                            int startway = Integer.parseInt(replace.split(",")[0]);
                            int identity = Integer.parseInt(replace.split(",")[2]);
                            int rtimes = Integer.parseInt(replace.split(",")[1]);
                            int thisway = startway + (i / rtimes) * identity;
                            teamtext += Integer.toString(thisway);
                        } else if (matchtype == 2) {
                            String startd = replace.split(",")[0];
                            String endd = replace.split(",")[1];
                            String thisdiffs = "";
                            int diffi1 = 0, diffi2 = 0, thisdiff = 0;
                            if (startd.equals("easy")) diffi1 = 1;
                            else if (startd.equals("normal")) diffi1 = 2;
                            else if (startd.equals("hard")) diffi1 = 3;
                            if (endd.equals("easy")) diffi2 = 1;
                            else if (endd.equals("normal")) diffi2 = 2;
                            else if (endd.equals("hard")) diffi2 = 3;
                            int rtimes = Integer.parseInt(replace.split(",")[3]);
                            int loop = Integer.parseInt(replace.split(",")[2]);
                            if (loop > 0) loop = 1;
                            else if (loop < 0) loop = -1;
                            thisdiff = diffi1 + ((i - 1) / rtimes) * loop;
                            if (loop != 0) thisdiff = thisdiff % 3;
                            else thisdiff = diffi2;
                            switch (Math.abs(thisdiff)) {
                                case 1:
                                    thisdiffs = "easy";
                                    break;
                                case 2:
                                    thisdiffs = "normal";
                                    break;
                                case 0:
                                    thisdiffs = "hard";
                                    break;
                                default:
                                    throw new Exception("难度填写错误");
                            }
                            teamtext += thisdiffs;
                        }
                        ms = -1;
                        replace = "";
                    } else if (ms > 0) {
                        replace += a[k];
                    } else {
                        teamtext += a[k];
                    }
                }
                code++;
                teamtext+="\n\n";
            }
        }

        printTeamTypes(list,file,teamtext,teamlisttext);
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
    public void setDefaultText() throws IOException {
        Path path = Paths.get("DefaultConfig.txt");
        Scanner scanner = new Scanner(path);
        while(scanner.hasNextLine())
        {
            String line=scanner.nextLine();
            String type=line.split("=")[0];
            if (type.equals("DefaultTeamFileName"))
            {
                fold.setText(line.split("=")[1]);
            }
            else if (type.equals("TeamTypeNameRule"))
            {
                teamname.setText(line.split("=")[1]);
            }
            else if (type.equals("TeamTypeOwner"))
            {
                owner.setText(line.split("=")[1]);
            }
            else if (type.equals("TeamTypeTechLevel"))
            {
                techlevel.setText(line.split("=")[1]);
            }
            else if (type.equals("TeamTypeMostOnField"))
            {
                most.setText(line.split("=")[1]);
            }
            else if (type.equals("TeamTypePriority"))
            {
                priority.setText(line.split("=")[1]);
            }
            else if (type.equals("TeamTypeWaypoint"))
            {
                waypoint.setText(line.split("=")[1]);
            }
            else if (type.equals("TeamTypeTransportWaypoint"))
            {
                waypoint2.setText(line.split("=")[1]);
            }
            else if (type.equals("TeamTypeGroup"))
            {
                group.setText(line.split("=")[1]);
            }
            else if (type.equals("TeamTypeTaskForce"))
            {
                troop.setText(line.split("=")[1]);
            }
            else if (type.equals("TeamTypeScript"))
            {
                script.setText(line.split("=")[1]);
            }
            else if (type.equals("TeamTypeTag"))
            {
                if (!line.endsWith("=")) tag.setText(line.split("=")[1]);
            }
        }
    }
    void setDefaultConfig(String filename,mapList list) throws IOException {
        PrintWriter pw=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        int i=0,t=0;
        File file=new File(filename);
        String line=System.getProperty("line.separator");//平台换行!
        String[] country=list.getCountries();
        int ownernum=Integer.parseInt(owner.getText());
        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                for (String str = br.readLine(); str != null; str = br.readLine())
                {
                    String type=str.split("=")[0];
                    if (type.equals("DefaultTeamFileName"))
                    {
                        str=type+"="+fold.getText()+"\n";
                    }
                    else if (type.equals("TeamTypeNameRule"))
                    {
                        str=type+"="+teamname.getText()+"\n";
                    }
                    else if (type.equals("TeamTypeOwner"))
                    {
                        str=type+"="+ownernum+"\n";
                    }
                    else if (type.equals("TeamTypeTechLevel"))
                    {
                        str=type+"="+techlevel.getText()+"\n";
                    }
                    else if (type.equals("TeamTypeMostOnField"))
                    {
                        str=type+"="+most.getText()+"\n";
                    }
                    else if (type.equals("TeamTypePriority"))
                    {
                        str=type+"="+priority.getText()+"\n";
                    }
                    else if (type.equals("TeamTypeWaypoint"))
                    {
                        str=type+"="+waypoint.getText()+"\n";
                    }
                    else if (type.equals("TeamTypeTransportWaypoint"))
                    {
                        str=type+"="+waypoint2.getText()+"\n";
                    }
                    else if (type.equals("TeamTypeGroup"))
                    {
                        str=type+"="+group.getText()+"\n";
                    }
                    else if (type.equals("TeamTypeTaskForce"))
                    {
                        str=type+"="+troop.getText()+"\n";
                    }
                    else if (type.equals("TeamTypeScript"))
                    {
                        str=type+"="+script.getText()+"\n";
                    }
                    else if (type.equals("TeamTypeTag"))
                    {
                        str=type+"="+tag.getText()+"\n";
                    }
                    buff.append(str+line);
                    i++;
                }
                buff.append("\n");
                buff=checkDefaultConfig(buff);
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
            buff.append("[addTeamTypes]\n");
            buff.append("DefaultTeamFileName=");
            buff.append(fold.getText()+"\n");
            buff.append("TeamTypeNameRule="+teamname.getText()+"\n");
            buff.append("TeamTypeOwner="+owner.getText()+"\n");
            buff.append("TeamTypeTechLevel="+techlevel.getText()+"\n");
            buff.append("TeamTypeMostOnField="+most.getText()+"\n");
            buff.append("TeamTypePriority="+priority.getText()+"\n");
            buff.append("TeamTypeWaypoint="+waypoint.getText()+"\n");
            buff.append("TeamTypeTransportWaypoint="+waypoint2.getText()+"\n");
            buff.append("TeamTypeGroup="+group.getText()+"\n");
            buff.append("TeamTypeTaskForce="+group.getText()+"\n");
            buff.append("TeamTypeScript="+group.getText()+"\n");
            buff.append("TeamTypeTag="+group.getText()+"\n");
            pw = new PrintWriter(new FileWriter(file), true);
            pw.println(buff);
        }
    }

    private StringBuffer checkDefaultConfig(StringBuffer buff) {
        String s=buff.toString();
        if (!s.contains("[addTeamTypes]"))
        {
            s+="[addTeamTypes]\n";
        }
        if(!s.contains("DefaultTeamFileName="))
        {
            s+="DefaultTeamFileName="+fold.getText()+"\n";
        }
        if(!s.contains("TeamTypeNameRule="))
        {
            s+="TeamTypeNameRule="+teamname.getText()+"\n";
        }
        if(!s.contains("TeamTypeOwner="))
        {
            s+="TeamTypeOwner="+owner.getText()+"\n";
        }
        if(!s.contains("TeamTypeTechLevel="))
        {
            s+="TeamTypeTechLevel="+techlevel.getText()+"\n";
        }
        if(!s.contains("TeamTypeMostOnField="))
        {
            s+="TeamTypeMostOnField="+most.getText()+"\n";
        }
        if(!s.contains("TeamTypePriority="))
        {
            s+="TeamTypePriority="+priority.getText()+"\n";
        }
        if(!s.contains("TeamTypeWaypoint="))
        {
            s+="TeamTypeWaypoint="+waypoint.getText()+"\n";
        }
        if(!s.contains("TeamTypeTransportWaypoint="))
        {
            s+="TeamTypeTransportWaypoint="+waypoint2.getText()+"\n";
        }
        if(!s.contains("TeamTypeGroup="))
        {
            s+="TeamTypeGroup="+group.getText()+"\n";
        }
        if(!s.contains("TeamTypeTaskForce="))
        {
            s+="TeamTypeTaskForce="+troop.getText()+"\n";
        }
        if(!s.contains("TeamTypeScript="))
        {
            s+="TeamTypeScript="+script.getText()+"\n";
        }
        if(!s.contains("TeamTypeTag="))
        {
            s+="TeamTypeTag="+tag.getText()+"\n";
        }
        buff.append(s);
        return buff;
    }
    int getScriptPara(mapList list,String thisscript,String waytext)
    {
        int thispoint=0;
        String waytype = waytext.split("\\{")[1].split("}")[0];
        int x=Integer.parseInt(waytype.split(",")[0]);
        int identity=Integer.parseInt(waytype.split(",")[1]);
        List<Script> scripts=list.getScripts();
        for(Script script:scripts)
        {
            if(script.getCode().equals(thisscript))
            {
                int[] a=script.getParalist();
                thispoint=a[x]+identity;
                break;
            }
        }
        return thispoint;
    }
}