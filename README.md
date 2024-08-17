# ProgettoBiglietteriaAutobus

Questo progetto è stato realizzato per gli esami di Ingegneria del Software e Advanced Computer Programming.
Il progetto consiste nell'implementazione di una applicazione Java e di un sito web per l'emissione di biglietti per autobus che operano su tratte interregionali tra città italiane. 

Ogni autobus ha un identificativo unico, un numero limitato di posti passeggeri e un certo numero di spazi per bagagli, con vincoli di dimensione. Ogni passeggero può portare un solo bagaglio, il cui trasporto comporta un supplemento di 5 euro sul prezzo del biglietto.
La compagnia effettua due corse giornaliere per ogni tratta: una per l'andata e una per il ritorno, utilizzando sempre lo stesso autobus per entrambe. Ogni corsa è identificata da un codice univoco, una data, un orario di partenza e arrivo, e un prezzo prefissato.

Il sistema di biglietteria consente agli impiegati di emettere biglietti verificando prima la disponibilità dei posti e degli spazi per i bagagli. Al momento dell'emissione, la disponibilità residua viene aggiornata e il sistema registra data e ora dell'emissione e il codice dell'impiegato responsabile. I clienti possono anche prenotare biglietti online, ma la stampa effettiva del biglietto sarà effettuata da un impiegato presso il punto di partenza, previa presentazione della prenotazione via email.

Il codice è organizzato secondo il modello Boundary-Control-Entity-Database (BCED):
- [boundary](./app/src/boundary) contiene gli oggetti responsabili dell'interfaccia utente e della logica di presentazione
- [control](./app/src/control) contiene la logica di controllo dell'applicazione, che coordina le operazioni tra boundary ed entity
- [entity](./app/src/entity) contiene oggetti che rappresentano la semantica delle entità del dominio applicativo
- [database](./app/src/database) contiene le classi responsabili dell'estrazione dei dati dal database e che implementano le operazioni CRUD
