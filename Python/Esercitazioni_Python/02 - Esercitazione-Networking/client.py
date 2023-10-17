import stomp, random, time

#Listener
class MyListener(stomp.ConnectionListener):

    def on_message(self, frame):
        
        #Stamo la risposta ottenuta
        print('[CLIENT] Ricevo la risposta: "%s"' % frame.body)

if __name__ == "__main__":

    #Crea la connection
    conn = stomp.Connection([('127.0.0.1', 61613)])

    #Setto il listener
    conn.set_listener('', MyListener())

    #Mi connetto e mi sottoscrivo alla coda 'response'
    conn.connect(wait=True)
    conn.subscribe(destination='/queue/response', id=1, ack='auto')

    #Faccio la richiesta
    for i in range(10):

        if( (i%2)==0):

            request = "deposita"
            id = random.randint(1,100)
            MSG = request + "-" + str(id)

        else:

            MSG = "preleva"

        #Invia la richiesta alla queue 'request'
        conn.send('/queue/request', MSG)

        print("[CLIENT] Request: ", MSG)

    while True:
        time.sleep(60)

    conn.disconnect()