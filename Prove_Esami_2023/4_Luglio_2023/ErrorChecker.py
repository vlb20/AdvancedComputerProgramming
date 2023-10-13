import stomp, time, sys

class MyListener(stomp.ConnectionListener):

    def __init__(self, stringa):
        self.stringa = stringa

    def on_message(self, frame):
        
        message = (frame.body).split("-")[0]

        print("Messaggio ricevuto: "+message)

        if message == self.stringa:

            print("Scrivo su error.txt")

            with open("error.txt", mode='a') as file:
                file.write("\n"+message)

            file.close()


if __name__ == "__main__":

    try:
        STRING =sys.argv[1]
    
    except IndexError:
        print("Scrivi in input 'fatal' o 'error'")

    conn = stomp.Connection({("127.0.0.1", 61613)})

    listener = MyListener(STRING)
    conn.set_listener("", listener)

    conn.connect()

    conn.subscribe("/queue/error", id=2, ack="auto")

    while True:
        time.sleep(10)