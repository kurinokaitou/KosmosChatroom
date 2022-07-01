package ui.panel;

import command.LogoutCommand;
import controller.ClientManager;
import ui.LoginFrame;
import ui.MainFrame;
import ui.UIConstant;
import ui.UIFrames;
import ui.component.SideIconButton;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {
    private static final long serialVersionUID = 6843816977313562242L;

    private static SideIconButton buttonChat;
    private static SideIconButton buttonGroupChat;
    private static SideIconButton buttonLogout;

    public SidebarPanel(){
        super();
        Dimension preferredSize = new Dimension(UIConstant.SIDEBAR_WIDTH, UIConstant.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setBackground(UIConstant.DEEP_BACK_COLOR);
        this.setLayout(new GridLayout(2, 1));
        addButtons();
        addListener();
    }
    private void addButtons() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(UIConstant.DEEP_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 4));
        JPanel panelDown = new JPanel();
        panelDown.setBackground(UIConstant.DEEP_BACK_COLOR);
        panelDown.setLayout(new BorderLayout(0, 0));

        buttonChat = new SideIconButton(UIConstant.CHAT_ICON, UIConstant.CHAT_ICON_ENABLED, "私聊");
        buttonChat.setLayout(new BorderLayout());
        buttonGroupChat = new SideIconButton(UIConstant.GROUP_CHAT_ICON, UIConstant.GROUP_CHAT_ICON_ENABLED, "群聊");
        buttonLogout = new SideIconButton(UIConstant.LOGOUT_ICON, UIConstant.LOGOUT_ICON_ENABLED, "登出");
        buttonLogout.setLayout(new BorderLayout());
        panelUp.add(buttonChat);
        panelUp.add(buttonGroupChat);
        panelDown.add(buttonLogout, BorderLayout.SOUTH);

        this.add(panelUp);
        this.add(panelDown);
    }

    private void addListener() {
        buttonChat.addActionListener((e -> {
            buttonChat.setIcon(UIConstant.CHAT_ICON_ENABLED);
            buttonGroupChat.setIcon(UIConstant.GROUP_CHAT_ICON);
            buttonLogout.setIcon(UIConstant.LOGOUT_ICON);

            MainFrame.centerPanel.removeAll();
            //TODO: 初始化chat
            MainFrame.centerPanel.add(MainFrame.chatPanel, BorderLayout.CENTER);
            MainFrame.centerPanel.updateUI();
        }));
        buttonGroupChat.addActionListener(e->{
            buttonChat.setIcon(UIConstant.CHAT_ICON);
            buttonGroupChat.setIcon(UIConstant.GROUP_CHAT_ICON_ENABLED);
            buttonLogout.setIcon(UIConstant.LOGOUT_ICON);
            MainFrame.centerPanel.removeAll();
            //TODO: 初始化group chat
            MainFrame.centerPanel.add(MainFrame.groupChatPanel, BorderLayout.CENTER);
            MainFrame.centerPanel.updateUI();
        });
        buttonLogout.addActionListener(e->{
            buttonChat.setIcon(UIConstant.CHAT_ICON);
            buttonGroupChat.setIcon(UIConstant.GROUP_CHAT_ICON);
            buttonLogout.setIcon(UIConstant.LOGOUT_ICON_ENABLED);
            ClientManager.commandQueue.offer(new LogoutCommand("logout"));
            UIFrames.mainFrame.dispose();
            UIFrames.loginFrame = new LoginFrame(true);
        });
    }
}
