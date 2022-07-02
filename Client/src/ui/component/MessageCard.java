package ui.component;

import serializable.Message;
import serializable.User;
import ui.UIConstant;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class MessageCard extends JPanel {
    private static final long serialVersionUID = -2736285248535104133L;
    private final Message message;
    private final JPanel messageBubble;
    private int bubbleWidth;
    private int bubbleHeight;
    private final int fontSize = 18;
    private final List<String> messageRows;
    private final boolean fromSelf;

    public MessageCard(Message message, boolean fromSelf){
        super();
        this.message = message;
        this.fromSelf = fromSelf;
        this.messageRows = new ArrayList<>();
        if(fromSelf){
            messageBubble = new RightArrowBubble();
        } else {
            messageBubble = new LeftArrowBubble();
        }
        calcBubbleSize();
        init();
    }

    private void init(){
        if(fromSelf){
            this.setLayout(new FlowLayout(FlowLayout.RIGHT, 25, 13));
        } else {
            this.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 13));
        }
        this.setPreferredSize(new Dimension(UIConstant.CHAT_AREA_WIDTH, bubbleHeight + 20));
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BorderLayout());
        wrapper.setPreferredSize(new Dimension(bubbleWidth, bubbleHeight));
        wrapper.add(messageBubble);
        JButton avatar = makeAvatar();
        JTextPane textPane = new JTextPane();
        textPane.setText(message.time);
        textPane.setFont(new Font(UIConstant.FONT_TEXT, Font.ITALIC, 12));
        textPane.setOpaque(true);
        textPane.setBackground(UIConstant.GRAY_BACK_COLOR);
        if(fromSelf){
            this.add(textPane);
            this.add(wrapper);
            this.add(avatar);
        }else {
            this.add(avatar);
            this.add(wrapper);
            this.add(textPane);
        }

    }

    private JButton makeAvatar(){
        JButton avatar = new JButton(message.fromUser.getName(), UIConstant.ACCOUNT_ICON);
        avatar.setBorderPainted(false);
        avatar.setFocusPainted(false);
        avatar.setContentAreaFilled(false);
        avatar.setFocusable(false);
        avatar.setMargin(new Insets(0, 0, 0, 0));
        return avatar;
    }

    private void calcBubbleSize(){
        int len = message.content.length();
        if(len <= 15){
            bubbleWidth = fontSize * len + 30;
            bubbleHeight = fontSize + 30;
            messageRows.add(message.content);
        } else {
            bubbleWidth = fontSize * 15 + 60;
            bubbleHeight = fontSize * (len / 15 + 1) + 30;
            int begin = 0;
            int end = 14;
            do {
                messageRows.add(message.content.substring(begin,end));
                begin = end;
                if(end+15 < len){
                    end += 15;
                }else {
                    end = len;
                }
            }while (end != len);
            messageRows.add(message.content.substring(begin,end));
        }
    }

    public class LeftArrowBubble extends JPanel {
        private static final long serialVersionUID = -8935291604349033019L;
        private final int strokeThickness = 5;
        private final int padding = strokeThickness / 2;
        private final int radius = 10;
        private final int arrowSize = 6;

        @Override
        protected void paintComponent(final Graphics g) {
            final Graphics2D graphics2D = (Graphics2D) g;
            RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics2D.setRenderingHints(qualityHints);
            graphics2D.setColor(UIConstant.GRAY_BACK_COLOR);
            graphics2D.setStroke(new BasicStroke(strokeThickness));
            int x = padding + strokeThickness + arrowSize;
            int width = getWidth() - arrowSize - (strokeThickness * 2);
            int height = getHeight() - strokeThickness;
            graphics2D.fillRect(x, padding, width, height);
            RoundRectangle2D.Double rect = new RoundRectangle2D.Double(x, padding, width, height, radius, radius);
            Polygon arrow = new Polygon();
            arrow.addPoint(14, 6);
            arrow.addPoint(arrowSize + 2, 10);
            arrow.addPoint(14, 12);
            Area area = new Area(rect);
            area.add(new Area(arrow));
            graphics2D.draw(area);
            // Define rendering hint, font name, font style and font size
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setFont(new Font(UIConstant.FONT_TEXT, Font.BOLD, fontSize));
            graphics2D.setPaint(UIConstant.SELECTED_BACK_COLOR);

            int y = 30;
            // Draw Hello World String
            for(String str: messageRows){
                graphics2D.drawString(str, 24, y);
                y += fontSize*1.1;
            }
            graphics2D.dispose();
        }

    }

    public class RightArrowBubble extends JPanel {

        private static final long serialVersionUID = 936320232781597224L;
        private final int strokeThickness = 5;
        private final int padding = strokeThickness / 2;
        private final int radius = 10;
        private final int arrowSize = 6;

        public RightArrowBubble(){
            this.setPreferredSize(new Dimension(bubbleWidth, bubbleHeight));
        }

        @Override
        protected void paintComponent(final Graphics g) {
            final Graphics2D graphics2D = (Graphics2D) g;
            RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics2D.setRenderingHints(qualityHints);
            graphics2D.setColor(UIConstant.SELECTED_BACK_COLOR);
            graphics2D.setStroke(new BasicStroke(strokeThickness));
            int width = getWidth() - arrowSize - (strokeThickness * 2);
            int height = getHeight() - strokeThickness;
            graphics2D.fillRect(padding, padding, width, height);
            RoundRectangle2D.Double rect = new RoundRectangle2D.Double(padding, padding, width, height, radius, radius);
            Polygon arrow = new Polygon();
            arrow.addPoint(width, 6);
            arrow.addPoint(width + arrowSize, 10);
            arrow.addPoint(width, 12);
            Area area = new Area(rect);
            area.add(new Area(arrow));
            graphics2D.draw(area);
            // Define rendering hint, font name, font style and font size
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setFont(new Font(UIConstant.FONT_TEXT, Font.BOLD, fontSize));
            graphics2D.setPaint(UIConstant.GRAY_BACK_COLOR);

            int y = 30;
            // Draw Hello World String
            for(String str: messageRows){
                graphics2D.drawString(str, 12, y);
                y += fontSize*1.1;
            }
            graphics2D.dispose();
        }

    }
}
