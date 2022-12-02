def pintar_tablero(self):
    tablero_img = Images.open("images/tablero.png").convert("RGBA")
    for ficha in self.posfichas:
        if ficha.visible:
            aux_img = Images.open(ficha.image_location()).convert("RGBA")
            tablero_img.alpha_composite(aux_img, (ficha.posx, ficha.posy))
        tablero_img.save("jugada.png")




def cambiar_posicion(self, ficha_fin):
    self = ficha_fin