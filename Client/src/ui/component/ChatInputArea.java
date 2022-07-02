package ui.component;

import command.BaseCommand;
import command.ChatCommand;
import command.GroupChatCommand;
import controller.ClientManager;
import serializable.Group;
import serializable.Message;
import ui.LoginFrame;
import ui.UIConstant;
import ui.panel.ChatPanel;
import ui.panel.GroupChatPanel;

import javax.swing.*;
import java.awt.*;

public class ChatInputArea extends JPanel {
    private static final long serialVersionUID = -7729141472310655826L;
    private final JTextArea textArea;
    private final JButton submitButton;
    private final boolean isGroup;
    public ChatInputArea( boolean isGroup){
        super();
        this.isGroup = isGroup;
        textArea = new JTextArea(5, 26);
        submitButton = new JButton("发送", UIConstant.SEND_ICON);
        init();
    }

    private void init(){
        Dimension preferredSize = new Dimension(UIConstant.CHAT_AREA_WIDTH, UIConstant.CHAT_INPUT_AREA_HEIGHT);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.black);
        this.setPreferredSize(preferredSize);
        Font font = new Font(UIConstant.FONT_TEXT, Font.BOLD, 18);
        textArea.setFont(font);
        textArea.setForeground(UIConstant.SELECTED_BACK_COLOR);
        textArea.setOpaque(true);
        textArea.setBackground(UIConstant.GRAY_BACK_COLOR);
        textArea.setLineWrap(true);
        this.add(textArea, BorderLayout.WEST);

        submitButton.setPreferredSize(new Dimension(108, UIConstant.CHAT_INPUT_AREA_HEIGHT));
        addEventListener();
        this.add(submitButton, BorderLayout.EAST);
    }

    private void addEventListener(){
        submitButton.addActionListener(e->{
            String text = textArea.getText();
            if(text.equals("")){
                JOptionPane.showMessageDialog(getParent(), "不能发送空信息",
                        "发送失败",JOptionPane.WARNING_MESSAGE);
                return;
            }
            Message message = new Message(ClientManager.getInstance().getCurrentUser(), text);
            textArea.setText("");
            BaseCommand command;
            if(isGroup){
                String groupCode = GroupChatPanel.currentChatArea.group.getGroupCode();
                String groupName = GroupChatPanel.currentChatArea.group.getGroupName();
                message.setGroupMessage(groupCode, groupName);
                GroupChatPanel.currentChatArea.addMessage(message);
                command = new GroupChatCommand(message);
            } else {
                int userId = ChatPanel.currentChatArea.user.getUserId();
                String userName = ChatPanel.currentChatArea.user.getName();
                message.setUserMessage(userId, userName);
                ChatPanel.currentChatArea.addMessage(message);
                command = new ChatCommand(message);
            }
            ClientManager.commandQueue.add(command);
        });
    }
}
