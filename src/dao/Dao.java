package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    /** method returns an Optional of the class T with the specified id from the database
     * @param id an integer that represents the id of the object to be returned from the
     *           database
     * */
    Optional<T> get(int id);

    /** method returns a List of objects T with all the objects of T class from the
     * database*
     * */
    List<T> getAll();

    /** method saves an object of class T in the database
     * @param t is the object to be persisted
     * */
    void save(T t);

    /** method deletes the object with the specified id from the database
     * @param id an integer that represents the id of the object to be
     *           removed from database
     * */
    void delete(int id);

}
