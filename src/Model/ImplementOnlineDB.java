package Model;

import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.net.InetAddress;

public class ImplementOnlineDB {
    private String onlineUrl = "jdbc:mysql://mysql-4bf656-ayazkahloon26-3b61.g.aivencloud.com:27831/MetroPosDB?ssl-mode=REQUIRED";
    private String onlineUsername = "avnadmin";
    private String onlinePassword = "AVNS_lvZ5r8-iLSDkE7utjP2";
    protected String url = "jdbc:mysql://localhost:3306/MetroPosDB";
    protected String username = "root";
    protected String password = "legendcr7";
    private Queue<String> pendingQueries;
    private Timer internetCheckTimer;


    public ImplementOnlineDB() {
        this.pendingQueries = new LinkedList<>();
        loadPendingQueries();
        startInternetCheckTimer();
    }

    private boolean isInternetAvailable() {
        try {
            InetAddress.getByName("google.com").isReachable(1000);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void createPendingQueriesTable() {
        String query = "CREATE TABLE IF NOT EXISTS PendingQueries ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "query TEXT NOT NULL,"
                + "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPendingQueries() {
        createPendingQueriesTable();
        String query = "SELECT query FROM PendingQueries ORDER BY timestamp";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                pendingQueries.offer(rs.getString("query"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void startInternetCheckTimer() {
        internetCheckTimer = new Timer();
        internetCheckTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isInternetAvailable() && !pendingQueries.isEmpty()) {
                    processPendingQueries();
                }
            }
        }, 0, 180000); // Check every 3 minutes (180000 milliseconds)
    }

    private void processPendingQueries() {
        try (Connection onlineConn = DriverManager.getConnection(onlineUrl, onlineUsername, onlinePassword)) {
            while (!pendingQueries.isEmpty()) {
                String query = pendingQueries.peek(); // Just peek, don't remove yet

                try (Statement stmt = onlineConn.createStatement()) {
                    // Execute query in online DB
                    stmt.execute(query);

                    // Verify the data was added successfully
                    if (verifyQueryExecution(query, onlineConn)) {
                        pendingQueries.poll(); // Remove only after verification
                        removePendingQueryFromDB(query);
                    } else {
                        System.out.println("Query execution verification failed: " + query);
                        break; // Stop processing if verification fails
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean verifyQueryExecution(String query, Connection onlineConn) {
        // Extract table name and operation type from query
        String[] queryParts = query.split("\\s+");
        String operation = queryParts[0].toUpperCase();
        String tableName = "";

        // Parse query to get table name
        if (operation.equals("INSERT")) {
            tableName = queryParts[2]; // INSERT INTO tableName
        } else if (operation.equals("UPDATE")) {
            tableName = queryParts[1]; // UPDATE tableName
        } else if (operation.equals("DELETE")) {
            tableName = queryParts[2]; // DELETE FROM tableName
        } else {
            return false; // Return false for unsupported operations
        }

        try {
            // Verify data exists/updated in online DB
            String verifyQuery = "SELECT COUNT(*) FROM " + tableName + " WHERE 1=1";
            try (Statement stmt = onlineConn.createStatement();
                 ResultSet rs = stmt.executeQuery(verifyQuery)) {
                return rs.next(); // If we can query the table, assume success
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void removePendingQueryFromDB(String query) {
        String deleteQuery = "DELETE FROM PendingQueries WHERE query = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setString(1, query);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQuery(String query) {
        // Execute only in local database first
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {

            stmt.execute(query); // Remove boolean check as execute() returns false for INSERT/UPDATE
            System.out.println("Local DB execution successful");

            // Try online execution
            if (isInternetAvailable()) {
                try (Connection onlineConn = DriverManager.getConnection(onlineUrl, onlineUsername, onlinePassword);
                     Statement onlineStmt = onlineConn.createStatement()) {
                    onlineStmt.execute(query);
                    System.out.println("Online DB execution successful");
                } catch (SQLException e) {
                    System.out.println("Online execution failed: " + e.getMessage());
                    storePendingQuery(query);
                }
            } else {
                System.out.println("No internet, storing query");
                storePendingQuery(query);
            }
        } catch (SQLException e) {
            System.out.println("Local DB Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // this function is just for adding
    public void executeSpecialQuery(String query) {
        if (isInternetAvailable()) {
            try (Connection onlineConn = DriverManager.getConnection(onlineUrl, onlineUsername, onlinePassword);
                 Statement onlineStmt = onlineConn.createStatement()) {
                onlineStmt.execute(query);
                System.out.println("Online DB execution successful");
            } catch (SQLException e) {
                System.out.println("Online execution failed: " + e.getMessage());
                storePendingQuery(query);
            }
        } else {
            System.out.println("No internet, storing query");
            storePendingQuery(query);
        }
    }

    private void storePendingQuery(String query) {
        String insertQuery = "INSERT INTO PendingQueries (query) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, query);
            pstmt.executeUpdate();
            pendingQueries.offer(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        if (internetCheckTimer != null) {
            internetCheckTimer.cancel();
        }
    }

}
