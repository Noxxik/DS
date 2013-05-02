package app;

import app.entities.Rooms;
import app.entities.Users;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author noxx
 */
public class ReservationInsertThread extends Thread {

    private final EntityManagerFactory factory;
    private final List<Users> users;
    private final List<Rooms> rooms;
    private final Random random;
    private final boolean isolationSerializable;
    private String name;

    public ReservationInsertThread(String name, boolean serializable, EntityManagerFactory factory, List<Users> users, List<Rooms> rooms) {
        this.name = name;
        this.factory = factory;
        this.users = users;
        this.rooms = rooms;
        this.random = new Random();
        this.isolationSerializable = serializable;
    }

    @Override
    public void run() {
        try {
            int usersSize = users.size();
            int roomsSize = rooms.size();
            int length;
            int startsOn;
            Users user;
            Rooms room;
            Calendar start;
            Calendar finish;
            Date date = new Date();

            Connection conn = DriverManager.getConnection("jdbc:postgresql://krizik.felk.cvut.cz:5434/ds2013_4", "ds2013_4", "modrookakobliha");
//            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ds_semestralka","noxxik","heslo");
            try {

                if (isolationSerializable) {
                    conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                } else {
                    conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                }
                conn.setAutoCommit(false);
                
                PreparedStatement select = conn.prepareStatement("SELECT count(*) FROM Reservations WHERE (start >= ? AND start <= ?) OR (finish >= ? AND finish <= ?)");
                PreparedStatement insert = conn.prepareStatement("INSERT INTO RESERVATIONS (room_id,user_id,start,finish,requested) VALUES (?,?,?,?,?)");
                
                for (int r = 0; r < roomsSize; r++) {
                    user = users.get(randomInteger(0, usersSize - 1));
                    room = rooms.get(r);
                    length = 1;
                    startsOn = 14;
                    start = Calendar.getInstance();
                    start.set(2013, 5, r, startsOn, 0);
                    finish = Calendar.getInstance();
                    finish.set(2013, 5, r, startsOn + length, 0);

                    insertIntoDB(user, room, start.getTime(), finish.getTime(), date, conn, select, insert);
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                conn.setAutoCommit(true);
                conn.close();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

    private int randomInteger(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long) end - (long) start + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long) (range * random.nextDouble());
        int randomNumber = (int) (fraction + start);
        return randomNumber;
    }

    private void insertIntoDB(Users user, Rooms room, Date start, Date finish, Date requestedDate, Connection conn, PreparedStatement select, PreparedStatement insert) throws Exception {
        Timestamp s = new Timestamp(start.getTime());
        Timestamp f = new Timestamp(finish.getTime());

        select.setTimestamp(1, s);
        select.setTimestamp(2, f);
        select.setTimestamp(3, s);
        select.setTimestamp(4, f);

        ResultSet res = select.executeQuery();
                
        sleep(randomInteger(0,10));

        if (res.next() & res.getInt(1)==0) {
            insert.setInt(1, room.getRoomId());
            insert.setInt(2, user.getUserId());
            insert.setTimestamp(3, s);
            insert.setTimestamp(4, f);
            insert.setTimestamp(5, new Timestamp(requestedDate.getTime()));
            insert.executeUpdate();
            conn.commit();
        } else {
            conn.rollback();
        }
        res.close();
        

        
    }
}
