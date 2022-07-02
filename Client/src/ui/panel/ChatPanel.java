package ui.panel;

import controller.ClientManager;
import serializable.Group;
import serializable.User;
import ui.UIConstant;
import ui.component.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ChatPanel extends JPanel {
    private static final long serialVersionUID = 1830092116307811090L;
    public static SearchChatInput searchChatInput;
    public static ChatList chatList;
    public static Map<Integer, ChatArea> chatAreaMap;
    public static ChatArea currentChatArea;
    private static ChatInputArea chatInputArea;
    public static JPanel eastPanel;
    private static ChatPanel instance;
    int k = 0;
    private int currentChatIndex = 0;

    public static ChatPanel getInstance(){
        if(instance == null){
            instance = new ChatPanel();
        }
        return instance;
    }

    private ChatPanel(){
        super();
        searchChatInput = new SearchChatInput();
        chatList = new ChatList();
        chatAreaMap = new HashMap<>();
        eastPanel = new JPanel();
        chatInputArea = new ChatInputArea(false);
        if(ClientManager.userHistory != null){
            for (User user : ClientManager.userHistory.values()) {
                chatList.addChatListItem(new ChatListItem(user, chatList, k++));
            }
        }
        init();
    }

    private void init(){
        Dimension preferredSize = new Dimension(UIConstant.CHAT_AREA_WIDTH, UIConstant.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(chatList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Dimension chatListPreferredSize = new Dimension(UIConstant.CHAT_LIST_WIDTH, UIConstant.MAIN_WINDOW_HEIGHT);
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BorderLayout());
        scrollPanel.setPreferredSize(chatListPreferredSize);
        scrollPanel.add(scrollPane);
        this.add(scrollPanel,  BorderLayout.WEST);
        initChatArea();
        initChatInputArea();
    }

    private void initChatArea(){
        eastPanel.setBackground(UIConstant.LIGHT_BACK_COLOR);
        eastPanel.setPreferredSize(new Dimension(UIConstant.CHAT_AREA_WIDTH - UIConstant.CHAT_LIST_SCROLL_BAR_WIDTH,
                UIConstant.MAIN_WINDOW_HEIGHT));
        eastPanel.setLayout(new BorderLayout());
        currentChatArea = new ChatArea(chatList.itemList.get(0).user);
        chatAreaMap.put(0, currentChatArea);
        eastPanel.add(currentChatArea, BorderLayout.NORTH);
        this.add(eastPanel, BorderLayout.EAST);
    }

    private void initChatInputArea(){
        eastPanel.add(chatInputArea, BorderLayout.SOUTH);
    }

    public void switchChatArea(int index){
        if(currentChatIndex == index) return;
        currentChatIndex = index;
        if(currentChatArea != null){
            eastPanel.remove(currentChatArea);
        }
        if(chatAreaMap.containsKey(index)){
            currentChatArea = chatAreaMap.get(index);
        }else {
            currentChatArea = new ChatArea(chatList.itemList.get(index).user);
            chatAreaMap.put(index, currentChatArea);
        }
        eastPanel.add(currentChatArea, BorderLayout.NORTH);
        eastPanel.updateUI();
    }
}
