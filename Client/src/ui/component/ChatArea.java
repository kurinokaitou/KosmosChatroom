package ui.component;

import serializable.Group;
import serializable.Message;
import serializable.User;
import ui.UIConstant;

import javax.swing.*;
import java.awt.*;

public class ChatArea extends JPanel {
    private static final long serialVersionUID = -4880241668060926352L;
    private final ChatCard chatCard;
    public String text;
    public ChatArea(User user){
        super();
        chatCard = new ChatCard();
        text = "与"+user.getName()+"的私聊";
        init(text);

    }

    public ChatArea(Group group){
        super();
        chatCard = new ChatCard();
        text = group.getGroupName()+"群聊";
        init(text);
    }

    private void init(String text){
        Dimension preferredSize = new Dimension(UIConstant.CHAT_AREA_WIDTH, UIConstant.CHAT_AREA_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setLayout(new BorderLayout());
        this.setBackground(UIConstant.LIGHT_BACK_COLOR);
        // 标题
        JLabel titleLabel = new JLabel(text);
        titleLabel.setPreferredSize(new Dimension(UIConstant.CHAT_AREA_WIDTH, UIConstant.CHAT_TITLE_HEIGHT));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
        titleLabel.setBackground(UIConstant.SELECTED_BACK_COLOR);
        Font font = new Font(UIConstant.FONT_TEXT, Font.BOLD, 30);
        titleLabel.setFont(font);
        titleLabel.setForeground(Color.white);
        this.add(titleLabel, BorderLayout.NORTH);
        // 聊天区域
        this.add(chatCard, BorderLayout.SOUTH);
    }

    public void addMessage(Message message){
        chatCard.addMessage(message);
    }
}
