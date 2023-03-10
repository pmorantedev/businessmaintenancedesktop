package entitats;

import dades.AppConfigDAO;
import dades.ComandaDAO;
import dades.ProducteDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextField;

/**
 * Aquí anirà la lògica de negoci de productes, amb les comprovacions pertinents
 *
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Izan Jimenez - Creació/Implementació
 */
public class ProducteLogic {

    //variables DAO per fer consultes necessaries
    private AppConfigDAO dataDefaults;
    private ProducteDAO dataProduct;
    private ComandaDAO dataOrder;

    //Llistes per fer comprovacions
    private final List<Producte> productList = new ArrayList<>();
    private final List<Comanda> ordersList = new ArrayList<>();
    private final List<AppConfig> valuesList = new ArrayList<>();

    /**
     * *
     * Constructor ProducteLogic
     *
     */
    public ProducteLogic() {
    }

    /**
     * Mètode per comprovar que el producte no existeix en cap comanda.
     *
     * @param p Instància de la classe Porducte
     * @return boolean (True/False) si es troben coincidències o no amb la BD
     * @author Izan Jimenez - Implementació
     */
    public boolean productIsInOrders(Producte p) {

        int productCode = p.getProductCode();
        boolean existeix = false;

        try {

            dataProduct = new ProducteDAO();
            dataOrder = new ComandaDAO();

            ordersList.addAll(dataOrder.getAll());

            for (int i = 0; i <= ordersList.size() - 1; i++) {

                List<ProductesComanda> llistProductesInComanda = dataOrder.getProductes(ordersList.get(i).getNumOrdre());

                // List<ProductesComanda> llistProductesInComanda = ordersList.get(i).getProductes();
                for (ProductesComanda productesComanda : llistProductesInComanda) {
                    if (productCode == productesComanda.getIdProducte()) {
                        return true;
                    }
                }
            }

        } catch (SQLException ex) {
        }
        ordersList.clear();
        return existeix;
    }

    /**
     * Mètode per retornar la quantitat mínima d'stock (RF32).
     *
     * @author Izan Jimenez - Implementació
     * @return Retorna el minim d'stock configurat a la BBDD
     */
    public int getDefaultStock() {

        int stock = 0;
        try {
            dataDefaults = new AppConfigDAO();
            valuesList.addAll(dataDefaults.getAll());
            stock = valuesList.get(0).getDefaultStock();
        } catch (SQLException ex) {
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        valuesList.clear();
        return stock;
    }

    /**
     * *
     * Mètode per comprovar si el paràmetre introduït és major al mínim Stock
     *
     * @param f Camp de text del formulari
     * @return boolean (True/False) si el valor és vàlid
     * @author Izan Jimenez - Implementació
     */
    public boolean checkStock(TextField f) {
        boolean ret = false;
        try {

            int stock = Integer.parseInt(f.getText());
            int minStock = getDefaultStock();

            if (stock >= minStock) {
                ret = true;
            }
        } catch (NumberFormatException e) {
        }
        return ret;
    }

    /**
     * Mètode per verificar que el producte no existeixi ja a la BD (nom).
     *
     * @param f Camp de texte del formulari
     * @return boolean (True/False) si es troben coincidències o no amb la BD
     * @author Izan Jimenez - Implementació
     */
    public boolean checkProductExists(TextField f) {

        boolean res = false;
        String nom = f.getText().trim();
        try {
            dataProduct = new ProducteDAO();
            productList.addAll(dataProduct.getAll());

            for (int i = 0; i < productList.size(); i++) {
                if (nom.equals(productList.get(i).getProductName())) {
                    res = true;
                }
            }
        } catch (SQLException ex) {
        }
        productList.clear();

        return res;
    }

    /**
     * Mètode per verificar que el preu sigui valid.
     *
     * @param f Camp de texte del formulari
     * @return boolean (True/False) si es valid
     * @author Izan Jimenez - Implementació
     */
    public boolean checkPreu(TextField f) {
        boolean ret = false;
        try {
            float preu = Float.parseFloat(f.getText());
            if (preu > 0.0) {
                ret = true;
            }
        } catch (NumberFormatException e) {

        }

        return ret;
    }

}
