import stomp, time
from multiprocessing import Process, Queue
from matplotlib import pyplot as plt

counter = 0
q = Queue()

def inserisci(q,value):
    q.put([value])

class MyListener(stomp.ConnectionListener):

    def __init__(self,conn):
        self.conn = conn
    
    def on_message(self, frame):
        global counter
        print('[TempAnalyzer] Ho ricevuto una richiesta')
        counter = counter+1
        global q
        value = frame.body
        intValue = int(value)

        p = Process(target=inserisci, args=(q,intValue))

        p.start()

        p.join()

    
if __name__ == "__main__":
    
    conn = stomp.Connection([('127.0.0.1', 61613)])

    conn.set_listener('', MyListener(conn))

    conn.connect(wait=True)
    conn.subscribe(destination='/topic/mytemp', id=1, ack='auto')

    print('[TempAnalyzer] Aspettando i messaggi')
    
    time.sleep(120)

    # Creazione del grafico dopo aver ricevuto i messaggi
    if not q.empty():
        data = []
        for _ in range(q.qsize()):  # Estrae tutti gli elementi dalla coda utilizzando un ciclo for con range
            data.append(q.get()[0]) #prende il primo elemento e lo aggiunge alla coda

        plt.figure()
        plt.plot(range(len(data)), data)#asse x -> numero di elementi in data e asse y-> valori degli elementi
        plt.title('Valori di Temperatura')
        plt.xlabel('Numero Occorrenze')
        plt.ylabel('Valori Ricevuti')
        plt.show()
