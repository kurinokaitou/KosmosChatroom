package ui;

import command.ShutdownCommand;
import controller.ClientManager;
import ui.panel.ChatPanel;
import ui.panel.GroupChatPanel;
import ui.panel.SidebarPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -8808883923263763897L;
    public static ChatPanel chatPanel;
    public static GroupChatPanel groupChatPanel;
    public static JPanel centerPanel;

    public MainFrame(){
        chatPanel =  ChatPanel.getInstance();
        groupChatPanel = GroupChatPanel.getInstance();
        centerPanel = new JPanel(true);
        this.init();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void init(){
        this.setTitle(UIConstant.CLIENT_NAME +"--"+ ClientManager.getInstance().getCurrentUser().getName() + "已登录");
        this.setIconImage(UIConstant.CLIENT_ICON.getImage());
        this.setBackground(UIConstant.LIGHT_BACK_COLOR);
        int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setBounds((x - this.getWidth())/2 - UIConstant.MAIN_WINDOW_WIDTH/2, (y - this.getHeight()) / 2 - UIConstant.MAIN_WINDOW_HEIGHT/2,
                UIConstant.MAIN_WINDOW_WIDTH, UIConstant.MAIN_WINDOW_HEIGHT);
        this.setResizable(false);

        JPanel mainPanel = new JPanel(true);
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());

        // 添加侧边栏
        SidebarPanel sidebarPanel = new SidebarPanel();
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        // 添加中间面板
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(chatPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel);

        this.add(mainPanel);
        //关闭窗口
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ClientManager.commandQueue.offer(new ShutdownCommand("shutdown"));
            }
        });
    }
}
