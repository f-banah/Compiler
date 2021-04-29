package com.asem;


public class Symbole {
   public  String identifant ;
    public  String type ;
     public String adresse;
    public  String valeur;
    public  boolean isConstante;
     

    public Symbole(String identifant, String type, String adresse, String valeur, boolean isConstante) {
		super();
		this.identifant = identifant;
		this.type = type;
		this.adresse = adresse;
		this.valeur = valeur;
		this.isConstante = isConstante;
	}

	public Symbole(){
        this.isConstante = false;
    }

    public String getIdentifant() {
        return identifant;
    }

    public void setIdentifant(String identifant) {
        this.identifant = identifant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public void setAdresse(String type) {
        this.adresse = type;
    }

    public boolean isConstante() {
        return isConstante;
    }

    public void setConstante(boolean constante) {
        isConstante = constante;
    }

    @Override
    public String toString() {
        return "Symbole{" +
                "identifant='" + identifant + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

/*public class Symbole {
private String identifant ;
private String type ;
private boolean isConstante;

public Symbole(){
    this.isConstante = false;
}

public String getIdentifant() {
    return identifant;
}

public void setIdentifant(String identifant) {
    this.identifant = identifant;
}

public String getType() {
    return type;
}

public void setType(String type) {
    this.type = type;
}

public boolean isConstante() {
    return isConstante;
}

public void setConstante(boolean constante) {
    isConstante = constante;
}

@Override
public String toString() {
    return "Symbole{" +
            "identifant='" + identifant + '\'' +
            ", type='" + type + '\'' +
            '}';
}
}
*/
