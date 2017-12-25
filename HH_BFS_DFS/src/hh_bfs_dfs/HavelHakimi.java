/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hh_bfs_dfs;

import general.drawArrow;
import general.hhNode;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author longang
 */
public class HavelHakimi {
    
    private List<Integer> degreeSequences;
    private List<Integer> ds_copy;
    private int[][] adjecencyMatrix;
    private int count = 0;
    private JTextArea lbResult;
    private clsPanel clsPanelObj;
    
    private int SIZE = 256;
    private int a = SIZE / 2;
    private int b = a;
    private int r = 4 * SIZE / 5;
    private String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ArrayList<hhNode> nodes;
    
    public HavelHakimi(){
        clsPanelObj = new clsPanel();
        nodes = new ArrayList<>();
        
        JFrame frame = new JFrame("Havel Hakimi");
        frame.setLayout(null);
        frame.setSize(950, 550);
        
        //create panel controls
        JPanel pn = new JPanel();
        pn.setLayout(new GridBagLayout());
        pn.setBounds(20, 10, frame.getBounds().width-40, 50);
        pn.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
        GridBagConstraints gbc = new GridBagConstraints();
        //label
        JLabel lb = new JLabel("Enter Degree Sequence: ");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        pn.add(lb,gbc);
        
        //text field
        JTextField text = new JTextField("5,4,3,3,2,2,2,1,1,1,0,0", 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        pn.add(text,gbc);
        //button
        JButton start = new JButton("Graphical?");
        start.setLayout(null);
        start.setSize(50, 40);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                degreeSequences = new ArrayList<>();
                ds_copy = new ArrayList<>();
                lbResult.setText("");
                
                String txt = text.getText();
                if(!txt.isEmpty()){
                    String[] s = txt.replaceAll("\\s+","").split(",");
                    count = s.length;
                    
                    for(int i=0;i<count;i++){
                        degreeSequences.add(Integer.parseInt(s[i]));
                        ds_copy.add(Integer.parseInt(s[i]));
                    }
                    if(isDescendingOrder()){
                        if(isGraphic()){
                            lbResult.setText(lbResult.getText().concat("\n >> It is graphical"));
                            generateGraph();
                        }else{
                            lbResult.setText(lbResult.getText().concat(">> It is not graphical\n"));
                            count = 0;
                        }
                        clsPanelObj.repaint();
                           
                    }else{
                        showMsg("Must enter in decreasing order", "Error");
                    }
                }else{
                    showMsg("Please enter degree sequence (separate by comma ,)", "Error");
                }
            }
        });
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 0;
        pn.add(start,gbc);
        
        //result
        JLabel lb2 = new JLabel("Result:");
        lb2.setBounds(20, 70, 50, 20);
        lbResult = new JTextArea();
        Font f = new Font("Arial", Font.PLAIN, 20);
        lbResult.setFont(f);
        JScrollPane scroll = new JScrollPane (lbResult, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(20, 90, (frame.getWidth()/2)-50, frame.getHeight()-180);
        
        // draw graph panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(frame.getBounds().width/2, 70, frame.getBounds().width/2, frame.getBounds().height-100);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Graph"));
        panel.add(clsPanelObj);
        
        frame.add(pn);
        frame.add(lb2);
        frame.add(scroll);
        frame.add(panel);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
    }
    class clsPanel extends JPanel{
        
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //g2d.setColor(Color.black);
            a = getWidth()/2;
            b = getHeight()/2;
            int m = Math.min(a,b);
            r = 4 * m/5;
            int r2 = Math.abs(m - r) / 2;
            //g2d.drawOval(a - r, b - r, 2 * r, 2 * r);
            g2d.setColor(new Color(0, 153, 204));
            nodes.clear();
            
            for (int i = 0; i < count; i++){
                double t = 2 * Math.PI * i / count;
                int x = (int) Math.round(a + r * Math.cos(t));
                int y = (int) Math.round(b + r * Math.sin(t));
                
                g2d.fillOval(x - r2, y - r2, 2 * r2, 2 * r2);
                g2d.drawString(letter.charAt(i)+"", x-r2, y-r2);
                
                hhNode n = new hhNode(letter.charAt(i)+"");
                n.setLoc(new Point(x, y));
                nodes.add(n);
            }
            drawArrow da = new drawArrow();
            g.setColor(Color.blue);
            for(int i = 0;i<count;i++){
                hhNode myNode1 = nodes.get(i);
                ArrayList<hhNode> adjacency = getAdjacencyOfNode(i);
                for(hhNode myNode2: adjacency){
                    int x1 = myNode1.getLoc().x;
                    int y1 = myNode1.getLoc().y;
                    int x2 = myNode2.getLoc().x;
                    int y2 = myNode2.getLoc().y;
                    
                    da.drawNonDirectedEdges(g,x1 ,y1, x2, y2);
                }
            }
        }
    }
    private ArrayList getAdjacencyOfNode(int idx){
        ArrayList<hhNode> adj = new ArrayList<>();
        
        for(int j=0;j<count;j++){
            if(adjecencyMatrix[idx][j] == 1){
                adj.add(nodes.get(j));
            }
        }
        
        return adj;
    }
    public static void showMsg(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    public boolean hh_process(List<Integer> new_sq){
        List<Integer> prev_sq = new ArrayList<>(new_sq);
        
        //if d(i)<0, fail
        for(int i=0;i<new_sq.size();i++){
            if(new_sq.get(i)<0)
            {
                lbResult.setText(lbResult.getText().concat(prev_sq + "\n"));
                return false;
            }
        }
        
        //if d(i)==0, success, is graphic
        boolean all_zero = true;
        for(int i=0;i<new_sq.size();i++){
            if(new_sq.get(i)!=0){
                all_zero = false;
                break;
            }
        }
        if(all_zero){
            lbResult.setText(lbResult.getText().concat(prev_sq + "\n"));
            return true;
        }
        
        // reorder sequence
        Collections.sort(new_sq, Collections.reverseOrder());
        if(prev_sq.equals(new_sq)){
            lbResult.setText(lbResult.getText().concat(prev_sq + "\n"));
        }else{
            lbResult.setText(lbResult.getText().concat(prev_sq + "\n"));
            lbResult.setText(lbResult.getText().concat("Rearrange in non-increasing order:\n"));
            lbResult.setText(lbResult.getText().concat(new_sq + "\n"));
        }
        // k = d(1)
        int k = new_sq.get(0);
        //remove d(1) from S
        new_sq.remove(0);
        //substract 1 from new k items in S
        for(int i=0;i<k;i++){
            new_sq.set(i, new_sq.get(i)-1);
        }
        
        return hh_process(new_sq);
    }
    
    public boolean isGraphic(){

        int total_odd_degree = 0;
        int num_vertices = degreeSequences.size();
        //2.if d(i)>=n, fail
        for(int i=0;i<num_vertices;i++){
            if(degreeSequences.get(i) >= num_vertices){
                return false;
            }
            if(degreeSequences.get(i)%2 != 0){
                total_odd_degree += 1;
            }
        }

        //3. odd degree,sum(all degrees)%2 != 0, fail
        if(total_odd_degree%2 != 0) return false;
        // 4. process havel hakimi
        return hh_process(degreeSequences);
    }
    
    public boolean isDescendingOrder(){
        boolean isDesc = false;
        int num_vertices = degreeSequences.size();
        for(int i=0;i<num_vertices-1;i++){
            if(degreeSequences.get(i)>=degreeSequences.get(i+1)){
                isDesc = true;
            }else{
                isDesc = false;
                break;
            }
        }
        return isDesc;
    }
    
    private void addEdges(int v, int node){
        for (int i = 0; i < count && node > 0; i++){
            if (ds_copy.get(i) != 0){
                adjecencyMatrix[v][i] = adjecencyMatrix[i][v] = 1;
                int val = ds_copy.get(i);
                if (val > 0)
                    ds_copy.set(i, val - 1);
                node--;
            }
        }
    }
 
    public void generateGraph(){
        adjecencyMatrix = new int[count][count];
        for (int i = 0; i < count; i++){
            int node = ds_copy.get(i);
            ds_copy.set(i, 0);
            addEdges(i, node);
        }
    }

}
