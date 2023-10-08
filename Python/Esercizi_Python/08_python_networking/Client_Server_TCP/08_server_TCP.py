import socket
import sys
import signal

""" e' possibile gestire il ctrl-c con il package signal, vedere doc
def signal_handler(sig, frame):
    print('You pressed Ctrl+C!')
    sys.exit(0)
"""
IP = 'localhost'
PORT = 0
BUFFER_SIZE = 1024

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
"""
    se faccio il bind specificando port=0 la chiamata mi restituisce 
    il primo porto TCP libero
"""
s.bind((IP, PORT))

## gestione del ctrl-c potrebbe non funzionare sempre con l'exception Keyboard
try:
    s.listen(1) #backlog pari a 1

    print("getsockname => ", s.getsockname()) # restituisce un tuple che rappresenta l’indirizzo del socket locale, che è una coppia (host, port)
    cur_port = s.getsockname()[1]

    print("server on: ", IP, "port: ", cur_port)

    conn, addr = s.accept()
    print('Connection address: {}' .format(addr))

except KeyboardInterrupt: ### gestione del ctrl-c
    print("Hai premuto Ctrl-C...close server and cleanup...")
    s.close()
    sys.exit()

toClient = "Forza Napoli...\n"

data = conn.recv(BUFFER_SIZE)
print("received data: "+data.decode("utf-8"))

conn.send(toClient.encode("utf-8"))

conn.close()
s.close