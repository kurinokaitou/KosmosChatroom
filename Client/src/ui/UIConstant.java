package ui;

import javax.swing.*;
import java.awt.*;

public class UIConstant {
    public final static  String CLIENT_NAME = "Kosmos聊天室";
    public final static  String CLIENT_VERSION = "1.0.0";

    public final static int MAIN_WINDOW_WIDTH = 900;
    public final static int MAIN_WINDOW_HEIGHT = 650;
    public final static int LOGIN_WINDOW_WIDTH = 500;
    public final static int LOGIN_WINDOW_HEIGHT = 350;

    public final static int SIDEBAR_WIDTH = 50;

    public final static int CHAT_LIST_WIDTH = 250;
    public final static int CHAT_LIST_SCROLL_BAR_WIDTH = 20;
    public final static int CHAT_LIST_ITEM_HEIGHT = 50;

    public final static int CHAT_AREA_WIDTH = 600;
    public final static int CHAT_AREA_HEIGHT = 450;

    public final static int CHAT_TITLE_HEIGHT = 50;
    public final static int CHAT_CARD_HEIGHT = 400;


    public final static int CHAT_INPUT_AREA_HEIGHT = 150;

    public final static ImageIcon CLIENT_ICON;
    public final static ImageIcon CHAT_ICON;
    public final static ImageIcon GROUP_CHAT_ICON;
    public final static ImageIcon LOGOUT_ICON;

    public final static ImageIcon CHAT_ICON_ENABLED;
    public final static ImageIcon GROUP_CHAT_ICON_ENABLED;
    public final static ImageIcon LOGOUT_ICON_ENABLED;
    public static final ImageIcon SEND_ICON;
    public static final ImageIcon ACCOUNT_ICON;

    public final static Color LIGHT_BACK_COLOR;
    public final static Color DEEP_BACK_COLOR;
    public final static Color SELECTED_BACK_COLOR;
    public final static Color CHAT_LIST_COLOR;
    public final static Color SELECTED_CHAT_LIST_COLOR;
    public static final String FONT_TEXT = "微软雅黑";
    public static final Color GRAY_BACK_COLOR;

    static{
        CLIENT_ICON = new ImageIcon("images/logo.png");
        SEND_ICON = new ImageIcon("images/send.png");
        ACCOUNT_ICON = new ImageIcon("images/account.png");
        CHAT_ICON = new ImageIcon("images/CHAT.png");
        GROUP_CHAT_ICON = new ImageIcon("images/GROUP_CHAT.png");
        LOGOUT_ICON = new ImageIcon("images/LOGOUT.png");
        CHAT_ICON_ENABLED = new ImageIcon("images/CHAT_ENABLED.png");
        GROUP_CHAT_ICON_ENABLED = new ImageIcon("images/GROUP_CHAT_ENABLED.png");
        LOGOUT_ICON_ENABLED = new ImageIcon("images/LOGOUT_ENABLED.png");

        LIGHT_BACK_COLOR = Color.WHITE;
        GRAY_BACK_COLOR = new Color(200, 200, 200);
        DEEP_BACK_COLOR = new Color(130, 62, 199);
        SELECTED_BACK_COLOR = new Color(79, 36, 123);
        CHAT_LIST_COLOR = new Color(62, 62, 62);
        SELECTED_CHAT_LIST_COLOR = new Color(100, 100, 100);
    }
}
