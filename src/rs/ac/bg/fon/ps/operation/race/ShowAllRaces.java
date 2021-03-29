/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.race;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Race;
import rs.ac.bg.fon.ps.domain.Rider;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Matija
 */
public class ShowAllRaces extends AbstractGenericOperation{

    List<Race> races;

    public List<Race> getRaces() {
        return races;
    }

    
    @Override
    protected void preconditions(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
       races = repository.getAll((Race) param);
        
        
    }
    
}
