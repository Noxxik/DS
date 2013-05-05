/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import app.entities.Buildings;
import app.entities.Jobs;
import app.entities.Rooms;
import app.entities.Users;
import java.awt.event.ItemEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JB
 */
public final class TablePanel extends javax.swing.JPanel {

    private EntityManagerFactory factory;
    private List<Users> loadedUsers;
    private int resultSize = 20;
    private long offset = 0;
    List<Jobs> jobs;


    /**
     * Creates new form TablePanel
     */
    public TablePanel() {
        initComponents();

    }

    /**
     * Need this because constructor can't have any arguments.
     *
     * @param factory
     */
    public void initialize(EntityManagerFactory factory) {
        this.factory = factory;
    }
    
    private List<Jobs> generateJobs() {
        List<Jobs> r = new ArrayList<>();
        String[] names = {"Manager", "Teacher", "Driver", "Cleaner", "Assistant"};
        String[] desc = {"Commands people", "Teaches people", "Drives cars", "Cleans up", "Helps others"};
        for (int i = 0; i < names.length; i++) {
            Jobs j = new Jobs();
            j.setName(names[i]);
            j.setDescription(desc[i]);
            j.setUsersCollection(new ArrayList<Users>());
            r.add(j);
        }
        return r;
    }

    private List<Users> generateUsers(List<Jobs> jobs) {
        List<Users> r = new ArrayList<>();
        int size = 950;
        int i = 0;
        try {
            Scanner namesS = new Scanner(new FileReader("random_names.txt"));
            Set<String> logins = new HashSet<>();
            int uId = 1;
            while (namesS.hasNextLine() && (i++ < size)) {
                String name = namesS.next();
                String surname = namesS.next();
                String login = ((name.length() <= 4) ? name : name.substring(0, 3))
                        + ((surname.length() <= 4) ? surname : surname.substring(0, 3));
                if (logins.contains(login)) {
                    login = login + String.valueOf(++uId);
                }
                logins.add(login);
                Users u = new Users();
                u.setName(name);
                u.setSurname(surname);
                u.setPass("fakepass");
                u.setSalt("fakesalt");
                u.setLogin(login);
                u.setJobsCollection(new ArrayList<Jobs>());
                for (Jobs j : jobs) {
                    if ((int) (Math.random() * (jobs.size())) == 0) {
                         u.getJobsCollection().add(j);
                    }
                }
                r.add(u);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SqlPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    private List<Buildings> generateBuildings() {
        List<Buildings> r = new ArrayList<>();
        String[] names = {"Dejvice", "Karlovo náměstí"};
        String[] postalCode = {"160 80", "121 35"};
        String[] street = {"Technická 2", "Karlovo nám. 13"};

        for (int i = 0; i < names.length; i++) {
            Buildings b = new Buildings();
            b.setName(names[i]);
            b.setCity("Prague");
            b.setPostalCode(postalCode[i]);
            b.setStreet(street[i]);
            r.add(b);
        }
        return r;
    }

    private List<Rooms> generateRooms(List<Buildings> buildings) {
        List<Rooms> r = new ArrayList<>();
        String[] codeDejvice = {"D3-256", "A3-431b"};
        String[] codeKarlak = {"E-107", "E-220", "E-128", "E-230"};
        for (int i = 0; i < codeDejvice.length; i++) {
            Rooms room = new Rooms();
            room.setCode(codeDejvice[i]);
            room.setBuildingId(buildings.get(0));
            r.add(room);
        }

        for (int i = 0; i < codeKarlak.length; i++) {
            Rooms room = new Rooms();
            room.setCode(codeKarlak[i]);
            room.setBuildingId(buildings.get(1));
            r.add(room);
        }
        return r;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        addUserButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        order = new javax.swing.JComboBox();
        prevPage = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        filter = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jobFilter = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        nextPage = new javax.swing.JButton();
        pageText = new javax.swing.JLabel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "login", "name", "surname", "positions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        usersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usersTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(usersTable);

        addUserButton.setText("Add new user");
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Order:");

        order.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "login", "name", "surname" }));
        order.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                orderItemStateChanged(evt);
            }
        });

        prevPage.setText("<<");
        prevPage.setEnabled(false);
        prevPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevPageActionPerformed(evt);
            }
        });

        jLabel3.setText("Filter:");

        filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterKeyReleased(evt);
            }
        });

        jLabel4.setText("Job filter:");

        jobFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jobFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jobFilterItemStateChanged(evt);
            }
        });

        jButton1.setText("Reset & Randomly Fill In");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addUserButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(order, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filter, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jobFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE)
                        .addComponent(prevPage))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addUserButton)
                    .addComponent(refreshButton)
                    .addComponent(jLabel1)
                    .addComponent(order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prevPage)
                    .addComponent(jLabel3)
                    .addComponent(filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jobFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jButton1)
                .addGap(0, 60, Short.MAX_VALUE))
        );

        nextPage.setText(">>");
        nextPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextPageActionPerformed(evt);
            }
        });

        pageText.setText("? - ? / ?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pageText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nextPage))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nextPage)
                            .addComponent(pageText))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        refreshTable();
    }//GEN-LAST:event_formComponentShown

    private void usersTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usersTableMouseClicked
        UserDialog dlg = new UserDialog(null, true, factory, loadedUsers.get(usersTable.getSelectedRow()));
        dlg.setVisible(true);
        if (dlg.isDbChanged()) {
            refreshTable();
        }
    }//GEN-LAST:event_usersTableMouseClicked

    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        UserDialog dlg = new UserDialog(null, true, factory, null);
        dlg.setVisible(true);
        if (dlg.isDbChanged()) {
            refreshTable();
        }
    }//GEN-LAST:event_addUserButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refreshTable();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void nextPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextPageActionPerformed
        offset += resultSize;
        refreshTable();
    }//GEN-LAST:event_nextPageActionPerformed

    private void prevPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevPageActionPerformed
        offset -= resultSize;
        refreshTable();
    }//GEN-LAST:event_prevPageActionPerformed

    private void orderItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_orderItemStateChanged
        if (order.isValid()) {
            offset = 0;
            refreshTable();
        }
    }//GEN-LAST:event_orderItemStateChanged

    private void filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterKeyReleased
        offset = 0;
        refreshTable();
    }//GEN-LAST:event_filterKeyReleased

    private void jobFilterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jobFilterItemStateChanged
        if (jobFilter.isValid()) {
            offset = 0;
            refreshTable();
        }
    }//GEN-LAST:event_jobFilterItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.createNamedQuery("Users.deleteAll").executeUpdate();
            em.createNamedQuery("Jobs.deleteAll").executeUpdate();
            em.createNamedQuery("Rooms.deleteAll").executeUpdate();
            em.createNamedQuery("Buildings.deleteAll").executeUpdate();
            List<Jobs> jobs = generateJobs();
            List<Users> users = generateUsers(jobs);
            List<Buildings> buildings = generateBuildings();
            List<Rooms> rooms = generateRooms(buildings);
            for (Users u : users) {
                em.persist(u);
            }
            for (Jobs j : jobs) {
                em.persist(j);
            }
            for (Rooms r : rooms) {
                em.persist(r);
            }
            for (Buildings b : buildings) {
                em.persist(b);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addUserButton;
    private javax.swing.JTextField filter;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jobFilter;
    private javax.swing.JButton nextPage;
    private javax.swing.JComboBox order;
    private javax.swing.JLabel pageText;
    private javax.swing.JButton prevPage;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTable usersTable;
    // End of variables declaration//GEN-END:variables

    private void updatePagination() {
        long c = getCount();
        long to = (offset + resultSize) > c ? c : (offset + resultSize);
        long maxOffset = c - resultSize;
        pageText.setText((offset + 1) + " - " + to + " / " + c);
        if (offset <= 0) {
            prevPage.setEnabled(false);
        } else {
            prevPage.setEnabled(true);
        }
        if (offset >= maxOffset) {
            nextPage.setEnabled(false);
        } else {
            nextPage.setEnabled(true);
        }
    }
    
    
    private void makeFilter(Root r, CriteriaQuery cq, CriteriaBuilder cb) {
        List<Predicate> pred = new ArrayList<>();
        if (!filter.getText().isEmpty()) {
            pred.add(cb.like(r.get((String) order.getSelectedItem()), filter.getText() + "%"));
        }
        if (jobFilter.getSelectedIndex() > 0) {
            Join jr = r.join(r.getModel().getCollection("jobsCollection"));
            pred.add(cb.equal(jr.get("name"), (String) jobFilter.getSelectedItem()));
        }
        cq.where(cb.and(pred.toArray(new Predicate[0])));
    }
    
    
    private Long getCount() {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root r = cq.from(Users.class);
        cq.select(cb.count(r));
        makeFilter(r, cq, cb);
        Long res = em.createQuery(cq).getSingleResult();
        em.close();
        if (offset + resultSize > res) {
            nextPage.setEnabled(false);
        }
        return res;
    }

    private void refreshTable() {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Users> cq = cb.createQuery(Users.class);
        Root r = cq.from(Users.class);
        cq.select(r);
        makeFilter(r, cq, cb);
        cq.orderBy(em.getCriteriaBuilder().asc(r.get((String) order.getSelectedItem())));
        TypedQuery q = em.createQuery(cq);
        q.setFirstResult((int) offset);
        q.setMaxResults(resultSize);
        loadedUsers = q.getResultList();
        DefaultTableModel m = new UsersTableModel();
        for (Users user : loadedUsers) {
            String[] cd = new String[4];
            cd[0] = user.getLogin();
            cd[1] = user.getName();
            cd[2] = user.getSurname();
            StringBuilder jS = new StringBuilder();
            for (Jobs j : user.getJobsCollection()) {
                if (jS.length() > 0) {
                    jS.append(", ");
                }
                jS.append(j.getName());
            }
            cd[3] = jS.toString();
            m.addRow(cd);
        }
        usersTable.setModel(m);
        em.close();
        updatePagination();
        refreshJobsList();
    }

    private void refreshJobsList() {
        EntityManager em = factory.createEntityManager();
        CriteriaQuery<Jobs> cq = em.getCriteriaBuilder().createQuery(Jobs.class);
        cq.select(cq.from(Jobs.class));
        jobs = em.createQuery(cq).getResultList();
        int index = jobFilter.getSelectedIndex();
        jobFilter.removeAllItems();
        jobFilter.addItem("all");
        for (Jobs j : jobs) {
            jobFilter.addItem(j.getName());
        }
        if (index < jobFilter.getItemCount()) {
            jobFilter.setSelectedIndex(index);
        }
    }

    class UsersTableModel extends DefaultTableModel {
        public UsersTableModel() {
            super();
            addColumn("Login");
            addColumn("Name");
            addColumn("Surname");
            addColumn("Positions");
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
