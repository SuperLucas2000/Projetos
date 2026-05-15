package br.com.delivery.dao;

import br.com.delivery.model.Entregador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelas operações CRUD da entidade Entregador.
 * Utiliza PreparedStatement para evitar SQL Injection.
 *
 * @author FlashFood — Projeto Integrado POO + BD
 * @version 3.0
 */
public class EntregadorDAO {

    // ==================== CREATE ====================

    /**
     * Insere um novo entregador no banco de dados.
     *
     * @param entregador Entregador a ser inserido
     * @return true se inserido com sucesso
     */
    public boolean inserir(Entregador entregador) {
        String sql = "INSERT INTO entregadores (nome, email, telefone, veiculo, status) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entregador.getNome());
            stmt.setString(2, entregador.getEmail());
            stmt.setString(3, entregador.getTelefone());
            stmt.setString(4, entregador.getVeiculo());
            stmt.setString(5, entregador.getStatus());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                entregador.setId(rs.getInt("id"));
            }
            return true;

        } catch (SQLException e) {
            System.out.println("✗ Erro ao inserir entregador: " + e.getMessage());
            return false;
        }
    }

    // ==================== READ ====================

    /**
     * Lista todos os entregadores cadastrados.
     *
     * @return Lista de entregadores
     */
    public List<Entregador> listarTodos() {
        List<Entregador> entregadores = new ArrayList<>();
        String sql = "SELECT id, nome, email, telefone, veiculo, status FROM entregadores ORDER BY nome";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Entregador e = new Entregador();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setEmail(rs.getString("email"));
                e.setTelefone(rs.getString("telefone"));
                e.setVeiculo(rs.getString("veiculo"));
                e.setStatus(rs.getString("status"));
                entregadores.add(e);
            }

        } catch (SQLException e) {
            System.out.println("✗ Erro ao listar entregadores: " + e.getMessage());
        }
        return entregadores;
    }

    /**
     * Busca entregadores com status DISPONÍVEL.
     *
     * @return Lista de entregadores disponíveis
     */
    public List<Entregador> listarDisponiveis() {
        List<Entregador> disponiveis = new ArrayList<>();
        String sql = "SELECT id, nome, email, telefone, veiculo, status FROM entregadores " +
                     "WHERE status = 'DISPONÍVEL' ORDER BY nome";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Entregador e = new Entregador();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setEmail(rs.getString("email"));
                e.setTelefone(rs.getString("telefone"));
                e.setVeiculo(rs.getString("veiculo"));
                e.setStatus(rs.getString("status"));
                disponiveis.add(e);
            }

        } catch (SQLException e) {
            System.out.println("✗ Erro ao listar entregadores disponíveis: " + e.getMessage());
        }
        return disponiveis;
    }

    /**
     * Busca um entregador pelo ID.
     *
     * @param id ID do entregador
     * @return Entregador encontrado ou null
     */
    public Entregador buscarPorId(int id) {
        String sql = "SELECT id, nome, email, telefone, veiculo, status FROM entregadores WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Entregador e = new Entregador();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setEmail(rs.getString("email"));
                e.setTelefone(rs.getString("telefone"));
                e.setVeiculo(rs.getString("veiculo"));
                e.setStatus(rs.getString("status"));
                return e;
            }

        } catch (SQLException e) {
            System.out.println("✗ Erro ao buscar entregador: " + e.getMessage());
        }
        return null;
    }

    // ==================== UPDATE ====================

    /**
     * Atualiza os dados de um entregador existente.
     *
     * @param entregador Entregador com novos dados
     * @return true se atualizado com sucesso
     */
    public boolean atualizar(Entregador entregador) {
        String sql = "UPDATE entregadores SET nome = ?, email = ?, telefone = ?, " +
                     "veiculo = ?, status = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entregador.getNome());
            stmt.setString(2, entregador.getEmail());
            stmt.setString(3, entregador.getTelefone());
            stmt.setString(4, entregador.getVeiculo());
            stmt.setString(5, entregador.getStatus());
            stmt.setInt(6, entregador.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("✗ Erro ao atualizar entregador: " + e.getMessage());
            return false;
        }
    }

    // ==================== DELETE ====================

    /**
     * Exclui um entregador pelo ID.
     *
     * @param id ID do entregador
     * @return true se excluído com sucesso
     */
    public boolean excluir(int id) {
        String sql = "DELETE FROM entregadores WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("✗ Erro ao excluir entregador: " + e.getMessage());
            return false;
        }
    }
}
