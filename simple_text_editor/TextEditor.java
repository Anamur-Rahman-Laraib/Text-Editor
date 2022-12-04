import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.*;

public class TextEditor implements ActionListener {
    JFrame frame;
    JTextArea textarea;
    JMenuBar menubar;
    TextEditor()
    {
        frame = new JFrame("Text Editor");
        textarea = new JTextArea();

        menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");

        JMenuItem openfile = new JMenuItem("open");
        JMenuItem savefile = new JMenuItem("save");
        JMenuItem print = new JMenuItem("print");
        JMenuItem newfile = new JMenuItem("new");

        openfile.addActionListener(this);
        savefile.addActionListener(this);
        print.addActionListener(this);
        newfile.addActionListener(this);

        file.add(openfile);
        file.add(savefile);
        file.add(print);
        file.add(newfile);

        JMenuItem cut = new JMenuItem("cut");
        JMenuItem copy = new JMenuItem("copy");
        JMenuItem paste = new JMenuItem("paste");
        JMenuItem close = new JMenuItem("close");

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        close.addActionListener(this);

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(close);

        menubar.add(file);
        menubar.add(edit);

        frame.setJMenuBar(menubar);
        frame.add(textarea);
        frame.setVisible(true);
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }


    public static void main (String[] args)
    {
        TextEditor object = new TextEditor();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String call = e.getActionCommand();
        if(call == "new")
        {
            textarea.setText("");
        }
        else if(call == "cut")
        {
            textarea.cut();
        }
        else if(call == "copy")
        {
            textarea.copy();
        }
        else if(call == "paste")
        {
            textarea.paste();
        }
        else if(call == "close")
        {
            frame.setVisible(false);
        }
        else if(call == "save")
        {
            JFileChooser filechooser = new JFileChooser("C:");
            int verdict = filechooser.showOpenDialog(null);
            if(verdict == filechooser.APPROVE_OPTION)
            {
                File file =  new File(filechooser.getSelectedFile().getAbsolutePath());
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(file,false));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    writer.write(textarea.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    writer.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else if(call == "open")
        {
            JFileChooser filechooser2 = new JFileChooser("C:");
            int variable = filechooser2.showOpenDialog(null);
            if(variable == JFileChooser.APPROVE_OPTION)
            {
                File file = new File(filechooser2.getSelectedFile().getAbsolutePath());
                try {
                    String s1 = "", s2 = "";
                    BufferedReader bufferreader = new BufferedReader(new FileReader(file));
                    try {
                        s2 = bufferreader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    while(true)
                    {
                        try {
                            if (!((s1 = bufferreader.readLine()) != null)) break;
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        s2 += s1+"\n";
                    }
                    textarea.setText(s2);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else if(call == "print")
        {
            try {
                textarea.print();
            } catch (PrinterException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
