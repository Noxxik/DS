package ConnectionInfo;

/**
 *
 * @author noxx
 */
public class ConnectionForTransactionLevels {

    private String user;
    private String url;
    private String password;
    private boolean localhost = false;

    public ConnectionForTransactionLevels(boolean localhost) {
        this.localhost = localhost;
        if (localhost) {
            url = "jdbc:postgresql://localhost:5432/ds_semestralka";
            user = "noxxik";
            password = "heslo";
        } else {
            url = "jdbc:postgresql://krizik.felk.cvut.cz:5434/ds2013_4";
            user = "ds2013_4";
            password = "modrookakobliha";
        }
    }
    
    public ConnectionForTransactionLevels() {        
        if (localhost) {
            url = "jdbc:postgresql://localhost:5432/ds_semestralka";
            user = "noxxik";
            password = "heslo";
        } else {
            url = "jdbc:postgresql://krizik.felk.cvut.cz:5434/ds2013_4";
            user = "ds2013_4";
            password = "modrookakobliha";
        }
    }

    public String getUser() {
        return user;
    }

    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLocalhost() {
        return localhost;
    }
    
}
