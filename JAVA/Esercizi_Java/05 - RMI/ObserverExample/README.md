## ObserverExample

Esempio di programma con pattern Observer utilizzando JavaRMI.
Il Server espone un servizio e registra in una lista una serie di Client Observer interessati all'evento di invocazione di quel servizio.
Ogni volta che un invocatore lato client invocherà il service_method, il server notificherà tutti gli Observer registrati.

