from multiprocessing import Process, Queue
import log
import socket
import time
import stomp

#definire i due process

def Cons(queue:Queue):

    conn = stomp.Connection([("127.0.0.1", 61613)])

    conn.connect()

    while True:
        time.sleep(10)

        #ogni 10 secondi invia il messaggio sulle code!

        mess = queue.get()

        print("INVIO:" +mess)

        if mess.split("-")[1]=="2":
            conn.send("/queue/error", mess)
        else:
            conn.send("/queue/info", mess)


def Prod(self, val, queue:Queue):

    print("Inserisco il valore nella coda di stringhe...")

    queue.put(val)

    print("valore inserito!")


class Skeleton(log.Logging):

    def __init__(self, queue:Queue) -> None:
        super().__init__()

        self.queue = queue

        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        print("Creo la socket...")

        self.sock.bind(("127.0.0.1", 3000))
        self.sock.listen(1) #quindi definisco il tutto per mettermi in attesa nel
        #runSkeleton!

    def log(self, message: str, type: int):
        pass

    def runSkeleton(self):

        conn, addr = self.sock.accept()

        print("Connessione accettata!")

        while True:
            mess = conn.recv(1024)

            print("Messaggio arrivato: " + mess.decode())

            messlog = (mess.decode()).split("-")[0]
            type = (mess.decode()).split("-")[1]

            print(messlog + type) #stampa di diagnostica

            self.log(messlog, type)

class LoggingImpl(Skeleton):

    def log(self, message: str, type: int):
        print("Avvio processo di gestione...")

        tipo= str(type)
        val = message + "-" + tipo

        print("Avvio processo 1 con valore: " +val)
        p1 = Process(target=Prod, args=(self, val, self.queue))

        p1.start()

if __name__ == "__main__":

    print("Server attivo...")

    queue = Queue(3)

    sk = LoggingImpl(queue=queue)

    p2 = Process(target=Cons, args=(queue,))

    p2.start()

    sk.runSkeleton()