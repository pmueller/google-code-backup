package view;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;


import util.LogVisitor;

public class LogView extends ViewPort {

    //the textfield which is the log
    private JTextArea taLog;
    
    public LogView(int componentWidth, int componentHeight) {
        //set size and color of this
        this.setSize(componentWidth, componentHeight);
        this.setBackground(Color.LIGHT_GRAY);

        //make new area for text, not editable, set size
        taLog = new JTextArea();
        taLog.setEditable(false);
        taLog.setColumns(60);
        taLog.setRows(10);
        taLog.setAutoscrolls(false);

        //make it so the log autoscrolls
        DefaultCaret caret = (DefaultCaret)taLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        //make the scrollbar
        JScrollPane sp = new JScrollPane(taLog);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.add(sp);
    }

    /**
     * send a visitor to model and update log if needed
     */
    public void update() {

        //make visitor and send to model
        LogVisitor lv  = new LogVisitor();
        View.getInstance().sendLogVisitorToModel(lv);
        //if there are any new entries, show them on screen
        for(String logEntry : lv.getLogUpdates()) {
            taLog.append(logEntry + "\n");
        }

    }

}
