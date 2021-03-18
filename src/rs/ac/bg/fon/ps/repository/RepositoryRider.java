/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Rider;

/**
 *
 * @author Matija
 */
public class RepositoryRider {

    private final List<Rider> riders;

    public RepositoryRider() {
        riders = new ArrayList<>();

    }

    public void add(Rider rider) {
        riders.add(rider);
    }

    public List<Rider> getAll() {
        return riders;
    }

    public void remove(Rider r) throws Exception {
        int index = riders.indexOf(r);
        if (index >= 0) {
            riders.remove(index);
        } else {
            throw new Exception("Error: Rider does not exist!");
        }
    }
}
