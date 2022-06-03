package data;

import java.io.Serializable;

public class Person implements Serializable {
    private final Double height;
    private final Color eyeColor;
    private final Color hairColor;
    private final Country nationality;

    public Person(Double height, Country nationality, Color eyeColor, Color hairColor){
        this.height = height;
        this.nationality = nationality;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }

    public Double getHeight(){return height;}

    public Color getEyeColor(){return eyeColor;}

    public Color getHairColor(){return hairColor;}

    public Country getNationality(){return nationality;}

    @Override
    public boolean equals(Object obj) {
        if(!this.getClass().equals(obj.getClass())){
            return false;
        }
        Person prsn = (Person)obj;
        return this.height.equals(prsn.getHeight())
                &&this.eyeColor==prsn.getEyeColor()
                &&this.hairColor==prsn.getHairColor()
                &&this.nationality.equals(prsn.getNationality());
    }

    @Override
    public int hashCode() {
        int hash = height.hashCode();
        hash += eyeColor==null ? 17 : eyeColor.hashCode();
        hash += hairColor==null ? 12319 : hairColor.hashCode();
        return hash;
    }
}