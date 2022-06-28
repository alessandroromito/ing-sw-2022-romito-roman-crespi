# Prova Finale di Ingegneria del Software -- 2021/2022 prof. Cugola

![image](https://user-images.githubusercontent.com/100211656/158979476-5ec96f53-0aeb-411f-8213-95391322a81d.png)

Sviluppo di una versione software del gioco da tavolo [Eriantys](https://www.craniocreations.it/prodotto/eriantys).

Il progetto consiste nello sviluppare un sistema distribuito composto da un server in grado di gestire una partita alla volta e da client multipli.
L'interazione avviene tramite socket di rete.

Interfacce grafiche implementate: CLI e GUI.

## Documentazione

### UML
- Diagramma UML iniziale dell'applicazione (ad alto livello): [UML iniziale](link)
- Diagramma UML finale dell'applicazione che mostrino com'è progettato il software: [UML finale](link)

### Documentazione di protocollo di comunicazione tra client e server
- Protocollo implementato: [Documentazione di protocollo](https://github.com/alessandroromito/ing-sw-2022-romito-roman-crespi/blob/main/deliverables/ProtocolDocumentation/SequenceDiagram.jpeg)

### Documenti di peer review
- [Prima peer review](https://github.com/alessandroromito/ing-sw-2022-romito-roman-crespi/blob/main/deliverables/PeerReviews/Peer%20review%20per%20GC18%20UML%20Model.pdf)
- [Seconda peer review](https://github.com/alessandroromito/ing-sw-2022-romito-roman-crespi/blob/main/deliverables/PeerReviews/Peer%20review%20per%20GC18%20Network.pdf)


### JavaDoc
- [JavaDoc](link)

### Test Coverage Report
Coverage dei test effettuati con Junit: [Report](link)

### Librerie e plugin implementati
- Maven
- JavaFx
- JUnit

## Funzionalità
### Funzionalità sviluppate
- Regole complete
- CLI
- GUI
- Socket
- 3 funzionalità aggiuntive
  - __12 character card__
  - __Persistenza__
  - __Resilienza alle disconnessioni__

## Esecuzione tramite files .jar
### Server
Per lanciare il server utilizzare il comando:
```
java -jar GC08-server.jar
```
Per lanciare il server con una porta diversa da "1511" utilizzare il comando:
```
java -jar GC08-server.jar --port <PORT>
```

### Client
Per lanciare il client:
- Per lanciare la cli:
```
java -jar GC08-client.jar --cli
```
- Per lanciare la gui basta aprire normalmente il file GC08-client.jar oppure utilizzare il comando:
```
java -jar GC08-client.jar
```

## Componenti del gruppo
- [Alessandro Romito](https://github.com/alessandroromito)
- [Gioele Crespi](https://github.com/GioeleCrespi)
- [Matteo Roman](https://github.com/TeoRomensPoli)
