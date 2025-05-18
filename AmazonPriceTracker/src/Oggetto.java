import com.amazon.paapi5.v1.*;
import java.io.*;

public class Oggetto implements Serializable{
    public String nome;
    public String link;
    public double prezzo;
    public String ASIN;
    
    Oggetto(){}
    
    Oggetto(String _nome, String _link, String _prezzo, String _asin){
        nome = _nome;
        link = _link;
        prezzo = Double.parseDouble(_prezzo);
        ASIN = _asin;
    }
    
    Oggetto(String _nome, String _link, Double _prezzo, String _asin){
        nome = _nome;
        link = _link;
        prezzo = _prezzo;
        ASIN = _asin;
    }
    
    Oggetto(Item item){
        this(item.getItemInfo().getTitle().getDisplayValue(),
            item.getDetailPageURL(),
            item.getOffers().getListings().get(0).getPrice().getAmount().toString(),
            item.getASIN()
        );
    }
    
    Oggetto(AmazonPriceTracker.OggettoBean o){
        this(o.getNome(),o.getLink(),o.getPrezzo(),o.getASIN());
    }
    public void stampa(){
        System.out.println(ASIN);
        System.out.println(link);
        System.out.println(nome);
        System.out.println(prezzo);
    }
}
