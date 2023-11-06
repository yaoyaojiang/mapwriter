package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Collections;
import java.util.Random;

public class Actions {
    public static void main(String[] args) throws InterruptedException, IOException {
        new NewActions();
    }
}
class NewActions extends JFrame {
    private JTextField fold = new JTextField("在此填写map文件的目录地址");
    private JFileChooser chooser = new JFileChooser("D:\\project\\mapreader");
    private JButton open = new JButton("浏览");
    private JDialog dialog=new JDialog();
    private JButton okBut = new JButton("确定");
    private JButton create = new JButton("生成");
    private JLabel success = new JLabel("修改成功");
    private JLabel direction=new JLabel("");
    private JPanel panel = new JPanel();
    private Font font = new Font("宋体", Font.PLAIN, 13);
    private CommonMethod m=new CommonMethod();
    private JTextField action1 = new JTextField("在此填写行为号");
    private JTextField trigger1 = new JTextField("起始触发号");
    private JTextField trigger2 = new JTextField("结束触发号");
    private JTextField randomidentity = new JTextField("随机数递增值及随机循环次数");
    private JTextField identity = new JTextField("行为在单触发内部循环次数");
    private JTextField parameter1  = new JTextField("在此填写参数1");
    private JTextField parameter2  = new JTextField("在此填写参数2");
    private JCheckBox replace = new JCheckBox("替换");
    private JButton ok = new JButton("确定");
    private JButton cancel = new JButton("取消");
    private JLabel confirm = new JLabel("是否要新增行为？");
    private JButton newaction = new JButton("新增");
    int num = 1;
    public NewActions() throws InterruptedException {
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
        fold.setBounds(70, 80, 220, 30);
        fold.dispatchEvent(new FocusEvent(fold, FocusEvent.FOCUS_GAINED, true));
        fold.setName("fold");
        open.setBounds(320, 80, 60, 30);
        open.dispatchEvent(new FocusEvent(open, FocusEvent.FOCUS_GAINED, true));
        open.setName("open");
        trigger1.setBounds(70, 120, 110, 30);
        trigger1.dispatchEvent(new FocusEvent(trigger1, FocusEvent.FOCUS_GAINED, true));
        trigger1.setName("trigger1");
        trigger2.setBounds(190, 120, 110, 30);
        trigger2.dispatchEvent(new FocusEvent(trigger2, FocusEvent.FOCUS_GAINED, true));
        trigger2.setName("trigger2");
        replace.setBounds(320, 120, 70, 30);
        replace.dispatchEvent(new FocusEvent(replace, FocusEvent.FOCUS_GAINED, true));
        replace.setName("replace");
        action1.setBounds(70, 160, 110, 30);
        action1.dispatchEvent(new FocusEvent(action1, FocusEvent.FOCUS_GAINED, true));
        action1.setName("action1");
        parameter1.setBounds(190, 160, 110, 30);
        parameter1.dispatchEvent(new FocusEvent(parameter1, FocusEvent.FOCUS_GAINED, true));
        parameter1.setName("parameter1");
        parameter2.setBounds(320, 160, 110, 30);
        parameter2.dispatchEvent(new FocusEvent(parameter2, FocusEvent.FOCUS_GAINED, true));
        parameter2.setName("parameter2");
        newaction.setBounds(440, 160, 110, 30);
        newaction.dispatchEvent(new FocusEvent(newaction, FocusEvent.FOCUS_GAINED, true));
        newaction.setName("newaction");
        randomidentity.setBounds(440, 80, 110, 30);
        randomidentity.dispatchEvent(new FocusEvent(randomidentity, FocusEvent.FOCUS_GAINED, true));
        randomidentity.setName("randomidentity");
        identity.setBounds(440, 120, 110, 30);
        identity.dispatchEvent(new FocusEvent(identity, FocusEvent.FOCUS_GAINED, true));
        identity.setName("identity");
        create.setBounds(440, 200, 110, 30);
        create.dispatchEvent(new FocusEvent(create, FocusEvent.FOCUS_GAINED, true));
        create.setName("create");
        direction.setBounds(560, 70, 200, 460);
        direction.setFont(font);
        direction.setForeground(Color.RED);
        m.JlabelSetText(direction,"新建行为的递增定义与事件类似。在定义新建行为的递增中，直接使用{初始数字（INI编号），递增额度}来定义递增。注意，本程序同样可以批量新增ARES3.0的行为。新建行为的参数中，只有参数的数字和INI编号可递增。当出现有非数字的参数时，一律填在参数1之中；需要填写路径点的参数一律填在参数2中，且路径点必须填写数字而不是map文本中的26进制字母。出现没有填写参数2的情况下，参数2会按照默认情况被定义为A或0。参数1可以用%random%{最小值，最大值}表示生成指定范围内INI序号或数字的随机数。其中，最大值和最小值可以在随机数递增值框来表示随机数范围的变化。随机数递增值一般仅填写数字，但是也可以用如12,7这种格式表示递增值为12，且递增7次后恢复为原取值范围继续循环。");
        panel.add(direction);
        panel.add(randomidentity);
        panel.add(fold);
        panel.add(trigger1);
        panel.add(trigger2);
        panel.add(action1);
        panel.add(parameter1);
        panel.add(parameter2);
        panel.add(open);
        panel.add(create);
        panel.add(replace);
        panel.add(newaction);
        panel.add(identity);
        addListener();
        this.add(panel);
        this.setBounds(300, 100, 800, 800);
        this.setPreferredSize(new Dimension(800, 600));
        this.pack();
        this.setVisible(true);
    }

