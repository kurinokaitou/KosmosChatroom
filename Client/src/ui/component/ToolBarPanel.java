package ui.component;

import ui.UIConstant;

import javax.swing.*;
import java.awt.*;

public class ToolBarPanel extends JPanel {
    private static final long serialVersionUID = 6843816977313562242L;

    private static SideIconButton buttonChat;
    private static SideIconButton buttonGroupChat;
    private static SideIconButton buttonLogout;

    public ToolBarPanel(){
        super();
        Dimension preferredSize = new Dimension(48, UIConstant.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setBackground(UIConstant.TOOL_BAR_BACK_COLOR);
        this.setLayout(new GridLayout(2, 1));
        addButtons();
        addListener();
    }
    private void addButtons() {

        JPanel panelUp = new JPanel();
        panelUp.setBackground(UIConstant.TOOL_BAR_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(-2, -2, -4));
        JPanel panelDown = new JPanel();
        panelDown.setBackground(UIConstant.TOOL_BAR_BACK_COLOR);
        panelDown.setLayout(new BorderLayout(0, 0));



        buttonChat = new SideIconButton(UIConstant.CHAT_ICON, UIConstant.CHAT_ICON_ENABLED, "私聊");
        buttonGroupChat = new SideIconButton(UIConstant.GROUP_CHAT_ICON, UIConstant.GROUP_CHAT_ICON_ENABLED, "群聊");
        buttonLogout = new SideIconButton(UIConstant.LOGOUT_ICON, UIConstant.LOGOUT_ICON_ENABLED, "登出");

        panelUp.add(buttonChat);
        panelUp.add(buttonGroupChat);
        panelDown.add(buttonLogout);

        this.add(panelUp);
        this.add(panelDown);
    }

    private void addListener() {
        buttonChat.addActionListener((e -> {
            buttonChat.setIcon(UIConstant.CHAT_ICON_ENABLED);
            buttonGroupChat.setIcon(UIConstant.GROUP_CHAT_ICON);
            buttonLogout.setIcon(UIConstant.LOGOUT_ICON);
        }));
        buttonGroupChat.addActionListener(e->{
            buttonChat.setIcon(UIConstant.CHAT_ICON);
            buttonGroupChat.setIcon(UIConstant.GROUP_CHAT_ICON_ENABLED);
            buttonLogout.setIcon(UIConstant.LOGOUT_ICON);
        });
        buttonLogout.addActionListener(e->{
            buttonChat.setIcon(UIConstant.CHAT_ICON);
            buttonGroupChat.setIcon(UIConstant.GROUP_CHAT_ICON);
            buttonLogout.setIcon(UIConstant.LOGOUT_ICON_ENABLED);
        });
    }
}
