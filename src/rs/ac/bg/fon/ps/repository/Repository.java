/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository;

import java.util.List;
import rs.ac.bg.fon.ps.domain.RaceItem;

/**
 *
 * @author Matija
 * @param <T>
 */
public interface Repository<T> {

    List<T> getAll() throws Exception;

    List<T> getByCriteria(Object criteria) throws Exception;

    void add(T param) throws Exception;

    void edit(T param) throws Exception;

    void delete(T param) throws Exception;

    public void update(Object r) throws Exception;
    
    List<T> getAll(T param) throws Exception;
}
