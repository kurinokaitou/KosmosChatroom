package ui;

import command.ShutdownCommand;
import controller.ClientManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -8808883923263763897L;
    public MainFrame(){
        this.init();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void init(){
        this.setTitle(UIConstant.CLIENT_NAME);
        this.setIconImage(UIConstant.CLIENT_ICON.getImage());
        this.setBackground(UIConstant.BACK_COLOR);
        int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setBounds((x - this.getWidth())/2 - UIConstant.MAIN_WINDOW_WIDTH/2, (y - this.getHeight()) / 2 - UIConstant.MAIN_WINDOW_HEIGHT/2,
                UIConstant.MAIN_WINDOW_WIDTH, UIConstant.MAIN_WINDOW_HEIGHT);
        this.setResizable(true);

        //关闭窗口
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ClientManager.commandQueue.offer(new ShutdownCommand("shutdown"));
            }
        });
    }
}
