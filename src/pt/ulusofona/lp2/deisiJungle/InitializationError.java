package pt.ulusofona.lp2.deisiJungle;

class InitializationError {

    String mensagem;

    public InitializationError(String mensagem) {
        this.mensagem = mensagem;
    }

    String buscaMensagemErro(){
        return this.mensagem;
    }
}
