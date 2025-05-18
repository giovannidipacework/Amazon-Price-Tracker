import java.io.*;
import java.time.*;

public class CacheInputOggetti {
    String path;
    AmazonPriceTracker gui;
    
    CacheInputOggetti(String _path, AmazonPriceTracker _gui){
        path=_path;
        gui=_gui;
    }
    
    public void ConservaCache() {
        try (FileOutputStream fout = new FileOutputStream(path);
                ObjectOutputStream oout = new ObjectOutputStream(fout);)
        { 
            if(!gui.TextFieldNomeUtente.getText().equals("")){
                oout.writeObject("1" + gui.TextFieldNomeUtente.getText());
                System.out.println(gui.TextFieldNomeUtente.getText());
            }
            if(gui.ComboBoxRicerca.getValue()!=null){
                oout.writeObject("2" + gui.ComboBoxRicerca.getValue().toString());
                System.out.println(gui.ComboBoxRicerca.getValue().toString());
            }
            if(!gui.TextFieldLink.getText().equals("")){
                oout.writeObject("3" + gui.TextFieldLink.getText());
                System.out.println(gui.TextFieldLink.getText());
            }
            if(!gui.TextFieldPrezzoDesiderato.getText().equals("")){
                oout.writeObject("4" + gui.TextFieldPrezzoDesiderato.getText());
                System.out.println(gui.TextFieldPrezzoDesiderato.getText());
            }
            if(!gui.DatePickerDal.getValue().toString().equals("")){
                oout.writeObject("5" + gui.DatePickerDal.getValue().toString());
                System.out.println(gui.DatePickerDal.getValue().toString());
            }
            if(!gui.DatePickerAl.getValue().toString().equals("")){
                oout.writeObject("6" + gui.DatePickerAl.getValue().toString());
                System.out.println(gui.DatePickerAl.getValue().toString());
            }
            if(gui.TabellaOggettiTracciati.getSelectionModel().getSelectedItem()!=null){
                oout.writeObject("7" + gui.TabellaOggettiTracciati.getSelectionModel().getFocusedIndex());
                System.out.println(gui.TabellaOggettiTracciati.getSelectionModel().getFocusedIndex());
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void PrelevaCache() {
        try (FileInputStream fin = new FileInputStream(path);
                ObjectInputStream oin = new ObjectInputStream(fin);
                )
        {
            boolean fine=true;
            while(fine){
                String object = (String) oin.readObject();
                char first = object.charAt(0);
                object = object.substring(1);
                switch(first) {
                    case '1':
                        System.out.println("NomeUtente: " + object);
                        gui.TextFieldNomeUtente.setText(object);
                        gui.AggiornaTabella();
                        break;
                    case '2':
                        System.out.println("ComboBox: " + object);
                        gui.ComboBoxRicerca.setValue(object);
                        break;
                    case '3':
                        System.out.println("Link: " + object);
                        gui.TextFieldLink.setText(object);
                        break;
                    case '4':
                        System.out.println("PrezzoDes: " + object);
                        gui.TextFieldPrezzoDesiderato.setText(object);
                        break;
                    case '5':
                        System.out.println("Dal: " + object);
                        gui.DatePickerDal.setValue(LocalDate.parse(object));
                        break;
                    case '6':
                        System.out.println("Al: " + object);
                        gui.DatePickerAl.setValue(LocalDate.parse(object));
                        break;
                    case '7':
                        gui.TabellaOggettiTracciati.getSelectionModel().select( Integer.parseInt(object) );
                        Oggetto select  = new Oggetto(gui.TabellaOggettiTracciati.getSelectionModel().getSelectedItem());
                        gui.AggiornaGrafico(select);
                        fine=false;
                        break;
                    default:
                        fine=false;
                        break;
                }
            }
            
            
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }
}
