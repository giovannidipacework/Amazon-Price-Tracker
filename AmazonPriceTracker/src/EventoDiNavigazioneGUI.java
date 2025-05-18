import java.io.*;
import java.time.*;
import java.util.*;

public class EventoDiNavigazioneGUI implements Serializable{
    String nome;
    String nomeApplicazione;
    String ip;
    Date data;
    
    EventoDiNavigazioneGUI(String _nome, String _nomeApplicazione, String _ip){
        nome = _nome;
        nomeApplicazione = _nomeApplicazione;
        ip = _ip;
        data = java.util.Date.from(Instant.now());
    }
}
