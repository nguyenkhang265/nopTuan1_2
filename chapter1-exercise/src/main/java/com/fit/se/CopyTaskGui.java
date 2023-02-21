package com.fit.se;

import java.awt.Container;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CopyTaskGui extends JFrame{

    private JTextField tfFrom;
    private JTextField tfTo;
    private JButton btnCopy;
    private JProgressBar progressBar;

    public CopyTaskGui() {
        super("Copy Task");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        Container content = getContentPane();
        Box b, b1, b2, b3, b4;
        content.add(b = Box.createVerticalBox());
        b.add(b1 = Box.createHorizontalBox());
        b.add(b2 = Box.createHorizontalBox());
        b.add(b3 = Box.createHorizontalBox());
        b.add(b4 = Box.createHorizontalBox());

        b1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10,10), BorderFactory.createTitledBorder("From")));
        b2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10,10), BorderFactory.createTitledBorder("To")));
        b4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));

        b1.add(tfFrom = new JTextField(20));
        b2.add(tfTo = new JTextField(20));
        b3.add(btnCopy = new JButton("Copy"));
        b4.add(progressBar = new JProgressBar(0, 100));
        progressBar.setStringPainted(true);
        
        tfFrom.setText("T:/Lab 8_ARRAY ADVANCE.pdf");
        tfTo.setText("T:/Lab 8_ARRAY ADVANCE_copy.pdf");

        btnCopy.addActionListener(e -> {
            String from = tfFrom.getText().trim();
            String to = tfTo.getText().trim();
            CopyTask task = new CopyTask(from, to, progressBar);
            Thread t = new Thread(task);
            t.start();
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CopyTaskGui().setVisible(true);
            }
        });
    }
    
}

class CopyTask implements Runnable {

    private String from;
    private String to;
    private JProgressBar progressBar;

    public CopyTask(String from, String to, JProgressBar progressBar) {
        this.from = from;
        this.to = to;
        this.progressBar = progressBar;
    }

    @Override
    public void run() {

        try(
            FileInputStream fis = new FileInputStream(from);
            BufferedInputStream bis = new BufferedInputStream(fis);

            FileOutputStream fos = new FileOutputStream(to);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
        ){
            long total = new File(from).length();
            byte[] buffer = new byte[1024];
            long copied = 0l;

            int len;
            while((len = bis.read(buffer)) != -1){
                bos.write(buffer, 0, len);
                copied += len;
                int percent = (int) (copied * 100 / total);
                progressBar.setValue(percent);
                Thread.sleep(1);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}