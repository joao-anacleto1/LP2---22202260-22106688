package pt.ulusofona.lp2.deisiJungle;

class Leao extends Especie {


    public Leao() {
        this.nome = "Leão";
        this.identificador = 'L';
        this.imagem = "lion.png";
        this.energiaInicial = 80;
        this.consumoEnergia = 2;
        this.ganhoEnergiaEmDescanso = 10;
        this.velocidadeMinima = 4;
        this.velocidadeMaxima = 6;
    }

    @Override
    public boolean eHerbivoro() {
        return false;
    }

    @Override
    public boolean eCarnivoro() {
        return true;
    }

    @Override
    public boolean eOmnivoro() {
        return false;
    }
}
