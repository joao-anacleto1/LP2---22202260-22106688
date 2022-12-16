package pt.ulusofona.lp2.deisiJungle;

class Tarzan extends Especie {

    public Tarzan() {

        this.nome = "Tarzan";
        this.identificador = 'Z';
        this.imagem = "tarzan.png";
        this.energiaInicial = 70;
        this.consumoEnergia = 2;
        this.ganhoEnergiaEmDescanso = 20;
        this.velocidadeMinima = 1;
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
}