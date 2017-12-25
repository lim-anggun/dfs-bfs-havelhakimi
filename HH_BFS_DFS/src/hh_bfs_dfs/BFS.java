/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hh_bfs_dfs;

import general.Node;
import general.drawArrow;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * @author longang
 */

public class BFS{
    private Queue<Node> queue;
    private static ArrayList<Node> nodes;
    
    private static int[][] matrix;
    private static ArrayList<Point> points;
    private static String letter = "SABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static char ch = 'A';
    private static int count = 0, loc = -1;
    private MyDrawingClass cls =  new MyDrawingClass();
    private JLabel lb;
    private static JButton start;
    private static boolean enableAddNode = true;
    private static int radioState = 1;
    
    public BFS(){
        nodes = new ArrayList<>();
        queue = new LinkedList<>();
        
        JFrame frame = new JFrame("BFS");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setSize(800, 700);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
               enableAddNode = true;
               count = 0;
               loc = -1;
               ch = 'A';
            }
        });
        //Panel
        JPanel pn = new JPanel();
        pn.setLayout(new GridBagLayout());
        pn.setBounds(20, 10, frame.getBounds().width-40, 50);
        pn.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Options
        ButtonGroup group = new ButtonGroup();
        JRadioButton r1 = new JRadioButton("Directed Graph", true);
        r1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    radioState = 1;
                }
            }
        });
        group.add(r1);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        pn.add(r1,gbc);
        
        JRadioButton r2 = new JRadioButton("Undirected Graph");
        r2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    radioState = 2;
                }
            }
        });
        
        group.add(r2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 0;
        pn.add(r2,gbc);
        // button
        start = new JButton("Search");
        start.setLayout(null);
        //start.setBounds(0,0,100,50);
        start.setEnabled(false);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(matrix, nodes.get(0));
                start.setEnabled(false);
            }
        });
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        gbc.gridy = 0;
        pn.add(start,gbc);
        
        lb = new JLabel(" Result: ");
        Font f = new Font("Arial", Font.PLAIN, 20);
        lb.setFont(f);
        lb.setBounds(20,55,frame.getWidth()-100,50);
        
        // draw panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(20, 100, frame.getBounds().width-40, frame.getBounds().height-130);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Draw Graph"));
        panel.add(cls);        
        
        frame.add(pn);
        frame.add(lb);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    
    private ArrayList getAdjacencyOfNode(Node node, int adjacency[][]){
        ArrayList<Node> adj = new ArrayList<>();
        int index = -1;
        int size = nodes.size();
        for(int i=0;i<size;i++){
            if(nodes.get(i).equals(node)){
                index = i;
                break;
            }
        }
        if(index!=-1){
            for(int j=0;j<size;j++){
                if(adjacency[index][j] == 1){
                    adj.add(nodes.get(j));
                }
            }
        }
        return adj;
    }
    
    public void search(int adjacency_matrix[][],Node root){
        ArrayList<javax.swing.Timer> t = new ArrayList<>();
        int time = 1000;
        
        root.color = "GRAY";
        queue.add(root);
        
        cls.setColorOfNode(root.getLoc(), Color.GRAY);
        cls.repaint();
        lb.setText(lb.getText().concat(root.label));
        
        while(!queue.isEmpty()){
            Node node= queue.remove();
            
            node.color = "BLACK";
            t.add(new javax.swing.Timer(time, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cls.setColorOfNode(node.getLoc(), Color.BLACK);
                    cls.repaint();
                }
            }));
            time += 1000;
            ArrayList<Node> adjacency = getAdjacencyOfNode(node, adjacency_matrix);
            
            for (Node adjOfNode : adjacency) {
                if(adjOfNode.color.equals("WHITE")){
                    
                    adjOfNode.color = "GRAY";
                    queue.add(adjOfNode);
                    
                    //visit node
                    t.add(new javax.swing.Timer(time, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            cls.setColorOfNode(adjOfNode.getLoc(), Color.GRAY);
                            cls.repaint();
                            lb.setText(lb.getText().concat(" "+ adjOfNode.label));
                        }
                    }));
                    time += 1000;
                }
            }
        }
        
        for(javax.swing.Timer t1: t){
            t1.setRepeats(false);
            t1.start();
        }
    }
    
    //Drawing Class
    static class MyDrawingClass extends JPanel{
        private int k = 0;
        
        private static final long serialVersionUID = 1L;
        
        private ArrayList<Point> prev_lines;
        private ArrayList<Point> cur_lines;
        private MM mm;
        private ArrayList<Color> colors;
        private int nodeId = -1;
        int m = -1, n = -1;
        
        //Mouse Event Class
        final class MM extends MouseAdapter{
            @Override
            public void mousePressed(MouseEvent e){
                if(enableAddNode == true){
                    if(points.isEmpty()){
                        points.add(new Point(e.getX(), e.getY()));
                        colors.add(new Color(0, 153, 204));
                        repaint();
                    }else{
                        boolean con = false;
                        for(int i=0;i<points.size();i++){
                            if(isInsideCircle(points.get(i), e.getPoint())){
                                //System.out.println("contain");
                                con = true;
                                break;
                            }
                        }
                        if(con == false){
                            points.add(new Point(e.getX(), e.getY()));
                            // create matrix
                            for (Point point : points) {
                                colors.add(new Color(0, 153, 204));
                            }
                            matrix = new int[points.size()][points.size()];
                            repaint();
                        }
                    }
                }
            }
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()== 2){
                    for(int i=0;i<points.size();i++){
                        if(isInsideCircle(points.get(i), e.getPoint())){
                            k += 1;
                            if(k == 1){
                                prev_lines.add(e.getPoint());
                                m = i;
                            }
                            else if(k == 2){
                                n = i;
                                if(radioState == 1){
                                    matrix[m][n] = 1;
                                }else{
                                    matrix[m][n] = 1;
                                    matrix[n][m] = 1;
                                }
                                cur_lines.add(e.getPoint());
                                repaint();
                                start.setEnabled(true);
                                enableAddNode = false;
                            }
                            break;
                        }
                    }
                }
            }
            
        }
        
        public MyDrawingClass() {
            points = new ArrayList<>();
            prev_lines = new ArrayList<>();
            cur_lines = new ArrayList<>();
            colors = new ArrayList<>();
            
            mm = new MM();
            addMouseListener(mm);
        }
        public boolean isInsideCircle(Point p1,Point p2){
            return new Ellipse2D.Float(p1.x,p1.y, 50, 50).contains(p2);
        }
        public void setColorOfNode(int nodeIndex, Color color){
            this.colors.set(nodeIndex, color);
        }
        public Color getColorOfNode(int nodeIndex){
            return this.colors.get(nodeIndex);
        }
        
        @Override
        public void paintComponent(Graphics g) {
            
            super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                nodes.clear();
                for (Point point : points) {
                    
                    g2.setColor(getColorOfNode(points.indexOf(point)));
                    
                    g2.fillOval(point.x, point.y, 50, 50);
                    
                    ch = letter.charAt(count);
                    count += 1;
                    String s = ch + "";
                    g2.drawString(s, point.x, point.y);
                    
                    // add nodes
                    loc += 1;
                    Node curnode = new Node(s);
                    curnode.setLoc(loc);
                    nodes.add(curnode);
                }
                count = 0;
                loc = -1;
                if(!(prev_lines.isEmpty() && cur_lines.isEmpty())){
                    drawArrow da = new drawArrow();
                    g.setColor(Color.blue);
                    for(int i=0;i<cur_lines.size();i++){
                        int x1 = prev_lines.get(i).x;
                        int y1 = prev_lines.get(i).y;
                        int x2 = cur_lines.get(i).x;
                        int y2 = cur_lines.get(i).y;
                        
                        if(radioState == 1){
                            da.drawDirectedEdges(g, x1, y1, x2, y2);
                        }else{
                            da.drawNonDirectedEdges(g, x1, y1, x2, y2);
                        }
                    }
                    k = 0;
                }
        }
        
    }
}