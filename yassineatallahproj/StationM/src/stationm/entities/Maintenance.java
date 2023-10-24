/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stationm.entities;

import java.util.Date;

/**
 *
 * @author 21655
 */
public class Maintenance {
 
    private int id_m;
    private Velo velol;
    private Date start_time;
    private Date end_time;
    
    public Maintenance (){
        
    }

    public Maintenance(int id_m, Velo velol, Date start_time, Date end_time) {
        this.id_m = id_m;
        this.velol = velol;
        this.start_time = start_time;
        this.end_time = end_time;
    }

  

   

   

    public int getId_m() {
        return id_m;
    }

    public void setId_m(int id_m) {
        this.id_m = id_m;
    }

    public Velo getVelol() {
        return velol;
    }

    public void setVelol(Velo velol) {
        this.velol = velol;
    }

   
    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }
    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
}

   