import proxy, random

if __name__ == "__main__":

    Proxy = proxy.Proxy()

    for _ in range(0, 10):

        tipo = random.randint(0, 3)

        if(tipo == 0 or tipo == 1):

            rand = random.randint(0, 50)
            
            if(rand%2 == 0):

                messaggioLog = "success"
                Proxy.log(messaggio=messaggioLog, tipo=tipo)
            
            else:

                messaggioLog = "checking"
                Proxy.log(messaggio=messaggioLog, tipo=tipo)
        
        elif(tipo == 2):

            rand = random.randint(0, 50)

            if(rand%2 == 0):

                messaggioLog = "fatal"
                Proxy.log(messaggio=messaggioLog, tipo=tipo)

            else:
                
                messaggioLog = "exception"
                Proxy.log(messaggio=messaggioLog, tipo=tipo)
                

        