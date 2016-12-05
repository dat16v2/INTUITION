package kea.intuition.model;

import kea.intuition.Intuition;

import java.sql.ResultSet;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        setUsername( username );
        setPassword( password );
    }

    public User() {
        setUsername( "" );
        setPassword( "" );
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean checkUser( String username, String password ) {
        ResultSet rs = Intuition.Config.getDb().select("*", "login", "login_id > 0");
        boolean triedValidUsername = false;

        try {
            while (rs.next()) {
                if( username.equals(rs.getString(2)) ) {
                    triedValidUsername = true;

                    if( password.equals(rs.getString(3)) ) {
                        logAttempt( rs.getInt(1), true );
                        return true;
                    }

                    logAttempt( rs.getInt(1), false );
                }
            }
        } catch (Exception ex) {
        }

        if( triedValidUsername == false ) {
            logAttempt( -1, false );
        }

        return false;
    }

    public static void logAttempt( int id, boolean success ) {
        Intuition.Config.getDb().insertLogAttempt(id, success);
    }
}
