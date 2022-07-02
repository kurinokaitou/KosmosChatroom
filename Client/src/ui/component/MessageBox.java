package ui.component;

import ui.UIConstant;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MessageBox extends JLabel {
    private static final long serialVersionUID = -8577154140296351399L;
//    private int messageWidth;
//    private int messageHeight;
    private final Paint p = new GradientPaint(0.0f, 0.0f, UIConstant.DEEP_BACK_COLOR,
            getWidth(), getHeight(),UIConstant.DEEP_BACK_COLOR, true);
    public MessageBox(String content) {
        super(content);
        Font font = new Font(UIConstant.FONT_TEXT, Font.BOLD, 18);
        this.setFont(font);
    }
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(p);
        g2d.setColor(UIConstant.LIGHT_BACK_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(UIConstant.SELECTED_BACK_COLOR);
        Shape shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 6.5D, 6.5D);
        g2d.draw(shape);
    }
}
