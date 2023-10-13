#Proxy - comunicazione socket TCP
import ILogging
import socket
import time

class Proxy(ILogging.ILogging):

    def __init__(self):
        
        #Creo una socker
        self.sock = socket.socket( socket.AF_INET, socket.SOCK_STREAM )

        self.sock.connect(("127.0.0.1",3000))

        print("[Service-Proxy] Creazione socket avvenuta!")
    
    def log(self, messaggio, tipo):
        
        print("[Service-Proxy] Log da inviare con messaggio", messaggio, "e tipo", str(tipo))

        stringa = messaggio + "-" + str(tipo) #Ad esempio fatal-2

        time.sleep(1)

        self.sock.send(stringa.encode("utf-8"))

        print("[Service-Proxy] Log inviato!")