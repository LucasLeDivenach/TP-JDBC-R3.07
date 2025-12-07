package fr.iut.vannes.info.jobs;

public class Operation {
    private int numOperation;
    private java.sql.Date dateOperation;
    private String typeOperation;
    private double montant;
    private int numCompte;

    public Operation(int numOperation, java.sql.Date dateOperation, String typeOperation, double montant, int numCompte) {
        this.numOperation = numOperation;
        this.dateOperation = dateOperation;
        this.typeOperation = typeOperation;
        this.montant = montant;
        this.numCompte = numCompte;
    }

    // Getters et setters
    public int getNumOperation() { return numOperation; }
    public java.sql.Date getDateOperation() { return dateOperation; }
    public String getTypeOperation() { return typeOperation; }
    public double getMontant() { return montant; }
    public int getNumCompte() { return numCompte; }
}
