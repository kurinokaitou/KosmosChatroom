package ui.component;

import serializable.Group;
import serializable.User;
import ui.UIConstant;
import ui.panel.ChatPanel;
import ui.panel.GroupChatPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChatListItem extends JLabel {
    private static final long serialVersionUID = -2258475021340851543L;
    public User user;
    public Group group;
    private final ChatList parentChatList;
    public Color trueColor = UIConstant.CHAT_LIST_COLOR;
    public boolean isGroup;
    private final int index;
    private final MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {
            setBackground(trueColor);
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            setBackground(UIConstant.SELECTED_CHAT_LIST_COLOR);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            handleMouseClick();
        }
    };

    public ChatListItem(User user, ChatList parentChatList, int index){
        super();
        this.index = index;
        this.isGroup = false;
        this.user = user;
        this.parentChatList = parentChatList;
        JLabel userName = new JLabel(user.getName());
        Font font = new Font(UIConstant.FONT_TEXT, Font.BOLD, 15);
        userName.setFont(font);
        userName.setForeground(Color.white);
        this.add(userName);
        init(index);
    }

    public ChatListItem(Group group, ChatList parentChatList, int index){
        super();
        this.index = index;
        this.isGroup = true;
        this.group = group;
        this.parentChatList = parentChatList;
        JLabel groupName = new JLabel(group.getGroupName());
        Font font = new Font(UIConstant.FONT_TEXT, Font.BOLD, 20);
        groupName.setFont(font);
        groupName.setForeground(Color.white);
        this.add(groupName);
        init(index);
    }

    private void init(int index){
        this.setOpaque(true);
        if(index == 0){
            this.setBackground(UIConstant.SELECTED_CHAT_LIST_COLOR);
        } else {
            this.setBackground(UIConstant.CHAT_LIST_COLOR);
        }
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
        Dimension preferredSizeListItem = new Dimension(UIConstant.CHAT_LIST_WIDTH - UIConstant.CHAT_LIST_SCROLL_BAR_WIDTH,
                UIConstant.CHAT_LIST_ITEM_HEIGHT);
        this.setPreferredSize(preferredSizeListItem);
        this.addMouseListener(mouseListener);
    }

    public void handleMouseClick(){
        parentChatList.itemList.forEach(item->{
            item.trueColor = UIConstant.CHAT_LIST_COLOR;
            item.setBackground(trueColor);
        });
        trueColor = UIConstant.SELECTED_CHAT_LIST_COLOR;
        setBackground(trueColor);
        if(isGroup){
            GroupChatPanel.getInstance().switchChatArea(index);
        } else {
            ChatPanel.getInstance().switchChatArea(index);
        }
    }

    public String getChatGroupCode(){
        return group.getGroupCode();
    }

    public int getUserId(){
        return user.getUserId();
    }
}
