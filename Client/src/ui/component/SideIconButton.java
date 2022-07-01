package ui.component;

import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 自定义按钮类，支持自定义默认图标、激活图标和tip提示
 *
 * @author Bob
 */
public class SideIconButton extends JButton {

    private static final long serialVersionUID = 1L;
    private final ImageIcon iconEnable;
    private final String tip;

    /**
     * 构造
     *
     * @param iconNormal  默认图标
     * @param iconEnable  激活图标
     * @param tip         提示
     */
    public SideIconButton(ImageIcon iconNormal, ImageIcon iconEnable, String tip) {
        super(iconNormal);
        this.iconEnable = iconEnable;
        this.tip = tip;
        initialize();
    }

    /**
     * 初始化，设置按钮属性：无边，无焦点渲染，无内容区，各边距0
     */
    private void initialize() {
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusable(true);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setRolloverIcon(iconEnable);
        this.setPressedIcon(iconEnable);
        this.setToolTipText(tip);
    }
}