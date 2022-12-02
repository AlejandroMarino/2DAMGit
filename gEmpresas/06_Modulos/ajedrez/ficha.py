def __init__(self, x):
    self.name = x
    self.caracter = x[:2]
    self.pos = x[3:]
    if x[0] == 't':
        self.tipo = "Torre"
        if x[1] == 'n':
            self.color = "Negra"
        else:
            self.color = "Blanca"
    elif x[0] == 'c':
        self.tipo = "Caballo"
        if x[1] == 'n':
            self.color = "Negro"
        else:
            self.color = "Blanco"
    elif x[0] == 'a':
        self.tipo = "Alfil"
        if x[1] == 'n':
            self.color = "Negro"
        else:
            self.color = "Blanco"
    elif x[0] == 'q':
        self.tipo = "Reina"
        if x[1] == 'n':
            self.color = "Negra"
        else:
            self.color = "Blanca"
    elif x[0] == 'r':
        self.tipo = "Rey"
        if x[0] == 'n':
            self.color = "Negro"
        else:
            self.color = "Blanco"
    elif x[0] == 'p':
        self.tipo = "Peon"
        if x[1] == 'n':
            self.color = "Negro"
        else:
            self.color = "Blanco"

def image_location(self):
    return "images/" + self.caracter + ".png"