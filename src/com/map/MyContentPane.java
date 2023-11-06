package com.map;

import javax.swing.*;
import java.awt.*;

class ContentPane_MainClass {
    public static void main(String[] args) {
        MyContentPane msp = new MyContentPane();
    }
}

public class MyContentPane extends JFrame {
    private JLabel label;
    private JButton button;
    private Container container;
    public MyContentPane() {
        super("我是JFrame的一个内容面板" );
        container = this.getContentPane();            // 获取JFrame的内容面板
        container.setLayout(new BorderLayout());      // 设置内容面板的布局
        label = new JLabel("内容面板上一个小标签");
        button = new JButton("内容面板上的一个小按钮");
        container.add(label,BorderLayout.NORTH);      // 将组件添加到内容面板
        container.add(button,BorderLayout.SOUTH);
        this.setSize(400, 360);
        this.setLocation(300, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
