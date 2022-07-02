package ui.component;

import ui.UIConstant;
import ui.panel.ChatPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChatList extends JPanel {
    private static final long serialVersionUID = 9211182535663928995L;
    public List<ChatListItem> itemList;
    public ChatList(){
        super();
        itemList = new ArrayList<>();
        init();
    }

    private void init(){
        Dimension preferredSize = new Dimension(UIConstant.CHAT_LIST_WIDTH - UIConstant.CHAT_LIST_SCROLL_BAR_WIDTH
                , UIConstant.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setBackground(UIConstant.CHAT_LIST_COLOR);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    }

    public void addChatListItem(ChatListItem item){
        itemList.add(item);
        this.add(item);
        this.updateUI();
    }
}
