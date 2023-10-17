from interface import Service
import socket, sys
import multiprocess as mp

#Process function
def proc_fun(conn, service):

    #Ricevo la richiesta
    data = conn.recv(1024)

    #Controllo il tipo della richieta e invoco il metodo opportuno
    #NOTA: l'operatore "in" è usato, dato che Java aggiunge caratteri extra quando invia Stringhe tramite socket
    if "preleva" in str(data.decode()):

        result = service.preleva()

    else:

        id = str(data.decode()).split('-')[1]
        result = service.deposita(id)

    #Invio la risposta
    #NOTA: Bisogna aggiungere "\n" alla fine della stringa in modo da permettere all'app Java di ricevere il dato
    string_to_send = (str(result)+"\n")
    conn.send(string_to_send.encode())

    #Chiudo la connessione
    conn.close()

#Skeleton
class ServiceSkeleton(Service):

    def __init__(self, port, queue):
        self.port = port
        self.queue = queue

    def deposita(self, id):
        pass

    def preleva(self):
        pass

    def run_skeleton(self):

        host = 'localhost'

        #Creo e bindo lo socket
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.bind((host, self.port))

        s.listen(5)
        print("Socket is listening")

        while True:

            #Stabilisco una connessione con il client
            conn, addr = s.accept()

            #Starto un nuovo processo per servire la richiesta
            p = mp.Process(target=proc_fun, args=(conn, self))
            p.start()

        s.close()

#Implementazione reale del Service
class ServiceImpl(ServiceSkeleton):

    def deposita(self, data):
        
        self.queue.put(data)
        print("[SERVER-IMPL] Depositato", data)

        return "Depositato"
    
    def preleva(self):
        
        result = self.queue.get()
        print("[SERVER-IMPL] Prelevato", result)

        return result
    
if __name__ == "__main__":

    try:
        PORT = sys.argv[1]
    except IndexError:
        print("Perfavore, specifica come argomento il PORT")

    print("Server running...")

    #Creo la Queue
    q = mp.Queue(5)

    #Creo il service e runn lo Skeleton
    serviceImpl = ServiceImpl(int(PORT), q)
    serviceImpl.run_skeleton()