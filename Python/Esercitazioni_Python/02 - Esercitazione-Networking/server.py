from interface import Service
import socket, sys
import multiprocessing as mp

#Funzione del process
def proc_fun(conn, service):

    #Ricevo la richiesta
    data = conn.recv(1024)

    #Controllo il tipo della richiesta e invoco il metodo del servizio appropriato
    if str(data.decode()) == "preleva":

        result = service.preleva()

    else:

        id = str(data.decode()).split('-')[1]
        result = service.deposita(id)

    #Invio la risposta
    conn.send(str(result).encode())

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

        #Creo e bindo la socket
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.bind((host, self.port))

        s.listen(5)
        print("Socket in ascolto")

        while True:

            #Stabilisco la connessione con il client
            conn, addr = s.accept()

            #Creo un nuovo processo per servire la richiesta
            p = mp.Process(target=proc_fun, args=(conn, self))
            p.start()

        s.close()

#Implementazione reale del Service
class ServiceImpl(ServiceSkeleton):

    def deposita(self, data):

        self.queue.put(data)
        print("[SERVER-IMPL] Depositato", data)

        return "deposited"
    
    def preleva(self):

        result = self.queue.get()
        print("[SERVER-IMPL] Prelevato", result)

        return result
    
if __name__ == "__main__":

    try:
        PORT = sys.argv[1]
    except IndexError:
        print("Specifica come argomento il PORT")
    
    print("Server running...")

    #Creo la coda
    q = mp.Queue(5)

    #Creo il Servizio e runno lo Skeleton
    serviceImpl = ServiceImpl(int(PORT), q)
    serviceImpl.run_skeleton()