package za.luna;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;


@Entity
public class Grass extends PanacheEntity {
    String grass_name;

    public String getGrass_name() {
        return grass_name;
    }

    public void setGrass_name(String grass_name) {
        this.grass_name = grass_name;
    }
}
