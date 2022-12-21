package pt.ulusofona.lp2.deisiJungle;



import java.util.Random;

public class Jogador {

    int id;
    private int energia;
    String nome;
    Especie especie;
    int posicaoAtual;

    int qtdDeBananasIngeridas;

    static int qtdMaxDeBananasIngeridas = 1; // se ingerir mais do que uma banana o jogador perde energia

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

        if(this.energia > 200) {
            return this.energia = 200;
        }
        return this.energia;
    }

    boolean mover(int nrSquares){

        if(this.energia < this.especie.consumoEnergia){
            return false;
        }
        this.energia -= especie.consumoEnergia * Math.abs(nrSquares);
        this.posicaoAtual += nrSquares;

        return true;

    }

    void ficar(){
        this.energia += this.especie.buscarGanhoEnergiaEmDescanso();
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
        return this.nome;
    }

    String buscarNomeEspecie(){
        return especie.buscarNome();
    }

    void consumirAlimento(Alimento a, int turno) {

        switch (a.buscarIdentificadorAlimento()) {
            case 'c':
                if (!especie.eHerbivoro()){
                    ingereCarne(turno);
                }
                break;
            case 'b':
                ingereCachoDeBananas((CachoDeBananas) a);
                break;

            case 'm':
                ingereCogumelosMagicos(turno, (CogumelosMagicos) a);
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
            if (this.energia > 200){
                this.energia = 200;
            }
            this.energia += 20;
        } else {
            this.energia -= 20;
        }

    }

    void ingereAgua(){

        if(especie.eCarnivoro() || especie.eHerbivoro()){
            if (this.energia > 200){
                this.energia = 200;
            }
            this.energia += 15;
        } else {
            this.energia += (this.energia * 0.20) + this.energia;
        }

    }

    void ingereCarne(int turno){

        Carne carne = new Carne();

        if (!carne.eToxica(turno)){
            if (especie.eCarnivoro() || especie.eOmnivoro()){
                if (this.energia > 200){
                    this.energia = 200;
                }
                this.energia += 50;
            }
        } else {
            this.energia -= (this.energia/2); // carne toxica
        }
    }

    void ingereCogumelosMagicos(int turno, CogumelosMagicos cogumelosMagicos){

        int valor = (this.energia/100) * cogumelosMagicos.buscarNrCogumelo();

        if (turno % 2 == 0){
            this.energia += valor;
            if (this.energia > 200){
                this.energia = 200;
            }
        } else {
            this.energia -= valor;
            if(this.energia < 0){
                this.energia = 0;
            }
        }
    }

    void ingereCachoDeBananas(CachoDeBananas cachoDeBananas){

        if(cachoDeBananas.buscarBananasNoCacho()){
            qtdDeBananasIngeridas++;
            int energiaASomar = 0;

            if (this.energia > 200) {
                this.energia = 200;

            }else if(qtdDeBananasIngeridas <= Jogador.qtdMaxDeBananasIngeridas){
                energiaASomar = 40;
            } else {
                energiaASomar = -40;
            }
            this.energia += energiaASomar;
        }
    }
}


