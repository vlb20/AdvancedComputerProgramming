import log, socket, time

#implemento il metodo di logging sotto l'aspetto comunicativo

class Proxy(log.Logging):

    def __init__(self):
        #definisco la parte di comunicazione da effettuare...

        ##definisco la socket da utilizzare
        self.sock = socket.socket( socket.AF_INET, socket.SOCK_STREAM)

        self.sock.connect(("127.0.0.1", 3000))
        #affress la tupla di localhost e il porto!

        print("Socket creata!")

    def log(self, message: str, type: int):

        #invio messaggio sulla socket!

        print("Messaggio da inviare: " + message + str(type))

        #concateno già qui la stringa per comodità
        val = message+"-"+str(type)

        print("Messaggio che invio: "+val)

        time.sleep(1)

        self.sock.send(val.encode("utf-8"))

        print("Messaggio inviato!")