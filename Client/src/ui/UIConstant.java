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
    public final static ImageIcon CLIENT_ICON;


    public final static Color BACK_COLOR;
    public final static Color TOOL_BAR_BACK_COLOR;
    public final static Color TABLE_LINE_COLOR;
    static{
         CLIENT_ICON = new ImageIcon("images/logo.png");
         BACK_COLOR = Color.WHITE;
         TOOL_BAR_BACK_COLOR = new Color(108, 92, 231);
         TABLE_LINE_COLOR = new Color(229, 229, 229);
    }
}
