package hu.elte.inetsense.probe.view;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.common.service.configuration.EnvironmentService;

@Component
public class ProbeView extends JFrame {

    private static final long serialVersionUID = 1L;

    public ProbeView(@Autowired EnvironmentService environmentService) {
        setSize(1200, 400);
        initTextarea(environmentService);

        try {
            initTray();
            setDefaultCloseOperation(HIDE_ON_CLOSE);
        } catch (Exception e) {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }

    private void initTray() throws AWTException {
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(createImage("/icon_16.png", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();

        // Create a pop-up menu components
        MenuItem openItem = new MenuItem("Open");
        MenuItem exitItem = new MenuItem("Exit");

        openItem.addActionListener(e -> {
            setVisible(true);
        });

        exitItem.addActionListener(e -> {
            tray.remove(trayIcon);
            System.exit(0);
        });

        popup.add(openItem);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        tray.add(trayIcon);

    }

    protected void initTextarea(EnvironmentService environmentService) {
        JTextArea jLoggingArea = new JTextArea(5, 0);
        jLoggingArea.setEditable(false);
        jLoggingArea.setFont(new Font("Courier", Font.PLAIN, 12));

        // Make scrollable console pane
        JScrollPane jConsoleScroll = new JScrollPane(jLoggingArea);
        jConsoleScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jConsoleScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(jConsoleScroll);
        new JTextAreaLogger(jLoggingArea, environmentService.getLogFileLocation());
    }

    protected static Image createImage(String path, String description) {
        URL imageURL = ProbeView.class.getResource(path);
        return (new ImageIcon(imageURL, description)).getImage();
    }
}
