package ui;

import command.BaseCommand;
import command.LoginCommand;
import command.RegisterCommand;
import command.ShutdownCommand;
import controller.ClientManager;
import serializable.ResponseStatus;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = -3426717670093483287L;

    private final JTextField loginUserNameText;
    private final JTextField registerUserNameText;
    private final JPasswordField loginPasswordField;
    private final JPasswordField registerPasswordField;
    private final JPasswordField confirmPasswordField;
    private final JTabbedPane tabPane;

    public LoginFrame(boolean success){
        loginUserNameText = new JTextField();
        registerUserNameText = new JTextField();
        loginPasswordField = new JPasswordField();
        registerPasswordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        tabPane = new JTabbedPane();
        this.init();
        setVisible(true);
        if(!success){
            JOptionPane.showMessageDialog(LoginFrame.this, "未能连接到服务器",
                    "错误",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void init() {
        this.setTitle("登录Kosmos聊天室");
        this.setTitle(UIConstant.CLIENT_NAME);
        this.setIconImage(UIConstant.CLIENT_ICON.getImage());
        this.setBackground(UIConstant.BACK_COLOR);
        int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setBounds((x - this.getWidth()) / 2 - UIConstant.LOGIN_WINDOW_WIDTH/2, (y - this.getHeight()) / 2 - UIConstant.LOGIN_WINDOW_HEIGHT/2,
                UIConstant.LOGIN_WINDOW_WIDTH, UIConstant.LOGIN_WINDOW_HEIGHT);
        this.setResizable(false);

        //把Logo放置到JFrame的西边
        Icon icon = UIConstant.CLIENT_ICON;
        JLabel label = new JLabel(icon);
        label.setPreferredSize(new Dimension(UIConstant.LOGIN_WINDOW_WIDTH * 2 / 5, UIConstant.LOGIN_WINDOW_HEIGHT));
        this.add(label, BorderLayout.WEST);

        //登录注册Tab信息面板
        initLoginTab(tabPane);
        initRegisterTab(tabPane);
        this.add(tabPane);

        //按钮面板放置在JFrame的南边
        JPanel btnPanel = new JPanel();
        this.add(btnPanel, BorderLayout.SOUTH);
        btnPanel.setLayout(new BorderLayout());
        btnPanel.setBorder(new EmptyBorder(2, 8, 4, 8));

        JButton confirmBtn = new JButton("确认");
        btnPanel.add(confirmBtn, BorderLayout.EAST);

        //关闭窗口
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ClientManager.commandQueue.offer(new ShutdownCommand("shutdown"));
            }
        });

        // 监听确认按钮
        confirmBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(tabPane.getSelectedIndex() == 0){
                    handleLogin();
                } else{
                    handleRegister();
                }
            }
        });

    }

    private void handleLogin(){
        String userName = loginUserNameText.getText();
        String password = String.valueOf(loginPasswordField.getPassword());
        System.out.println("用户名"+userName + " " +password);
        if (userName.length() == 0
                || password.length() == 0) {
            JOptionPane.showMessageDialog(LoginFrame.this, "未输入账号和密码",
                    "输入不能为空",JOptionPane.ERROR_MESSAGE);
            loginUserNameText.requestFocusInWindow();
            return;
        }

        BaseCommand command = new LoginCommand("login", userName, password);
        command.execute();
        if(command.response.status == ResponseStatus.SUCCESS){
            this.dispose();
            new MainFrame();
        } else {
            JOptionPane.showMessageDialog(LoginFrame.this, command.response.shortMessage,
                    "登录失败",JOptionPane.ERROR_MESSAGE);
        }

    }

    private void handleRegister(){
        String userName = registerUserNameText.getText();
        String password = String.valueOf(registerPasswordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
        if (userName.length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
            JOptionPane.showMessageDialog(LoginFrame.this, "未输入账号和密码",
                    "输入不能为空",JOptionPane.ERROR_MESSAGE);
            registerUserNameText.requestFocusInWindow();
            return;
        }
        if(password.length() < 6){
            JOptionPane.showMessageDialog(LoginFrame.this, "密码位数不能小于6位",
                    "输入错误",JOptionPane.ERROR_MESSAGE);
            registerPasswordField.requestFocusInWindow();
            return;
        }
        if(!password.equals(confirmPassword)){
            JOptionPane.showMessageDialog(LoginFrame.this, "确认密码与上次输入不同",
                    "输入错误",JOptionPane.ERROR_MESSAGE);
            confirmPasswordField.requestFocusInWindow();
            return;
        }
        BaseCommand command = new RegisterCommand("register", userName, password);
        command.execute();
        if(command.response.status == ResponseStatus.SUCCESS){
            JOptionPane.showMessageDialog(LoginFrame.this, "注册成功！",
                    "注册成功",JOptionPane.INFORMATION_MESSAGE);
            tabPane.setSelectedIndex(0);
        }else {
            JOptionPane.showMessageDialog(LoginFrame.this, "服务器错误",
                    "注册失败",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initLoginTab(JTabbedPane tabPane){
        JPanel mainPanel = new JPanel();
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        mainPanel.setBorder(BorderFactory.createTitledBorder(border, "输入登录信息", TitledBorder.CENTER, TitledBorder.TOP));
        mainPanel.setLayout(null);
        makeInputLabel(mainPanel, loginUserNameText, loginPasswordField);

        tabPane.addTab("登录", mainPanel);
    }

    private void initRegisterTab(JTabbedPane tabPane){
        JPanel mainPanel = new JPanel();
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        mainPanel.setBorder(BorderFactory.createTitledBorder(border, "输入注册信息", TitledBorder.CENTER, TitledBorder.TOP));
        mainPanel.setLayout(null);
        makeInputLabel(mainPanel, registerUserNameText, registerPasswordField);

        JLabel pwdConfirmLabel = new JLabel("确认密码:");
        pwdConfirmLabel.setBounds(20, 140, 150, 22);
        mainPanel.add(pwdConfirmLabel);
        confirmPasswordField.setBounds(95, 140, 150, 22);
        mainPanel.add(confirmPasswordField, BorderLayout.SOUTH);

        tabPane.addTab("注册", mainPanel);
    }

    private void makeInputLabel(JPanel mainPanel, JTextField userNameText, JPasswordField passwordField){
        JLabel nameLabel = new JLabel("账号:");
        nameLabel.setBounds(50, 80, 150, 22);
        mainPanel.add(nameLabel);
        userNameText.setBounds(95, 80, 150, 22);
        userNameText.requestFocusInWindow();//用户名获得焦点
        mainPanel.add(userNameText, BorderLayout.SOUTH);

        JLabel pwdLabel = new JLabel("密码:");
        pwdLabel.setBounds(50, 110, 150, 22);
        mainPanel.add(pwdLabel);
        passwordField.setBounds(95, 110, 150, 22);
        mainPanel.add(passwordField, BorderLayout.SOUTH);
    }
}