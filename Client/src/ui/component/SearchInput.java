package ui.component;

import command.GroupCreateCommand;
import command.GroupSearchCommand;
import command.SearchCommand;
import controller.ClientManager;
import ui.UIConstant;

import javax.swing.*;
import java.awt.*;

public class SearchInput extends JPanel {
    private static final long serialVersionUID = 2403192533025166529L;
    private final JTextField textField;
    private final boolean isGroup;
    public SearchInput(boolean isGroup){
        super();
        this.isGroup = isGroup;
        this.textField = new JTextField();
        init();
    }

    private void init() {
        this.setBackground(UIConstant.CHAT_LIST_COLOR);
        textField.setOpaque(true);
        textField.setBackground(UIConstant.CHAT_LIST_COLOR);
        textField.setForeground(UIConstant.LIGHT_BACK_COLOR);
        textField.setFont(new Font(UIConstant.FONT_TEXT, Font.BOLD, 18));
        if(isGroup){
            this.setLayout(new BorderLayout());
            this.setPreferredSize(new Dimension(UIConstant.CHAT_LIST_WIDTH, UIConstant.CHAT_LIST_ITEM_HEIGHT));
            textField.setPreferredSize(new Dimension(UIConstant.CHAT_LIST_WIDTH - 100, UIConstant.CHAT_LIST_ITEM_HEIGHT));
            JButton searchButton = makeSearchButton();
            JButton addButton = makeAddButton();
            this.add(textField, BorderLayout.WEST);
            this.add(addButton, BorderLayout.CENTER);
            this.add(searchButton, BorderLayout.EAST);
        } else {
            this.setLayout(new BorderLayout());
            this.setPreferredSize(new Dimension(UIConstant.CHAT_LIST_WIDTH, UIConstant.CHAT_LIST_ITEM_HEIGHT));
            textField.setPreferredSize(new Dimension(UIConstant.CHAT_LIST_WIDTH - 50, UIConstant.CHAT_LIST_ITEM_HEIGHT));
            JButton searchButton = makeSearchButton();
            this.add(textField, BorderLayout.WEST);
            this.add(searchButton, BorderLayout.EAST);
        }
    }

    private JButton makeSearchButton(){
        JButton button = new JButton();
        button.setIcon(UIConstant.SEARCH_ICON);
        button.setPressedIcon(UIConstant.SEARCH_ICON_ENABLED);
        button.setPreferredSize(new Dimension(50, UIConstant.CHAT_LIST_ITEM_HEIGHT));
        button.setBackground(UIConstant.CHAT_LIST_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusable(true);
        if(isGroup){
            button.addActionListener((e -> {
                if(textField.getText().length() != 0){
                    ClientManager.commandQueue.offer(new GroupSearchCommand("groupSearch", textField.getText()));
                }
            }));
        } else {
            button.addActionListener((e -> {
                if(textField.getText().length() != 0){
                    ClientManager.commandQueue.offer(new SearchCommand("search", textField.getText()));
                }
            }));
        }
        return button;
    }

    private JButton makeAddButton(){
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusable(true);
        button.setIcon(UIConstant.ADD_ICON);
        button.setPressedIcon(UIConstant.ADD_ICON_ENABLED);
        button.setPreferredSize(new Dimension(50, UIConstant.CHAT_LIST_ITEM_HEIGHT));

        button.addActionListener((e -> {
            if(textField.getText().length() != 0){
                ClientManager.commandQueue.offer(new GroupCreateCommand("groupCreate", textField.getText()));
            }
        }));
        return button;
    }

}
