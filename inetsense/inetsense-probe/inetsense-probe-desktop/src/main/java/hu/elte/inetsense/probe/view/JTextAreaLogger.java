package hu.elte.inetsense.probe.view;

import java.io.File;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;

public class JTextAreaLogger implements TailerListener {
    private final JTextArea destination;
    
    private int counter;
    private static final int LINE_LIMIT = 500;

    public JTextAreaLogger(JTextArea destination, String logFile) {
        if (destination == null)
            throw new IllegalArgumentException("Destination is null");

        this.destination = destination;
        Tailer.create(new File(logFile), this, 1000);
    }

    @Override
    public void init(Tailer tailer) {
        destination.setText("");
    }

    @Override
    public void fileNotFound() {
        append("Log File not found!");
    }

    @Override
    public void fileRotated() {
    }

    @Override
    public void handle(String line) {
        append(line);
        counter++;
        if(counter > LINE_LIMIT) {
            reset();
        }
    }

    private void reset() {
        SwingUtilities.invokeLater(() -> {
            destination.setText("");
            counter = 0;
        });
    }

    protected void append(String line) {
        SwingUtilities.invokeLater(() -> {
            if(!destination.getText().contains(line)) {
                destination.append(line);
                destination.append("\n");
            } 
        });
    }

    @Override
    public void handle(Exception ex) {
        append(ex.getMessage());
    }

    
}
