package entity;

public class Location {
    private int id;
    private int id_u;
    private int id_v;
    private String startDate;
    private String endDate;
    private int prix;

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public Location(int id, int id_u, int id_v, String startDate, String endDate, int prix) {
        this.id = id;
        this.id_u = id_u;
        this.id_v = id_v;
        this.startDate = startDate;
        this.endDate = endDate;
        this.prix = prix;
    }

    public Location(int id_u, int id_v, String startDate, String endDate) {
        this.id_u = id_u;
        this.id_v = id_v;
        this.startDate = startDate;
        this.endDate = endDate;

        // Calculate the total price based on the rental dates and the rented bike

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return id_u;
    }

    public void setClientId(int clientId) {
        this.id_u = clientId;
    }

    public int getVeloId() {
        return id_v;
    }

    public void setVeloId(int veloId) {
        this.id_v = veloId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }





    private float calculateTotalPrice() {
        return 0;
    }

    @Override
    public String toString() {
        // Method toString to display the location details
        return null;
    }



}

