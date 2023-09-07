#client.py

import socket, sys
from interface import Subject

class Proxy(Subject): #Definiamo la classe Proxy che eredita dalla classe Subject

	def __init__(self, port): #il costruttore del proxy richiede un argomento 'port'
		self.port = port        #che rappresenta il port number a cui il proxy si collegherà

	def request(self, data): #sovrascrive il metodo 'request' definito nell'interfaccia
		IP = 'localhost'   #indirizzo IP del server a cui il proxy si collegherà
		BUFFER_SIZE = 1024  #dimensione del buffer utilizzata per ricevere e inviare dati
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) #creazione socket TCP e IPv4
		s.connect((IP, self.port)) #il proxy si connette al server passando indirizzo IP e port
		s.send(message.encode("utf-8")) #viene inviato il messaggio al server convertito in bytes
		data = s.recv(BUFFER_SIZE) #viene ricevuta la risposta dal server
		print ("received data: " + data.decode("utf-8")) #decodificata e stampata
		s.close() #il socket del proxy 's' viene chiuso per terminare la comunicazione
...

if __name__ == "__main__":
	try:
		PORT = sys.argv[1] #vengono utilizzati il numero di porta 
		MESSAGE = sys.argv[2] #e messaggio da riga di comando
	except IndexError:
		print("Specify args!")

	print("Client: Generating request")
	proxy = Proxy(int(PORT)) #viene creata un'istanza della classe Proxy con il port number convertito in intero
	proxy.request(MESSAGE) #viene chiamato il metodo request del proxy, passando il messaggio come argomento