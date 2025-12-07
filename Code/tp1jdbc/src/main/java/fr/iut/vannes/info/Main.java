package fr.iut.vannes.info;

import fr.iut.vannes.info.jobs.*;
import fr.iut.vannes.info.dao.BanqueDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Connexion avec Docker
            connection = DriverManager.getConnection("jdbc:postgresql://db:5432/banque_db", "user", "password");

            BanqueDAO dao = new BanqueDAO(connection);

            // Ajout agence
            Agence agence = new Agence(1, "0297472651", "10 rue de la nature");
            dao.ajouterAgence(agence);
            System.out.println("Agence ajoutee");

            // Ajout agent
            Agent agent = new Agent(1, "Le Divenach", "Lucas", 2500.0, true, 1);
            dao.ajouterAgent(agent);
            System.out.println("Agent ajoute");

            // Ajout client
            Client client = new Client(1, "Lambert", "Damien", "5 avenue de la république", Date.valueOf("1980-07-25"), 1);
            dao.ajouterClient(client);
            System.out.println("Client ajoute");

            // Ajout compte
            Compte compte = new Compte(1, 100000.0, "Courant");
            dao.ajouterCompte(compte);
            System.out.println("Compte ajoute");

            // Ajout operation
            Operation operation = new Operation(1, Date.valueOf("2025-11-27"), "Depot", 500.0, 1);
            dao.ajouterOperation(operation);
            System.out.println("Operation ajoutee");

            // Recuperer solde
            double solde = dao.obtenirSoldeCompte(1);
            System.out.println("Solde du compte 1 : " + solde);

            // Recuperer client par numéro (CORRECTION : id = 1)
            Client clientRecup = dao.obtenirClientParNum(1);
            System.out.println("Client récupéré : " +
                    clientRecup.getNomClient() + " " + clientRecup.getPrenomClient());

            // Recuperer operations d'un compte
            List<Operation> operations = dao.obtenirOperationsParCompte(1);
            System.out.println("Liste des operations du compte 1 :");
            for (Operation op : operations) {
                System.out.println(op.getTypeOperation() + " : " + op.getMontant());
            }

            // Recuperer clients d'un agent
            List<Client> clients = dao.obtenirClientsParAgent(1);
            System.out.println("Clients de l'agent 1 :");
            for (Client c : clients) {
                System.out.println(c.getNomClient() + " " + c.getPrenomClient());
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
