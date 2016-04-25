
package gui;

import parser.Calculator;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.math.BigDecimal;
import java.text.*;
/**
 * 
 * @author yancunxiang
 * 窗口类
 *
 */
public class mainWindow
{
  JFrame mainFrame;
  JTextField inputField;
  JTextField outputField;
  JButton getResult;
  int i;
  JPanel p1, p2, p3, p4, p5, p6, p7, p8;
  JLabel l1, l2;
  JMenuBar menubar;
  JMenuItem item1, item2, item3, item4, item5, item6, item7;

  public void display() {
    mainFrame = new JFrame("科学计算器");
    mainFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        int i = JOptionPane.showConfirmDialog(null, "是否确定退出系统",
            "退出确认对话框", JOptionPane.YES_NO_CANCEL_OPTION);
        if (i == 0) {
          System.exit(0);
        }
      }
    });
    mainFrame.getContentPane().setLayout(new BorderLayout());
    mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    JPanel ioPanel = new JPanel();
    mainFrame.getContentPane().add(ioPanel);
    mainFrame.add(ioPanel);
    ioPanel.setLayout(new BorderLayout());
    menubar = new JMenuBar();

    mainFrame.setJMenuBar(menubar);
    JMenu menu1 = new JMenu("编辑");
    JMenu menu2 = new JMenu("操作");
    JMenu menu3 = new JMenu("帮助");
    menubar.add(menu1);
    menubar.add(menu2);
    menubar.add(menu3);
    item1 = new JMenuItem("复制", 'C');
    item2 = new JMenuItem("粘贴", 'V');
    item3 = new JMenuItem("剪切", 'X');
    item4 = new JMenuItem("计算", 'G');
    item5 = new JMenuItem("清空所有", 'R');
    item6 = new JMenuItem("退出", 'Q');
    item7 = new JMenuItem("关于计算器", 'H');
    menu1.add(item1);
    menu1.add(item2);
    menu1.add(item3);
    menu2.add(item4);
    menu2.add(item5);
    menu2.addSeparator();
    menu2.add(item6);
    menu3.add(item7);
    // 给菜单项注册监听器
    item1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        outputField.setEnabled(true);
        outputField.selectAll();
        outputField.copy();
        outputField.setEnabled(false);
      }
    });

    item2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        inputField.setText("");
        inputField.paste();
      }
    });

    item3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        inputField.selectAll();
        inputField.cut();
      }
    });

    item5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        inputField.setText("");
        outputField.setText("");
      }
    });

    item6.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int i = JOptionPane.showConfirmDialog(null, "是否确定退出系统",
            "退出确认对话框", JOptionPane.YES_NO_CANCEL_OPTION);
        if (i == 0) {
          System.exit(0);
        }
      }
    });

    item7.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(mainFrame, getHelpMessage());
      }
    });

    Font font = new Font("Arial", Font.BOLD, 20);
    l1 = new JLabel("算术表达式:");
    l2 = new JLabel("计算结果:");

    inputField = new JTextField(40);
    inputField.setFont(font);
    inputField.addKeyListener(new EnterKey());
    
    outputField = new JTextField(40);
    outputField.setFont(font);
    outputField.setEnabled(false);
    
    getResult = new JButton("Get Result");
    getResult.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e){
        if(e.getActionCommand()=="Get Result")
                {
                  getResult();
             }
      }
    });

    p3 = new JPanel(new FlowLayout());
    p4 = new JPanel(new FlowLayout());
    p1 = new JPanel(new GridLayout(2, 2, 10, 10));
    p3.add(l1);
    p3.add(inputField);
    p4.add(l2);
    p4.add(outputField);
    p1.add(p3);
    p1.add(p4);
    
    ioPanel.add(p1, "North");
    ioPanel.add(getResult, "South");
    mainFrame.setSize(850, 250);
    mainFrame.setVisible(true);
  }

  public void getResult() {
    try {
      Calculator calculator = new Calculator();
      BigDecimal bigResult = calculator.calculate(inputField.getText().trim());
      bigResult.setScale(100,BigDecimal.ROUND_HALF_EVEN);
      
      outputField.setText("" + bigResult);
    } catch (Exception ex) {
      outputField.setText(ex.getMessage());
    }
  }

  String getHelpMessage() {
    String msg = "";
    msg += "";
    msg += "";
    return msg;
  }
  
  class EnterKey implements KeyListener
  {

    @Override
    public void keyTyped(KeyEvent e) {
      // TODO Auto-generated method stub
      if (e.getKeyChar() == KeyEvent.VK_ENTER)
      {
        getResult();
      }
    }

    @Override
    public void keyPressed(KeyEvent e) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
      // TODO Auto-generated method stub
      
    }
    
  }
}
