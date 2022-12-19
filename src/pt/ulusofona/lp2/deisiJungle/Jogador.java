package pt.ulusofona.lp2.deisiJungle;

import jdk.jshell.spi.ExecutionControl;
import kotlin.NotImplementedError;

public class Jogador {

    int id;
    private int energia;
    String nome;
    Especie especie;
    int posicaoAtual;

    int qtdDeBananasIngeridas;

    static int qtdMaxDeBananasIngeridas = 1;

    public Jogador(int id, String nome, Especie especie,int posicaoAtual) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.energia = especie.buscarEnergiaInicial();
        this.posicaoAtual = posicaoAtual;
        this.qtdDeBananasIngeridas = 0;
    }

    boolean validarNome() {
        if (nome == null) {
            return false;
        }
        return nome.length() > 0;
    }

    int buscarId() {
        return id;
    }

    int buscarPosicaoAtual() {
        return posicaoAtual;
    }

    int buscarEnergia() {
        return energia;
    }

    boolean mover(int nrSquares){

        if(this.energia < this.especie.consumoEnergia){
            return false;
        }
        this.energia -= especie.consumoEnergia * nrSquares;
        this.posicaoAtual += nrSquares;

        return true;

    }

    void ficar(){
        this.energia += this.especie.ganhoEnergiaEmDescanso;

        if (this.energia > 200){
            this.energia = 200;
        }
    }

    void removeEnergia(int valor) {
        this.energia -= valor;
    }

    void atualizarPosicao(int casaPretendida) {
        this.posicaoAtual = casaPretendida;
    }

    String buscarNomeJogador(){
        return nome;
    }

    String buscarNomeEspecie(){
        return especie.buscarNome();
    }

    void consumir(Alimento a) {

        switch (a.buscarIdentificadorAlimento()){
            case 'b':
                ingereCachoDeBananas((CachoDeBananas) a);
                break;
            case 'c':
                ingereCarne(1);
                break;
            case 'm':
                ingereCogumelosMagicos();
                break;

            case 'e':
                ingereErva();
                break;

            case 'a':
                ingereAgua();
                break;

            default:
        }
    }

    void ingereErva(){

        if(especie.eHerbivoro() || especie.eOmnivoro()){
            this.energia += 20;
        }else {
            this.energia -= 20;
        }
    }

    void ingereAgua(){
        if(especie.eCarnivoro() || especie.eHerbivoro()){
            this.energia += 15;
        }else{
            this.energia += ((this.energia * 0.20)) + this.energia;
        }
    }

    void ingereCarne(int turno){

        Carne carne = new Carne();

        if (!carne.eToxica(turno)){
            if (especie.eCarnivoro() || especie.eOmnivoro()){
                this.energia += 50;
            }
            this.energia -= (this.energia/2); // carne toxica
        }
    }

    void ingereCogumelosMagicos(){

    }

    void ingereCachoDeBananas(CachoDeBananas cachoDeBananas){

        if(cachoDeBananas.getBanana()){
            qtdDeBananasIngeridas++;
            int energiaASomar;
            if(qtdDeBananasIngeridas <= Jogador.qtdMaxDeBananasIngeridas)
                energiaASomar = 40;
            else
                energiaASomar = -40;

            this.energia += energiaASomar;
        }
    }
}


