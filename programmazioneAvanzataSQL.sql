CREATE DATABASE  IF NOT EXISTS `amazon_price_tracker` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `amazon_price_tracker`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: amazon_price_tracker
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `oggetto`
--

DROP TABLE IF EXISTS `oggetto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oggetto` (
  `nome_oggetto` varchar(255) NOT NULL,
  `link_oggetto` varchar(255) NOT NULL,
  `asin_oggetto` varchar(255) NOT NULL,
  `data_ultimo_update` date NOT NULL,
  PRIMARY KEY (`asin_oggetto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oggetto`
--

LOCK TABLES `oggetto` WRITE;
/*!40000 ALTER TABLE `oggetto` DISABLE KEYS */;
INSERT INTO `oggetto` VALUES ('Logitech B100 Mouse USB Cablato, 3 Pulsanti, Rilevamento Ottico, Ambidestro, PC/Mac/?Laptop, ?Nero','https://www.amazon.it/dp/B00AZKNPZC?tag=verona97-21&linkCode=osi&th=1&psc=1','B00AZKNPZC','2021-01-12'),('Vitamaze® Maca Capsule ad Alto Dosaggio 4000 mg Polvere + L-Arginina + Vitamine + Zinco, 240 Capsule per 2 Mesi, Qualità Tedesca, Maca Root Peruviana delle Ande, Qualità Tedesca, senza Additivi','https://www.amazon.it/dp/B01C370Q1U?tag=verona97-21&linkCode=osi&th=1&psc=1','B01C370Q1U','2021-01-11'),('Maca Peruviana Biologica in Polvere [ Gelatinizzata ] 1 kg. 100% Naturale e Pura, Prodotto in Perù dalla Radice di Maca Bio. NaturaleBio','https://www.amazon.it/dp/B07DCB88QJ?tag=verona97-21&linkCode=osi&th=1&psc=1','B07DCB88QJ','2021-01-11'),('AOC C24G1 Monitor Gaming Curvo da 24\", Pannello VA, FHD 1920 x 1080 a 144 Hz, 1 Porta D-SUB, 2 Porte HDMI, 1 Porta Display, Tempo di Risposta 1 msec, Nero','https://www.amazon.it/dp/B07DTN4BM8?tag=verona97-21&linkCode=osi&th=1&psc=1','B07DTN4BM8','2021-01-12'),('AOC 27V2Q Monitor LED da 27\" IPS, FHD, 1920 x 1080, Senza Bordi, HDMI, DP, Nero','https://www.amazon.it/dp/B07FBW3PBM?tag=verona97-21&linkCode=osi&th=1&psc=1','B07FBW3PBM','2021-01-05'),('Rotoloni Regina Carta Igienica | Confezione da 42 Maxi Rotoli | 500 strappi per rotolo* | Lunghi più del doppio dei normali rotoli| Carta 100% certificata FSC®','https://www.amazon.it/dp/B07JYZ68YT?tag=verona97-21&linkCode=osi&th=1&psc=1','B07JYZ68YT','2021-01-12'),('HyperX FURY Black HX426C16FB3/8 Memoria 8GB 2666MHz DDR4 CL16 DIMM 1Rx8','https://www.amazon.it/dp/B07WD5VKTS?tag=verona97-21&linkCode=osi&th=1&psc=1','B07WD5VKTS','2021-01-09'),('Apple iPhone 11 Pro (64GB) - Verde Notte','https://www.amazon.it/dp/B07XS3ZX16?tag=verona97-21&linkCode=osi&th=1&psc=1','B07XS3ZX16','2021-01-12'),('AOC 24G2U/BK Monitor da Gaming Flat 23.8\" IPS, Frameless, FHD 1920 x 1080 a 144 Hz, Tempo di Risposta 1 msec/MPRT, 2 x HDMI, 1 DP, 4 x USB, Speaker, Regolabile in Altezza, FreeSync, Nero/Rosso','https://www.amazon.it/dp/B07Y3RYLVH?tag=verona97-21&linkCode=osi&th=1&psc=1','B07Y3RYLVH','2021-01-12'),('AOC 27G2U/BK Monitor da Gaming Flat 27\" IPS, Frameless,FHD 1920 x 1080 a 144 Hz, Tempo di Risposta 1 msec/MPRT, 2 x HDMI, 1 DP, 4 x USB, Speaker, Regolabile in Altezza, FreeSync, Nero/Rosso','https://www.amazon.it/dp/B07YBHBNJB?tag=verona97-21&linkCode=osi&th=1&psc=1','B07YBHBNJB','2021-01-05'),('L.O.L. Surprise! Bambole da Collezione per Bambine - Con 10 Sorprese e Accessori - Boss Queen - Mobili Serie 3','https://www.amazon.it/dp/B085B1VDZK?tag=verona97-21&linkCode=osi&th=1&psc=1','B085B1VDZK','2021-01-11'),('Samsung QE55Q64TAUXZT Serie Q60T Modello Q64T QLED Smart TV 55\", Ultra HD 4K, Wi-Fi, Silver, 2020, Esclusiva Amazon','https://www.amazon.it/dp/B086B5K94Y?tag=verona97-21&linkCode=ogi&th=1&psc=1','B086B5K94Y','2021-01-12'),('Nuova Blink Outdoor, Videocamera di sicurezza in HD, senza fili, resistente alle intemperie, batteria autonomia 2 anni, rilevazione movimento, prova gratuita del Blink Subscription Plan |1 videocamera','https://www.amazon.it/dp/B086DKVS1P?tag=verona97-21&linkCode=ogi&th=1&psc=1','B086DKVS1P','2021-01-12'),('Roffie Webcam 1080P FHD con Microfono, PC Portatile Desktop USB 2.0 Videocamera per Videochiamate, Studio, Conferenza, Registrazione, Gioca a Giochi e Lavoro a casa','https://www.amazon.it/dp/B0878VGZZ5?tag=verona97-21&linkCode=osi&th=1&psc=1','B0878VGZZ5','2021-01-08'),('Samsung TV UE55TU7190UXZT Smart TV 55\" Serie TU7190, Crystal UHD 4K, Wi-Fi, 2020, Argento','https://www.amazon.it/dp/B087KB7WQL?tag=verona97-21&linkCode=ogi&th=1&psc=1','B087KB7WQL','2021-01-12'),('Apple MacBook Pro (13\", 16GB RAM, 512GB Memoria SSD, Magic Keyboard) - Grigio siderale (Ultimo Modello)','https://www.amazon.it/dp/B08838PN5F?tag=verona97-21&linkCode=osi&th=1&psc=1','B08838PN5F','2021-01-07'),('HP - PC V24i Monitor, Schermo 24 Pollici IPS Full HD, Risoluzione 1920 x 1080, Micro-Edge, Antiriflesso, Tempo di Risposta 5 ms, Comandi sullo Schermo, HDMI e VGA, Reclinabile, Nero','https://www.amazon.it/dp/B08CRFM1VK?tag=verona97-21&linkCode=osi&th=1&psc=1','B08CRFM1VK','2021-01-07'),('Novità Apple iPhone 12 (128GB) - Azzurro','https://www.amazon.it/dp/B08L5S3XNM?tag=verona97-21&linkCode=osi&th=1&psc=1','B08L5S3XNM','2021-01-05'),('Novità Apple iPhone 12 mini (128GB) - nero','https://www.amazon.it/dp/B08L5S9ZP8?tag=verona97-21&linkCode=osi&th=1&psc=1','B08L5S9ZP8','2021-01-05'),('Novità Apple iPhone 12 Pro (128GB) - Grafite','https://www.amazon.it/dp/B08L5SBHBB?tag=verona97-21&linkCode=osi&th=1&psc=1','B08L5SBHBB','2021-01-12'),('Apple iPhone 11 (64GB) - nero','https://www.amazon.it/dp/B08L6XCTRZ?tag=verona97-21&linkCode=osi&th=1&psc=1','B08L6XCTRZ','2021-01-12'),('Apple iPhone SE (128GB) - nero','https://www.amazon.it/dp/B08L6YWM4B?tag=verona97-21&linkCode=osi&th=1&psc=1','B08L6YWM4B','2021-01-11'),('Novità Apple MacBook Pro (13\", Chip Apple M1 con CPU 8-core e GPU 8?core, 8GB RAM, 256GB SSD) - Grigio siderale','https://www.amazon.it/dp/B08N5SP3FZ?tag=verona97-21&linkCode=osi&th=1&psc=1','B08N5SP3FZ','2021-01-11'),('Novità Apple MacBook Air (13\", Chip Apple M1 con CPU 8-core e GPU 7?core, 8GB RAM, 256GB SSD) - Grigio siderale','https://www.amazon.it/dp/B08N5VQK59?tag=verona97-21&linkCode=osi&th=1&psc=1','B08N5VQK59','2021-01-06'),('Novità Apple Mac mini (Chip Apple M1 con CPU 8-core e GPU 8?core, 8GB RAM, 256GB SSD)','https://www.amazon.it/dp/B08N5VZ75B?tag=verona97-21&linkCode=osi&th=1&psc=1','B08N5VZ75B','2021-01-12'),('UbyFit Elastici Fitness *Set di 5 Pezzi*, Bande Elastiche Extra Resistenti, Bande in Lattice Naturale, per Esercizi, Crossfit, Yoga, Pilates, Allenamento, Riabilitazione, Palestra','https://www.amazon.it/dp/B08P6QM33H?tag=verona97-21&linkCode=ogi&th=1&psc=1','B08P6QM33H','2021-01-11');
/*!40000 ALTER TABLE `oggetto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storico_prezzi`
--

DROP TABLE IF EXISTS `storico_prezzi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storico_prezzi` (
  `asin_oggetto` varchar(255) NOT NULL,
  `data` date NOT NULL,
  `prezzo` double NOT NULL,
  PRIMARY KEY (`asin_oggetto`,`data`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storico_prezzi`
--

LOCK TABLES `storico_prezzi` WRITE;
/*!40000 ALTER TABLE `storico_prezzi` DISABLE KEYS */;
INSERT INTO `storico_prezzi` VALUES ('B00AZKNPZC','2021-01-12',6.99),('B01C370Q1U','2021-01-11',18.97),('B07DCB88QJ','2021-01-11',19.99),('B07DTN4BM8','2021-01-05',149.25),('B07DTN4BM8','2021-01-07',149.25),('B07DTN4BM8','2021-01-09',149.25),('B07DTN4BM8','2021-01-11',149.25),('B07DTN4BM8','2021-01-12',189.99),('B07FBW3PBM','2021-01-05',191.24),('B07JYZ68YT','2021-01-12',43),('B07WD5VKTS','2021-01-09',36.99),('B07XS3ZX16','2021-01-05',877.35),('B07XS3ZX16','2021-01-06',877.35),('B07XS3ZX16','2021-01-08',877.35),('B07XS3ZX16','2021-01-10',877.35),('B07XS3ZX16','2021-01-12',877.35),('B07Y3RYLVH','2021-01-05',257.43),('B07Y3RYLVH','2021-01-06',258.5),('B07Y3RYLVH','2021-01-08',258.5),('B07Y3RYLVH','2021-01-11',258.5),('B07Y3RYLVH','2021-01-12',257.9),('B07YBHBNJB','2021-01-05',224.46),('B085B1VDZK','2021-01-11',22.03),('B086B5K94Y','2021-01-12',699),('B086DKVS1P','2021-01-11',119.99),('B086DKVS1P','2021-01-12',119.99),('B0878VGZZ5','2021-01-05',19.99),('B0878VGZZ5','2021-01-07',19.99),('B0878VGZZ5','2021-01-08',19.99),('B087KB7WQL','2021-01-12',452.71),('B08838PN5F','2021-01-05',1958),('B08838PN5F','2021-01-07',1958),('B08CRFM1VK','2021-01-05',109.99),('B08CRFM1VK','2021-01-07',109.99),('B08L5S3XNM','2021-01-05',949),('B08L5S9ZP8','2021-01-05',873),('B08L5SBHBB','2021-01-08',1189),('B08L5SBHBB','2021-01-10',1189),('B08L5SBHBB','2021-01-12',1189),('B08L6XCTRZ','2020-12-06',622.99),('B08L6XCTRZ','2020-12-17',719),('B08L6XCTRZ','2020-12-18',629),('B08L6XCTRZ','2020-12-25',695.89),('B08L6XCTRZ','2021-01-05',689),('B08L6XCTRZ','2021-01-07',689),('B08L6XCTRZ','2021-01-09',689),('B08L6XCTRZ','2021-01-10',719),('B08L6XCTRZ','2021-01-11',695.99),('B08L6XCTRZ','2021-01-12',695.99),('B08L6YWM4B','2021-01-11',532.99),('B08N5SP3FZ','2021-01-11',1479),('B08N5VQK59','2021-01-06',1159),('B08N5VZ75B','2021-01-11',819),('B08N5VZ75B','2021-01-12',819),('B08P6QM33H','2021-01-11',9.97);
/*!40000 ALTER TABLE `storico_prezzi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente` (
  `id_utente` int(11) NOT NULL AUTO_INCREMENT,
  `nome_utente` varchar(255) NOT NULL,
  PRIMARY KEY (`id_utente`,`nome_utente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,'giorgio'),(2,'pippo'),(3,'andrea'),(4,'lorenzo'),(5,'federico');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente_oggetti`
--

DROP TABLE IF EXISTS `utente_oggetti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente_oggetti` (
  `id_utente` varchar(255) NOT NULL,
  `asin_oggetto` varchar(255) NOT NULL,
  `prezzo_desiderato` double NOT NULL,
  PRIMARY KEY (`id_utente`,`asin_oggetto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente_oggetti`
--

LOCK TABLES `utente_oggetti` WRITE;
/*!40000 ALTER TABLE `utente_oggetti` DISABLE KEYS */;
INSERT INTO `utente_oggetti` VALUES ('andrea','B07JYZ68YT',30),('giorgio','B00AZKNPZC',4),('giorgio','B07DTN4BM8',180),('giorgio','B07XS3ZX16',750),('giorgio','B07Y3RYLVH',30),('giorgio','B08L6XCTRZ',750),('pippo','B07Y3RYLVH',100),('Ruggi','B086B5K94Y',89),('Ruggi','B08L6XCTRZ',300);
/*!40000 ALTER TABLE `utente_oggetti` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-12 18:27:44
