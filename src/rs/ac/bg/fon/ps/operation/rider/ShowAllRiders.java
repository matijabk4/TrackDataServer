/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.rider;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Rider;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Matija
 */
public class ShowAllRiders extends AbstractGenericOperation{
    List<Rider> riders;

    public List<Rider> getRiders() {
        return riders;
    }

    
    @Override
    protected void preconditions(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
       riders = repository.getAll((Rider) param);
        
        //return riders;
    }
    
}
