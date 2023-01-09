package pt.ulusofona.lp2.deisiJungle

enum class CommandType{
    GET,POST
}


fun tipoGet(game:GameManager, lista:List<String>) : String? {

    when (lista[0]) {
        "PLAYER_INFO" -> return :: get_Player_Info.invoke(game, lista[1])

        "PLAYERS_BY_SPECIE" -> return ::get_Players_By_Species.invoke(game, lista[1])

        "MOST_TRAVELED" -> return :: get_most_traveled.invoke(game,"")

        "TOP_ENERGETIC_OMNIVORES" -> return :: get_Top_Energetic_Omnivoros.invoke(game,lista[1])

        "CONSUMED_FOODS" -> return :: get_consumed_foods.invoke(game)
        else -> return null
    }
    return null
}

fun tipoPost(game: GameManager, lista: List<String>): String? {

    when (lista[0]) {
        "MOVE" -> return :: post_move.invoke(game, lista[1])

        else -> return null
    }
    return null
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

}


fun get_Players_By_Species(game: GameManager, param: String): String{

    var result: String = ""

    result += game.jogadores
            .filter{ it.buscarEspecie().buscarIdentificador().equals(param[0]) }
            .sortedWith(Comparator<Jogador> { j1, j2 -> j2.buscarNome()[0].lowercaseChar() - j1.buscarNome()[0].lowercaseChar()})
            .joinToString (","){ it.buscarNome() }

    return result

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
        .map{"${it.buscarNome()}:${it.buscarEspecie().buscarIdentificador()}:${it.buscarPosicaoPercorrida()}"}
        .joinToString ("\n") + "\n" + "Total" + ":" + distanciaPercorrida


    return lista
}
fun get_Top_Energetic_Omnivoros(game: GameManager , param : String) : String{

    val paramNumero = param.toInt()

    val lista = game.jogadores
                .filter {it.buscarEspecie().eOmnivoro()}
                .sortedWith({i1 , i2 -> i2.buscarEnergia() - i1.buscarEnergia() })
                .map{"${it.buscarNome()}:${it.buscarEnergia()}"}
                .take(paramNumero)
                .joinToString ("\n" )



    return lista
}

fun get_consumed_foods(game: GameManager) : String{

    var result: String = ""

    result += game.alimentosConsumidos.distinctBy { it.buscarNomeAlimento() }
            .sortedWith(Comparator<Alimento>{a1, a2 -> a1.buscarIdentificadorAlimento() - a2.buscarIdentificadorAlimento()})
            .joinToString ("\n"){ it.buscarNomeAlimento() }

    return result

}

fun post_move(game: GameManager, param: String) : String{

    var result: String = game.moveCurrentPlayer(param.toInt(), true).let { when (it.code){

        MovementResultCode.INVALID_MOVEMENT -> "Movimento invalido"
        MovementResultCode.CAUGHT_FOOD -> "Apanhou comida"
        MovementResultCode.NO_ENERGY -> "Sem energia"
        MovementResultCode.VALID_MOVEMENT -> "OK"

        else -> ""
    } }

    return result

}



//FUNCOES PARA DEPOIS COLOCAR NO TIPOPOST