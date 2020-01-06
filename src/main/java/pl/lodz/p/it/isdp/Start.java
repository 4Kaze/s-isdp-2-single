package pl.lodz.p.it.isdp;

import java.sql.SQLException;

/**
 * Rozwiązanie problemu sortowania tablicy (zawiera błędy które należy usunąć z
 * zastosowanie nadzorowanego wykonania programu (debug)). Diagnostykę należy
 * przeprowadzić z zastosowaniem programu narzędziowego debuggera jdb
 */
public class Start {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak podanej liczby całkowitej jako argumentu wywołania");
            System.exit(1);
        }
        
        String dbName = System.getenv("DERBY_NAME");
        String username = System.getenv("DERBY_USER");
        String password = System.getenv("DERBY_PASS");
        String host = System.getenv("DERBY_HOST");
        DBConnector db = null;
        
        try {
            db = new DBConnector(host, dbName, username, password);
            SortTabNumbers sortExample = new SortTabNumbers(Integer.parseInt(args[0].trim()));

            System.out.println("Przed sortowaniem: " + sortExample); //niejawne wywołanie metody sortExample.toString()

            sortExample.sort(); 

            if (sortExample.checkMinOrderSort()) {
                System.out.println("Po sortowaniu: " + sortExample); //niejawne wywołanie metody sortExample.toString()
            }
            
            db.putSortedTable(sortExample.getTab());
            db.disconnect();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(4);
        } catch (NumberFormatException nfe) {
            System.out.println("Podany argument nie jest liczbą");
            System.exit(2);
        } catch (Throwable ex) {
            System.out.println("Zakończenie programu w wyniku zgłoszenia wyjątku typu " + ex.getClass().getName());
            System.exit(3);
        }
    }
}
