package connection;
import model.Conta;
import model.ContaCorrente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class contaDAO {
    public void inserir (Conta conta) {
        String sql = "INSERT INTO dados_conta (numero, titular, saldo) VALUES (?, ?, ?)";

        try (
            Connection con = Conexao.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)
        ){

            stmt.setInt(1, conta.getNumero());
            stmt.setString(2, conta.getTitular());
            stmt.setDouble(3, conta.getSaldo());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Conta> listar () {
        List<Conta> contas = new ArrayList<>();
        String sql = "SELECT * FROM dados_conta";

        try (
            Connection con = Conexao.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ){
            while (rs.next()) {
                ContaCorrente c = new ContaCorrente(
                        rs.getString("titular"),
                        rs.getInt("numero"),
                        rs.getDouble("saldo")
                );
                contas.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contas;
    }


    public Conta buscarPorNumero (int numero) {
        String sql = "SELECT * FROM dados_conta WHERE numero = ?";

        try (
                Connection con = Conexao.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)
        ) {

            stmt.setInt(1, numero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ContaCorrente(
                            rs.getString("titular"),
                            rs.getInt("numero"),
                            rs.getDouble("saldo")
                    );
                }
            }

        }catch (SQLException e) {
                e.printStackTrace();
        }
        return null;
    }


    public void atualizarSaldo (int numero, double novoSaldo) {
        String sql = "UPDATE dados_conta SET saldo = ? WHERE numero = ?";

        try (
            Connection con = Conexao.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)
        ){

            stmt.setDouble(1, novoSaldo);
            stmt.setInt(2, numero);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar (int numero) {
        String sql = "DELETE FROM dados_conta WHERE numero = ?";

        try (
            Connection con = Conexao.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)
        ){

            stmt.setInt(1, numero);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
