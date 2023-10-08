from interface import Subject
import socket, sys
import threading

def thd_fun(conn, self):

    #data received from client
    data = conn.recv(1024)

    result = self.request(data)

    #send back reversed string to client

    conn.send(result)

    #chiusura connessione
    conn.close()

class Skeleton(Subject):

    def __init__(self, port):
        self.port = port

    def request(self, data):
        pass

    def run_skeleton(self):

        host = 'localhost'

        #create and bind a TCP socket
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.bind((host, self.port))
        self.port = s.getsockname()[1] #get used port

        print("Socket binded to port", self.port)

        s.listen(5)
        print("Socket is listening")

        while True:

            #stabilisco una connessione con il cliente
            conn, addr = s.accept()
            print('Connected to :', addr[0], ':', addr[1])

            #start a new thread
            t = threading.Thread(target=thd_fun, args=(conn, self))
            print("Starting new thread to handle request...")
            t.start()

        s.close()

class RealSubject(Skeleton):

    def request(self, data):
        #fa il reverse della stringa data dal client
        data = data[::-1]

        return data
    
if __name__ == "__main__":

    try:
        PORT = sys.argv[1]
    except IndexError:
        print("Perfavore, specifica il PORT arg")

    print("Server running")
    realSubject = RealSubject(int(PORT))
    realSubject.run_skeleton() #ereditarietà