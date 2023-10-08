import socket, sys

if __name__ == '__main__':

    try:
        port = int(sys.argv[1])
    except IndexError:
        print("Please, specify PORT arg...")

    assert port != "", 'specify port'

    #Server IP
    host = '127.0.0.1'

    #creo la socket e mi connetto al server
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((host, port))

    #messaggio da inviare
    message = "Ciao Server, forza Napoli"

    #send message
    s.send(message.encode('ascii'))

    #messaggio ricevuto dal server
    data = s.recv(1024)
    print('Messaggio ricevuto dal server: ', str(data.decode('ascii')))

    s.close()