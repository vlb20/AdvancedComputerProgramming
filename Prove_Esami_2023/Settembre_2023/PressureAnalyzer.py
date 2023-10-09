import stomp, csv, time

counter = 0

class MyListener(stomp.ConnectionListener):

    def __init__(self,conn):
        self.conn = conn
    
    def on_message(self, frame):
        global counter
        print('[PressAnalyzer] Ho ricevuto una richiesta')
        counter = counter+1
        value = frame.body
        intValue = int(value)

        with open('press.csv', mode='a', newline='') as pressures:
            writer = csv.writer(pressures)
            writer.writerow([intValue, counter])


    
if __name__ == "__main__":
    
    conn = stomp.Connection([('127.0.0.1', 61613)])

    conn.set_listener('listener', MyListener(conn))

    conn.connect(wait=True)
    conn.subscribe(destination='/topic/mypress', id=1, ack='auto')

    print('[PressAnalyzer] Aspettando i messaggi')

    while True:
        time.sleep(20)

