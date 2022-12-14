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
    public boolean isHerbivoro() {
        return false;
    }

    @Override
    public boolean isCarnivoro() {
        return false;
    }

    @Override
    public boolean isOmnivoro() {
        return true;
    }
}