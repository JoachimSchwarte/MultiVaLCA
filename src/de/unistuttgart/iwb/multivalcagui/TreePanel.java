/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
 
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import de.unistuttgart.iwb.multivalca.Flow;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
 
import java.awt.Dimension;
import java.awt.GridLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.602
 */
 
public class TreePanel extends JPanel
                      implements TreeSelectionListener {
    private JTree tree;
    private static boolean DEBUG = false;
 
    //Optionally play with line styles.  Possible values are
    //"Angled" (the default), "Horizontal", and "None".
    private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";
 
    public TreePanel() {
        super(new GridLayout(1,0));
 
        //Create the nodes.
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("LCA-Objects");
        createNodes(top);
 
        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
 
        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
 
        if (playWithLineStyle) {
            System.out.println("line style = " + lineStyle);
            tree.putClientProperty("JTree.lineStyle", lineStyle);
        }
 
        //Create the scroll pane and add the tree to it. 
        JScrollPane treeView = new JScrollPane(tree);
 
        Dimension minimumSize = new Dimension(150, 250);
        treeView.setMinimumSize(minimumSize);
 
        //Add the split pane to this panel.
        add(treeView);
    }
 
    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();
 
        if (node == null) return;
 
        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {

            if (DEBUG) {
                
            }
        } else {

        }
        if (DEBUG) {
            System.out.println(nodeInfo.toString());
        }
    }
 
    private class BookInfo {
        public String bookName;
 
        public BookInfo(String book, String filename) {
            bookName = book;

        }
 
        public String toString() {
            return bookName;
        }
    }


 
    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;
        DefaultMutableTreeNode flussknoten = null;
 
        category = new DefaultMutableTreeNode("Flows");
        top.add(category);
        
        for(String flussname : Flow.getAllInstances().keySet()) {
        	flussknoten = new DefaultMutableTreeNode(flussname);
        	category.add(flussknoten);
        }
 
        category = new DefaultMutableTreeNode("Process Modules");
        top.add(category);
 
        //VM
        book = new DefaultMutableTreeNode(new BookInfo
            ("The Java Virtual Machine Specification",
             "vm."));
        category.add(book);
 
        //Language Spec
        book = new DefaultMutableTreeNode(new BookInfo
            ("The Java Language Specification",
             "jls.html"));
        category.add(book);
        
        category = new DefaultMutableTreeNode("Product Systems");
        top.add(category);
        
        category = new DefaultMutableTreeNode("Impact Categories");
        top.add(category);
    }
}