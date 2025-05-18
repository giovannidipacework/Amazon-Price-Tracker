# Amazon Price Tracker

Amazon Price Tracker is a JavaFX-based application developed for a university advanced programming course. It provides a user-friendly interface to monitor and record price fluctuations of Amazon products over time. Users can locate items either by manual keyword search or by entering the ASIN code, leveraging the Amazon API for real-time data retrieval.

---

## 📌 Features

- **Product Tracking**  
  - Search products manually or by ASIN code using the Amazon API.  
  - Track price changes over time.

- **Data Management**  
  - Product and user data stored in a SQL database.  
  - Configuration and validation handled with XML and XSD files.

- **Performance Optimization**  
  - Implements a caching system to reduce redundant data requests.

- **Logging System**  
  - Dedicated server to receive and store GUI event logs in real time.

---

## 📊 Technologies

- JavaFX for GUI
- SQL Server for data persistence
- XML/XSD for configuration and validation
- Amazon API integration

---

## 📂 Project Contents

- `AmazonPriceTracker/src/` — Source code for the main JavaFX application  
- `AmazonPriceTracker/paapi5-java-sdk/` — Amazon Product Advertising API SDK  
- `ServerLog/src/` — Source code for the dedicated logging server  
- `programmazioneAvanzataSQL.sql` — XML and XSD files for configuration and validation
- `progrmmazione Avanzata documento+uml.pdf` — Documentation and diagrams

---

## 🏫 Academic Context

**University of Pisa**  
**Course**: Advanced Programming  
**Year**: 2025  
**Author**: Giovanni Dipace

---

## 📄 License

This project is for academic use. Usage for educational or portfolio purposes is allowed.
