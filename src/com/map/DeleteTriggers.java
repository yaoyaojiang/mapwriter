package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class Triggers {
    public static void main(String[] args) throws InterruptedException, IOException {
        new DeleteTriggers();
    }
}

class DeleteTriggers extends JFrame {
    private JTextField fold = new JTextField("在此填写map文件的目录地址");
    private JFileChooser chooser = new JFileChooser("D:\\project\\mapreader");
    private JButton open = new JButton("浏览");
    private JTextField start = new JTextField("在此填写起始触发的编号");
    private JTextField end = new JTextField("在此填写结束触发的编号");
    private JButton del = new JButton("删除");
    private JButton ok = new JButton("确定");
    private JButton okBut = new JButton("确定");
    private JButton cancel = new JButton("取消");
    private JLabel confirm = new JLabel("是否要删除指定的触发？");
    private JLabel success = new JLabel("删除成功");
    private JLabel direction=new JLabel("");
    private JDialog dialog=new JDialog();
    private JPanel panel = new JPanel();
    private CommonMethod m=new CommonMethod();
    public DeleteTriggers() throws InterruptedException, IOException {
        dialog.setBounds(400, 200, 350, 150);//设置弹出对话框的位置和大小
        dialog.setLayout(new FlowLayout());//设置弹出对话框的布局为流式布局
        dialog.add(confirm);
        dialog.add(success);
        dialog.add(ok);
        dialog.add(okBut);
        dialog.add(cancel);
        fold.setBounds(70, 80, 220, 30);
        fold.dispatchEvent(new FocusEvent(fold, FocusEvent.FOCUS_GAINED, true));
        fold.setName("fold");
        open.setBounds(320, 80, 60, 30);
        open.dispatchEvent(new FocusEvent(open, FocusEvent.FOCUS_GAINED, true));
        open.setName("open");
        end.setBounds(70, 160, 180, 30);
        end.dispatchEvent(new FocusEvent(end, FocusEvent.FOCUS_GAINED, true));
        end.setName("end");
        start.setBounds(70, 120, 180, 30);
        start.dispatchEvent(new FocusEvent(start, FocusEvent.FOCUS_GAINED, true));
        start.setName("start");
        del.setBounds(280, 120, 60, 30);
        del.dispatchEvent(new FocusEvent(del, FocusEvent.FOCUS_GAINED, true));
        del.setName("del");
        okBut.setBounds(200, 150, 60, 30);
        okBut.dispatchEvent(new FocusEvent(okBut, FocusEvent.FOCUS_GAINED, true));
        okBut.setName("okBut");
        okBut.setVisible(false);
        success.setVisible(false);
        ok.setBounds(60, 150, 60, 30);
        ok.dispatchEvent(new FocusEvent(ok, FocusEvent.FOCUS_GAINED, true));
        ok.setName("ok");
        dialog.add(ok);
        dialog.add(okBut);
        dialog.add(cancel);
        dialog.add(success);
        m.JlabelSetText(direction,"");
        panel.setLayout(null);
        panel.add(start);
        panel.add(end);
        panel.add(del);
        panel.add(fold);
        panel.add(open);
        addListener();
        this.add(panel);
        this.setBounds(300, 100, 800, 800);
        this.setPreferredSize(new Dimension(800, 600));
        this.pack();
        this.setVisible(true);
    }
    public void addListener() {
        m.fileChoose(open, chooser, fold);
        m.dialogExit(cancel, dialog);
        m.dialogExit(okBut, dialog);
        keyUpOrDown(fold,start,end);
        del.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 dialog.setVisible(true);
             }
        });
        ok.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String fileName = fold.getText();
                String startTriggers=start.getText(),endTriggers=end.getText();
                File file = new File(fileName);
                try {
                    if (!m.lastName(file).equals("map")) {
                        throw new FileNotFoundException("后缀名必须为map");
                    }
                    mapList list=m.readMapList(fold.getText());
                    java.util.List<String> trlist=getTriggerList(list,startTriggers,endTriggers);
                    java.util.List<String> talist=getTagList(list,startTriggers,endTriggers,trlist);
                    confirm.setVisible(false);
                    ok.setVisible(false);
                    cancel.setVisible(false);
                    printTriggers(trlist,talist,file);
                    success.setVisible(true);
                    okBut.setVisible(true);
                    super.mouseClicked(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
    public java.util.List<String> getTriggerList(mapList list, String start, String end)
    {
        Collections.sort(list.getTriggers());
        java.util.List<String> result=new java.util.ArrayList<String>();
        for(String s:list.getTriggers())
        {
            if(Integer.parseInt(s)>=Integer.parseInt(start)&&Integer.parseInt(s)<=Integer.parseInt(end))
            {
                result.add(s);
            }
        }
        return result;
    }
    public java.util.List<String> getTagList(mapList list,String start,String end,List<String> triggersList)
    {
        Collections.sort(list.getTags());
        java.util.List<String> result=new java.util.ArrayList<String>();
        List<Map<String, String>> ttlist=list.getTagsTriggers();

        for(Map<String, String> s:list.getTagsTriggers())
        {
            for (String str:s.keySet()) {
                if(triggersList.contains(s.get(str)))
                {
                    result.add(str);
                }
            }
        }
        return result;
    }
    public void printTriggers(List<String> triggers,List<String> tags,File file)
    {
        BufferedReader br=null;
        StringBuffer buff=new StringBuffer();//临时容器!
        String line=System.getProperty("line.separator");//平台换行!
        int i=1;
        try {
            br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
            for(String str=br.readLine();str!=null;str=br.readLine()) {
                if(containslist(str,triggers,tags)==false) {
                    buff.append(str + line);
                }
                i++;
            }
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
    public boolean containslist(String str,List<String> triggers,List<String> tags)
    {
        for(String s:triggers)
        {
            if (str.contains(s+"=")) return true;
        }
        for(String s:tags)
        {
            if (str.contains(s+"=")) return true;
        }
        return false;
    }
    static void keyUpOrDown(JTextField fold,JTextField start,JTextField end)
    {
        fold.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyReleased(KeyEvent e) {
                                    int keyCode = e.getKeyCode();
                                    if (keyCode == KeyEvent.VK_DOWN) {
                                        start.requestFocusInWindow();
                                    }
                                    super.keyReleased(e);
                                }
                            }
        );
        start.addKeyListener(new KeyAdapter() {
                                    @Override
                                    public void keyReleased(KeyEvent e) {
                                        int keyCode = e.getKeyCode();
                                        if (keyCode == KeyEvent.VK_DOWN) {
                                            end.requestFocusInWindow();
                                        }
                                        else if (keyCode == KeyEvent.VK_UP) {
                                            fold.requestFocusInWindow();
                                        }
                                        super.keyReleased(e);
                                    }
                                }
        );
        end.addKeyListener(new KeyAdapter() {
                                  @Override
                                  public void keyReleased(KeyEvent e) {
                                      int keyCode = e.getKeyCode();
                                      if (keyCode == KeyEvent.VK_UP) {
                                          start.requestFocusInWindow();
                                      }
                                      super.keyReleased(e);
                                  }
                              }
        );
    }
}
