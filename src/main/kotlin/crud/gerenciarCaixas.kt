package org.example.crud

import crud.EntidadeJDBC
import entidades.CaixaDAgua
import enumeradores.Material
import java.sql.ResultSet

val conectar = EntidadeJDBC(
    url = "jdbc:postgresql://localhost:5432/postgres",
    usuario = "postgres",
    senha = "postgres",
)

fun criarTabelaCaixa(){

    /**
    val material: Material,
    val capacidade: Int,
    val cor: String,
    val peso: Double,
    val preco: Double,
    val altura: Double,
    val profundidade: Double,
    val largura: Double,
    val marca: String,
     */
    val sql = "CREATE TABLE IF NOT EXISTS CaixaDAgua" +
            " (id serial NOT NULL PRIMARY KEY," +
            " material varchar(255)," +
            " capacidade integer," +
            " cor varchar(255)," +
            " peso float," +
            " preco varchar(255)," +
            " altura float," +
            " profundidade float," +
            " largura float," +
            " marca varchar(255)" +
            ")"

    val banco = conectar.conectarComBanco()
    val enviarParaBanco = banco!!.createStatement().execute(sql)
    println(enviarParaBanco)

    banco.close()
}

fun cadastrarCaixa(id : Int){
    /*
    *val material: Material,
    val capidade: Int,
    val cor: String,
    val peso: Double,
    val preco: Double,
    val altura: Double,
    val profundidade: Double,
    val largura: Double,
    val marca: String,
     */
    println("Digite o material do qual a caixa é composta")
    println("2 - Plástico")
    println("1 - PVC")
    println("3 - Metal")
    println("4 - Argamassa")

    val opcao = readln().toInt()
    var material : Material
    when (opcao) {
        1 -> material = Material.PVC
        2 -> material = Material.PLÁSTICO
        3 -> material = Material.METAL
        4 -> material = Material.ARGAMASSA
        else -> material = Material.PLÁSTICO
    }

    println("Digite a capacidade da caixa")
    val capacidade = readln().toInt()

    println("Digite a cor da caixa")
    val cor = readln().toString()

    println("Digite o peso da caixa")
    val peso  = readln().toDouble()

    println("Digite o preço da caixa")
    val preco = readln().toDouble()

    println("Digite a altura da caixa")
    val altura = readln().toDouble()

    println("Digite a profundidade da caixa")
    val profundidade = readln().toDouble()

    println("Digite a largura da caixa")
    val largura = readln().toDouble()

    println("Digite a marca da caixa")
    val marca = readln().toString()

//    Salvar váriaveis agora dentro da classe

    val c = CaixaDAgua(
        material = material,
        capacidade = capacidade,
        cor = cor,
        peso = peso,
        preco = preco,
        altura = altura,
        profundidade = profundidade,
        largura = largura,
        marca = marca
    )
    val banco = conectar.conectarComBanco()!!
    if(id == 0){
        val salvar = banco.prepareStatement(
            "INSERT INTO CaixaDAgua" +
                    " (material, capacidade, cor, peso, preco, altura, profundidade, largura, marca)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        )
        salvar.setString(1, c.material.name)
        salvar.setInt(2, c.capacidade!!)
        salvar.setString(3, c.cor)
        salvar.setDouble(4, c.peso)
        salvar.setDouble(5, c.preco)
        salvar.setDouble(6, c.altura)
        salvar.setDouble(7, c.profundidade)
        salvar.setDouble(8, c.largura)
        salvar.setString(9, c.marca)
        salvar.executeUpdate()//Isso fará COMMIT no banco

    }else{
        val sql = "UPDATE FROM CaixaDAgua SET " +
                " material = ?," +
                " capacidade = ?," +
                " cor = ?," +
                " peso = ?," +
                " preco = ?," +
                " altura = ?," +
                " profundidade = ?," +
                " largura = ?," +
                " marca = ?," +
                " WHERE id = ?"
        val editar = banco.prepareStatement(sql)
        editar.setInt(8, id)
        editar.setString(1, c.material.name)
        editar.setInt(2, c.capacidade!!)
        editar.setString(3, c.cor)
        editar.setDouble(4, c.peso)
        editar.setDouble(5, c.preco)
        editar.setDouble(6, c.altura)
        editar.setDouble(7, c.profundidade)
        editar.setDouble(8, c.largura)
        editar.setString(9, c.marca)

        editar.close()
    }
    banco.close()//Fecha Transação e a conexão com o banco
}

fun editarCaixa(){
    println("Digite o ID que deseja editar")
    var id = readln().toInt()

    val banco = conectar.conectarComBanco()
    val sqlBusca = "SELECT * FROM CaixaDAgua WHERE id = ?"
    val resultados = banco!!.prepareStatement(sqlBusca)
    resultados.setInt(1, id)
    val retorno = resultados.executeQuery()

    while (retorno.next()){
        println("----------------------------------------------------")
        println("Id:${retorno.getString("id")}")
        id = retorno.getString("id").toInt()

        println("Material: ${retorno.getString("material")}")
        println("Capacidade: ${retorno.getString("capacidade")}")
        println("Cor: ${retorno.getString("cor")}")
        println("Peso: ${retorno.getString("peso")}")
        println("Preço: ${retorno.getString("preco")}")
        println("Altura: ${retorno.getString("altura")}")
        println("Profundidade: ${retorno.getString("profundidade")}")
        println("Largura: ${retorno.getString("largura")}")
        println("Marca: ${retorno.getString("marca")}")
    }
    println("Faça suas alterações:")
    cadastrarCaixa(id)
    banco.close()


}

fun listarCaixas(){
    val banco = conectar.conectarComBanco()
    val sql = "SELECT * FROM CaixaDAgua"
    val resultados : ResultSet= banco!!.createStatement().executeQuery(sql)

    while (resultados.next()){
        println("----------------------------------------------------")
        println("Id:${resultados.getString("id")}")
        println("Material: ${resultados.getString("material")}")
        println("Capacidade: ${resultados.getString("capacidade")}")
        println("Cor: ${resultados.getString("cor")}")
        println("Peso: ${resultados.getString("peso")}")
        println("Preço: ${resultados.getString("preco")}")
        println("Altura: ${resultados.getString("altura")}")
        println("Profundidade: ${resultados.getString("profundidade")}")
        println("Largura: ${resultados.getString("largura")}")
        println("Marca: ${resultados.getString("marca")}")
    }
    resultados.close()
    banco.close()
}

fun excluirCaixa(){
    println("Digite o ID que deseja excluir")
    val id = readln().toInt()

    val banco = conectar.conectarComBanco()
    val sqlBusca = "SELECT * FROM CaixaDAgua WHERE id = ?"
    val resultados = banco!!.prepareStatement(sqlBusca)
    resultados.setInt(1, id)
    val retorno = resultados.executeQuery()

    while (retorno.next()){
        println("----------------------------------------------------")
        println("Id:${retorno.getString("id")}")
        println("Material: ${retorno.getString("material")}")
        println("Capacidade: ${retorno.getString("capacidade")}")
        println("Cor: ${retorno.getString("cor")}")
        println("Peso: ${retorno.getString("peso")}")
        println("Preço: ${retorno.getString("preco")}")
        println("Altura: ${retorno.getString("altura")}")
        println("Profundidade: ${retorno.getString("profundidade")}")
        println("Largura: ${retorno.getString("largura")}")
        println("Marca: ${retorno.getString("marca")}")
    }

   println("Tem certeza que deseja excluir esse registro")
    val resposta = readln().lowercase()
    when (resposta) {
        "sim" ->{
            val deletar = banco.prepareStatement("DELETE FROM CaixaDAgua WHERE id = ?")
                deletar.setInt(1, id)
                deletar.executeUpdate()
                deletar.close()

        }
        else -> {
            println("Não entendi sua resposta, operação cancelada!")
        }
    }
    banco.close()
}