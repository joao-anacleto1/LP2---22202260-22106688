package pt.ulusofona.lp2.deisiJungle;

class Passaro extends Especie {


    public Passaro() {
        this.nome = "Passaro";
        this.identificador = 'P';
        this.imagem = "bird.png";
        this.energiaInicial = 70;
        this.consumoEnergia = 4;
        this.ganhoEnergiaEmDescanso = 50;
        this.velocidadeMinima = 5;
        this.velocidadeMaxima = 6;
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
