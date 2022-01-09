/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author giuse
 */
public class WrongQueryFormulationException extends Exception{

    @Override
    public String getMessage() {
        return "Wrong Query Formulation!"; //To change body of generated methods, choose Tools | Templates.
    }
}
