package com.example.mybike;

import entity.Location;
import java.util.List;

public interface IserviceLocation<X> {
    void ajouter(X t);

    void supprimer(int id);

    void modifier(X t);
    List<Location> afficher();
}