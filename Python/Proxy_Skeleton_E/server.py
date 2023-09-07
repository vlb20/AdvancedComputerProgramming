#server.py – skeleton per ereditarietà
import socket, sys, threading
from interface import Subject

class Skeleton(Subject): #classe Skeleton eredita dalla classe Subject

	def __init__(self, port): #il costruttore richiede un argomento 'port' che
		self.port = port        # rappresententa il numero di port a cui il server si collegherà

	def request(self, data): #non viene implementato direttamente
		pass

	def run_skeleton(self): #Metodo che avvia il funzionamento dello Skeleton del server
		host = 'localhost' #indirizzo IP del server = localhost

		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) #creazione socket TCP e IPv4
		s.bind((host, self.port)) #la soket viene collegata all'indirizzo IP e al numero di port
		self.port = s.getsockname()[1] # get used port - il server ottiene la porta effettivamente utilizzata

		s.listen(5) #il server inizia ad ascoltare le richeieste in arrivo con una coda massima di 
								#connessioni in attesa pari a 5
		print("Socket is listening")

		while True: #viene avviato un loop infinito che attende continuamente le connessioni in arrivo

			# establish a connection with client
			c, addr = s.accept() #quando una connessione è accettate, vengono ottenuti
			# il socket della connessione ('c') e l'indirizzo del client ('addr')
			print('Connected to :', addr[0], ':', addr[1])
			#viene stampato l'indirizzo IP e il numero di porta del client a cui è stata stabilita la connessone

			# start a new thread
			t = threading.Thread(target=thd_fun, args=(c, self))# Lo passo come argomento al thread che serve la richiesta
			t.start() #chiamerà la funzione thd_fun() per gestire la comunicazione con il client

		s.close()#il socket principale viene chiuso alla fine del programma per rilasciare la 
	  #risorsa utilizzata dal server
...

#thread function per gestire la comunicazione con il client
def thd_fun(c, self): #prende la socket di connessione 'c' e l'oggetto 'self' (istanza del server Skeleton)

		# data received from client
		data = c.recv(1024)

		# upcall
		result = self.request(data) #per ottenere il risultato

		# send reversed string to client
		c.send(result)

		# connection closed
		c.close()

...
class RealSubject(Skeleton): #classe che eredita dalla classe Skeleton
	#questa classe rappresenta il soggetto reale che implementa il metodo 'request'
	#per gestire le richieste del client

	def request(self, data):#sovrascrive il metodo request implementando l'inversione

		# reverse the given string from client
		data = data[::-1]

		return data


if __name__ == "__main__":

	try:
		PORT = sys.argv[1] #si prova ad ottenre il numero di port da riga di comando
	except IndexError:
		print("Please, specify PORT arg")

	print("Server running")

	realSubject = RealSubject(int(PORT)) #creata un'istanza della classe RealSubject con il numero di port
	realSubject.run_skeleton()#per avviare il funzionamento del server
...