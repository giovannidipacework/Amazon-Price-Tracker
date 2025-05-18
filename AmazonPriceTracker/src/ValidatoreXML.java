
import com.thoughtworks.xstream.*;
import java.io.*;
import java.nio.file.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ValidatoreXML {

    public static void valida(String pathFile, String pathSchema) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = db.parse(new File(pathFile)); 
            Schema s = sf.newSchema(new StreamSource(new File(pathSchema)));
            s.newValidator().validate(new DOMSource(d));
        } catch (Exception e) {
            if (e instanceof SAXException) {
                System.out.println("Errore di validazione: " + e.getMessage());
            } else {
                System.out.println(e.getMessage());
            }
        }
        
    }
    
    public static Object LeggiXMLDa(String path){
        String string = "";
        XStream xs = new XStream();
        try{
            string = new String(Files.readAllBytes(Paths.get(path)));
        } catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        Object x = xs.fromXML(string);
        return x;
    }
}
