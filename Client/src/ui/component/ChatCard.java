package ui.component;

import serializable.Message;
import ui.UIConstant;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChatCard extends JPanel {
    private static final long serialVersionUID = -1297797484236621004L;
    private final List<JButton> messageCardList;
    public ChatCard(){
        messageCardList = new ArrayList<>();
        init();
    }

    private void init() {
        this.setPreferredSize(new Dimension(UIConstant.CHAT_AREA_WIDTH, UIConstant.CHAT_CARD_HEIGHT));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setOpaque(true);
        this.setBackground(UIConstant.LIGHT_BACK_COLOR);
    }

    public void addMessage(Message message){
        //MessageCard messageCard = new MessageCard(message);
        JButton button = new JButton(message.content);
        button.setPreferredSize(new Dimension(UIConstant.CHAT_AREA_WIDTH, UIConstant.CHAT_LIST_ITEM_HEIGHT));
        messageCardList.add(button);
        this.add(button, BorderLayout.NORTH);
    }
}
