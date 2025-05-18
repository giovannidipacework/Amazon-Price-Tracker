import com.thoughtworks.xstream.*;
import java.io.*;
import java.net.*;

public class LogEventiDiNavigazione {

    String ip;
    String porta;

    LogEventiDiNavigazione(String _ip, String _porta) {
        ip = _ip;
        porta = _porta;
    }

    public void InviaEvento(String ev) {
        String ev_nomeApplicazione = "AmazonPriceTracker";
        String ev_ip = "192.168.1.1";
        EventoDiNavigazioneGUI evento = new EventoDiNavigazioneGUI(ev, ev_nomeApplicazione, ev_ip);
        
        XStream xs = new XStream(); 
        String x = "\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"  + xs.toXML(evento) + "\n"; 
        try ( DataOutputStream dout = new DataOutputStream((new Socket(ip, Integer.parseInt(porta) )).getOutputStream()) ) 
        {
            dout.writeUTF(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