    private void addListener() {
        m.fileChoose(open, chooser, fold);
        m.dialogExit(okBut, dialog);
        keyUpOrDown(fold,trigger1,trigger2,parameter1,parameter2,action1);
        m.dialogExit(cancel, dialog);
        ok.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String fileName = fold.getText();
                int i,mode;
                File file = new File(fileName);
                try {
                    CommonMethod m=new CommonMethod();
                    mapList list=m.readMapList(fold.getText());
                    writeActions(list);
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
        newaction.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField action = new JTextField();
                JTextField p1 = new JTextField();
                JTextField p2 = new JTextField();
                action.setBounds(70, action1.getY() + num * 40, 110, 30);
                p1.setBounds(190, action1.getY() + num * 40, 110, 30);
                p2.setBounds(320, action1.getY() + num * 40, 110, 30);
                Color color = panel.getBackground();
                panel.setBackground(Color.white);
                panel.setBackground(color);
                action.dispatchEvent(new FocusEvent(action, FocusEvent.FOCUS_GAINED, true));
                action.setName("action" + (num+1));
                p1.dispatchEvent(new FocusEvent(action, FocusEvent.FOCUS_GAINED, true));
                p1.setName("parameter" + (2*num+1));
                p2.dispatchEvent(new FocusEvent(action, FocusEvent.FOCUS_GAINED, true));
                p2.setName("parameter" + (2*num+2));
                action.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        int keyCode = e.getKeyCode();
                        String name = action.getName();
                        if (keyCode == KeyEvent.VK_UP) {
                            focus(name, 0);
                        } else if (keyCode == KeyEvent.VK_DOWN) {
                            focus(name, 1);
                        }
                        else if  (keyCode == KeyEvent.VK_RIGHT) {
                            p1.requestFocusInWindow();
                        }
                        super.keyReleased(e);
                    }
                    public void focus(String name, int mode) {
                        int num = Integer.parseInt(name.substring(5, name.length())), i;
                        for (Component c : panel.getComponents()) {
                            if (c instanceof JTextField) {
                                JTextField jt = (JTextField) c;
                                if (jt.getName().indexOf("action") < 0) {
                                    continue;
                                }
                                i = Integer.parseInt(jt.getName().substring(5));
                                if (mode == 0) {
                                    if (i == num - 1) jt.requestFocusInWindow();
                                } else if (mode == 1) {
                                    if (i == num + 1) jt.requestFocusInWindow();
                                }
                            }
                        }
                    }
                });
                p1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        int keyCode = e.getKeyCode();
                        String name = p1.getName();
                        if (keyCode == KeyEvent.VK_UP) {
                            focus(name, 0);
                        } else if (keyCode == KeyEvent.VK_DOWN) {
                            focus(name, 1);
                        }
                        else if  (keyCode == KeyEvent.VK_RIGHT) {
                            p2.requestFocusInWindow();
                        }else if  (keyCode == KeyEvent.VK_LEFT) {
                            action.requestFocusInWindow();
                        }
                        super.keyReleased(e);
                    }
                    public void focus(String name, int mode) {
                        int num = Integer.parseInt(name.substring(9, name.length())), i;
                        for (Component c : panel.getComponents()) {
                            if (c instanceof JTextField) {
                                JTextField jt = (JTextField) c;
                                if (jt.getName().indexOf("parameter") < 0) {
                                    continue;
                                }
                                i = Integer.parseInt(jt.getName().substring(9));
                                if (mode == 0) {
                                    if (i == num - 2) jt.requestFocusInWindow();
                                } else if (mode == 1) {
                                    if (i == num + 2) jt.requestFocusInWindow();
                                }
                            }
                        }
                    }
                });
                p2.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        int keyCode = e.getKeyCode();
                        String name = p2.getName();
                        if (keyCode == KeyEvent.VK_UP) {
                            focus(name, 0);
                        } else if (keyCode == KeyEvent.VK_DOWN) {
                            focus(name, 1);
                        }else if  (keyCode == KeyEvent.VK_LEFT) {
                            p1.requestFocusInWindow();
                        }
                        super.keyReleased(e);
                    }
                    public void focus(String name, int mode) {
                        int num = Integer.parseInt(name.substring(9, name.length())), i;
                        for (Component c : panel.getComponents()) {
                            if (c instanceof JTextField) {
                                JTextField jt = (JTextField) c;
                                if (jt.getName().indexOf("parameter") < 0) {
                                    continue;
                                }
                                i = Integer.parseInt(jt.getName().substring(9));
                                if (mode == 0) {
                                    if (i == num - 2) jt.requestFocusInWindow();
                                } else if (mode == 1) {
                                    if (i == num + 2) jt.requestFocusInWindow();
                                }
                            }
                        }
                    }
                });
                panel.add(action);
                panel.add(p1);
                panel.add(p2);
                num++;
            }
        });
        action1.addKeyListener(new KeyAdapter() {
                                  @Override
                                  public void keyReleased(KeyEvent e) {
                                      int keyCode = e.getKeyCode();
                                      if (keyCode == KeyEvent.VK_DOWN) {
                                          for (Component c : panel.getComponents()) {
                                              if (c instanceof JTextField) {
                                                  JTextField jt = (JTextField) c;
                                                  if (jt.getName().equals("event2")) {
                                                      jt.requestFocusInWindow();
                                                  }
                                              }
                                          }
                                      }
                                      else if (keyCode == KeyEvent.VK_UP) {
                                          trigger1.requestFocusInWindow();
                                      }else if (keyCode == KeyEvent.VK_RIGHT) {
                                          parameter1.requestFocusInWindow();
                                      }
                                      super.keyReleased(e);
                                  }
                              }
        );
        parameter1.addKeyListener(new KeyAdapter() {
                                      @Override
                                      public void keyReleased(KeyEvent e) {
                                          int keyCode = e.getKeyCode();
                                          if (keyCode == KeyEvent.VK_DOWN) {
                                              for (Component c : panel.getComponents()) {
                                                  if (c instanceof JTextField) {
                                                      JTextField jt = (JTextField) c;
                                                      if (jt.getName().equals("parameter3")) {
                                                          jt.requestFocusInWindow();
                                                      }
                                                  }
                                              }
                                          }
                                          else if (keyCode == KeyEvent.VK_UP) {
                                              trigger2.requestFocusInWindow();
                                          }else if (keyCode == KeyEvent.VK_RIGHT) {
                                              parameter2.requestFocusInWindow();
                                          }else if (keyCode == KeyEvent.VK_LEFT) {
                                              action1.requestFocusInWindow();
                                          }
                                          super.keyReleased(e);
                                      }
                                  }
        );
        parameter2.addKeyListener(new KeyAdapter() {
                                      @Override
                                      public void keyReleased(KeyEvent e) {
                                          int keyCode = e.getKeyCode();
                                          if (keyCode == KeyEvent.VK_LEFT) {
                                              parameter1.requestFocusInWindow();
                                          }
                                          else if (keyCode==KeyEvent.VK_DOWN)
                                          {
                                              for (Component c : panel.getComponents()) {
                                                  if (c instanceof JTextField) {
                                                      JTextField jt = (JTextField) c;
                                                      if (jt.getName().equals("parameter4")) {
                                                          jt.requestFocusInWindow();
                                                      }
                                                  }
                                              }
                                          }
                                          super.keyReleased(e);
                                      }
                                  }
        );
    }

    private void writeActions(mapList list) throws IOException {
        File file=new File(fold.getText());
        String starttrigger=trigger1.getText(),endtrigger=trigger2.getText();
        java.util.List<String> triggerlist=list.getTriggers();
        java.util.List<String> actionlist=list.getActions();
        Collections.sort(triggerlist);
        Collections.sort(actionlist);
        String text="",lasttrigger="",firsttrigger="";
        int i=0,evnum=0;

        String iden=identity.getText();
        int ide;
        if(Integer.parseInt(iden)>0) ide=Integer.parseInt(iden);
        else ide=1;
        for(Component c : panel.getComponents()) {
            if (c instanceof JTextField) {
                String ctext = "";
                JTextField jt = (JTextField) c;
                if (jt.getName().indexOf("action") < 0) {
                    continue;
                }
                if(m.isInteger(jt.getText(),true)) evnum++;
            }
        }
        int order=1;
        for(String trigger:triggerlist)
        {
            if(starttrigger.compareTo(trigger)>0||endtrigger.compareTo(trigger)<0) continue;
            while(i<actionlist.size()&&actionlist.get(i).split("=")[0].compareTo(trigger)<0)
            {
                //遍历目前的所有触发和事件，查询该触发是否已存在事件。若事件编号未能查询到，却遍历到大于当前触发号的事件的所属触发号，则停止遍历
                i++;
            }
            String actioncode;
            int eventnum;
            if(i>=actionlist.size())
            {
                actioncode="";
                eventnum=0;
            }
            else
            {
                actioncode=actionlist.get(i);
                if(actioncode.endsWith("="))
                {
                    eventnum=0;
                    actioncode="";
                }
                else eventnum=Integer.parseInt(actioncode.split("=")[1].split(",")[0]);
            }
            if(replace.isSelected()||actioncode.equals("")||!actionlist.get(i).split("=")[0].equals(trigger))
            {
                if (i<actionlist.size()&&!actionlist.get(i).split("=")[0].equals(trigger)) i--;
                eventnum=0;
                text+=trigger+"="+evnum*ide;
            }
            else
            {
                System.out.println(actionlist.get(i));
                int len=actionlist.get(i).split("=")[1].split(",")[0].length();
                text+=trigger+"="+(eventnum+evnum*ide)+",";
                text+=actionlist.get(i).split("=")[1].substring(len+1);
            }
            if(order==1) firsttrigger=trigger;
            int ecode=0;
            for(int j=1;j<=ide;j++) {
                for(Component c : panel.getComponents()) {
                    if (c instanceof JTextField) {
                        String ctext="";
                        JTextField jt = (JTextField) c;
                        if (jt.getName().indexOf("action") < 0&&(jt.getName().indexOf("parameter") < 0)) {
                            continue;
                        }
                        if (jt.getName().contains("action")) {
                            String etext = jt.getText();
                            ecode = Integer.parseInt(etext);

                            text += "," + etext + "," + String.valueOf(m.actionType(ecode)) + ",";
                        } else if (jt.getName().contains("parameter")) {
                            int pnum = Integer.parseInt(jt.getName().substring(9));
                            String etext = jt.getText();
                            int atype = m.actionType(ecode), ri;
                            String rist = randomidentity.getText();
                            String ris;
                            if (rist.contains(",")) ris = rist.split(",")[0];
                            else ris = rist;
                            int loop;
                            if (rist.contains(",")) loop = Integer.parseInt(rist.split(",")[1]);
                            else loop = 0;
                            if (m.isInteger(ris, false)) ri = Integer.parseInt(ris);
                            else ri = 0;
                            if (pnum % 2 != 0) {
                                if (etext.contains("{") && etext.contains("}")) {
                                    String s = etext.split("\\{")[1].split("}")[0].split(",")[0];
                                    String s2 = etext.split("\\{")[1].split("}")[0].split(",")[1];
                                    String ls = "", rs = "";
                                    if (!etext.startsWith("{")) ls = etext.replace("%random%", "").split("\\{")[0];
                                    if (!etext.endsWith("}")) rs = etext.replace("%random%", "").split("}")[1];

                                    if (m.isNormalCode(s)) {
                                        if (etext.contains("%random%")) {
                                            Random random = new Random();
                                            int start = Integer.parseInt(s.substring(1));
                                            int end = Integer.parseInt(s2.substring(1));
                                            int rannum = random.nextInt(end - start) + start;
                                            if (loop == 0) rannum += (order - 1) * ri;
                                            else rannum += ri * ((order - 1) % loop);
                                            etext = "0" + String.valueOf(rannum);
                                        } else {
                                            String code = etext.split("\\{")[1].split("}")[0];
                                            int start = Integer.parseInt(code.split(",")[0]);
                                            int identity = Integer.parseInt(code.split(",")[1]);
                                            etext = "0" + String.valueOf((order - 1) * identity + start);
                                        }
                                    } else if (m.isInteger(s, true)) {
                                        if (etext.contains("%random%")) {
                                            Random random = new Random();
                                            int start = Integer.parseInt(s);
                                            int end = Integer.parseInt(s2);
                                            int rannum = random.nextInt(end - start) + start;
                                            if (loop == 0) rannum += (order - 1) * ri;
                                            else rannum += ri * ((order - 1) % loop);
                                            etext = String.valueOf(rannum);
                                        } else {
                                            String code = etext.split("\\{")[1].split("}")[0];
                                            int start = Integer.parseInt(code.split(",")[0]);
                                            int identity = Integer.parseInt(code.split(",")[1]);
                                            etext = String.valueOf((order - 1) * identity + start);
                                        }
                                    }
                                    if (ls.length() > 0) etext = ls + etext;
                                    if (rs.length() > 0) etext = etext + rs;

                                }
                                text += etext + ",0,0,0,0,";
                            } else if (etext.length() > 0) {
                                if (etext.contains("{") && etext.contains("}")) {
                                    String s = etext.substring(1).split("}")[0].split(",")[0];
                                    if (m.isInteger(s, true)) {
                                        String code = etext.substring(1).split("}")[0];
                                        int start = Integer.parseInt(code.split(",")[0]);
                                        int identity = Integer.parseInt(code.split(",")[1]);
                                        etext = String.valueOf((order - 1) * identity + start);
                                    }
                                }
                                if ((m.isInteger(etext, true) || etext.equals("0")) && (atype <= 1 || atype == 10)) {
                                    etext = m.Waypoint_toAl(Integer.parseInt(etext));
                                }
                                text += etext;
                            } else {
                                switch (atype) {
                                    case 0:
                                    case 7:
                                    case 1:
                                    case 4:
                                    case 10:
                                    case 6:
                                    case 3:
                                    case 2:
                                    case 8: {
                                        text += "A";
                                        break;
                                    }
                                    default: {
                                        text += "0";
                                        break;
                                    }
                                }

                            }
                        }
                    }
                }
                order++;
            }
            if (trigger.compareTo(endtrigger)<=0&&trigger.compareTo(triggerlist.get(triggerlist.size()-1))<0)
            {
                int nextcode=triggerlist.indexOf(trigger);
                if (nextcode<triggerlist.size()-1) {
                    String next = triggerlist.get(nextcode+1);
                    if (endtrigger.compareTo(next)>=0) text += "\n";
                }
                else if (nextcode>=triggerlist.size()-1)
                {
                    text += "\n";
                }
            }
            else text += "\n";
            lasttrigger=trigger;
        }
        text+="\n";
        System.out.println("text:"+text);
        printActions(file,text,firsttrigger,lasttrigger);
    }
    public void printActions(File file,String text,String firsttrigger,String lasttrigger) throws IOException {
        BufferedReader br=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        int row=0;
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
        int used=0;
        try {
            String  str=br.readLine();
            while((str=br.readLine())!=null) {
                if (str.equals("[Actions]")) row=1;
                if (row==1&&!str.equals("[Actions]")) {
                    String s=str.split("=")[0];
                    if ((s.compareTo(firsttrigger)>=0&&s.compareTo(lasttrigger)<=0)||(s.length()==0&&used==0))
                    {
                        str = str.replaceAll(str, text);
                        if (used==0)
                        {
                            buff.append(str + line);
                            used++;
                        }
                    }
                    else buff.append(str + line);
                    if (str.length()==0) row=0;
                }
                else buff.append(str + line);
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
    static void keyUpOrDown(JTextField fold,JTextField trigger1,JTextField trigger2,JTextField parameter1,JTextField parameter2,JTextField action1) {
        fold.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_DOWN) {
                                        trigger1.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
        trigger1.addKeyListener(new KeyAdapter() {
                                    @Override
                                    public void keyReleased(KeyEvent e) {
                                        int keyCode = e.getKeyCode();
                                        if (keyCode == KeyEvent.VK_DOWN) {
                                            action1.requestFocusInWindow();
                                        }
                                        else if (keyCode == KeyEvent.VK_UP) {
                                            fold.requestFocusInWindow();
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
                                        if (keyCode == KeyEvent.VK_DOWN) {
                                            parameter1.requestFocusInWindow();
                                        }
                                        else if (keyCode == KeyEvent.VK_UP) {
                                            fold.requestFocusInWindow();
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
