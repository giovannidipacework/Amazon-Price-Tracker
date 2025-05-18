import java.sql.*;
import java.text.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;

public class DatabaseUtentiOggetti {

    String ip;
    String porta;
    String nome;
    String user;
    String password;

    DatabaseUtentiOggetti(String _ip, String _porta, String _nome, String _user, String _password) {
        ip = _ip;
        porta = _porta;
        nome = _nome;
        user = _user;
        password = _password;
    }

    public List<Oggetto> PrelevaOggetti(String utente, int intervalloGiorniAggiornamentoSotricoPrezzi) {
        List<Oggetto> oggetti = new ArrayList();
        try (
                Connection co = DriverManager.getConnection("jdbc:mysql://"+ip+":"+porta+"/"+nome, user, password);
                PreparedStatement statementPrelevaOggetti = co.prepareStatement("SELECT uo.asin_oggetto,  nome_oggetto, link_oggetto, spMax.prezzo, uo.prezzo_desiderato, o.data_ultimo_update FROM utente_oggetti AS uo JOIN oggetto AS o ON uo.asin_oggetto=o.asin_oggetto JOIN (SELECT sp.asin_oggetto, sp.prezzo FROM storico_prezzi as sp INNER JOIN (SELECT asin_oggetto, MAX(data) AS MaxDateTime FROM storico_prezzi GROUP BY asin_oggetto ) as gsp ON sp.asin_oggetto = gsp.asin_oggetto AND sp.data = gsp.MaxDateTime) as spMax ON spMax.asin_oggetto=o.asin_oggetto WHERE id_utente = ?");
                )
        {
            statementPrelevaOggetti.setString(1, utente);
            ResultSet rs = statementPrelevaOggetti.executeQuery();
            while (rs.next()){
                Oggetto result = new Oggetto(
                    rs.getString("nome_oggetto"),
                    rs.getString("link_oggetto"),
                    rs.getString("prezzo"),
                    rs.getString("asin_oggetto")
                );
                oggetti.add(result);
                //result.stampa();
                
                java.util.Date now = java.util.Date.from(Instant.now());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                
                java.util.Date dataUltimoUpdate = new java.util.Date();
                try { 
                    dataUltimoUpdate= format.parse( rs.getString("data_ultimo_update") );
                } catch (ParseException ex) {
                    Logger.getLogger(DatabaseUtentiOggetti.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                long diff = now.getTime() - dataUltimoUpdate.getTime();
                long giorniDifferrenza =  TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                if(giorniDifferrenza > intervalloGiorniAggiornamentoSotricoPrezzi){
                    InserisciOggeto(result, rs.getString("prezzo_desiderato"), utente);
                }
                
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return oggetti;
    }

    public List<Double> PrelevaPrezziDesiderati (String id_utente){
        List<Double> prezziDesiderati = new ArrayList();
        try (
                Connection co = DriverManager.getConnection("jdbc:mysql://"+ip+":"+porta+"/"+nome, user, password);
                PreparedStatement statementPrelevaOggetti = co.prepareStatement("SELECT uo.prezzo_desiderato FROM utente_oggetti AS uo JOIN oggetto AS o ON uo.asin_oggetto=o.asin_oggetto JOIN (SELECT sp.asin_oggetto, sp.prezzo FROM storico_prezzi as sp INNER JOIN (SELECT asin_oggetto, MAX(data) AS MaxDateTime FROM storico_prezzi GROUP BY asin_oggetto ) as gsp ON sp.asin_oggetto = gsp.asin_oggetto AND sp.data = gsp.MaxDateTime) as spMax ON spMax.asin_oggetto=o.asin_oggetto WHERE id_utente = ?");
                )
        {
            statementPrelevaOggetti.setString(1, id_utente);
            ResultSet rs = statementPrelevaOggetti.executeQuery();
            while (rs.next()){
                prezziDesiderati.add(rs.getDouble("prezzo_desiderato"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return prezziDesiderati;
    }
    
    public StoricoPrezzi PrelevaStoricoPrezzi(Oggetto oggetto, java.util.Date _dal, java.util.Date _al, String utente) {
        java.sql.Date dal = new java.sql.Date(_dal.getTime());
        java.sql.Date al = new java.sql.Date(_al.getTime());
        List<Double> prezzi = new ArrayList();
        List<java.util.Date> date = new ArrayList();
        double prezzoDesiderato = 0;
        
        try (
                Connection co = DriverManager.getConnection("jdbc:mysql://"+ip+":"+porta+"/"+nome, user, password);
                PreparedStatement statementPrelevaStorico = co.prepareStatement("select prezzo, data, prezzo_desiderato from storico_prezzi as sp join utente_oggetti as uo on sp.asin_oggetto=uo.asin_oggetto where sp.asin_oggetto = ? and (data >= ? and  data <= ?) and id_utente = ?");
                )
        {
            statementPrelevaStorico.setString(1, oggetto.ASIN);
            statementPrelevaStorico.setDate(2, dal);
            statementPrelevaStorico.setDate(3, al);
            statementPrelevaStorico.setString(4,utente);
            ResultSet rs = statementPrelevaStorico.executeQuery();
            while (rs.next()){
                prezzoDesiderato = Double.valueOf( rs.getString("prezzo_desiderato") );
                
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date dataResult = format.parse( rs.getString("data") );
                date.add(dataResult);
                
                Double prezzoResult = Double.valueOf( rs.getString("prezzo") );
                prezzi.add(prezzoResult);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        StoricoPrezzi storico = new StoricoPrezzi(prezzi,date,prezzoDesiderato);
        
        return storico;
    }

    public void InserisciOggeto(Oggetto oggetto, String prezzoDesiderato, String utente) {
        java.util.Date JavaDate = java.util.Date.from(Instant.now());
        java.sql.Date now = new java.sql.Date(JavaDate.getTime());
        try (
                Connection co = DriverManager.getConnection("jdbc:mysql://"+ip+":"+porta+"/"+nome, user, password);
                PreparedStatement statementOggettoEsistente = co.prepareStatement("SELECT * FROM oggetto WHERE asin_oggetto = ?");
                PreparedStatement statementAggiornaOggetto = co.prepareStatement("UPDATE oggetto SET data_ultimo_update = ? WHERE asin_oggetto = ?");
                PreparedStatement statementInserisciOggetto = co.prepareStatement("INSERT INTO oggetto VALUES (?, ?, ?, ?)");
                PreparedStatement statementUtenteOggetto = co.prepareStatement("INSERT INTO utente_oggetti VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE prezzo_desiderato=?");
                PreparedStatement statementStoricoPrezzi = co.prepareStatement("INSERT INTO storico_prezzi VALUES (?, ?, ?)");) 
        {
            statementOggettoEsistente.setString(1, oggetto.ASIN);
            ResultSet rs = statementOggettoEsistente.executeQuery();

            rs.last();
            statementStoricoPrezzi.setString(1, oggetto.ASIN);
            statementStoricoPrezzi.setDate(2, now);
            statementStoricoPrezzi.setDouble(3, oggetto.prezzo);
            //Oggetto non esiste nel Database
            if (rs.getRow() == 0) {
                //Inserisce Oggetto nel Databse
                statementInserisciOggetto.setString(1, oggetto.nome);
                statementInserisciOggetto.setString(2, oggetto.link);
                statementInserisciOggetto.setString(3, oggetto.ASIN);
                statementInserisciOggetto.setDate(4, now);
                statementInserisciOggetto.executeUpdate();

                statementStoricoPrezzi.executeUpdate();
            } //Oggetto già esistente nel database
            else {
                //Se data_ultimo_update non è oggi, oggiorna data_ultimo_update e sotrico_prezzi
                String getDate = rs.getDate("data_ultimo_update").toString();
                if (!getDate.equals(now.toString())) {
                    //Aggiorna data_ultimo_update
                    statementAggiornaOggetto.setDate(1, now);
                    statementAggiornaOggetto.setString(2, oggetto.ASIN);
                    statementAggiornaOggetto.executeUpdate();

                    //Inserisce storico_prezzi nel Databse
                    statementStoricoPrezzi.executeUpdate();
                }
            }

            //Inserisce Oggetto tra quelli tracciati dall'Utente
            statementUtenteOggetto.setString(1, utente);
            statementUtenteOggetto.setString(2, oggetto.ASIN);
            statementUtenteOggetto.setDouble(3, Double.parseDouble(prezzoDesiderato));
            statementUtenteOggetto.setDouble(4, Double.parseDouble(prezzoDesiderato));
            statementUtenteOggetto.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public void EliminaOggetto(String ASIN, String utente){
        try (
                Connection co = DriverManager.getConnection("jdbc:mysql://"+ip+":"+porta+"/"+nome, user, password);
                PreparedStatement statementElimina = co.prepareStatement("DELETE FROM utente_oggetti WHERE asin_oggetto=? AND id_utente=?");
            )
        {
            statementElimina.setString(1, ASIN);
            statementElimina.setString(2, utente);
            statementElimina.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
    }
}
