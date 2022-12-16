package pt.ulusofona.lp2.deisiJungle;

class Carne extends Alimento{

    public Carne() {
        this.identificadorAlimento = 'c';
        this.nomeAlimento = "Carne";
        this.imagem = "meat.png";
    }
    boolean eToxica(int turno){
        return turno >= 12;
    }

}
