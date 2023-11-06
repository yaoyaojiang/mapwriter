package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class CommonMethod {
    CommonMethod(){}
    /*获取map文件的各占据INI编号的条目是哪些，即获取Script有哪些INI编号，TaskForces有哪些INI编号等。*/
    public mapList readMapList(String fileName) throws IOException
    {
        mapList list = new mapList();
        List<Map<String,String>> taglist=new ArrayList<Map<String, String>>();
        list.initialize();
        int start = 0,row=1;
        String maxcode = "01000000", code="01000000";
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        List<Script> scripts= new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (start > 0) {
                if (line.indexOf("=") > -1) {
                    if (start == 1) {
                        code = line.replace(" ", "").split("=")[1];
                        int number= Integer.parseInt(line.replace(" ", "").split("=")[0]);
                        list.addTaskForces(number,code);
                    } else if (start == 2) {
                        code = line.replace(" ", "").split("=")[0];
                        list.addTags(code);
                        int len=line.replace(" ", "").split(",").length;
                        String triggers = line.replace(" ", "").split(",")[len-1];
                        Map<String,String> map=new HashMap<String,String>();
                        map.put(code,triggers);
                        taglist.add(map);
                    } else if (start == 3) {
                        code = line.replace(" ", "").split("=")[0];
                        list.addTriggers(code);
                    } else if (start == 4) {
                        code = line.replace(" ", "").split("=")[1];
                        int number= Integer.parseInt(line.replace(" ", "").split("=")[0]);
                        list.addTeamTypes(number,code);
                    } else if (start == 5) {
                        code = line.replace(" ", "").split("=")[1];
                        int number= Integer.parseInt(line.replace(" ", "").split("=")[0]);
                        list.addScriptTypes(number,code);
                    } else if (start == 6) {
                        code = line.replace(" ", "").split("=")[0];
                        list.addAITriggerTypes(code);
                    } else if (start==7)
                    {
                        code = line.replace(" ", "").split("=")[1];
                        int nu=Integer.parseInt(line.replace(" ", "").split("=")[0]);
                        list.addCountries(code,nu);
                    }else if (start==8)
                    {
                        code = line.replace(" ", "");
                        list.addEvents(code);
                    }else if (start==9)
                    {
                        code = line.replace(" ", "");
                        list.addActions(code);
                    }else if (start==12)
                    {
                        if(line.startsWith("Size="))
                        {
                            int height = Integer.parseInt(line.replace(" ", "").split("=")[1].split(",")[3]);
                            int width = Integer.parseInt(line.replace(" ", "").split("=")[1].split(",")[2]);
                            list.setWidth(width);
                            list.setHeight(height);
                        }
                    }
                    if(start<7) maxcode=getNormalMaxCode(maxcode,code);//用来获取本map文件中最大的通常代码号
                }
                else
                {
                    if (start==1) list.setRow_TaskLineList(row);
                    else if (start==5) list.setRow_ScriptLineList(row);
                    else if (start==4) list.setRow_TeamTypeLineList(row);
                    else if (start==3) list.setRow_TriggerLineList(row);
                    else if (start==2) list.setRow_TagLineList(row);
                    else if (start==8) list.setRow_EventLineList(row);
                    else if (start==9) list.setRow_ActionLineList(row);
                    start = 0;
                }
            } else if (line.contains("[TaskForces]")) {
                start = 1;
            } else if (line.contains("[Tags]")) {
                start = 2;
            } else if (line.contains("[Triggers]")) {
                start = 3;
            } else if (line.contains("[TeamTypes]")) {
                start = 4;
            } else if (line.contains("[ScriptTypes]")) {
                start = 5;
            } else if (line.contains("[AITriggerTypes]")) {
                start = 6;
            }else if (line.contains("[Countries]")){
                start=7;
            }else if (line.contains("[Events]")){
                start=8;
            }else if (line.contains("[Actions]")){
                start=9;
            }else if (line.contains("[Terrain]")){
                start=10;
            }else if (line.contains("[Waypoints]")){
                start=11;
            }else if (line.contains("[Map]")){
                start=12;
            }
            row++;
        }
        list.setMaxnormalcode(maxcode);
        list.setTagsTriggers(taglist);
        scanner.close();
        return list;
    }
    /*获取map文件的各占据INI编号的条目是哪些，即获取Script有哪些INI编号，TaskForces有哪些INI编号等。除此之外,该INI编号对应的那个事物的名称也会被记录。*/
    public mapList readMapList_Name(String fileName) throws IOException
    {
        mapList list=readMapList(fileName);
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path,"gb2312");
        String code="",name = null;
        List<Map<String,String>> TaskForcesName = new ArrayList<>();
        List<Map<String,String>> TagsName= new ArrayList<>();
        List<Map<String,String>> TagsTriggers= new ArrayList<>();
        List<Map<String,String>> TriggersName= new ArrayList<>();
        List<Map<String,String>> TeamTypesName= new ArrayList<>();
        List<Map<String,String>> ScriptTypesName= new ArrayList<>();
        List<Map<String,String>> AITriggerTypesName= new ArrayList<>();
        List<Map<String,String>> Terrain= new ArrayList<>();
        List<Map<String,String>> Waypoints= new ArrayList<>();
        List<Script> scripts= new ArrayList<>();
        Script script=new Script();
        int[] scriptactlist=new int[50];
        int[] scriptparalist=new int[50];
        for(int i=0;i<50;i++)
        {
            scriptactlist[i]=0;
            scriptparalist[i]=0;
        }
        int start = 0,row=1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("[")&&line.endsWith("]"))
            {
                code=line.substring(0,line.indexOf("]")).substring(1);
                if (list.inTaskForces(code))
                {
                    start=1;
                }
                else if (list.inTeamTypes(code))
                {
                    start=4;
                }
                else if (list.inScriptTypes(code))
                {
                    start=5;
                    script.setCode(code);
                    script.setXh(list.getScriptPosition(code));
                }
                else if (code.equals("Tags"))
                {
                    start=2;
                }
                else if (code.equals("Triggers"))
                {
                    start=3;
                }
                else if (code.equals("AITriggerTypes"))
                {
                    start=6;
                }
                else if (code.equals("Terrain"))
                {
                    start=10;
                }
                else if (code.equals("Waypoints"))
                {
                    start=11;
                }
                else start=0;
            }
            else if (start>0)
            {
                if (line.contains("="))
                {
                    int onname=1;
                    Map<String,String> map2=new HashMap<String,String>();
                    String triggersname;
                    if (start == 1 || start == 4 ) {
                        if (line.startsWith("Name=")) {
                            name = line.replace(" ", "").substring(5);                            
                        }
                        else continue;
                    }
                    else if (start==5)
                    {
                        if (line.startsWith("Name=")) {
                            name = line.replace(" ", "").substring(5);
                            script.setName(name);
                        }
                        else if (line.contains("="))
                        {
                            onname=0;
                            int x=Integer.parseInt(line.split("=")[0]);
                            int act=Integer.parseInt(line.split("=")[1].split(",")[0]);
                            int par=Integer.parseInt(line.split("=")[1].split(",")[1]);
                            scriptactlist[x]=act;
                            scriptparalist[x]=par;
                        }
                    }
                    else if (start==3){
                        name=line.replace(" ", "").split(",")[2];
                        code=line.replace(" ", "").split("=")[0];
                    }
                    else if (start==2){
                        name=line.split(",")[1];
                        triggersname=line.split(",")[2];
                        code=line.replace(" ", "").split("=")[0];
                        map2.put(code,triggersname);
                    }
                    else if (start==10)
                    {
                        name=line.replace(" ", "").split("=")[1];
                        code=line.replace(" ", "").split("=")[0];
                    }
                    else if (start==11)
                    {
                        code=line.replace(" ", "").split("=")[0];
                        name=line.replace(" ", "").split("=")[1];
                    }
                    else
                    {
                        name=line.replace(" ", "").split(",")[0].split("=")[1];
                        code=line.replace(" ", "").split("=")[0];
                    }
                    if(onname==1) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put(code, name);
                        switch (start) {
                            case 1:
                                TaskForcesName.add(map);
                                break;
                            case 2:
                                TagsName.add(map);
                                TagsTriggers.add(map2);
                                break;
                            case 3:
                                TriggersName.add(map);
                                break;
                            case 4:
                                TeamTypesName.add(map);
                                break;
                            case 5:
                                ScriptTypesName.add(map);
                                break;
                            case 6:
                                AITriggerTypesName.add(map);
                                break;
                            case 10:
                                Terrain.add(map);
                                break;
                            case 11:
                                Waypoints.add(map);
                                break;
                        }
                    }
                }
                else
                {
                    if(start==5)
                    {
                        Script sc=new Script();
                        int[] a=new int[50],p=new int[50];
                        sc.setCode(script.getCode());
                        sc.setXh(script.getXh());
                        for(int i=0;i<50;i++) {
                            a[i]=scriptactlist[i];
                            p[i]=scriptparalist[i];
                        }
                        sc.setActlist(a);
                        sc.setParalist(p);
                        sc.setName(script.getName());
                        scripts.add(sc);
                        script.initialize();
                        for(int i=0;i<50;i++)
                        {
                            scriptactlist[i]=0;
                            scriptparalist[i]=0;
                        }
                    }
                    code="";
                    name="";
                    start=0;
                }
            }
        }
        scanner.close();
        list.setTagsName(TagsName);
        list.setTagsTriggers(TagsTriggers);
        list.setTaskForcesName(TaskForcesName);
        list.setTeamTypesName(TeamTypesName);
        list.setTriggersName(TriggersName);
        list.setAITriggerTypesName(AITriggerTypesName);
        list.setScriptTypesName(ScriptTypesName);
        list.setTerrains(Terrain);
        list.setWaypoint(Waypoints);
        list.setScripts(scripts);
        return list;
    }
    boolean isDecimal(String str) {
        DecimalFormat df = new DecimalFormat("0.##"); // 设置格式为两位小数，且最小值为0

        try {
            double number = Double.parseDouble(df.format(Double.parseDouble(str))); // 尝试解析并格式化字符串为小数
            if (number > 0 && number < 1) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private String getNormalMaxCode(String a, String b)
    {
        if(!b.startsWith("010")) return a;
        try {
            int anum=Integer.parseInt(a.substring(1)),bnum=Integer.parseInt(b.substring(1));
            if (a.startsWith("010")&&b.startsWith("010")&&a.length()==8&&b.length()==8&&anum>0&&bnum>0)
            {
                if (a.compareTo(b)>0) return a;
                else return b;
            }
            else return a;
        } catch (NumberFormatException e) {
            return a;
        }
    }
    public boolean isNormalCode(String a)
    {
        if (!a.startsWith("010")) return false;
        int anum=Integer.parseInt(a.substring(1));
        if (a.length()==8&&anum>0)
        {
            return true;
        }
        else return false;
    }
    public static void fileChoose(JButton open, JFileChooser chooser, JTextField fold) {
        open.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chooser.setFileSelectionMode(0);
                //0表示文件1表示文件夹
                int status = chooser.showOpenDialog(null);
                if (status == 1) {
                    return;
                } else {
                    File file = chooser.getSelectedFile();
                    fold.setText(file.getAbsolutePath());
                }
            }
        });
    }
    public String lastName(File file) {
        if (file == null) return null;
        String filename = file.getName();
// split用的是正则，所以需要用 //. 来做分隔符
        String[] split = filename.split("\\.");
//注意判断截取后的数组长度，数组最后一个元素是后缀名
        if (split.length > 1) {
            return split[split.length - 1];
        } else {
            return "";
        }
    }
    public boolean isWaypointInMap(int rx,int ry,int height,int width)
    {
        if (rx<=0||ry<=0||height<=10||width<=10) return false;
        if(ry>=rx+width) return false;
        if(ry<=rx-width) return false;
        if(ry<=width-rx) return false;
        if(ry>=2*height+width+1-rx) return false;
        return true;
    }
    void JlabelSetText(JLabel jLabel, String longString) throws InterruptedException {
        StringBuilder builder = new StringBuilder("<html>");
        char[] chars = longString.toCharArray();
        FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
        int start = 0;
        int len = 0;
        while (start + len < longString.length()) {
            while (true) {
                len++;
                if (start + len > longString.length())break;
                if (fontMetrics.charsWidth(chars, start, len)
                        > jLabel.getWidth()) {
                    break;
                }
            }
            builder.append(chars, start, len-1).append("<br/>");
            start = start + len - 1;
            len = 0;
        }
        builder.append(chars, start, longString.length()-start);
        builder.append("</html>");
        jLabel.setText(builder.toString());
    }
    void dialogExit(JButton okBut, JDialog dialog)
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
    String Waypoint_toAl(int k)
    {
        String s = null;
        try {
            if (k < 26) {
                char ch = (char) (k + 65);
                s = String.valueOf(ch);
            } else if (k >= 26&&k<702) {
                int fi = k / 26;
                int en = k % 26;
                char ch1 = (char) (fi + 64);
                char ch2 = (char) (en + 65);
                s = String.valueOf(ch1) + String.valueOf(ch2);
            } else if (k > 701) throw new Exception("路径点不能大于701");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    int Waypoint_toInt(String s)
    {
        int result=-1;
        try {
            if (s.length() == 1) {
                result=s.toUpperCase().charAt(0)-65;
            } else if (s.length() == 2) {

                result=(s.toUpperCase().charAt(0)-64)*26+(s.toUpperCase().charAt(1)-65);
            }
            else result=-1;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    public boolean isDoubleParaEvent(int code) {
        if (code==60||code==61||code==77||code==81||code==82||code==86)
            return true;
        else return false;
    }
    public boolean isInteger(String str,boolean isPositive)
    {
        if(str.length()<=0) return false;
        Pattern pattern=Pattern.compile("^[-\\+]?[\\d]*$");
        if(!isPositive)
            return pattern.matcher(str).matches();
        if (pattern.matcher(str).matches()){
            return Integer.parseInt(str)>0;
        }
        return pattern.matcher(str).matches();
    }
    public boolean isActionUsingWaypoint(int a)
    {
        if (actionType(a)==0||actionType(a)==1||actionType(a)==10)
        {
            switch (a)
            {
                case 8:
                case 17:
                case 18:
                case 41:
                case 42:
                case 43:
                case 48:
                case 55:
                case 58:
                case 59:
                case 63:
                case 70:
                case 80:
                case 90:
                case 95:
                case 102:
                case 107:
                case 108:
                case 109:
                case 112:
                case 116:
                case 125:
                case 128:
                case 135:
                case 136:
                case 137:
                case 138:
                case 140:
                case 141:
                    return true;
                default:
                    return false;
            }
        }
        else return false;
    }
    public int actionType(int a)
    {
        switch (a)
        {
            //类型0为数字+路径点结尾
            case 1:
            case 2:
            case 3:
            case 6:
            case 8:
            case 9:
            case 10:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 100:
            case 101:
            case 102:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 126:
            case 127:
            case 128:
            case 130:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 147:
            case 148:
            case 149:
                return 0;
            //类型1为作战小队代码+路径点结尾
            case 4:
            case 5:
            case 7:
            case 80:
            case 105:
            case 107:
                return 1;
            //类型2为触发代码+路径点A结尾
            case 12:
            case 22:
            case 53:
            case 54:
                return 2;
            //类型3为数字+路径点A结尾，几乎没有用。
            case 70:
                return 3;
            //类型4为csf文本代码+路径点A结尾
            case 11:
            case 103:
                return 4;
            //类型5为作战小队代码+数字结尾，数字表示帧数
            case 104:
                return 5;
            //类型6为EVA语音代码+路径点A结尾
            case 21:
                return 6;
            //类型7为Sound声音代码+路径点A结尾
            case 19:
            case 98:
            case 99:
                return 7;
            //类型8为Theme声音代码+路径点A结尾
            case 20:
                return 8;
            //类型9为科技类型代码+数字结尾
            case 106:
            case 115:
            case 131:
                return 9;
            //类型10为建筑类型代码+路径点结尾
            case 125:
                return 10;
            //类型11为数字代码+数字结尾，一般用于超武相关的行为
            case 129:
            case 132:
            case 133:
            case 146:
                return 11;
            default:
                return -1;
        }
    }
}
