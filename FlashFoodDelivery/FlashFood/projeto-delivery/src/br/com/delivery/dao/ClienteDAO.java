package br.com.delivery.dao;

import br.com.delivery.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) responsável pelas operações CRUD
 * da entidade Cliente no banco de dados PostgreSQL.
 *
 * Operações implementadas:
 * - Create : inserir(Cliente)
 * - Read   : listarTodos() e buscarPorId(int)
 * - Update : atualizar(Cliente)
 * - Delete : excluir(int)
 *
 * Utiliza PreparedStatement para evitar SQL Injection.
 *
 * @author FlashFood — Projeto Integrado POO + BD
 * @version 3.0
 */
public class ClienteDAO {

    // ==================== CREATE ====================

    /**
     * Insere um novo cliente no banco de dados.
     * O ID é gerado automaticamente pelo banco e atribuído ao objeto.
     *
     * @param cliente Cliente a ser inserido
     * @return true se inserido com sucesso, false caso contrário
     */
    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, email, telefone, endereco) " +
                     "VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cliente.setId(rs.getInt("id")); // atribui o ID gerado pelo BD
            }
            return true;

        } catch (SQLException e) {
            System.out.println("✗ Erro ao inserir cliente: " + e.getMessage());
            return false;
        }
    }

    // ==================== READ ====================

    /**
     * Lista todos os clientes cadastrados no banco de dados.
     *
     * @return Lista de clientes (pode ser vazia)
     */
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nome, email, telefone, endereco FROM clientes ORDER BY nome";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                c.setEndereco(rs.getString("endereco"));
                clientes.add(c);
            }

        } catch (SQLException e) {
            System.out.println("✗ Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    /**
     * Busca um cliente pelo ID.
     *
     * @param id ID do cliente
     * @return Cliente encontrado ou null se não existir
     */
    public Cliente buscarPorId(int id) {
        String sql = "SELECT id, nome, email, telefone, endereco FROM clientes WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                c.setEndereco(rs.getString("endereco"));
                return c;
            }

        } catch (SQLException e) {
            System.out.println("✗ Erro ao buscar cliente: " + e.getMessage());
        }
        return null;
    }

    // ==================== UPDATE ====================

    /**
     * Atualiza os dados de um cliente existente no banco.
     *
     * @param cliente Cliente com os novos dados (deve ter ID preenchido)
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, email = ?, telefone = ?, endereco = ? " +
                     "WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());
            stmt.setInt(5, cliente.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("✗ Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // ==================== DELETE ====================

    /**
     * Exclui um cliente do banco de dados pelo ID.
     *
     * @param id ID do cliente a ser excluído
     * @return true se excluído com sucesso, false caso contrário
     */
    public boolean excluir(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("✗ Erro ao excluir cliente: " + e.getMessage());
            return false;
        }
    }
}
