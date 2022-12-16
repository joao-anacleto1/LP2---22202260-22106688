package pt.ulusofona.lp2.deisiJungle;

class Elefante extends Especie {

    public Elefante() {
        this.nome = "Elefante";
        this.identificador = 'E';
        this.imagem = "elephant.png";
        this.energiaInicial = 180;
        this.consumoEnergia = 4;
        this.ganhoEnergiaEmDescanso = 10;
        this.velocidadeMinima = 1;
        this.velocidadeMaxima = 6;
    }

    @Override
    public boolean eHerbivoro() {
        return true;
    }

    @Override
    public boolean eCarnivoro() {
        return false;
    }

    @Override
    public boolean eOmnivoro() {
        return false;
    }

}
