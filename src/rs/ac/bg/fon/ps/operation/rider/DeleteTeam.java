/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operation.rider;

import rs.ac.bg.fon.ps.domain.RacingTeam;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Matija
 */
public class DeleteTeam extends AbstractGenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
         repository.delete((RacingTeam) param);
    }
    
}
