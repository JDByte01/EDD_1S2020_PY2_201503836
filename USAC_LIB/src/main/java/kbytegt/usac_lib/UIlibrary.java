/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbytegt.usac_lib;

import java.math.BigInteger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author JOSED
 */
class ModeloTablaLibros extends AbstractTableModel{

    private ListaLibros lista;
    public ModeloTablaLibros(ListaLibros lista){
        System.out.println(" >> Preparando tabla...");
        this.lista = lista;
    }
    @Override
    public int getRowCount() {
        if(this.lista != null)
            return lista.getContador();
        else
            return 0;
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object obj = null;
        if(this.lista != null){
            Libro lib =  this.lista.buscarIndex(rowIndex);
            switch(columnIndex){
                case 0:
                    obj = lib.getISBN();
                    break;
                case 1:
                    obj = lib.getTitulo();
                    break;
                case 2:
                    obj = lib.getAutor();
                    break;
                case 3:
                    obj = lib.getEditorial();
                    break;
                case 4:
                    obj = lib.getAño();
                    break;
                case 5:
                    obj = lib.getEdicion();
                    break;
                case 6:
                    obj = lib.getIdioma();
                    break;
                default:
                    obj = null;
                    break;
            }
        }

        return obj;
    }
    
    @Override
    public String getColumnName(int c){
        String nombre = "";
        switch(c){
            case 0:
                nombre = "ISBN";
                break;
            case 1:
                nombre = "Titulo";
                break;
            case 2:
                nombre = "Autor";
                break;
            case 3:
                nombre = "Editorial";
                break;
            case 4:
                nombre = "Año";
                break;
            case 5:
                nombre = "Edición";
                break;
            case 6:
                nombre = "Idioma";
                break;
            default:
                nombre = "";
                break;
        }
        return nombre;
    }
    
}
public final class UIlibrary extends javax.swing.JFrame {
    
    ListaCategorias lcategorias;
    DefaultMutableTreeNode categorias = new DefaultMutableTreeNode("Categorias");
    DefaultTreeModel modelo_tree = new DefaultTreeModel(categorias);
    TableModel modelo_tabla;
    /*
     *  Actualizar JTree
     */
    public void actualizarJTree(ListaCategorias lista){
        
        jTree1.setModel(modelo_tree);
        
        //Limpiar JTree
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)modelo_tree.getRoot();
        root.removeAllChildren();
        modelo_tree.reload(root);
        
