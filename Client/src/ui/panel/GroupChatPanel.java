package ui.panel;

import controller.ClientManager;
import serializable.Group;
import serializable.User;
import ui.UIConstant;
import ui.component.ChatArea;
import ui.component.ChatList;
import ui.component.ChatListItem;
import ui.component.SearchChatInput;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GroupChatPanel extends JPanel {
    private static final long serialVersionUID = 1830092116307811090L;
    public static SearchChatInput searchChatInput;
    public static ChatList chatList;
    public static Map<Integer, ChatArea> chatAreaMap;
    public static ChatArea currentChatArea;
    private static GroupChatPanel instance = null;
    public static JPanel eastPanel;
    int k = 0;

    public static GroupChatPanel getInstance(){
        if(instance == null){
            instance = new GroupChatPanel();
        }
        return instance;
    }
    private GroupChatPanel(){
        super();
        searchChatInput = new SearchChatInput();
        chatList = new ChatList();
        chatAreaMap = new HashMap<>();
        eastPanel = new JPanel();
        if(ClientManager.groupHistory != null){

            for (Group group : ClientManager.groupHistory) {
                chatList.addChatListItem(new ChatListItem(group, chatList, k++));
            }
        }
        init();
    }

    private void init(){
        Dimension preferredSize = new Dimension(UIConstant.CHAT_AREA_WIDTH, UIConstant.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(chatList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane,  BorderLayout.WEST);
        initChatArea();
    }

    private void initChatArea(){
        switchChatArea(0);
        eastPanel.setBackground(UIConstant.LIGHT_BACK_COLOR);
        eastPanel.setLayout(new BorderLayout());
        eastPanel.add(currentChatArea, BorderLayout.NORTH);
        this.add(eastPanel, BorderLayout.EAST);
    }

    public void switchChatArea(int index){
        if(currentChatArea != null){
            remove(currentChatArea);
        }
        if(chatAreaMap.containsKey(index)){
            currentChatArea = chatAreaMap.get(index);
        }else {
            currentChatArea = new ChatArea(chatList.itemList.get(index).group);
            chatAreaMap.put(index, currentChatArea);
        }
        eastPanel.add(currentChatArea, BorderLayout.NORTH);
        eastPanel.updateUI();
    }
}
