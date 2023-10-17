import stomp, random, time

#Listener
class MyListener(stomp.ConnectionListener):

    def on_message(self, frame):
        
        #print della risposta ottenuta
        print('[CLIENT] Risposta ricevuta: "%s"' % frame.body)


if __name__ == "__main__":

    #Creo una connection e setto auto_content_lenght a false per interagire con una applicazione JMS
    conn = stomp.Connection([('127.0.0.1', 61613)], auto_content_length=False)

    #Setto il listener
    conn.set_listener('', MyListener())

    #Connessione e sottoscrizione alla queue 'response'
    conn.connect(wait=True)
    conn.subscribe(destination='/queue/response', id=1, acl='auto')

    #Fai la richiesta
    for i in range(10):

        if( (i%2) == 0):

            request = "deposita"
            id = random.randint(1,100)
            MSG = request + "-" +str(id)

        else:

            MSG = "preleva"

        #Invio la richiesta sulla queue 'request'
        conn.send('/queue/request', MSG)
        
        print("[CLIENT] Request: ", MSG)

    while True:
        time.sleep(60)

    conn.disconnect()