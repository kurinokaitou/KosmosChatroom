package ui.component;

import serializable.Group;
import serializable.User;
import ui.UIConstant;

import javax.swing.*;
import java.awt.*;

public class ChatArea extends JPanel {
    private static final long serialVersionUID = -4880241668060926352L;
    public ChatArea(User user){
        super();
        init("与"+user.getName()+"的私聊");

    }

    public ChatArea(Group group){
        super();
        init(group.getGroupName()+"群聊");
    }

    private void init(String text){
        Dimension preferredSize = new Dimension(UIConstant.CHAT_AREA_WIDTH, UIConstant.CHAT_AREA_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setLayout(new BorderLayout());
        this.setBackground(UIConstant.LIGHT_BACK_COLOR);

        JLabel titleLabel = new JLabel(text);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
        titleLabel.setBackground(UIConstant.DEEP_BACK_COLOR);
        Font font = new Font(UIConstant.FONT_TEXT, Font.BOLD, 30);
        titleLabel.setFont(font);
        titleLabel.setForeground(Color.white);
        this.add(titleLabel, BorderLayout.NORTH);
    }
}
