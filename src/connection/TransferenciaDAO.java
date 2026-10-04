package connection;

import model.Transferencia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransferenciaDAO {

    public void inserir(Connection con, Transferencia transferencia) {
        String sql = "INSERT INTO transferencia (origem, destino, valor, tarifa, data_hora) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setInt(1, transferencia.getOrigem());
            stmt.setInt(2, transferencia.getDestino());
            stmt.setDouble(3, transferencia.getValor());
            stmt.setDouble(4, transferencia.getTarifa());
            stmt.setTimestamp(5, Timestamp.valueOf(transferencia.getDataHora()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Transferencia> listar() {
            List<Transferencia> transferencias = new ArrayList<>();

            String sql = "SELECT * FROM transferencia";

        try (
                Connection con = Conexao.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {

            while (rs.next()) {
                Transferencia t = new Transferencia(
                        rs.getInt("id"),
                        rs.getInt("origem"),
                        rs.getInt("destino"),
                        rs.getDouble("valor"),
                        rs.getDouble("tarifa"),
                        rs.getTimestamp("data_hora").toLocalDateTime());

                transferencias.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transferencias;
    }



}
