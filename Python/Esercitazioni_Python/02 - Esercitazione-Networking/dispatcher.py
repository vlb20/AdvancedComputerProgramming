import socket, sys, stomp, time
from multiprocess import Process
from interface import Service

#Funzione del processo
def proc_fun(conn, proxy, mess):

    request = mess.split('-')[0]

    if request == "deposita":
        id=mess.split('-')[1]
        result = proxy.deposita(id)
    else:
        result = proxy.preleva()

    conn.send('/queue/response', result)

#Proxy
class ServiceProxy(Service):

    def __init__(self, port):
        self.port = port
        self.ip = 'localhost'
        self.buffer_size = 1024

    def preleva(self):

        #Creo la socket e mi connetto
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((self.ip, self.port))

        #Genero e invio la request
        message = "preleva"
        s.send(message.encode("utf-8"))

        #Get della risposta
        data = s.recv(self.buffer_size)

        s.close()

        return data
    
    def deposita(self, id):

        #Crea una socket e una ci connettiamo
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect((self.ip, self.port))

        #Genero e invio la request
        message = "deposita-" + str(id)
        s.send(message.encode("utf-8"))

        data = s.recv(self.buffer_size)

        s.close()

        return data
    
#Listener
class MyListener(stomp.ConnectionListener):

    def __init__(self, conn, port):
        self.port = port
        self.conn = conn

    def on_message(self, frame):
        
        print('[DISPATCHER] Richiesta ricevututa: "%s"' % frame.body)

        #Genero il Proxy
        proxy = ServiceProxy(int(self.port))

        #Avvio il processo per servire la richiesta
        p = Process(target=proc_fun, args=(self.conn, proxy, frame.body))
        p.start()

if __name__ == "__main__":

    try:
        PORT = sys.argv[1]
    except IndexError:
        print("Pleas, specifica l'arg PORT")

    #Creo la connection
    conn = stomp.Connection([('127.0.0.1', 61613)])

    #Setto il listener
    conn.set_listener('', MyListener(conn, PORT))

    #Mi connetto e sottoscrivo alla queue 'request'
    conn.connect(wait=True)
    conn.subscribe(destination='/queue/request', id=1, ack='auto')

    print("[DISPATCHER] In attesa di richieste...")

    #Lascia il listener attivo
    while True:
        time.sleep(60)