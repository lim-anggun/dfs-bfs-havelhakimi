/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hh_bfs_dfs;

import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author longang
 */
public class HH_BFS_DFS{

    private static JButton hh;
    private static JButton bfs;
    private static JButton dfs;
    
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("HH-BFS-DFS");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setSize(300, 450);
        
        int width = frame.getBounds().width;
        int height = frame.getBounds().height;
        
        hh = new JButton("Havel Hakimi");
        bfs = new JButton("BFS");
        dfs = new JButton("DFS");
        JLabel lb = new JLabel("Lim Ang/Ankara University");
        
        hh.setBounds((width/2)-50, (height/4), 100, 50);
        bfs.setBounds((width/2)-50, (height/4)+60, 100, 50);
        dfs.setBounds((width/2)-50, (height/4)+120, 100, 50);
        lb.setBounds((width/2)-80, (height/4)+180, 200, 50);
        
        hh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HavelHakimi();
            }
        });
        bfs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BFS();
            }
        });
        dfs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DFS();
            }
        });
        frame.add(hh);
        frame.add(bfs);
        frame.add(dfs);
        frame.add(lb);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
