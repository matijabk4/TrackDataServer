/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation;

import rs.ac.bg.fon.ps.repository.Repository;
import rs.ac.bg.fon.ps.repository.db.DBRepository;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBGeneric;

/**
 *
 * @author Matija
 */
public abstract class AbstractGenericOperation {

    protected final Repository repository;

    public AbstractGenericOperation() {
        this.repository = new RepositoryDBGeneric();
    }

    public final void execute(Object param) throws Exception {
        try {
            startTransaction();
            preconditions(param);
            executeOperation(param);
            commitTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            rollbackTransaction();
            throw ex;
        } finally {
            disconnect();
        }
    }

    private void startTransaction() throws Exception {
        ((DBRepository) repository).connect();
    }

    //Zavisi od konkretne operacije
    protected abstract void preconditions(Object param) throws Exception;

    //Zavisi od konkretne operacije
    protected abstract void executeOperation(Object param) throws Exception;
    
    private void commitTransaction() throws Exception {
        ((DBRepository) repository).commit();
    }

    private void rollbackTransaction() throws Exception {
        ((DBRepository) repository).rollback();
    }

    private void disconnect() throws Exception {
        //((DBRepository) repository).disconnect();
    }
}
