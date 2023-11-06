package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class Base {
    public static void main(String[] args) {
        new StartFrame();
    }
}
class StartFrame extends JFrame {
    private JButton troops = new JButton("批量新增特遣部队");
    private JButton scripts=new JButton("批量新增脚本");
    private JButton teamtypes=new JButton("批量新增作战小队");
    private JButton deletetriggers=new JButton("批量删除触发");
    private JButton createtriggers=new JButton("批量新增触发");
    private JButton createevents=new JButton("批量新增事件");
    private JButton createactions=new JButton("批量新增行为");
    private JButton copytrigger=new JButton("复制触发");
    private JButton copyteam=new JButton("复制作战小队");
    private JButton randomterrain=new JButton("随机地形对象");
    private JButton newteamtypes=new JButton("批量新增作战小队（新）");
    private JPanel panel = new JPanel();
    public StartFrame()
    {
        panel.setLayout(null);
        troops.setBounds(35,20,170,30);
        scripts.setBounds(35,60,170,30);
        teamtypes.setBounds(35,100,170,30);
        deletetriggers.setBounds(35,140,170,30);
        createtriggers.setBounds(35,180,170,30);
        createevents.setBounds(35,220,170,30);
        createactions.setBounds(35,260,170,30);
        copytrigger.setBounds(35,300,170,30);
        copyteam.setBounds(35,340,170,30);
        randomterrain.setBounds(35,380,170,30);
        newteamtypes.setBounds(230,20,170,30);
        addListener();
        panel.add(troops);
        panel.add(scripts);
        panel.add(teamtypes);
        panel.add(deletetriggers);
        panel.add(createtriggers);
        panel.add(createevents);
        panel.add(createactions);
        panel.add(newteamtypes);
        panel.add(copytrigger);
        panel.add(copyteam);
        panel.add(randomterrain);
        this.add(panel);
        this.setBounds(300,100,1024,800);
        this.setPreferredSize(new Dimension(1024, 800));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
    void addListener()
    {
        troops.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MyJFrame mj= null;
                try {
                    mj = new MyJFrame();
                } catch (InterruptedException | IOException interruptedException) {
                    interruptedException.printStackTrace();
                }
                mj.setDefaultCloseOperation(HIDE_ON_CLOSE);
                mj.setVisible(true);
            }
        });
        scripts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NewScript mj= null;
                try {
                    mj = new NewScript();
                } catch (InterruptedException | IOException interruptedException) {
                    interruptedException.printStackTrace();
                }
                mj.setDefaultCloseOperation(HIDE_ON_CLOSE);
                mj.setVisible(true);
            }
        });
        teamtypes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NewTeamTypes mj= null;
                try {
                    mj = new NewTeamTypes();
                } catch (InterruptedException | IOException interruptedException) {
                    interruptedException.printStackTrace();
                }
                mj.setDefaultCloseOperation(HIDE_ON_CLOSE);
                mj.setVisible(true);
            }
        });
        deletetriggers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DeleteTriggers mj= null;
                try {
                    mj = new DeleteTriggers();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                mj.setDefaultCloseOperation(HIDE_ON_CLOSE);
                mj.setVisible(true);
            }
        });
        createtriggers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CreateTriggers mj= null;
                try {
                    mj = new CreateTriggers();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                mj.setDefaultCloseOperation(HIDE_ON_CLOSE);
                mj.setVisible(true);
            }
        });
        createevents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NewEvents mj= null;
                try {
                    mj = new NewEvents();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                mj.setDefaultCloseOperation(HIDE_ON_CLOSE);
                mj.setVisible(true);
            }
        });
        createactions.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NewActions mj= null;
                try {
                    mj = new NewActions();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                mj.setDefaultCloseOperation(HIDE_ON_CLOSE);
                mj.setVisible(true);
            }
        });
        copytrigger.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CopyTriggers mj= null;
                try {
                    mj = new CopyTriggers();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                mj.setDefaultCloseOperation(HIDE_ON_CLOSE);
                mj.setVisible(true);
            }
        });
        copyteam.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CopyTeamTypes mj= null;
                try {
                    mj = new CopyTeamTypes();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                mj.setDefaultCloseOperation(HIDE_ON_CLOSE);
                mj.setVisible(true);
            }
        });
        randomterrain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RandomT mj= null;
                try {
                    mj = new RandomT();
                }catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                mj.setDefaultCloseOperation(HIDE_ON_CLOSE);
                mj.setVisible(true);
            }
        });
        newteamtypes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TeamType mj= null;
                try {
                    mj = new TeamType();
                }catch (InterruptedException | IOException interruptedException) {
                    interruptedException.printStackTrace();
                }
                mj.setDefaultCloseOperation(HIDE_ON_CLOSE);
                mj.setVisible(true);
            }
        });
    }
}