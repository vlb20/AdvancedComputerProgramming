import socket
import threading

def thd_fun(conn):

    #dati ricevuti dal client
    data = conn.recv(1024)

    # reverse the given string from client
    data = data[::-1]

    # send back reversed string to client
    conn.send(data)

    # connection closed
    conn.close()

if __name__ == '__main__':

    host = ""
    cur_port = 0

    #creo e bind una socket TCP
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((host, cur_port))
    cur_port = s.getsockname()[1] #get used port

    print("Socket binded to port", cur_port)

    s.listen(5)
    print("Socket is listening")

    while True:

        #stabilisco la connession con il client
        conn, addr = s.accept()
        print('Connected to :', addr[0], ':', addr[1])

        #start a new thread
        t = threading.Thread(target=thd_fun, args=(conn,))
        t.start()

    s.close()