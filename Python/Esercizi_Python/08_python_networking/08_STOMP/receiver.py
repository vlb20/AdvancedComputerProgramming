import time
import stomp

class MyListener(stomp.ConnectionListener):

    def __init__(self, conn):
        self.conn = conn
    
    def on_message(self, frame):
        print('Ho ricevuto un messaggio')
        print('text: "%s"' % frame.body)
        print('headers: "%s"' % frame.headers)
        print('cmd: "%s"' % frame.cmd)

if __name__ == "__main__":

    conn = stomp.Connection([('127.0.0.1', 61613)])

    conn.set_listener('', MyListener(conn))

    conn.connect(wait=True)
    #conn.subscribe(destination='/queue/test', id=1, ack='auto')
    conn.subscribe(destination='/topic/mytesttopic', id=1, ack='auto')
    # Nota: specifica il nome fisico dopo /topic o /queue
    # per inviare TextMessage ad una applicazione JMS

    print('Receiver aspettando i messaggi...')

    time.sleep(60)

    conn.disconnect()