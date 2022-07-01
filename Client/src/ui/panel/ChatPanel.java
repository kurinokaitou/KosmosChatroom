package ui.panel;

import controller.ClientManager;
import serializable.Group;
import serializable.User;
import ui.UIConstant;
import ui.component.ChatList;
import ui.component.ChatListItem;
import ui.component.SearchChatInput;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {
    private static final long serialVersionUID = 1830092116307811090L;
    public final SearchChatInput searchChatInput;
    public final ChatList chatList;
    public ChatPanel(boolean isGroup){
        super();
        searchChatInput = new SearchChatInput();
        chatList = new ChatList();
        if(isGroup){
           if(ClientManager.groupHistory != null){
               for(Group group : ClientManager.groupHistory){
                   chatList.addChatListItem(new ChatListItem(group));
               }
           }
        } else {
            if(ClientManager.userHistory != null){
                for (User user : ClientManager.userHistory.values()) {
                    chatList.addChatListItem(new ChatListItem(user));
                }
            }
        }
        init();
    }


    private void init(){
        Dimension preferredSize = new Dimension(600, UIConstant.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setLayout(new BorderLayout());
        this.add(chatList,  BorderLayout.WEST);
        initChatArea();
    }



    private void initChatArea(){
        JPanel chatArea = new JPanel();
        Dimension preferredSize = new Dimension(UIConstant.CHAT_AREA_WIDTH, UIConstant.MAIN_WINDOW_HEIGHT);
        chatArea.setPreferredSize(preferredSize);
        chatArea.setBackground(UIConstant.LIGHT_BACK_COLOR);
        this.add(chatArea, BorderLayout.EAST);
    }
}
