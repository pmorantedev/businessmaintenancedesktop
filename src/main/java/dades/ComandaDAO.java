package dades;

import entitats.Comanda;
import entitats.Producte;
import entitats.ProductesComanda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CRUD de Comandes a la BBDD.
 *
 * @author Pablo Morante - Creació/Implementació
 * @author Victor García - Creació/Implementació
 */
public class ComandaDAO extends DataLayer implements DAOInterface<Comanda> {

    // Preparar instància per realitzar una consulta SQL
    private final Statement stmt = con.createStatement();

    public ComandaDAO() throws SQLException {
        super();
    }

    /**
     * Mostrar tots els clients de la taula 'orders'
     *
     * @author Víctor García
     * @throws SQLException
     */
    @Override
    public List<Comanda> getAll() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        // Crear llistat on desar els registres dels clients
        List<Comanda> ret = new ArrayList<>();

        // Definir consulta SQL per llegir totes les dades de la taula 'clients'
        ResultSet resultats = stmt.executeQuery("SELECT * FROM orders;");

        // Mostrar resultats disponibles a la taula via nom del camp
        while (resultats.next()) {
            Comanda o = new Comanda();

            o.setNumOrdre(resultats.getInt("orderNumber"));
            System.out.println("orderNumber: " + resultats.getInt("orderNumber"));
            o.setDataOrdre(resultats.getDate("orderDate"));
            o.setDataEntrega(resultats.getDate("requiredDate"));
            o.setDataEnviament(resultats.getDate("shippedDate"));
            o.setCustomers_customerEmail(resultats.getString("customers_customerEmail"));

            ret.add(o);
        }

        return ret;
    }

    @Override
    public void save(Comanda c) throws SQLException {
        String consulta = "INSERT INTO orders (orderDate, requiredDate, customers_customerEmail) VALUES (?,?,?);";
        
        PreparedStatement preparedStatement;
        try {
            
            preparedStatement = con.prepareStatement(consulta);
            
            preparedStatement.setDate(1, c.getDataOrdre());
            preparedStatement.setDate(2, c.getDataEntrega());
            preparedStatement.setString(3, c.getCustomers_customerEmail());
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("ERROR amb la connexió a SQL");
            System.out.println("SQLExceptionq: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void update(Comanda t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Comanda t) {
        // Definir consulta SQL per eliminar el registre actual de la taula 'orders'
        String consulta = "DELETE FROM orders WHERE orderNumber = ?;";

        PreparedStatement preparedStatement;
        try {

            preparedStatement = con.prepareStatement(consulta);

            preparedStatement.setString(1, Integer.toString(t.getNumOrdre()));
            preparedStatement.executeUpdate();
            System.out.println(">> Eliminada ordre: " + t.toString());

        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Comanda getOne(Comanda t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<ProductesComanda> getProductes(int id) throws SQLException {
        List<ProductesComanda> ret = new ArrayList<>();
        String consulta = "select productCode, quantityOrdered, priceEach, orderLineNumber from orderdetails where orderNumber = ?";
        PreparedStatement sentencia = this.getCon().prepareStatement(consulta);

        sentencia.setInt(1, id);

        ResultSet resultats = sentencia.executeQuery();

        while (resultats.next()) {
            ProductesComanda pc = new ProductesComanda();

            pc.setIdProducte(resultats.getInt("productCode"));
            pc.setNom("prueba");
            pc.setNumberLine(resultats.getInt("orderLineNumber"));
            pc.setQuantitat(resultats.getInt("quantityOrdered"));
            pc.setUnitaryPrice(resultats.getFloat("priceEach"));
            pc.setTotal(resultats.getFloat("priceEach")*resultats.getInt("quantityOrdered"));

            ret.add(pc);
        }

        return ret;

    }

    public void saveProduct(boolean novaComanda, Producte p, int idComanda) throws SQLException {
        if (novaComanda) {
            String insert = "insert into orderdetails values ((select orderNumber from orders where orderNumber = ?), (select productCode from products where productCode = ?), ?, ?, ?)";

            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(insert);

                preparedStatement.setInt(1, 4);
                preparedStatement.setInt(2, p.getProductCode());
                preparedStatement.setInt(3, 1);
                preparedStatement.setFloat(4, p.getBuyPrice());
                preparedStatement.setInt(5, 1);
                preparedStatement.executeUpdate();
                System.out.println(">> Producte insertat: " + p.toString());
            } catch (SQLException ex) {
                Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            ResultSet resultats = stmt.executeQuery("insert into orderdetails where orderNumber = ?");
        }
    }

}
