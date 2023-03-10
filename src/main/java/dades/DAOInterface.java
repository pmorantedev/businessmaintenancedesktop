package dades;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfície dels mètodes comuns pel DAO
 *
 * @author Pablo Morante - Creació/Implementació
 * @param <T> Instància de tipus T
 */
public interface DAOInterface<T> {

    /**
     * *
     * Retorna tots els elements d'una taula
     *
     * @return List tipus T
     * @throws java.sql.SQLException java.sql.SQLException
     */
    public List<T> getAll() throws SQLException;

    /**
     * *
     * Desa un nou element a la taula si aquest no existeix ja
     *
     * @param t Instància de tipus T
     * @throws java.sql.SQLException java.sql.SQLException
     */
    public void save(T t) throws SQLException;

    /**
     * *
     * Actualitza un element existent a la taula pel seu id si existeix
     *
     * @param t Instància de tipus T
     * @throws java.sql.SQLException java.sql.SQLException
     */
    public void update(T t) throws SQLException;

    /**
     * *
     * Elimina un element de la taula pel seu id si existeix
     *
     * @param t Instància de tipus T
     * @throws java.sql.SQLException java.sql.SQLException
     */
    public void delete(T t) throws SQLException;

    /**
     * *
     * Recupera un element de la taula pel seu id o null si no existeix
     *
     * @param t Instància de tipus T
     * @return Instància de tipus T
     * @throws java.sql.SQLException java.sql.SQLException
     */
    public T getOne(T t) throws SQLException;

}
