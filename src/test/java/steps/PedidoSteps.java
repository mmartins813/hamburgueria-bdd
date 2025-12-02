package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;
import peppa.hamburgueria.CardapioService;
import peppa.hamburgueria.PedidoService;

import java.util.List;
import java.util.Map;

public class PedidoSteps {

    private CardapioService cardapioService;
    private PedidoService pedidoService;
    private double valorTotalCalculado;
    private int tempoEstimadoCalculado;
    private Exception excecaoLancada;

    @Dado("que o cardápio contém os itens:")
    public void que_o_cardapio_contem_os_itens(DataTable dataTable) {
        cardapioService = new CardapioService();
        // Converte a tabela do Gherkin para uma lista de Mapas
        List<Map<String, String>> linhas = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> colunas : linhas) {
            String item = colunas.get("item");
            double preco = Double.parseDouble(colunas.get("preco"));
            cardapioService.cadastrarItem(item, preco);
        }

        // Inicializa o serviço de pedidos com o cardápio preenchido
        pedidoService = new PedidoService(cardapioService);
    }

    @Quando("o cliente pede {int} unidade(s) de {string}")
    public void o_cliente_pede_unidades_de(int quantidade, String item) {
        try {
            valorTotalCalculado = pedidoService.calcularTotal(item, quantidade);
        } catch (Exception e) {
            // Guarda a exceção para verificar a mensagem depois nos testes de erro
            excecaoLancada = e;
        }
    }

    @Entao("o valor total deve ser {double}")
    public void o_valor_total_deve_ser(double valorEsperado) {
        Assertions.assertEquals(valorEsperado, valorTotalCalculado);
    }

    @Entao("o sistema deve lançar uma exceção com a mensagem {string}")
    public void o_sistema_deve_lancar_uma_excecao_com_a_mensagem(String mensagemEsperada) {
        Assertions.assertNotNull(excecaoLancada, "Deveria ter lançado uma exceção, mas não lançou.");
        Assertions.assertEquals(mensagemEsperada, excecaoLancada.getMessage());
    }

    @Quando("o cliente solicita o tempo estimado para {int} itens")
    public void o_cliente_solicita_o_tempo_estimado_para_itens(int quantidadeTotal) {
        tempoEstimadoCalculado = pedidoService.calcularTempoEstimado(quantidadeTotal);
    }

    @Entao("o tempo estimado deve ser {int} minutos")
    public void o_tempo_estimado_deve_ser_minutos(int tempoEsperado) {
        Assertions.assertEquals(tempoEsperado, tempoEstimadoCalculado);
    }
}