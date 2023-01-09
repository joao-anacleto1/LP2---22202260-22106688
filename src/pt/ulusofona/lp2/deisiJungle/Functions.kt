package pt.ulusofona.lp2.deisiJungle

enum class CommandType{
    GET,POST
}


fun tipoGet(game:GameManager, lista:List<String>) : String? {

    when (lista[0]) {
        "PLAYER_INFO" -> return :: get_Player_Info.invoke(game, lista[1])

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

fun get_Player_Info(game: GameManager, param: String): String {

    val lista = game.jogadores
                .filter {it.buscarNome().equals(param)}


    if(lista.isEmpty()){
        return "Inexistent player"
    }

    return lista
           .map { "${it.buscarId()} | ${it.buscarNome()} | ${it.buscarEspecie().buscarNome()} | ${it.buscarEnergia()}" +
                   " | ${it.buscarPosicaoAtual()}"}[0]


/*
    val resultado: String? = game.jogadores.filter { it.buscarNome().equals(param[1]) }.joinToString {
        it.buscarId().toString()+ " | " + it.buscarNome() + " | " + it.buscarEspecie().buscarNome() + " | " +
                it.buscarEnergia().toString() + " | " + it.buscarPosicaoAtual().toString() }

    if(resultado == null){
        return "Inexistent player"
    } else {
        return resultado
    }

 */

}


fun getPlayersBySpecies(game: GameManager, param: String): String{

    return "" //game.buscarJogadoresDeUmaEspecie(param); parametro da funcao é char
}


fun get_most_traveled(game: GameManager, param : String) : String{

    val distanciaPercorrida = game.jogadores
                            .map({it.buscarPosicaoPercorrida()})
                            .sum()

    if(game.jogadores.isEmpty()){
        return ""
    }

    if (game.mapa.casas.isEmpty()){
        return ""
    }
    //caso nao entre nas verificações retorna o resultado esperado

    val lista =  game.jogadores
        .sortedWith({i1, i2->i2.buscarPosicaoPercorrida() - i1.buscarPosicaoPercorrida()})
        .map{"${it.buscarNome()} : ${it.buscarEspecie().buscarIdentificador()} : ${it.buscarPosicaoPercorrida()}"}
        .joinToString ("\n") + "\n" + "Total" + ":" + distanciaPercorrida


    return lista
}
fun get_Top_Energetic_Omnivoros(game: GameManager , param : String) : String{

    val paramNumero = param.toInt()

    val lista = game.jogadores
                .filter {it.buscarEspecie().eOmnivoro()}
                .sortedWith({i1 , i2 -> i2.buscarEnergia() - i1.buscarEnergia() })
                .map{"${it.buscarNome()} : ${it.buscarEnergia()}"}
                .take(paramNumero)
                .joinToString ("\n" )



    return lista
}



//FUNCOES PARA DEPOIS COLOCAR NO TIPOPOST