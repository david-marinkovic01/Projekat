package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
public class PredlogKorisnickogInterfejsa extends JDialog {
    private JPanel contentPane;
    private JButton buttonClose;
    private JButton buttonSave;
    private JButton buttonOpen;
    private JButton buttonGetSelection;
    private JTextArea textAreaTop;
    private JTextArea textAreaBottom;
    private JTextArea textAreaNew;
    private JButton buttonOpenBottom;
    private JButton buttonGetSelectionB;
    String directory;
    String selection;
    private Object args;
    public PredlogKorisnickogInterfejsa() {
        setContentPane(contentPane);
        setModal(true);

        buttonOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onbuttonOpenTop();
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonSave();
            }
        });

        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonClose();
            }
        });

        buttonGetSelection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonGetSelection();
            }
        });

        buttonOpenBottom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onbuttonOpenBottom();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onButtonClose();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        buttonGetSelectionB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onButtonGetSelectionB();
            }
        });
    }

    private void onButtonSave() {
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.SAVE);
        f.setDirectory(directory);
        f.setVisible(true);
        directory = f.getDirectory();
        saveFile(directory, f.getFile());
        f.dispose();
    }
    public void saveFile(String directory, String filename) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileWriter out = null;
        try {
            file = new File(directory, filename);
            out = new FileWriter(file);
            textAreaTop.getLineCount();
            textAreaBottom.getLineCount();
            String s = textAreaTop.getText();
            out.write(s);
        }
        catch (IOException e) {
            textAreaTop.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("FileViewer: " + filename + ": I/O Exception");
            textAreaBottom.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("FileViewer: " + filename + ": I/O Exception");
        }
        finally {
            try {
                if (out != null)
                    out.close();
            }
            catch (IOException e) {
            }
        }
    }

    private void onButtonGetSelection() {
        selection = textAreaTop.getSelectedText();
        textAreaNew.append(selection);
    }

    private void onButtonGetSelectionB() {
        selection = textAreaBottom.getSelectedText();
        textAreaNew.append(selection);
    }

    private void onButtonClose() {
        dispose();
    }

    private void onbuttonOpenTop() {
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.LOAD);
        f.setDirectory(directory);
        f.setVisible(true);
        directory = f.getDirectory();
        loadAndDisplayFile(directory, f.getFile());
        f.dispose();
    }

    private void onbuttonOpenBottom() {
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.LOAD);
        f.setDirectory(directory);
        f.setVisible(true);
        directory = f.getDirectory();
        loadAndDisplayFiles(directory, f.getFile());
        f.dispose();
    }

    private void loadAndDisplayFiles(String directory, String filename) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileReader in = null;

        try {
            file = new File(directory, filename);
            in = new FileReader(file);
            char[] buffer = new char[4096];
            int len;
            textAreaBottom.setText("");
            while ((len = in.read(buffer)) != -1) {
                String s = new String(buffer, 0, len);
                textAreaBottom.append(s);
            }
            this.setTitle("FileViewer: " + filename);
            textAreaBottom.setCaretPosition(0);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAndDisplayFile(String directory, String filename) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileReader in = null;

        try {
            file = new File(directory, filename);
            in = new FileReader(file);
            char[] buffer = new char[4096];
            int len;
            textAreaTop.setText("");
            while ((len = in.read(buffer)) != -1) {
                String s = new String(buffer, 0, len);
                textAreaTop.append(s);
            }
            this.setTitle("FileViewer: " + filename);
            textAreaTop.setCaretPosition(0);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main (String[] args) {
        PredlogKorisnickogInterfejsa dialog = new PredlogKorisnickogInterfejsa();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}