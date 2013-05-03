package app;

import app.entities.Buildings;
import app.entities.Jobs;
import app.entities.Rooms;
import app.entities.Users;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 *
 * @author JB
 */
public final class SqlPanel extends javax.swing.JPanel {

    private EntityManagerFactory factory;

    /**
     * Creates new form SqlPanel
     */
    public SqlPanel() {
        initComponents();
    }

    /**
     * Need this because constructor can't have any arguments.
     *
     * @param factory
     */
    public void initialize(EntityManagerFactory factory) {
        this.factory = factory;
        this.reservationsPanel1.initialize(factory);
        this.aggreatedDataPanel2.initialize(factory);
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
        String[] names = {"Jan", "Josef", "Ondra", "Pavel", "Jan", "Zbynek", "Tomas", "Lukas"};
        String[] snames = {"Novak", "Svoboda", "Novak", "Rucinsky", "Jagr", "Straka", "Patera", "Ruzicka"};
        String[] logins = {"JaNo", "Jozka", "OndNo", "Ruca", "JagrGodr", "Str121", "Pat36", "Ruza13"};
        for (int i = 0; i < names.length; i++) {
            Users u = new Users();
            u.setName(names[i]);
            u.setSurname(snames[i]);
            u.setPass("fakepass");
            u.setSalt("fakesalt");
            u.setLogin(logins[i]);
            u.setJobsCollection(new ArrayList<Jobs>());
            for (Jobs j : jobs) {
                if ((int) (Math.random() * (jobs.size())) == 0) {
                    j.getUsersCollection().add(u);
                    u.getJobsCollection().add(j);
                }
            }
            r.add(u);
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
        String[] codeDejvice = {"D3-256","A3-431b"};
        String[] codeKarlak = {"E-107","E-220","E-128","E-230"};
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

        resetButton = new javax.swing.JButton();
        reservationsPanel1 = new app.OverlapingReservationsPanel();
        aggreatedDataPanel2 = new app.AggreatedDataPanel();

        resetButton.setText("Reset Data");
        resetButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resetButton)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(reservationsPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aggreatedDataPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resetButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reservationsPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aggreatedDataPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void resetButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetButtonMouseClicked
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
            for (Buildings b: buildings) {
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
        reservationsPanel1.refreshData();
        aggreatedDataPanel2.refreshData();
    }//GEN-LAST:event_resetButtonMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.AggreatedDataPanel aggreatedDataPanel2;
    private app.OverlapingReservationsPanel reservationsPanel1;
    private javax.swing.JButton resetButton;
    // End of variables declaration//GEN-END:variables
}
