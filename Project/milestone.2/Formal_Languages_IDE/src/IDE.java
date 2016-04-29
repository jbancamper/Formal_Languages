
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javax.smartcardio.ATR;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.text.*;


public class IDE extends JFrame implements ActionListener, KeyListener{
    private final JSplitPane content;
    
    private final JMenuBar menuBar;
    private final JMenu file, edit, options;
    private final JMenuItem new_file, open, save;
    
    private JTextPane editor;
    private JScrollPane open_files;
    
    private JList files;

    public IDE(String n){
        super(n);
        
        menuBar = new JMenuBar();
        
        file = new JMenu("File");
        edit = new JMenu("Edit");
        options = new JMenu("Options");
        
        new_file = new JMenuItem("New File");
        open = new JMenuItem("Open File...");
        save = new JMenuItem("Save");
        
        new_file.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        
        file.add(new_file);
        file.add(open);
        file.add(save);
        
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(options);
        
        String a[] = {"index.txt", "loops.txt"};
        files = new JList(a);
        files.setBackground(new Color(190,190,190));
        files.setFocusable(false);
        
        editor = new JTextPane();
        editor.addKeyListener(this);
        
        open_files = new JScrollPane(files);
        open_files.setFocusable(false);
        
        content = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        content.setDividerSize(1);
        
        content.setDividerLocation(110);
        
        content.setLeftComponent(open_files);
        content.setRightComponent(editor);
        
        
        this.setSize(1200, 680);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setJMenuBar(menuBar);
        this.add(content);
    }
    
    

    public static void main(String[] args) {
        IDE a = new IDE("Formal Languages IDE");
        a.setLocationRelativeTo(null);
        a.setVisible(true);
    }
    
    public void open_file(File file) {
        Scanner in = null;
        
        try{
            in = new Scanner(file);
        }
        catch (FileNotFoundException ex) {
            
        }
        
        editor.setText("");
        Document doc = editor.getDocument();
        
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setForeground(attributes, Color.red);
        
        while(in.hasNextLine()){
            try{
                doc.insertString(doc.getLength(), in.nextLine() + "\n", attributes);
            }
            catch(BadLocationException ex){
                
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        
        if (e.getActionCommand().equals("Open File...")) {
            JFileChooser fc = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("Text files", ".txt");

            fc.addChoosableFileFilter(filter);
            fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnVal = fc.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
             
                System.out.println("Opening: " + file.getName() + ".\n");
                open_file(file);
            } 
            else {
                System.out.println("Open command cancelled by user.\n");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
