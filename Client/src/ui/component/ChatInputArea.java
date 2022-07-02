package ui.component;

import controller.ClientManager;
import serializable.Message;
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
            Message message = new Message(ClientManager.getInstance().getCurrentUser(), textArea.getText());
            textArea.setText("");
            if(isGroup){
                GroupChatPanel.currentChatArea.addMessage(message);
                GroupChatPanel.currentChatArea.updateUI();
            } else {
                ChatPanel.currentChatArea.addMessage(message);
                ChatPanel.currentChatArea.updateUI();
            }
        });
    }
}
