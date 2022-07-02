package ui.component;

import serializable.Message;

import javax.swing.*;
public class MessageCard extends JLabel {
    private static final long serialVersionUID = -2736285248535104133L;
    private final Message message;
    private final MessageBox messageBox;

    public MessageCard(Message message){
        super();
        this.message = message;
        this.messageBox = new MessageBox(message.content);
        init();
    }

    private void init(){
        this.add(messageBox);
    }
}
