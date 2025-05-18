import java.text.*;
import java.time.*;
import java.util.*;
import javafx.application.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.*;

public class AmazonPriceTracker extends Application {

    ConfigurazioneXML configurazioneXML;
    AmazonAPI amazonAPI;
    CacheInputOggetti cacheLocale;
    LogEventiDiNavigazione logEventi;
    DatabaseUtentiOggetti dbManager;
    
    TextField TextFieldNomeUtente,TextFieldLink,TextFieldPrezzoDesiderato;
    ComboBox ComboBoxRicerca;
    Button BottoneInserisciOggetto;
    
    DatePicker DatePickerDal, DatePickerAl;
    
    TableView<OggettoBean> TabellaOggettiTracciati;
    
    ObservableList<OggettoBean> ElementiTabella = FXCollections.observableArrayList();
    ObservableList<OggettoBean> ElementiComboBox = FXCollections.observableArrayList();
    LineChart<String, Number> GraficoStoricoPrezzi;

    VBox Layout;
    Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage _stage) {
        ValidatoreXML.valida("configurazione.xml","configurazione.xsd");
        configurazioneXML = (ConfigurazioneXML)ValidatoreXML.LeggiXMLDa("configurazione.xml");
        
        amazonAPI = new AmazonAPI(configurazioneXML.AccessKey,
                                  configurazioneXML.SecretKey,
                                  configurazioneXML.PartnerTag,
                                  configurazioneXML.Host,
                                  configurazioneXML.Region);
        
        String path = "cache.bin";
        cacheLocale = new CacheInputOggetti(path, this);
        
        logEventi = new LogEventiDiNavigazione(configurazioneXML.ipServerLog,
                                  configurazioneXML.portaServerLog);
        
        dbManager = new DatabaseUtentiOggetti(configurazioneXML.ipDBMS,
                                  configurazioneXML.portaDBMS,
                                  configurazioneXML.nomeDBMS,
                                  configurazioneXML.userDBMS,
                                  configurazioneXML.passwordDBMS);
        
        stage=_stage;
        
        TextFieldNomeUtente = new TextField();

        ComboBoxRicerca = new ComboBox();
        ComboBoxRicerca.setEditable(true);
        ComboBoxRicerca.setItems(ElementiComboBox);
        ComboBoxRicerca.setCellFactory(ComboBoxListCell.forListView(ElementiComboBox));

        TextFieldLink = new TextField();

        TextFieldPrezzoDesiderato = new TextField();

        BottoneInserisciOggetto = new Button("Inserisci Oggetto");
        
        TabellaOggettiTracciati = new TableView<>();
        DefinizioneTabella();
        
        DatePickerDal = new DatePicker(LocalDate.now().minusDays(configurazioneXML.intervalloGiorniDatepicker));
        DatePickerAl = new DatePicker(LocalDate.now());

        //Configurazione GraficoPrezzi
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Data");
        yAxis.setLabel("Prezzo");
        yAxis.setForceZeroInRange(false);
        GraficoStoricoPrezzi = new LineChart<String, Number>(xAxis, yAxis);
        GraficoStoricoPrezzi.setAnimated(false);
        
        DefinizioneAzioni();
        DefinizioneLayout();
        DefinisciStile();

        Group root = new Group(Layout);
        Scene scene = new Scene(root);
        
        stage.setTitle("Amazon Price Tracker");
        stage.setScene(scene);
        stage.show();

        ComboBoxRicerca.setPrefWidth(TextFieldNomeUtente.getWidth());
        
        
        TabellaOggettiTracciati.setFixedCellSize(TabellaOggettiTracciati.getHeight()
                / (double) (configurazioneXML.intervalloGiorniAggiornamentoStoricoPrezzi + 0.5)
        );
        
        ComboBoxRicerca.hide();
    }
    
    void DefinizioneAzioni(){
        TextFieldNomeUtente.setOnAction((ActionEvent ev) -> {
            AggiornaTabella();
        }
        );

        TextFieldLink.setOnAction((ActionEvent ev) -> {
            AggiornaGUI("link");
        });

        ComboBoxRicerca.setOnAction((Event e) -> {
            AzioneComboBox(e);
        });
        
        BottoneInserisciOggetto.setOnAction((ActionEvent ev) -> {
            AzioneInserisciOggetto();
        });

        TabellaOggettiTracciati.setOnMouseClicked((Event ev) -> {
            if (TabellaOggettiTracciati.getSelectionModel().getSelectedItem() != null) {
                Oggetto select  = new Oggetto(TabellaOggettiTracciati.getSelectionModel().getSelectedItem());
                AggiornaGrafico(select);
            }
        });
        
        TabellaOggettiTracciati.setOnContextMenuRequested((Event ev) -> {
            if (TabellaOggettiTracciati.getSelectionModel().getSelectedItem() != null) {
                Oggetto temp = new Oggetto(TabellaOggettiTracciati.getSelectionModel().getSelectedItem());
                ComboBoxRicerca.setValue(temp.nome);
                TextFieldLink.setText(temp.link);
            }
        });

        DatePickerAl.setOnAction((ActionEvent ev) -> {
            System.out.println(DatePickerAl);
            if (TabellaOggettiTracciati.getSelectionModel().getSelectedItem() != null) {
                Oggetto select  = new Oggetto(TabellaOggettiTracciati.getSelectionModel().getSelectedItem());
                AggiornaGrafico(select);
            }
        });
        DatePickerDal.setOnAction((ActionEvent ev) -> {
            System.out.println(DatePickerDal);
            if (TabellaOggettiTracciati.getSelectionModel().getSelectedItem() != null) {
                Oggetto select  = new Oggetto(TabellaOggettiTracciati.getSelectionModel().getSelectedItem());
                AggiornaGrafico(select);
            }
        });
        
        
        stage.setOnShown((WindowEvent ev) -> {
            apertura();
        });

        stage.setOnCloseRequest((WindowEvent ev) -> {
            chiusura();
        });
    }

    void DefinizioneTabella(){
        //Configurazione TabellaOggettiTracciati
        TableColumn ColonnaNome = new TableColumn("Nome");
        ColonnaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn ColonnaPrezzoAttuale = new TableColumn("Prezzo Attuale");
        ColonnaPrezzoAttuale.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        TableColumn ColonnaPrezzoDesiderato = new TableColumn("Prezzo Desiderato");
        ColonnaPrezzoDesiderato.setCellValueFactory(new PropertyValueFactory<>("prezzoDesiderato"));
        TableColumn ColonnaLink = new TableColumn("Link");
        ColonnaLink.setCellValueFactory(new PropertyValueFactory<>("link"));
        TableColumn ColonnaASIN = new TableColumn("ASIN");
        ColonnaASIN.setCellValueFactory(new PropertyValueFactory<>("ASIN"));
        TableColumn ColonnaElimina = new TableColumn("Elimina");
        ColonnaElimina.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        //https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view
        Callback<TableColumn<OggettoBean, String>, TableCell<OggettoBean, String>> ButtonCellFactory
                = new Callback<TableColumn<OggettoBean, String>, TableCell<OggettoBean, String>>() {
                    @Override
                    public TableCell call(final TableColumn<OggettoBean, String> param) {
                        final TableCell<OggettoBean, String> cell = new TableCell<OggettoBean, String>() {

                            final Button btn = new Button("Elimina");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction((ActionEvent event) -> {
                                        OggettoBean oggetto = getTableView().getItems().get(getIndex());
                                        dbManager.EliminaOggetto(oggetto.getASIN(), TextFieldNomeUtente.getText());
                                        AggiornaTabella();
                                        logEventi.InviaEvento("ELIMINA");
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        ColonnaElimina.setCellFactory(ButtonCellFactory);
        TabellaOggettiTracciati.setFixedCellSize(10);
        TabellaOggettiTracciati.getColumns().addAll(ColonnaNome, ColonnaPrezzoAttuale, ColonnaPrezzoDesiderato, ColonnaLink, ColonnaASIN, ColonnaElimina);
        TabellaOggettiTracciati.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    void DefinizioneLayout(){
        
        Label LabelNomeUtente,LabelRicerca,LabelLink,LabelPrezzoDesiderato;
        LabelRicerca = new Label("Ricerca Oggetto");
        LabelNomeUtente = new Label("Nome Utente");
        LabelLink = new Label("Link Oggetto");
        LabelPrezzoDesiderato = new Label("Prezzo Desiderato");
        
        BorderPane borderPane = new BorderPane();
        ListView list = new ListView();

        VBox bpTop = new VBox();
        bpTop.getChildren().addAll(LabelNomeUtente, TextFieldNomeUtente);
        bpTop.setFillWidth(false);
        bpTop.setAlignment(Pos.BASELINE_CENTER);
        bpTop.setSpacing(10);
        borderPane.setTop(bpTop);

        VBox bpLeft = new VBox();
        bpLeft.getChildren().addAll(LabelRicerca, ComboBoxRicerca);
        bpLeft.setAlignment(Pos.CENTER);
        bpLeft.setSpacing(10);
        borderPane.setMargin(bpLeft, new Insets(30));
        borderPane.setLeft(bpLeft);

        VBox bpRight = new VBox();
        bpRight.getChildren().addAll(LabelLink, TextFieldLink);
        bpRight.setAlignment(Pos.CENTER);
        bpRight.setSpacing(10);
        borderPane.setMargin(bpRight, new Insets(30));
        borderPane.setRight(bpRight);

        VBox bpBottom = new VBox();
        bpBottom.getChildren().addAll(LabelPrezzoDesiderato, TextFieldPrezzoDesiderato, BottoneInserisciOggetto);
        bpBottom.setAlignment(Pos.TOP_CENTER);
        bpBottom.setFillWidth(false);
        bpBottom.setSpacing(10);
        borderPane.setBottom(bpBottom);

        borderPane.setPrefWidth(320);
        borderPane.setPadding(new Insets(30));

        HBox top = new HBox();
        top.getChildren().addAll(borderPane, TabellaOggettiTracciati);
        top.setPadding(new Insets(30));
        top.setSpacing(20);
        TabellaOggettiTracciati.setPrefWidth(800);

        HBox bottom = new HBox();
        bottom.getChildren().addAll(DatePickerDal, DatePickerAl, GraficoStoricoPrezzi);
        bottom.setPadding(new Insets(10));
        bottom.setSpacing(20);
        bottom.setAlignment(Pos.CENTER);
        GraficoStoricoPrezzi.setPrefWidth(900);
        GraficoStoricoPrezzi.setPrefHeight(320);

        Layout = new VBox();
        Layout.getChildren().addAll(top, bottom);
    }
    
    void DefinisciStile(){
        Layout.setStyle("-fx-font-family:" + configurazioneXML.font + ";"
                + "-fx-font-size:" + configurazioneXML.dimensioneFont + "px;"
                + "-fx-background-color:" + configurazioneXML.coloreBackground + ";"
                + "-fx-dark-text-color:" + configurazioneXML.coloreFont + ";"
                + "-fx-mid-text-color:" + configurazioneXML.coloreFont + ";"
                + "-fx-light-text-color:" + configurazioneXML.coloreFont + ";"
        );
    }
    
    
    void apertura() {
        System.out.println("Caricamento della cache");
        logEventi.InviaEvento("AVVIO");
        cacheLocale.PrelevaCache();
    }

    void chiusura() {
        System.out.println("Chiusura");
        logEventi.InviaEvento("TERMINE");
        cacheLocale.ConservaCache();
    }

    
    void AggiornaGUI(String metodo) {
        switch (metodo) {
            case "link":
                Oggetto temp = amazonAPI.PrelevaOggettoASIN(TextFieldLink.getText());
                if(temp!=null){
                    ComboBoxRicerca.setValue(temp.nome);
                    logEventi.InviaEvento("LINK");
                }
                break;
            case "nome":
                TextFieldLink.setText(( (OggettoBean)(ComboBoxRicerca.getValue())).getLink() );
                logEventi.InviaEvento("RICERCA");
                break;
        }
    }

    void MostraOpzioni() {
        if (ComboBoxRicerca.getValue() != null) {
            if (!ComboBoxRicerca.getValue().toString().equals("")) {

                String SalvaRicerca = ComboBoxRicerca.getValue().toString();
                for (OggettoBean o : ElementiComboBox) {
                    if (o.toString().equals(ComboBoxRicerca.getValue().toString())) {
                        return;
                    }
                }
                String value = ComboBoxRicerca.getValue().toString();
                List<Oggetto> risultati = amazonAPI.RicercaOggetto(value);
                ElementiComboBox.clear();
                ComboBoxRicerca.setOnAction(null);
                ComboBoxRicerca.setValue(SalvaRicerca);
                ComboBoxRicerca.setOnAction((Event e) -> {
                    AzioneComboBox(e);
                });
                for (Oggetto o : risultati) {
                    if (o != null) {
                        ElementiComboBox.add(new OggettoBean(o));
                    }
                }
                ComboBoxRicerca.show();
            }
        }
    }

    void AzioneComboBox(Event ev) {
        System.out.println(ev.getEventType());
        if (ComboBoxRicerca.getValue() != null) {
            System.out.println(ComboBoxRicerca.getValue().getClass().toString());
        }
        if (ComboBoxRicerca.getValue() != null && ComboBoxRicerca.getValue().getClass().toString().equals("class java.lang.String")) {
            MostraOpzioni();
        } else if (ComboBoxRicerca.getValue() != null && ComboBoxRicerca.getValue().getClass().toString().equals("class AmazonPriceTracker$OggettoBean")) {
            AggiornaGUI("nome");
        }
    }

    void AzioneInserisciOggetto() {
        if(TextFieldPrezzoDesiderato.getText().equals("") ||
            TextFieldNomeUtente.getText().equals(""))
            return;
        
        logEventi.InviaEvento("INSERIMENTO");

        String PrelevaLink = TextFieldLink.getText();
        String PrelevaUtente = TextFieldNomeUtente.getText();
        String PrelevaPrezzoDesiderato = TextFieldPrezzoDesiderato.getText();
        
        try {
            Double.parseDouble(PrelevaPrezzoDesiderato);
        }
        catch (Exception e) { 
            System.err.println(e);
            return;
        } 

        String PrelevaRicerca = "";
        if (ComboBoxRicerca.getValue() != null) {
            PrelevaRicerca = ComboBoxRicerca.getValue().toString();
        }

        //Se Ricerca non è vuota e Link non è vuoto
        if (!PrelevaRicerca.equals("") && PrelevaLink.equals("")) {
            MostraOpzioni();
        } //Se Link non è vuoto aggiorna e Inserisci
        else if (!PrelevaLink.equals("")) {
            Oggetto trovato = amazonAPI.PrelevaOggettoASIN(PrelevaLink);
            dbManager.InserisciOggeto(trovato, PrelevaPrezzoDesiderato, PrelevaUtente);
            AggiornaGUI("link");
            AggiornaTabella();
            TabellaOggettiTracciati.getSelectionModel().selectLast();
            Oggetto select  = new Oggetto(TabellaOggettiTracciati.getSelectionModel().getSelectedItem());
            AggiornaGrafico(select);
        }

    }

    void AggiornaGrafico(Oggetto selezionato) {
        if (selezionato == null) {
            return;
        }
        
        StoricoPrezzi storico = dbManager.PrelevaStoricoPrezzi(selezionato, ConvertiDatePicker(DatePickerDal), ConvertiDatePicker(DatePickerAl), TextFieldNomeUtente.getText());
        if(storico ==null)
            return;
        List<Double> prezzi = storico.prezzi;
        List<Date> date = storico.date;
        
        if (prezzi.size() <= 0) {
            return;
        }
        GraficoStoricoPrezzi.getData().clear();

        XYChart.Series<String, Number> StoricoPrezzi = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> PrezzoDesiderato = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> PrezzoMinimo = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> PrezzoMassimo = new XYChart.Series<String, Number>();

        StoricoPrezzi.setName(selezionato.nome);
        PrezzoDesiderato.setName("Prezzo Desiderato");
        PrezzoMinimo.setName("Prezzo Minimo");
        PrezzoMassimo.setName("Prezzo Massimo");

        int i = 0;
        double prezzoMin = prezzi.get(0);
        double prezzoMax = 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateMin = date.get(0);
        Date dateMax = null;
        try {
            dateMax = simpleDateFormat.parse("2000-01-01");
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        for (Double d : prezzi) {
            XYChart.Data<String, Number> punto = new XYChart.Data<String, Number>(
                    simpleDateFormat.format(date.get(i)), d
            );
            StoricoPrezzi.getData().add(punto);
            prezzoMin = (d < prezzoMin) ? d : prezzoMin;
            prezzoMax = (d > prezzoMax) ? d : prezzoMax;
            dateMin = (date.get(i).before(dateMin) ) ? date.get(i) : dateMin;
            dateMax = (date.get(i).after(dateMax) ) ? date.get(i) : dateMax;
            i++;
        }

        PrezzoDesiderato.getData().add(new XYChart.Data<String, Number>(simpleDateFormat.format(dateMin), storico.prezzoDesiderato));
        PrezzoDesiderato.getData().add(new XYChart.Data<String, Number>(simpleDateFormat.format(dateMax), storico.prezzoDesiderato));
        PrezzoMinimo.getData().add(new XYChart.Data<String, Number>(simpleDateFormat.format(dateMin), prezzoMin));
        PrezzoMinimo.getData().add(new XYChart.Data<String, Number>(simpleDateFormat.format(dateMax), prezzoMin));
        PrezzoMassimo.getData().add(new XYChart.Data<String, Number>(simpleDateFormat.format(dateMin), prezzoMax));
        PrezzoMassimo.getData().add(new XYChart.Data<String, Number>(simpleDateFormat.format(dateMax), prezzoMax));

        GraficoStoricoPrezzi.getData().addAll(StoricoPrezzi, PrezzoDesiderato, PrezzoMinimo, PrezzoMassimo);
    }

    void AggiornaTabella() {
        List<Oggetto> result = dbManager.PrelevaOggetti(TextFieldNomeUtente.getText(), configurazioneXML.intervalloGiorniAggiornamentoStoricoPrezzi );
        List<Double> prezziDesiderati = dbManager.PrelevaPrezziDesiderati(TextFieldNomeUtente.getText());
        if(result==null || prezziDesiderati==null)
            return;
        int i = 0;
        ElementiTabella.clear();
        for (Oggetto o : result) {
            ElementiTabella.add(new OggettoBean(o, prezziDesiderati.get(i).doubleValue()));
            i++;
        }

        TabellaOggettiTracciati.setItems(ElementiTabella);
    }

    Date ConvertiDatePicker(DatePicker datePicker) {
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.of("Z")));
        Date date = Date.from(instant);
        return date;
    }

    public static class OggettoBean {

        private final SimpleStringProperty nome;
        private final SimpleStringProperty link;
        private final SimpleDoubleProperty prezzo;
        private final SimpleDoubleProperty prezzoDesiderato;
        private final SimpleStringProperty ASIN;

        private OggettoBean(String _nome, String _link, String _prezzo, String _asin, double _prezzoDesiderato) {
            nome = new SimpleStringProperty(_nome);
            link = new SimpleStringProperty(_link);
            prezzo = new SimpleDoubleProperty(Double.parseDouble(_prezzo));
            ASIN = new SimpleStringProperty(_asin);
            prezzoDesiderato = new SimpleDoubleProperty(_prezzoDesiderato);
        }

        private OggettoBean(Oggetto oggetto, double prezzoDesiderato) {
            this(oggetto.nome, oggetto.link, String.valueOf(oggetto.prezzo), oggetto.ASIN, prezzoDesiderato);
        }

        private OggettoBean(Oggetto oggetto) {
            this(oggetto.nome, oggetto.link, String.valueOf(oggetto.prezzo), oggetto.ASIN, 0);
        }

        public String getNome() {
            return nome.get();
        }

        public String getLink() {
            return link.get();
        }

        public double getPrezzo() {
            return prezzo.get();
        }

        public String getASIN() {
            return ASIN.get();
        }

        public double getPrezzoDesiderato() {
            return prezzoDesiderato.get();
        }

        public String toString() {
            return getNome();
        }
    }
}
