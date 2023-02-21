package com.fit.se;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class MiniNotepad extends JFrame {

    private JMenuItem menuItemOpen;
    private JTextArea ta;
    private JProgressBar progressBar;

    public MiniNotepad() {
        super("Mini Notepad");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        JMenuBar menubar;
        setJMenuBar(menubar = new JMenuBar());
        JMenu menuFile;
        menubar.add(menuFile = new JMenu("File"));
        menuFile.add(menuItemOpen = new JMenuItem("Open"));
        menuItemOpen.setMnemonic('O');
        menuItemOpen.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        
        add(new JScrollPane(ta = new JTextArea()));
        add(progressBar = new JProgressBar(0, 100), "South");
        progressBar.setStringPainted(true);

        menuItemOpen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./chapter1-exercise/data"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                ta.setText("");
               File file = fileChooser.getSelectedFile();
                NotepadTask task = new NotepadTask(file, ta, progressBar);
                task.execute();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MiniNotepad notepad = new MiniNotepad();
            notepad.setVisible(true);
        });
    }
    
}


class NotepadTask extends SwingWorker<Long, Long>{
    private File file;
    private JTextArea ta;
    private JProgressBar progressBar;

    JButton btn;
    
    public NotepadTask(File file, JTextArea ta, JProgressBar progressBar) {
        this.file = file;
        this.ta = ta;
        this.progressBar = progressBar;
    }
    
    @Override
    protected Long doInBackground() throws Exception {

        try (
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
        ) {
            char[] buffer = new char[1024];
            int len;
            long total = file.length();
            long loaded = 0;

            while ((len = br.read(buffer)) != -1) {
                loaded += len;
                
                ta.append(new String(buffer, 0, len));

                progressBar.setValue((int) (loaded * 100 / total));

                Thread.sleep(10);
            }

            return loaded;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void done() {
       try {
        JOptionPane.showMessageDialog(null, "Done. Loaded " + get() + " bytes");
    } catch (Exception e) {
        e.printStackTrace();
    } 
    }

    

}