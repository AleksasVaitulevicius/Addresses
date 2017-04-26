


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aleksas
 */
public class AddressModel{
    public int ID;
    public String country;
    public String city;
    public String street;
    public String buildingNr;
    public String flatNr;
    public String ZIPCode;

    public AddressModel(int ID, String country, String city, String street, String buildingNr, String flatNr, String ZIPCode){
        this.ID = ID;
        this.country = country;
        this.city = city;
        this.street = street;
        this.buildingNr = buildingNr;
        this.flatNr = flatNr;
        this.ZIPCode = ZIPCode;
    }

    @Override
    public String toString(){
        return ID + ":" + country + "," + city + "," + street + " " + buildingNr + "-" + flatNr + " ZIP:" + ZIPCode;
    }
}
