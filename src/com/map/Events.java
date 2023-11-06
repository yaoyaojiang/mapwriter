package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Events {
    public static void main(String[] args) throws InterruptedException, IOException {
        new NewEvents();
    }

}
class NewEvents extends JFrame {
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
    private JTextField event1 = new JTextField("在此填写事件号");
    private JTextField trigger1 = new JTextField("起始触发号");
    private JTextField trigger2 = new JTextField("结束触发号");
    private JTextField parameter1  = new JTextField("在此填写参数1");
    private JTextField parameter2  = new JTextField("在此填写参数2");
    private JCheckBox replace = new JCheckBox("替换");
    private JButton ok = new JButton("确定");
    private JButton cancel = new JButton("取消");
    private JLabel confirm = new JLabel("是否要新增事件？");
    private JButton newevent = new JButton("新增");
    int num = 1;

    public NewEvents() throws InterruptedException {
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
        event1.setBounds(70, 160, 110, 30);
        event1.dispatchEvent(new FocusEvent(event1, FocusEvent.FOCUS_GAINED, true));
        event1.setName("event1");
        parameter1.setBounds(190, 160, 110, 30);
        parameter1.dispatchEvent(new FocusEvent(parameter1, FocusEvent.FOCUS_GAINED, true));
        parameter1.setName("parameter1");
        parameter2.setBounds(320, 160, 110, 30);
        parameter2.dispatchEvent(new FocusEvent(parameter2, FocusEvent.FOCUS_GAINED, true));
        parameter2.setName("parameter2");
        newevent.setBounds(440, 160, 110, 30);
        newevent.dispatchEvent(new FocusEvent(newevent, FocusEvent.FOCUS_GAINED, true));
        newevent.setName("newevent");
        create.setBounds(440, 200, 110, 30);
        create.dispatchEvent(new FocusEvent(create, FocusEvent.FOCUS_GAINED, true));
        create.setName("create");
        direction.setBounds(560, 70, 200, 460);
        direction.setFont(font);
        direction.setForeground(Color.RED);
        m.JlabelSetText(direction,"新建事件中，大部分事件号仅有一个参数。这类事件中仅有参数1有效。若出现需要填两个参数的事件，第一个参数为数字（数量、路径点等），第二个参数填写对应的参数字符串。新增事件中，仅有参数1存在数字递增，直接使用{初始数字，递增额度}来定义递增。若需要使用随机数，则应该以%random%{初始数字,结束数字,递增额度（可以不填，默认为0）}为格式。注意，本程序同样可以批量新增ARES3.0的事件。若某个事件不需要参数，仍然会读取参数1。若参数1未填写，则按0处理。");
        panel.add(direction);
        panel.add(fold);
        panel.add(trigger1);
        panel.add(trigger2);
        panel.add(event1);
        panel.add(parameter1);
        panel.add(parameter2);
        panel.add(open);
        panel.add(create);
        panel.add(replace);
        panel.add(newevent);
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
        m.dialogExit(okBut, dialog);
        m.dialogExit(cancel, dialog);
        keyUpOrDown(fold,trigger1,trigger2,parameter1,parameter2,event1);
        ok.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String fileName = fold.getText();
                int i,mode;
                File file = new File(fileName);
                try {
                    CommonMethod m=new CommonMethod();
                    mapList list=m.readMapList(fold.getText());
                    writeEvents(list);

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
        newevent.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTextField event = new JTextField();
            JTextField p1 = new JTextField();
            JTextField p2 = new JTextField();
            event.setBounds(70, event1.getY() + num * 40, 110, 30);
            p1.setBounds(190, event1.getY() + num * 40, 110, 30);
            p2.setBounds(320, event1.getY() + num * 40, 110, 30);
            Color color = panel.getBackground();
            panel.setBackground(Color.white);
            panel.setBackground(color);
            event.dispatchEvent(new FocusEvent(event, FocusEvent.FOCUS_GAINED, true));
            event.setName("event" + (num+1));
            p1.dispatchEvent(new FocusEvent(event, FocusEvent.FOCUS_GAINED, true));
            p1.setName("parameter" + (2*num+1));
            p2.dispatchEvent(new FocusEvent(event, FocusEvent.FOCUS_GAINED, true));
            p2.setName("parameter" + (2*num+2));
            event.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    String name = event.getName();
                    if (keyCode == KeyEvent.VK_UP) {
                        focus(name, 0);
                    } else if (keyCode == KeyEvent.VK_DOWN) {
                        focus(name, 1);
                    }
                    super.keyReleased(e);
                }
                public void focus(String name, int mode) {
                    int num = Integer.parseInt(name.substring(5, name.length())), i;
                    for (Component c : panel.getComponents()) {
                        if (c instanceof JTextField) {
                            JTextField jt = (JTextField) c;
                            if (jt.getName().indexOf("event") < 0) {
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
                        event.requestFocusInWindow();
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
            panel.add(event);
            panel.add(p1);
            panel.add(p2);
            num++;
        }
    });
        event1.addKeyListener(new KeyAdapter() {
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
                                          }
                                          super.keyReleased(e);
                                      }
                                  }
        );
        parameter2.addKeyListener(new KeyAdapter() {
                                      @Override
                                      public void keyReleased(KeyEvent e) {
                                          int keyCode = e.getKeyCode();
                                          if (keyCode==KeyEvent.VK_DOWN)
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

    private void writeEvents(mapList list) throws IOException {
        File file=new File(fold.getText());
        String starttrigger=trigger1.getText(),endtrigger=trigger2.getText();
        java.util.List<String> triggerlist=list.getTriggers();
        java.util.List<String> eventlist=list.getEvents();
        System.out.println(eventlist);
        Collections.sort(triggerlist);
        Collections.sort(eventlist);
        String text="",lasttrigger="",firsttrigger="";
        int i=0,evnum=0;
        for(Component c : panel.getComponents()) {
            if (c instanceof JTextField) {
                String ctext = "";
                JTextField jt = (JTextField) c;
                if (jt.getName().indexOf("event") < 0) {
                    continue;
                }
                if(m.isInteger(jt.getText(),true)) evnum++;
            }
        }
        int order=1;
        for(String trigger:triggerlist)
        {
            if(starttrigger.compareTo(trigger)>0||endtrigger.compareTo(trigger)<0) continue;
            while(i<eventlist.size()&&eventlist.get(i).split("=")[0].compareTo(trigger)<0)
            {
                //遍历目前的所有触发和事件，查询该触发是否已存在事件。若事件编号未能查询到，却遍历到大于当前触发号的事件的所属触发号，则停止遍历
                i++;
            }
            String eventcode;
            int eventnum;
            if(i>=eventlist.size())
            {
                eventcode="";
                eventnum=0;
            }
            else
            {
                eventcode=eventlist.get(i);
                if(eventcode.endsWith("=")||eventcode.endsWith("=0"))
                {
                    eventnum=0;
                    eventcode="";
                }
                else eventnum=Integer.parseInt(eventcode.split("=")[1].split(",")[0]);
            }
            if(replace.isSelected()||eventcode.equals("")||!eventlist.get(i).split("=")[0].equals(trigger))
            {
                if (i<eventlist.size()&&!eventlist.get(i).split("=")[0].equals(trigger)) i--;
                eventnum=0;
                text+=trigger+"="+evnum+",";
            }
            else
            {
                int len=eventlist.get(i).split("=")[1].split(",")[0].length();
                text+=trigger+"="+(eventnum+evnum)+",";
                text+=eventlist.get(i).split("=")[1].substring(len+1)+",";
            }
            if(order==1) firsttrigger=trigger;
            int ecode=0;
            for(Component c : panel.getComponents()) {
                if (c instanceof JTextField) {
                    String ctext="";
                    JTextField jt = (JTextField) c;
                    if (jt.getName().indexOf("event") < 0&&(jt.getName().indexOf("parameter") < 0)) {
                        continue;
                    }

                    if(jt.getName().contains("event"))
                    {
                        String etext=jt.getText();
                        ecode=Integer.parseInt(etext);
                        if(m.isDoubleParaEvent(ecode)) {
                            ctext += etext+",2,";
                        }
                        else {
                            ctext += etext+",";
                        }
                        text+=ctext;
                    }
                    else if (jt.getName().contains("parameter"))
                    {
                        int pnum=Integer.parseInt(jt.getName().substring(9));
                        String etext=jt.getText();
                        if(etext.startsWith("%random%")&&etext.contains("{"))
                        {
                            String s=etext.substring(9).split("}")[0];
                            Random random=new Random();
                            String[] sp=s.split(",");
                            int identity=0;
                            int start=Integer.parseInt(s.split(",")[0]);
                            int end=Integer.parseInt(s.split(",")[1]);
                            if(sp.length>2) identity=Integer.parseInt(s.split(",")[2]);
                            int rannum=random.nextInt(end-start)+start+(order-1)*identity;
                            etext=String.valueOf(rannum);
                        }
                        else if(pnum%2!=0&&etext.contains("{"))
                        {
                            String s=etext.split("}")[0].substring(1);
                            int start=Integer.parseInt(s.split(",")[0]);
                            int identity=Integer.parseInt(s.split(",")[1]);
                            etext=String.valueOf((order-1)*identity+start);
                        }
                        else if(pnum%2!=0&&!m.isInteger(etext,true)) etext="0";
                        if(m.isDoubleParaEvent(ecode)) {
                            text += etext;
                            if(evnum*2>pnum) text+=",";
                        }
                        else {
                            if (pnum%2!=0)
                            {
                                text += "0," + etext;
                                if (evnum * 2 - 1 > pnum) text += ",";
                            }
                        }
                    }
                }
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
            order++;
            lasttrigger=trigger;
        }
        text+="\n";
        System.out.println("TEXT:"+text);
        printEvents(file,text,firsttrigger,lasttrigger,eventlist);
    }
    public void printEvents(File file, String text, String firsttrigger, String lasttrigger, List<String> eventlist) throws IOException {
        BufferedReader br=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        int row=0;
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
        int used=0;
        try {
            String  str=br.readLine();
            while((str=br.readLine())!=null) {
                if (str.equals("[Events]")) row=1;
                if (row==1&&!str.equals("[Events]")) {
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
    static void keyUpOrDown(JTextField fold,JTextField trigger1,JTextField trigger2,JTextField parameter1,JTextField parameter2,JTextField event1) {
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
                                        event1.requestFocusInWindow();
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
