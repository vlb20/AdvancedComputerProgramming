import stomp, time

class MyListener(stomp.ConnectionListener):

    def on_message(self, frame):
        
        tipo = (frame.body).split("-")[1]

        print("[InfoFilter] Ricevuto il messaggio con tipo:", tipo)

        if tipo == "1":
            message = (frame.body).split("-")[0]

            with open("info.txt", mode='a') as file:
                file.write("\n"+message)

            file.close()

if __name__ == "__main__":

    conn = stomp.Connection([("127.0.0.1", 61613)])

    listener = MyListener()
    conn.set_listener("", listener)

    conn.connect()

    conn.subscribe("/queue/info", id=1, ack="auto")

    while True:
        time.sleep(10)


