package app;

/**
 * This class in current state is not working.
 */
import app.entities.Reservations;
import app.entities.Rooms;
import app.entities.Rooms_;
import app.entities.Users;
import app.entities.Users_;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author noxx
 */
public class AggreatedDataPanel extends javax.swing.JPanel {

    private EntityManagerFactory factory;

    /**
     * Creates new form AggreatedDataPanel
     */
    public AggreatedDataPanel() {
        initComponents();
    }

    public void initialize(EntityManagerFactory factory) {
        this.factory = factory;
    }

    private void refreshUsersTable() {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();

        Root<Users> u = cq.from(Users.class);
        Join<Users, Reservations> r = u.join(Users_.reservationsCollection);

        cq.multiselect(u.get(Users_.login).alias("login"), cb.count(r).alias("count"));

        cq.groupBy(u.get(Users_.login));
        cq.orderBy(cb.desc(cb.count(r)));
        List<Tuple> list = em.createQuery(cq).getResultList();

        DefaultTableModel tm = new UsersTableModel();
        for (Tuple tuple : list) {
            String[] row = {tuple.get("login").toString(), tuple.get("count").toString()};
            tm.addRow(row);
        }
        usersTable.setModel(tm);
    }

    private void refreshRoomsTable() {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();

        Root<Rooms> roo = cq.from(Rooms.class);
        Join<Rooms, Reservations> res = roo.join(Rooms_.reservationsCollection);

        cq.multiselect(roo.get(Rooms_.code).alias("code"), cb.count(res).alias("count"));

        cq.groupBy(roo.get(Rooms_.code));
        cq.orderBy(cb.desc(cb.count(res)));
        List<Tuple> list = em.createQuery(cq).getResultList();

        DefaultTableModel tm = new RoomsTableModel();
        for (Tuple tuple : list) {
            String[] row = {tuple.get("code").toString(), tuple.get("count").toString()};
            tm.addRow(row);
        }
        roomsTable.setModel(tm);
    }

    private void refreshAvgRooms() {
        EntityManager em = factory.createEntityManager();
        Query q = em.createNativeQuery("select avg(sq.count) from (select ro.code, count(*) as count from rooms ro join reservations r on ro.room_id=r.room_id group by ro.code) sq");
        BigDecimal avg= (BigDecimal)q.getSingleResult();
        
        avgRoomsValueLabel.setText((avg!=null)?String.format("%.2f", avg):"<html><i>no result</i></html>");
        
    }

    private void refreshMaxRooms() {
        EntityManager em = factory.createEntityManager();
        Query q = em.createNativeQuery("select max(sq.count) from (select ro.code, count(*) as count from rooms ro join reservations r on ro.room_id=r.room_id group by ro.code) sq");
        Long max= (Long)q.getSingleResult();
        
        maxRoomsValueLabel.setText((max!=null)?String.format("%d", max):"<html><i>no result</i></html>");
    }

    private void refreshAvgUsers() {
        EntityManager em = factory.createEntityManager();
        Query q = em.createNativeQuery("select avg(sq.count) from (select u.login, count(*) as count from users u join reservations r on u.user_id=r.user_id group by u.login) sq");
        BigDecimal avg= (BigDecimal)q.getSingleResult();
        
        avgUsersValueLabel.setText((avg!=null)?String.format("%.2f", avg):"<html><i>no result</i></html>");
    }
    

    private void refreshMaxUsers() {
        EntityManager em = factory.createEntityManager();
        Query q = em.createNativeQuery("select max(sq.count) from (select u.login, count(*) as count from users u join reservations r on u.user_id=r.user_id group by u.login) sq");
        Long max = (Long)q.getSingleResult();
        
        maxUsersValueLabel.setText((max!=null)?String.format("%d", max):"<html><i>no result</i></html>");
    }
    
    
    public void refreshData() {
        refreshUsersTable();
        refreshRoomsTable();
        refreshMaxUsers();
        refreshAvgUsers();
        refreshAvgRooms();
        refreshMaxRooms();
    }

    class UsersTableModel extends DefaultTableModel {

        public UsersTableModel() {
            super();
            addColumn("User");
            addColumn("Requests");
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    class RoomsTableModel extends DefaultTableModel {

        public RoomsTableModel() {
            super();
            addColumn("Room");
            addColumn("Requests");
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
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
        roomsTitleLabel = new javax.swing.JLabel();
        usersTitleLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        roomsTable = new javax.swing.JTable();
        maxRoomsCaptionLabel = new javax.swing.JLabel();
        avgRoomsCaptionLabel = new javax.swing.JLabel();
        maxUsersCaptionLabel = new javax.swing.JLabel();
        avgUsersCaptionLabel = new javax.swing.JLabel();
        maxRoomsValueLabel = new javax.swing.JLabel();
        avgRoomsValueLabel = new javax.swing.JLabel();
        maxUsersValueLabel = new javax.swing.JLabel();
        avgUsersValueLabel = new javax.swing.JLabel();

        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User", "Requests"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(usersTable);

        roomsTitleLabel.setText("Rooms statistic");

        usersTitleLabel.setText("Users statistic");

        roomsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Room", "Requests"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(roomsTable);

        maxRoomsCaptionLabel.setText("max:");

        avgRoomsCaptionLabel.setText("avg:");

        maxUsersCaptionLabel.setText("max:");

        avgUsersCaptionLabel.setText("avg:");

        maxRoomsValueLabel.setText("<html><i>no result</i></html>");

        avgRoomsValueLabel.setText("<html><i>no result</i></html>");

        maxUsersValueLabel.setText("<html><i>no result</i></html>");

        avgUsersValueLabel.setText("<html><i>no result</i></html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roomsTitleLabel)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(avgRoomsCaptionLabel)
                            .addComponent(maxRoomsCaptionLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(maxRoomsValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(avgRoomsValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usersTitleLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(maxUsersCaptionLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(maxUsersValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(avgUsersCaptionLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(avgUsersValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomsTitleLabel)
                    .addComponent(usersTitleLabel))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maxRoomsCaptionLabel)
                    .addComponent(maxUsersCaptionLabel)
                    .addComponent(maxRoomsValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxUsersValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(avgRoomsCaptionLabel)
                    .addComponent(avgUsersCaptionLabel)
                    .addComponent(avgRoomsValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avgUsersValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avgRoomsCaptionLabel;
    private javax.swing.JLabel avgRoomsValueLabel;
    private javax.swing.JLabel avgUsersCaptionLabel;
    private javax.swing.JLabel avgUsersValueLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel maxRoomsCaptionLabel;
    private javax.swing.JLabel maxRoomsValueLabel;
    private javax.swing.JLabel maxUsersCaptionLabel;
    private javax.swing.JLabel maxUsersValueLabel;
    private javax.swing.JTable roomsTable;
    private javax.swing.JLabel roomsTitleLabel;
    private javax.swing.JTable usersTable;
    private javax.swing.JLabel usersTitleLabel;
    // End of variables declaration//GEN-END:variables
}
