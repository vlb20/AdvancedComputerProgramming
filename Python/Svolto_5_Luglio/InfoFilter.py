import time
import stomp


class ListnerInfo(stomp.ConnectionListener):


    def on_message(self, frame):
        
        type = (frame.body).split("-")[1]

        print("Valore arrivato: " + type)

        if type == "1":
            message = (frame.body).split("-")[0]
            print("Scrivo su info.txt: "+ message)

            with open("info.txt", mode="a") as file:
                file.write("\n"+ message)


            file.close()

if __name__ == "__main__":

    #ricezione stomp

    conn = stomp.Connection([("127.0.0.1", 61613)])

    #listner
    listner = ListnerInfo()
    conn.set_listener("", listner)

    conn.connect()

    conn.subscribe("/queue/info", id=1, ack="auto")

    while True:
        time.sleep(10)
