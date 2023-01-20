package pt.ulusofona.lp2.deisiJungle;

class Unicornio extends Especie {


    public Unicornio() {
        this.nome = "Unicórnio";
        this.identificador = 'U';
        this.imagem = "unicorn.png";
        this.energiaInicial = 200;
        this.consumoEnergia = 8;
        this.ganhoEnergiaEmDescanso = 20;
        this.velocidadeMinima = 3;
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
        return false;
    }

    @Override
    public boolean eMitologico() {
        return true;
    }


}