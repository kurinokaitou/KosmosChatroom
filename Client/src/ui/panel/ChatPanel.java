package ui.panel;

import controller.ClientManager;
import serializable.Group;
import serializable.Message;
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
    private static final ChatPanel instance = new ChatPanel();
    int k = 0;
    private int currentChatIndex = 0;

    public static ChatPanel getInstance(){
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
                addChatListItem(user);
            }
        }
        init();
    }

    private void init(){
        Dimension preferredSize = new Dimension(UIConstant.CHAT_AREA_WIDTH, UIConstant.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(chatList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Dimension chatListPreferredSize = new Dimension(UIConstant.CHAT_LIST_WIDTH, UIConstant.MAIN_WINDOW_HEIGHT);
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setOpaque(true);
        scrollBar.setBackground(UIConstant.CHAT_LIST_COLOR);
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BorderLayout());
        scrollPanel.setPreferredSize(chatListPreferredSize);
        scrollPanel.add(scrollPane);
        scrollPanel.add(new SearchInput(false), BorderLayout.NORTH);
        this.add(scrollPanel,  BorderLayout.WEST);
        initChatArea();
        initChatInputArea();
    }

    private void initChatArea(){
        eastPanel.setBackground(UIConstant.LIGHT_BACK_COLOR);
        eastPanel.setPreferredSize(new Dimension(UIConstant.CHAT_AREA_WIDTH - UIConstant.CHAT_LIST_SCROLL_BAR_WIDTH,
                UIConstant.MAIN_WINDOW_HEIGHT));
        eastPanel.setLayout(new BorderLayout());
        if(!chatList.itemList.isEmpty()){
            currentChatArea = new ChatArea(chatList.itemList.get(0).user);
            chatAreaMap.put(0, currentChatArea);
            eastPanel.add(currentChatArea, BorderLayout.NORTH);
        }
        this.add(eastPanel, BorderLayout.EAST);
    }

    private void initChatInputArea(){
        eastPanel.add(chatInputArea, BorderLayout.SOUTH);
    }

    public void switchChatArea(int index){
        if(currentChatArea != null){
            eastPanel.remove(currentChatArea);
        }
        currentChatIndex = index;
        if(chatAreaMap.containsKey(index)){
            currentChatArea = chatAreaMap.get(index);
        }else {
            currentChatArea = new ChatArea(chatList.itemList.get(index).user);
            chatAreaMap.put(index, currentChatArea);
        }
        eastPanel.add(currentChatArea, BorderLayout.NORTH);
        eastPanel.updateUI();
    }

    public void distributeMessage(Message message){
        boolean distributed = false;
        for(ChatListItem item: chatList.itemList){
            if(item.user.getUserId() == message.fromUser.getUserId()){
                switchChatArea(item.index);
                chatAreaMap.get(item.index).addMessage(message);
                distributed = true;
            }
        }
        if(!distributed){
            if(!ClientManager.userHistory.containsKey(message.fromUser.getName())){
                ClientManager.userHistory.put(message.fromUser.getName(), message.fromUser);
                addChatListItem(message.fromUser);
                currentChatArea.addMessage(message);
            }
            switchChatArea(k-1);    // 上一个
        }
    }

    public void addChatListItem(User user){
        chatList.addChatListItem(new ChatListItem(user, chatList, k++));
    }
}
