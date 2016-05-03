

import java.awt.Color;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.*;
import javax.swing.text.*;


public class IDE extends JFrame implements ActionListener, KeyListener,
        ListSelectionListener{
    private final JSplitPane content;
    
    private final JMenuBar menuBar;
    private final JMenu file, edit, options, colorScheme;
    private final JMenuItem new_file, open, save, cut, copy, paste, light, dark;
    
    private ArrayList<String> fileContents;
    
    public JTextPane editor;
    private JScrollPane open_files;
    
    private JList files;
    
    private HashMap<Character, Integer> key;
    
    DefaultListModel model = new DefaultListModel();
    
    private long lastPressProcessed = 0;

    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println();
    }
    
    public enum States{
      q0(0), q1(1), q2(2), q3(3), q4(4), q5(5), q6(6), q7(7), q8(8), q9(9),
      q10(10), q11(11), q12(12), q13(13), q14(14), q15(15), q16(16), q17(17), q18(18),
      q19(19), q20(20), q21(21), q22(22), q23(23), q24(24), q25(25), q26(26), q27(27),
      q28(28), q29(29), q30(30), q31(31), q32(32), q33(33), q34(34), q35(35), q36(36),
      q37(37), q38(38), q39(39);
      
      private int s;
      
      private States(int a){
          this.s = a;
      }
    };
    
    private boolean dark_theme;
    
    private int delta[][] = {
        //    a            b             c           d           e              f           g            h            i             j           k            l               m           n           o           p           q               r           s           t           u               v           w               x           y           z           0               1             2             3             4             5           6               7           8             9              (               )            +           =           space           "            -             *
        {States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q2.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q3.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q32.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q4.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q6.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q4.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q5.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q4.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q8.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q7.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q9.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q11.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q13.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q10.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q14.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q2.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q3.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q32.s},
        {States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q12.s, States.q18.s, States.q18.s, States.q18.s, States.q18.s, States.q18.s, States.q18.s, States.q18.s, States.q18.s, States.q18.s, States.q18.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q17.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q15.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q16.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q29.s, States.q23.s, States.q23.s, States.q23.s, States.q23.s, States.q23.s, States.q23.s, States.q23.s, States.q23.s, States.q23.s, States.q23.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q24.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q19.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q2.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q3.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q13.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q32.s},
        {States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q20.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q21.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q20.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q21.s, States.q22.s, States.q39.s, States.q39.s},
        {States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q2.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q3.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q13.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q32.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q16.s, States.q39.s, States.q30.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q25.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q26.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q27.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q26.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q27.s, States.q28.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q30.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q30.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q31.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q2.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q3.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q13.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q32.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q33.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q34.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q35.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q36.s, States.q39.s, States.q39.s, States.q39.s},
        {States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q35.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q36.s, States.q39.s, States.q37.s, States.q39.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q38.s},
        {States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q2.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q3.s, States.q1.s, States.q1.s, States.q1.s, States.q1.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q13.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q32.s},
        {States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s, States.q39.s}
    };
    
    private int state;

    public IDE(String n){
        super(n);
        
        menuBar = new JMenuBar();
        
        file = new JMenu("File");
        edit = new JMenu("Edit");
        options = new JMenu("Options");
        
        new_file = new JMenuItem("New File");
        open = new JMenuItem("Open File...");
        save = new JMenuItem("Save");
        
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        
        colorScheme = new JMenu("Color Scheme");
        
        light = new JMenuItem("Light Theme");
        dark = new JMenuItem("Dark Theme");
        
        new_file.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        
        light.addActionListener(this);
        dark.addActionListener(this);
        
        file.add(new_file);
        file.add(open);
        file.add(save);
        
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        
        colorScheme.add(light);
        colorScheme.add(dark);
        
        options.add(colorScheme);
        
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(options);
        
        files = new JList(model);
        files.setBackground(new Color(220,220,220));
        files.setFocusable(false);
        
        files.addListSelectionListener((ListSelectionListener) this);
        
        editor = new JTextPane();
        editor.addKeyListener(this);
        editor.setFont(new Font("Courier", 0, 12));
        
        
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
        
        dark_theme = false;
        
        fileContents = new ArrayList<>();
        
        state = States.q0.s;
        
        key = new HashMap();
        loadMap();
    }
    
    private void loadMap(){
        try{
            Scanner in = new Scanner(new File("mappings.txt"));
            while(in.hasNextLine()){
                char c = (char)in.nextInt();
                int a = in.nextInt();
                
                key.put(c, a);
            }
        }
        catch(FileNotFoundException ex){
            
        }
        
    }

    public static void main(String[] args) {
        IDE a = new IDE("Formal Languages IDE");
        a.setLocationRelativeTo(null);
        a.setVisible(true);
    }
    
    public void openFile(File file) {
        Scanner in = null;
        
        try{
            in = new Scanner(file);
        }
        catch (FileNotFoundException ex) {
            
        }
        
        editor.setText("");
        Document doc = editor.getDocument();
        
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        
        while(in.hasNextLine()){
            try{
                doc.insertString(doc.getLength(), in.nextLine() + "\n", attributes);
            }
            catch(BadLocationException ex){}
        }
        
        lex();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equals("Open File...")) {
            JFileChooser fc = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("Text files", ".txt");

            fc.addChoosableFileFilter(filter);
            fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int returnVal = fc.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                model.add(files.getModel().getSize(), file.getName());
                System.out.println("Opening: " + file.getName() + ".\n");
                openFile(file);
            } 
            else {
                System.out.println("Open command cancelled by user.\n");
            }
        }
        else if(e.getActionCommand().equals("New File")){
            model.add(files.getModel().getSize(), "untitiled");
        }
        
        else if(e.getActionCommand().equals("Save")){
            JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
            fc.showSaveDialog(null);
            
            int retrival = fc.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try {
                    FileWriter fw = new FileWriter(fc.getSelectedFile() + ".txt");
                    fw.write(editor.getText());
                    fw.close();
                } 
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        else if(e.getActionCommand().equals("Cut")){
            String source = editor.getText();
            
            editor.setText(source.replace(editor.getSelectedText(), ""));
        }
        else if(e.getActionCommand().equals("Copy")){
            StringSelection stringSelection = new StringSelection(editor.getSelectedText());
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            clpbrd.setContents(stringSelection, null);
        }
        else if(e.getActionCommand().equals("Paste")){
            try {
                String result = "";
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                
                clipboard.getContents(null);
                
                int caretPosition = editor.getCaretPosition();
                
                editor.getDocument().insertString(
                        caretPosition, 
                        (String) clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor), 
                        null
                );
                
                lex();
            } 
            catch (BadLocationException | UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(IDE.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(e.getActionCommand().equals("Light Theme")){
            editor.setBackground(new Color(255, 255, 255));
            dark_theme = false;
            lex();
        }
        else if(e.getActionCommand().equals("Dark Theme")){
            editor.setBackground(new Color(0, 21, 75));
            dark_theme = true;
            lex();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        lex();
    }
    
    public void lex(){
        String source = editor.getText();
        char c;
        int input = -1, strCount = 0, commentCount = 0;
        
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        Document doc = editor.getDocument();
        
        for (int i = 0; i < source.length(); i++) {
            try {
                c = source.charAt(i);
                if (!(key.get(c) == null)) {
                    input = key.get(c);
                }
                else{
                    input = -2;
                }
                
                if(!(input == -2)){
                    state = delta[state][input];
                }
                
                switch(state){
                    case 4: // id
                        System.out.println(source.substring(i - 1, i));
                        break;
                    case 7: // var
                        if(dark_theme){
                            StyleConstants.setForeground(attributes, Color.ORANGE);
                        }
                        else{
                            StyleConstants.setForeground(attributes, Color.BLUE);
                        }
                        StyleConstants.setBold(attributes, true);
                        
                        try{
                            doc.remove(i - 2, 3);
                            doc.insertString(i - 2, source.substring(i - 2, i + 1), attributes);
                        }
                        catch(BadLocationException ex){
                            doc.remove(i - 3, 3);
                            doc.insertString(i - 3, source.substring(i - 2, i + 1), attributes);
                        }
                        System.out.println(source.substring(i - 2, i + 1));
                        break;
                    case 12: // id
                        System.out.println(source.substring(i, i + 1));
                        break;
                    case 14: // print
                        if(dark_theme){
                            StyleConstants.setForeground(attributes, Color.ORANGE);
                        }
                        else{
                            StyleConstants.setForeground(attributes, Color.BLUE);
                        }
                        StyleConstants.setBold(attributes, true);
                        
                        try{
                            doc.remove(i - 4, 5);
                            doc.insertString(i - 4, source.substring(i - 4, i + 1), attributes);
                            System.out.println(source.substring(i - 4, i + 1));
                        }
                        catch (BadLocationException ex) {
                            doc.remove(i - 5, 5);
                            doc.insertString(i - 5, source.substring(i - 4, i + 1), attributes);
                            System.out.println(source.substring(i - 4, i + 1));
                        }
                        break;
                    case 17:
                        strCount++;
                        break;
                    case 18: // num
                        if(dark_theme){
                            StyleConstants.setForeground(attributes, Color.GREEN.darker());
                        }
                        else{
                            StyleConstants.setForeground(attributes, Color.ORANGE);
                        }
                        StyleConstants.setBold(attributes, true);
                        doc.remove(i, 1);
                        doc.insertString(i, source.substring(i, i + 1), attributes);
                        System.out.println(source.substring(i, i + 1));
                        break;
                    case 19:
                        strCount++;
                        break;
                    case 20:
                        strCount++;
                        break;
                    case 21:
                        strCount++;
                        break;
                    case 22: // string
                        if(dark_theme){
                            StyleConstants.setForeground(attributes, Color.YELLOW);
                        }
                        else{
                            StyleConstants.setForeground(attributes, Color.GREEN.darker());
                        }
                        StyleConstants.setBold(attributes, true);
                        doc.remove(i - strCount, strCount + 1);
                        doc.insertString(i - strCount, source.substring(i - strCount, i + 1), attributes);
                        System.out.println(source.substring(i - strCount, i + 1));
                        strCount = 0;
                        break;
                    case 23: // num
                        if(dark_theme){
                            StyleConstants.setForeground(attributes, Color.GREEN.darker());
                        }
                        else{
                            StyleConstants.setForeground(attributes, Color.ORANGE);
                        }
                        StyleConstants.setBold(attributes, true);
                        doc.remove(i, 1);
                        doc.insertString(i, source.substring(i, i + 1), attributes);
                        System.out.println(source.substring(i, i + 1));
                        break;
                    case 24:
                        strCount++;
                        break;
                    case 25:
                        strCount++;
                        break;
                    case 26:
                        strCount++;
                        break;
                    case 27:
                        strCount++;
                        break;
                    case 28: // string
                        if(dark_theme){
                            StyleConstants.setForeground(attributes, Color.YELLOW);
                        }
                        else{
                            StyleConstants.setForeground(attributes, Color.GREEN.darker());
                        }
                        StyleConstants.setBold(attributes, true);
                        doc.remove(i - strCount, strCount + 1);
                        doc.insertString(i - strCount, source.substring(i - strCount, i + 1), attributes);
                        System.out.println(source.substring(i - strCount, i + 1));
                        strCount = 0;
                        break;
                    case 29: // id
                        System.out.println(source.substring(i, i + 1));
                        break;
                    case 32:
                        commentCount++;
                        break;
                    case 33:
                        commentCount++;
                        break;
                    case 34:
                        commentCount++;
                        break;
                    case 35:
                        commentCount++;
                        break;
                    case 36:
                        commentCount++;
                        break;
                    case 37:
                        commentCount++;
                        break;
                    case 38: // comment
                        if(dark_theme){
                            StyleConstants.setForeground(attributes, new Color(0, 157, 247));
                        }
                        else{
                            StyleConstants.setForeground(attributes, Color.GRAY.darker());
                        }
                        
                        doc.remove(i - commentCount, commentCount + 1);
                        doc.insertString(i - commentCount, source.substring(i - commentCount, i + 1), attributes);
                        System.out.println(source.substring(i - commentCount, i + 1));
                        commentCount = 0;
                        break;
                    default:
                        break;
                }
            } catch (BadLocationException ex) {
                Logger.getLogger(IDE.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println(input);
        System.out.println("State: " + state + "\n");
        
        attributes.removeAttributes(attributes);
        if(dark_theme){
            StyleConstants.setForeground(attributes, Color.white);
        }
        editor.setCharacterAttributes(attributes, true);
        
        state = States.q0.s;
    }
    
}
