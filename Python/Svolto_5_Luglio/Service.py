import random
import proxy

if __name__=="__main__":

    print("Creo il proxy!")

    prox = proxy.Proxy()

    for i in range(0, 10):
        #creo casualmente i valori del messaggio

        type = random.randint(0,3)

        if type == 0:
            print("Invio messaggio di DEBUG!")

            if random.random() < 0.5:
                prox.log("success", 0)
            else:
                prox.log("checking", 0)
            
        elif type == 1:
            print("Invio messaggio di INFO!")

            if random.random() < 0.5:
                prox.log("checking", 1)
            else:
                prox.log("success", 1)
        
        else:
            print("Invio messaggio di ERROR!")

            if random.random() < 0.5:
                prox.log("fatal",2)
            else:
                prox.log("exception", 2)