package pt.ulusofona.lp2.deisiJungle;

class Tartaruga extends Especie {


    public Tartaruga() {
        this.nome = "Tartaruga";
        this.identificador = 'T';
        this.imagem = "turtle.png";
        this.energiaInicial = 150;
        this.consumoEnergia = 1;
        this.ganhoEnergiaEmDescanso = 5;
        this.velocidadeMinima = 1;
        this.velocidadeMaxima = 3;
    }

    @Override
    public boolean eHerbivoro() {
        return false;
    }

    @Override
    public boolean eCarnivoro() {
        return false;
    }

    @Override
    public boolean eOmnivoro() {
        return true;
    }

    @Override
    public boolean eMitologico() {
        return false;
    }
}
