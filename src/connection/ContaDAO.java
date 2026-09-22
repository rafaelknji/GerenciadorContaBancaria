package connection;
import model.Conta;
import model.ContaCorrente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {
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


    public List<ContaCorrente> listar () {
        List<ContaCorrente> contas = new ArrayList<>();
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

    public void remover (int numero) {
        String sql = "DELETE FROM dados_conta WHERE numero = ?";

        try (
            Connection con = Conexao.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)
        ){
            stmt.setInt(1, numero);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transferir (int origem, int destino, double valor) {
        String creditoSql = "UPDATE dados_conta SET saldo = saldo + ? WHERE numero = ?";
        String debitoSql = "UPDATE dados_conta SET saldo = saldo - ? WHERE numero = ?";

        try(Connection con = Conexao.getConnection()) {
            con.setAutoCommit(false);

            try (
                    PreparedStatement debito = con.prepareStatement(debitoSql);
                    PreparedStatement credito = con.prepareStatement(creditoSql);
                    ){
                debito.setDouble(1, valor);
                debito.setInt(2, origem);
                debito.executeUpdate();

                credito.setDouble(1, valor);
                credito.setInt(2, destino);
                credito.executeUpdate();

                con.commit();

                System.out.println("Transferencia realiza com sucesso!");

            } catch (SQLException e) {
                con.rollback();
                System.err.println("Erro na transação. Rollback realizado!");
                System.out.println("Erro: " + e.getMessage());

            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro na conexao: " + e.getMessage());
        }
    }
}
