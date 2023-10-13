import stomp
import ILogging
import socket
import time
from multiprocessing import Process, Queue

def Prod(self, val, queue:Queue):

    print("[Produttore-Server] Inserisco il dato nella coda")

    queue.put(val)

    print("[PRODUTTORE-SERVER] Inserimento avvenuto!")

def Cons(queue:Queue):

    conn = stomp.Connection([("127.0.0.1", 61613)])

    conn.connect()

    while True:

        time.sleep(10) #ogni 10 sec invio il messaggio sulle QUEUE Stomp

        mess = queue.get()

        print("Invio il messaggio:", mess)

        if mess.split("-")[1]=="2":
            conn.send("/queue/error", mess)
        else:
            conn.send("/queue/info", mess)



class LoggingSkeleton(ILogging.ILogging):

    def __init__(self, queue:Queue) -> None:
        super().__init__()
        
        self.queue = queue

        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        self.sock.bind(("127.0.0.1",3000))

        print("[LoggingServer] In attesa di richieste...")
        self.sock.listen(1)

    def runSkeleton(self):

        conn, addr = self.sock.accept()

        print("[LoggingServer] Connessione accettat")

        while True:
            
            mess = conn.recv(1024)
            print("Messaggio arrivato: "+mess.decode())

            messaggioLog = (mess.decode()).split("-")[0]
            tipo = (mess.decode()).split("-")[1]

            print(messaggioLog + tipo)

            self.log(messaggioLog, tipo)



class LoggingImpl(LoggingSkeleton):

    def log(self, message: str, tipo: int):

        val = message + "-" + str(tipo)

        print("Avvio processo con messaggio: "+val)

        prod = Process(target=Prod, args=(self, val, self.queue))

        prod.start()


if __name__ == "__main__":

    print("Server pronto!")

    q = Queue(3)

    skeleton = LoggingImpl(queue=q)

    cons = Process(target=Cons, args=(q,))

    cons.start()

    skeleton.runSkeleton()

