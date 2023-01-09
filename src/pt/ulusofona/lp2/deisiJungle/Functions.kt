package pt.ulusofona.lp2.deisiJungle

enum class CommandType{
    GET,POST
}


fun tipoGet(game:GameManager, lista:List<String>) : String? {

    when (lista[0]) {
        "PLAYER_INFO" -> return ::getPlayerInfo.invoke(game, lista)

        "PLAYERS_BY_SPECIE" -> print("x == 2")
        else -> return null
    }
    return null
}

fun tipoPost(game: GameManager, lista: List<String>): String {

    return ""
}

fun tipoComando(comando: CommandType): Function2<GameManager, List<String>, String?> {

    if (comando == CommandType.GET) {
        return ::tipoGet
    } else {
        return ::tipoPost
    }

}


fun router(): Function1<CommandType, Function2<GameManager, List<String>, String?>> {

    return ::tipoComando
}


//FUNCOES PARA DEPOIS COLOCAR NO TIPOGET

fun getPlayerInfo(game: GameManager, param: List<String>): String? {

    // game.buscarNomeJogadorIgualAoParametro(param);

        val resultado: String? = game.jogadores.filter { it.buscarNome().equals(param[1]) }.joinToString {
            it.buscarId().toString()+ " | " + it.buscarNome() + " | " + it.buscarEspecie().buscarNome() + " | " +
                    it.buscarEnergia().toString() + " | " + it.buscarPosicaoAtual().toString() }

    if(resultado == null){
        return "Inexistent player"
    } else {
        return resultado
    }


}



fun getPlayersBySpecies(game: GameManager, param: String): String{

    return "" //game.buscarJogadoresDeUmaEspecie(param); parametro da funcao é char
}


fun getTopEnergeticOmnivoros(game: GameManager , param : String) : String{

    return "";

}








//FUNCOES PARA DEPOIS COLOCAR NO TIPOPOST