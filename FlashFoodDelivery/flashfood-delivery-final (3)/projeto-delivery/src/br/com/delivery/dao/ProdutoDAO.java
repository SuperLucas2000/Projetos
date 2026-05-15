package br.com.delivery.dao;

import br.com.delivery.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelas operações CRUD da entidade Produto.
 *
 * @author FlashFood — Projeto Integrado POO + BD
 * @version 3.0
 */
public class ProdutoDAO {

    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO produtos (nome, preco) VALUES (?, ?) RETURNING id";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) produto.setId(rs.getInt("id"));
            return true;

        } catch (SQLException e) {
            System.out.println("✗ Erro ao inserir produto: " + e.getMessage());
            return false;
        }
    }

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, preco FROM produtos ORDER BY nome";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                produtos.add(p);
            }

        } catch (SQLException e) {
            System.out.println("✗ Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT id, nome, preco FROM produtos WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                return p;
            }

        } catch (SQLException e) {
            System.out.println("✗ Erro ao buscar produto: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, preco = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("✗ Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("✗ Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }
}
