package ui.component;

import serializable.Group;
import serializable.User;
import ui.UIConstant;

import javax.swing.*;
import java.awt.*;

public class ChatListItem extends JLabel {
    private static final long serialVersionUID = -2258475021340851543L;
    private User user;
    private Group group;
    public ChatListItem(User user){
        super();
        this.user = user;
        JLabel userName = new JLabel(user.getName());
        Font font = new Font(UIConstant.FONT_TEXT, Font.BOLD, 15);
        userName.setFont(font);
        userName.setBackground(UIConstant.SELECTED_BACK_COLOR);
        userName.setForeground(Color.white);
        this.add(userName);
        init();
    }

    public ChatListItem(Group group){
        super();
        this.group = group;
        JLabel groupName = new JLabel(group.getGroupName());
        Font font = new Font(UIConstant.FONT_TEXT, Font.BOLD, 15);
        groupName.setFont(font);
        groupName.setBackground(UIConstant.SELECTED_CHAT_LIST_COLOR);
        groupName.setForeground(Color.white);
        this.add(groupName);
        init();
    }

    private void init(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
        Dimension preferredSizeListItem = new Dimension(UIConstant.CHAT_LIST_WIDTH, UIConstant.CHAT_LIST_ITEM_HEIGHT);
        this.setPreferredSize(preferredSizeListItem);
    }

    public String getChatGroupCode(){
        return group.getGroupCode();
    }

    public int getUserId(){
        return user.getUserId();
    }
}
