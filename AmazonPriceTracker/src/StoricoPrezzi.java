import java.util.*;

public class StoricoPrezzi {
    List<Double> prezzi;
    List<Date> date;
    double prezzoDesiderato;
    
    StoricoPrezzi(List<Double> _prezzi,List<Date> _date, double _prezzoDesiderato){
        prezzi=_prezzi;
        date=_date;
        prezzoDesiderato=_prezzoDesiderato;
    }
}
