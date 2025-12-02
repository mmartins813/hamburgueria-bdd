# language: pt
@hamburgueria
Funcionalidade: Pedidos na hamburgueria Peppa Lanches
  Para realizar pedidos corretos
  Como cliente
  Eu quero saber se o item pode ser pedido, o valor total e o tempo estimado

  Contexto:
    Dado que o cardápio contém os itens:
      | item         | preco |
      | x-bacon      | 25.00 |
      | x-salada     | 22.00 |
      | batata frita | 12.00 |

  @feliz
  Cenario: Pedido simples de item existente
    Quando o cliente pede 2 unidades de "x-bacon"
    Entao o valor total deve ser 50,00

  @inexistente
  Cenario: Pedido de item inexistente
    Quando o cliente pede 1 unidade de "x-tudo"
    Entao o sistema deve lançar uma exceção com a mensagem "Item indisponível no cardápio"

  @quantidade
  Cenario: Pedido com quantidade inválida
    Quando o cliente pede 0 unidades de "x-salada"
    Entao o sistema deve lançar uma exceção com a mensagem "Quantidade inválida"

  @sla
  Cenario: Calcular tempo estimado de preparo
    Quando o cliente solicita o tempo estimado para 3 itens
    Entao o tempo estimado deve ser 14 minutos