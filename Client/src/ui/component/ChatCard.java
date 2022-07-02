package ui.component;

import controller.ClientManager;
import serializable.Message;
import ui.UIConstant;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChatCard extends JPanel {
    private static final long serialVersionUID = -1297797484236621004L;
    private final List<MessageCard> messageCardList;
    public ChatCard(){
        messageCardList = new ArrayList<>();
        init();
    }

    private void init() {
        this.setPreferredSize(new Dimension(UIConstant.CHAT_AREA_WIDTH - UIConstant.CHAT_LIST_SCROLL_BAR_WIDTH, 1000*UIConstant.CHAT_CARD_HEIGHT));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setOpaque(true);
        this.setBackground(UIConstant.LIGHT_BACK_COLOR);
    }

    public void addMessage(Message message){
        boolean fromSelf = (message.fromUser == ClientManager.getInstance().getCurrentUser());
        MessageCard messageCard = new MessageCard(message, fromSelf);
        messageCardList.add(messageCard);
        this.add(messageCard, BorderLayout.NORTH);
    }
}
