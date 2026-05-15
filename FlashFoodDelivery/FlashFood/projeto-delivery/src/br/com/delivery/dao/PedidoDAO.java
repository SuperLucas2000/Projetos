package br.com.delivery.dao;

import br.com.delivery.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelas operações CRUD da entidade Pedido.
 *
 * Gerencia a persistência do pedido principal e seus itens
 * nas tabelas pedidos e itens_pedido do banco de dados.
 *
 * @author FlashFood — Projeto Integrado POO + BD
 * @version 3.0
 */
public class PedidoDAO {

    // ==================== CREATE ====================

    /**
     * Insere um pedido e seus itens no banco de dados.
     * Usa transação para garantir consistência.
     *
     * @param pedido Pedido a ser inserido (deve ter cliente definido)
     * @return true se inserido com sucesso
     */
    public boolean inserir(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedidos (cliente_id, entregador_id, subtotal, desconto, " +
                           "taxa_entrega, valor_total, status) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
        String sqlItem = "INSERT INTO itens_pedido (pedido_id, produto_nome, produto_preco, quantidade) " +
                         "VALUES (?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = ConexaoBD.getConexao();
            conn.setAutoCommit(false); // inicia transação

            PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido);
            stmtPedido.setInt(1, pedido.getCliente().getId());
            stmtPedido.setObject(2, pedido.getEntregador() != null
                    ? pedido.getEntregador().getId() : null, Types.INTEGER);
            stmtPedido.setDouble(3, pedido.getSubtotal());
            stmtPedido.setDouble(4, pedido.getDesconto());
            stmtPedido.setDouble(5, pedido.getTaxaEntrega());
            stmtPedido.setDouble(6, pedido.getValorTotal());
            stmtPedido.setString(7, pedido.getStatus());

            ResultSet rs = stmtPedido.executeQuery();
            int pedidoId = 0;
            if (rs.next()) {
                pedidoId = rs.getInt("id");
                pedido.setId(pedidoId);
            }

            // Insere cada item do pedido
            for (ItemPedido item : pedido.getItens()) {
                PreparedStatement stmtItem = conn.prepareStatement(sqlItem);
                stmtItem.setInt(1, pedidoId);
                stmtItem.setString(2, item.getProduto().getNome());
                stmtItem.setDouble(3, item.getProduto().getPreco());
                stmtItem.setInt(4, item.getQuantidade());
                stmtItem.executeUpdate();
            }

            conn.commit(); // confirma transação
            return true;

        } catch (SQLException e) {
            System.out.println("✗ Erro ao inserir pedido: " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { /* ignora */ }
            return false;
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException ex) { /* ignora */ }
        }
    }

    // ==================== READ ====================

    /**
     * Lista todos os pedidos cadastrados (sem itens detalhados).
     *
     * @return Lista de pedidos
     */
    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT p.id, p.subtotal, p.desconto, p.taxa_entrega, p.valor_total, " +
                     "p.status, c.id AS cid, c.nome AS cnome " +
                     "FROM pedidos p " +
                     "JOIN clientes c ON p.cliente_id = c.id " +
                     "ORDER BY p.id DESC";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setSubtotal(rs.getDouble("subtotal"));
                pedido.setDesconto(rs.getDouble("desconto"));
                pedido.setTaxaEntrega(rs.getDouble("taxa_entrega"));
                pedido.setValorTotal(rs.getDouble("valor_total"));
                pedido.setStatus(rs.getString("status"));

                Cliente c = new Cliente();
                c.setId(rs.getInt("cid"));
                c.setNome(rs.getString("cnome"));
                pedido.setCliente(c);

                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println("✗ Erro ao listar pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    /**
     * Busca um pedido pelo ID, incluindo seus itens.
     *
     * @param id ID do pedido
     * @return Pedido completo ou null se não encontrado
     */
    public Pedido buscarPorId(int id) {
        String sqlPedido = "SELECT p.id, p.subtotal, p.desconto, p.taxa_entrega, " +
                           "p.valor_total, p.status, " +
                           "c.id AS cid, c.nome AS cnome, c.email AS cemail, " +
                           "c.telefone AS ctel, c.endereco AS cend " +
                           "FROM pedidos p JOIN clientes c ON p.cliente_id = c.id WHERE p.id = ?";
        String sqlItens = "SELECT produto_nome, produto_preco, quantidade FROM itens_pedido WHERE pedido_id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sqlPedido)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setSubtotal(rs.getDouble("subtotal"));
                pedido.setDesconto(rs.getDouble("desconto"));
                pedido.setTaxaEntrega(rs.getDouble("taxa_entrega"));
                pedido.setValorTotal(rs.getDouble("valor_total"));
                pedido.setStatus(rs.getString("status"));

                Cliente c = new Cliente();
                c.setId(rs.getInt("cid"));
                c.setNome(rs.getString("cnome"));
                c.setEmail(rs.getString("cemail"));
                c.setTelefone(rs.getString("ctel"));
                c.setEndereco(rs.getString("cend"));
                pedido.setCliente(c);

                // Carrega os itens do pedido
                PreparedStatement stmtItens = conn.prepareStatement(sqlItens);
                stmtItens.setInt(1, id);
                ResultSet rsItens = stmtItens.executeQuery();
                while (rsItens.next()) {
                    Produto p = new Produto(rsItens.getString("produto_nome"),
                                            rsItens.getDouble("produto_preco"));
                    ItemPedido item = new ItemPedido(p, rsItens.getInt("quantidade"));
                    pedido.getItens().add(item);
                }

                return pedido;
            }

        } catch (SQLException e) {
            System.out.println("✗ Erro ao buscar pedido: " + e.getMessage());
        }
        return null;
    }

    // ==================== UPDATE ====================

    /**
     * Atualiza o status e os valores financeiros de um pedido.
     *
     * @param pedido Pedido com dados atualizados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Pedido pedido) {
        String sql = "UPDATE pedidos SET status = ?, subtotal = ?, desconto = ?, " +
                     "taxa_entrega = ?, valor_total = ?, entregador_id = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getStatus());
            stmt.setDouble(2, pedido.getSubtotal());
            stmt.setDouble(3, pedido.getDesconto());
            stmt.setDouble(4, pedido.getTaxaEntrega());
            stmt.setDouble(5, pedido.getValorTotal());
            stmt.setObject(6, pedido.getEntregador() != null
                    ? pedido.getEntregador().getId() : null, Types.INTEGER);
            stmt.setInt(7, pedido.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("✗ Erro ao atualizar pedido: " + e.getMessage());
            return false;
        }
    }

    // ==================== DELETE ====================

    /**
     * Exclui um pedido e seus itens do banco de dados.
     * Os itens são excluídos em cascata (configurado no banco).
     *
     * @param id ID do pedido a ser excluído
     * @return true se excluído com sucesso
     */
    public boolean excluir(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("✗ Erro ao excluir pedido: " + e.getMessage());
            return false;
        }
    }
}