        //Insertar en JTree
        DefaultMutableTreeNode cat;
        jTree1.expandRow(lista.getContador());
        System.out.println(" [JTre] contador: "+lista.getContador());
        if(lista != null){
            NodoLC temp = lista.getInicio();
        
            while(temp.getSiguiente() != null){
                //Agregar categoria a jTree
                System.out.println(" -> "+temp.getCategoria());
                cat = new DefaultMutableTreeNode(temp.getCategoria());
                modelo_tree.insertNodeInto(cat, categorias, 0);
                temp = temp.getSiguiente();
            }
            System.out.println(" -> "+temp.getCategoria());
            cat = new DefaultMutableTreeNode(temp.getCategoria());
            modelo_tree.insertNodeInto(cat, categorias, 0);
            modelo_tree.reload();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo obtener las categorias del Árbol AVL");
        }
    }
    
    /*
     *  Actualizar JTable
     */
    public void actualizarJTable(ListaLibros lista){
        
        modelo_tabla = new ModeloTablaLibros(lista);
        jTable1.setModel(modelo_tabla);
        //modelo_tabla.setValueAt("Hola", 1, 1);
    }
    
    /*
     *  Actualizar JTable
     */
    public void setMenuCarnet(String carnet){
        jMenu3.setText(carnet);
    }
    /**
     * Creates new form UIlibrary
     */
    public UIlibrary() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Biblioteca virtual");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Buscar libro:");

        jTextField1.setText("ISBN / Titulo");

        jButton2.setText("Buscar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(303, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jTextField1))
                    .addComponent(jButton2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButton1.setText("Cargar archivo JSON - libros");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Agregar libro");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton1)
                .addComponent(jButton3))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Biblioteca"));

        jSplitPane1.setDividerLocation(115);
        jSplitPane1.setDividerSize(8);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Biblioteca");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setInheritsPopupMenu(true);
        jTree1.setMaximumSize(new java.awt.Dimension(65, 16));
        jTree1.setMinimumSize(new java.awt.Dimension(30, 0));
        jTree1.setPreferredSize(new java.awt.Dimension(65, 16));
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ISBN", "Titulo", "Autor", "Editorial", "Año", "Edición", "Idioma"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jSplitPane1.setRightComponent(jScrollPane1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu3.setText("Usuario");

        jMenuItem1.setText("Editar usuario");
        jMenu3.add(jMenuItem1);
        jMenu3.add(jSeparator1);

        jMenuItem2.setText("Salir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Reportes");

        jMenuItem3.setText("Árbol AVL de categorias");
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Árbol B por Categoria");
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("Tabla de dispersión de usuarios");
        jMenu2.add(jMenuItem5);

        jMenuItem6.setText("AVL - preorden");
        jMenu2.add(jMenuItem6);

        jMenuItem7.setText("AVL - inorden");
        jMenu2.add(jMenuItem7);

        jMenuItem8.setText("AVL - postorden");
        jMenu2.add(jMenuItem8);
        jMenu2.add(jSeparator2);

        jMenuItem9.setText("Listado de Nodos en la red");
        jMenu2.add(jMenuItem9);

        jMenuItem10.setText("Log de operaciones");
        jMenu2.add(jMenuItem10);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Configuración");

        jMenuItem11.setText("Definir carpeta");
        jMenu1.add(jMenuItem11);

        jMenuItem12.setText("IP y puerto");
        jMenu1.add(jMenuItem12);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // BOTON BUSCAR LIBRO POR ISBN
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // BOTON CARGAR JSON DE LIBROS
        
        // LEER ARCHIVO JSON EN RUTA
        Archivo a = new Archivo();
        String txt;
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JavaScript Object Notation", "json");
        jFileChooser1.setFileFilter(filter);
        int returnVal = jFileChooser1.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println(jFileChooser1.getSelectedFile().getAbsolutePath());
            txt = a.getJson(jFileChooser1.getSelectedFile().getAbsolutePath());
           
            boolean flag = USAC_LIBRARY.ingresarLibros(txt);
             if(flag){
                 
                JOptionPane.showMessageDialog(this,"Carga de libros exitosa","JSON", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,"No se pudieron cargar los libros","JSON", JOptionPane.ERROR_MESSAGE);
            }
            //System.out.println(txt);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // BOTON AGREGAR LIBRO
        UIlibroregistro registro = new UIlibroregistro();
        registro.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        // METODO QUE OBTIENE LA CATEGORIA A MOSTRAR EN LA TABLA
        DefaultMutableTreeNode categoria = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
        try {
            NodoCategoria tempCat = USAC_LIBRARY.biblioteca.buscar(categoria.getPath()[1].toString());
            ListaLibros listaLib = tempCat.getListaLibros();
            actualizarJTable(listaLib);
            //JOptionPane.showMessageDialog(this, categoria.getPath()[1].toString());
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_jTree1ValueChanged

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // METODO DE SALIR
        USAC_LIBRARY.LogOut();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // OBTENER LIBRO DE TABLA
//        DefaultTableModel moelo = (DefaultTableModel) jTable1.getModel();
//        if(evt.getClickCount() == 1){
//            for (int i = 0; i < jTable1.getRowCount(); i++) {
//                
//            }
//        }
        //Obtener ISBN de la tabla al hacer doble click
        if(evt.getClickCount() == 2){
            int fila = jTable1.rowAtPoint(evt.getPoint());
            if(fila > -1){
                JOptionPane.showMessageDialog(this, String.valueOf(jTable1.getValueAt(fila, 0)));
            }
        }
        
    }//GEN-LAST:event_jTable1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UIlibrary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIlibrary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIlibrary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIlibrary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UIlibrary().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
