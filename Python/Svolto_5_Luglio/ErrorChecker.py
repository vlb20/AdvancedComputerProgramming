import time, stomp, sys

class ListenerChecker(stomp.ConnectionListener):

    def __init__(self, param) -> None:
        self.p = param

    def on_message(self, frame):
        
        message = (frame.body).split("-")[0]

        print("Valore arrivato: "+message)

        if message == PARAM:

            print("Scrivo su error.txt: "+message)

            with open("error.txt", mode="a") as file:
                file.write("\n"+message)
            
            file.close()

if __name__=="__main__":

    try:
        PARAM = sys.argv[1]
    except IndexError:
        print("Aggiungi fatal o error")
    
    #ricezione stomp
    conn = stomp.Connection(["127.0.0.1", 61613])

    #listener
    listener = ListenerChecker(PARAM)
    conn.set_listener("", listener)

    conn.connect()

    conn.subscribe("/queue/error", id=2, ack="auto")

    while True:
        time.sleep(10)