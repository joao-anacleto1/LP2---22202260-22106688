package pt.ulusofona.lp2.deisiJungle

enum class CommandType{
    GET,POST
}


fun tipoGet(game:GameManager, lista:List<String>) : String {

    when (lista[0]) {
        "PLAYER_INFO" -> getPlayerInfo(game, lista[1])

        "PLAYERS_BY_SPECIE" -> print("x == 2")

        else -> { // Note the block
            print("x is neither 1 nor 2")
        }
    }
    return ""
}

fun tipoPost(game: GameManager, lista: List<String>): String {

    return ""
}

fun tipoComando(comando: CommandType): Function2<GameManager, List<String>, String> {

    if (comando == CommandType.GET) {
        return ::tipoGet
    } else {
        return ::tipoPost
    }

}


fun router(): Function1<CommandType, Function2<GameManager, List<String>, String>> {

    return ::tipoComando
}


//FUNCOES PARA DEPOIS COLOCAR NO TIPOGET

fun getPlayerInfo(game: GameManager, param: String): String {

    return game.buscarNomeJogadorIgualAoParametro(param)

}






//FUNCOES PARA DEPOIS COLOCAR NO TIPOPOST