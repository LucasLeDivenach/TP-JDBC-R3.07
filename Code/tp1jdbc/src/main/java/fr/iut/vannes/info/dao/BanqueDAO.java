package fr.iut.vannes.info.dao;

import fr.iut.vannes.info.jobs.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BanqueDAO {

    private static final Logger logger = LogManager.getLogger(BanqueDAO.class);
    private Connection connection;

    public BanqueDAO(Connection connection) {
        this.connection = connection;
        logger.info("Initialisation de BanqueDAO avec une nouvelle connexion");
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        logger.info("Connexion mise à jour dans BanqueDAO");
    }

    public Connection getConnection() {
        return connection;
    }

    public void ajouterAgence(Agence agence) throws SQLException {
        logger.debug("Tentative d'ajout de l'agence : {}", agence.getNumAgence());
        String sql = "INSERT INTO Agence (numAgence, telAgence, adAgence) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (agenceExisteDeja(agence.getNumAgence())) {
                logger.warn("Agence déjà existante : {}", agence.getNumAgence());
                throw new SQLException("Agence déjà existante !");
            }

            connection.setAutoCommit(false);
            pstmt.setInt(1, agence.getNumAgence());
            pstmt.setString(2, agence.getTelAgence());
            pstmt.setString(3, agence.getAdAgence());

            int rowsAffected = pstmt.executeUpdate();
            connection.commit();
            logger.info("Agence {} ajoutée avec succès. Lignes affectées : {}", agence.getNumAgence(), rowsAffected);

        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout de l'agence {} : {}", agence.getNumAgence(), e.getMessage());
            throw e;
        }
    }

    private boolean agenceExisteDeja(int numAgence) throws SQLException {
        String sql = "SELECT 1 FROM Agence WHERE numAgence = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numAgence);
            return pstmt.executeQuery().next();
        }
    }

    public void ajouterAgent(Agent agent) throws SQLException {
        logger.debug("Tentative d'ajout de l'agent : {}", agent.getNumAgent());
        String sql = "INSERT INTO Agent (numAgent, nomAgent, prenomAgent, salaire, estDirecteur, numAgence) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (agentExisteDeja(agent.getNumAgent())) {
                logger.warn("Agent déjà existant : {}", agent.getNumAgent());
                throw new SQLException("Agent déjà existant !");
            }

            connection.setAutoCommit(false);
            pstmt.setInt(1, agent.getNumAgent());
            pstmt.setString(2, agent.getNomAgent());
            pstmt.setString(3, agent.getPrenomAgent());
            pstmt.setDouble(4, agent.getSalaire());
            pstmt.setBoolean(5, agent.isEstDirecteur());
            pstmt.setInt(6, agent.getNumAgence());

            int rowsAffected = pstmt.executeUpdate();
            connection.commit();
            logger.info("Agent {} ajouté avec succès. Lignes affectées : {}", agent.getNumAgent(), rowsAffected);

        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout de l'agent {} : {}", agent.getNumAgent(), e.getMessage());
            throw e;
        }
    }

    private boolean agentExisteDeja(int numAgent) throws SQLException {
        String sql = "SELECT 1 FROM Agent WHERE numAgent = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numAgent);
            return pstmt.executeQuery().next();
        }
    }

    public void ajouterClient(Client client) throws SQLException {
        logger.debug("Tentative d'ajout du client : {}", client.getNomClient());
        String sql = "INSERT INTO Client (numClient, nomClient, prenomClient, adClient, dateNaissClient, ageClient, numAgent) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (clientExisteDeja(client.getNumClient())) {
                logger.warn("Client déjà existant : {}", client.getNumClient());
                throw new SQLException("Client déjà existant !");
            }

            connection.setAutoCommit(false);
            pstmt.setInt(1, client.getNumClient());
            pstmt.setString(2, client.getNomClient());
            pstmt.setString(3, client.getPrenomClient());
            pstmt.setString(4, client.getAdClient());
            pstmt.setDate(5, client.getDateNaissClient());
            pstmt.setInt(6, client.getAgeClient());
            pstmt.setInt(7, client.getNumAgent());

            int rowsAffected = pstmt.executeUpdate();
            connection.commit();
            logger.info("Client {} ajouté avec succès. Lignes affectées : {}", client.getNomClient(), rowsAffected);

        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout du client {} : {}", client.getNomClient(), e.getMessage());
            throw e;
        }
    }

    private boolean clientExisteDeja(int numClient) throws SQLException {
        String sql = "SELECT 1 FROM Client WHERE numClient = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numClient);
            return pstmt.executeQuery().next();
        }
    }

    public Client obtenirClientParNum(int numClient) throws SQLException {
        logger.debug("Recherche du client avec numClient = {}", numClient);
        String sql = "SELECT * FROM Client WHERE numClient = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numClient);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Client client = new Client(rs.getInt("numClient"), rs.getString("nomClient"), rs.getString("prenomClient"), rs.getString("adClient"), rs.getDate("dateNaissClient"), rs.getInt("numAgent"));
                logger.info("Client trouvé : {}", client.getNomClient());
                return client;
            } else {
                logger.warn("Aucun client trouvé avec numClient = {}", numClient);
                return null;
            }
        }
    }

    public void ajouterCompte(Compte compte) throws SQLException {
        logger.debug("Tentative d'ajout du compte : {}", compte.getNumCompte());
        String sql = "INSERT INTO Compte (numCompte, solde, typeCompte) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (compteExisteDeja(compte.getNumCompte())) {
                logger.warn("Compte déjà existant : {}", compte.getNumCompte());
                throw new SQLException("Compte déjà existant !");
            }

            connection.setAutoCommit(false);
            pstmt.setInt(1, compte.getNumCompte());
            pstmt.setDouble(2, compte.getSolde());
            pstmt.setString(3, compte.getTypeCompte());

            int rowsAffected = pstmt.executeUpdate();
            connection.commit();
            logger.info("Compte {} ajouté avec succès. Lignes affectées : {}", compte.getNumCompte(), rowsAffected);

        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout du compte {} : {}", compte.getNumCompte(), e.getMessage());
            throw e;
        }
    }

    private boolean compteExisteDeja(int numCompte) throws SQLException {
        String sql = "SELECT 1 FROM Compte WHERE numCompte = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numCompte);
            return pstmt.executeQuery().next();
        }
    }

    public double obtenirSoldeCompte(int numCompte) throws SQLException {
        logger.debug("Consultation du solde du compte : {}", numCompte);
        String sql = "SELECT solde FROM Compte WHERE numCompte = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numCompte);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double solde = rs.getDouble("solde");
                logger.info("Solde du compte {} : {}", numCompte, solde);
                return solde;
            } else {
                logger.warn("Compte introuvable : {}", numCompte);
                return 0;
            }
        }
    }

    public void ajouterOperation(Operation operation) throws SQLException {
        logger.debug("Tentative d'ajout de l'opération : {}", operation.getNumOperation());
        String sql = "INSERT INTO Operation (numOperation, dateOperation, typeOperation, montant, numCompte) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (operationExisteDeja(operation.getNumOperation())) {
                logger.warn("Opération déjà existante : {}", operation.getNumOperation());
                throw new SQLException("Opération déjà existante !");
            }

            connection.setAutoCommit(false);
            pstmt.setInt(1, operation.getNumOperation());
            pstmt.setDate(2, operation.getDateOperation());
            pstmt.setString(3, operation.getTypeOperation());
            pstmt.setDouble(4, operation.getMontant());
            pstmt.setInt(5, operation.getNumCompte());

            int rowsAffected = pstmt.executeUpdate();
            connection.commit();
            logger.info("Opération {} ajoutée avec succès. Lignes affectées : {}", operation.getNumOperation(), rowsAffected);

        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout de l'opération {} : {}", operation.getNumOperation(), e.getMessage());
            throw e;
        }
    }

    private boolean operationExisteDeja(int numOperation) throws SQLException {
        String sql = "SELECT 1 FROM Operation WHERE numOperation = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numOperation);
            return pstmt.executeQuery().next();
        }
    }

    public List<Operation> obtenirOperationsParCompte(int numCompte) throws SQLException {
        logger.debug("Récupération des opérations du compte : {}", numCompte);
        List<Operation> liste = new ArrayList<>();
        String sql = "SELECT * FROM Operation WHERE numCompte = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numCompte);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Operation op = new Operation(rs.getInt("numOperation"), rs.getDate("dateOperation"), rs.getString("typeOperation"), rs.getDouble("montant"), rs.getInt("numCompte"));
                liste.add(op);
            }
        }
        logger.info("Nombre d'opérations pour le compte {} : {}", numCompte, liste.size());
        return liste;
    }

    public void ajouterCompteClient(int numCompte, int numClient) throws SQLException {
        logger.debug("Ajout du compte {} au client {}", numCompte, numClient);
        String sql = "INSERT INTO Compte_Client (numCompte, numClient) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            pstmt.setInt(1, numCompte);
            pstmt.setInt(2, numClient);

            int rowsAffected = pstmt.executeUpdate();
            connection.commit();
            logger.info("Compte {} associé au client {}. Lignes affectées : {}", numCompte, numClient, rowsAffected);

        } catch (SQLException e) {
            logger.error("Erreur lors de l'association du compte {} au client {} : {}", numCompte, numClient, e.getMessage());
            throw e;
        }
    }

    public List<Compte> obtenirComptesParClient(int numClient) throws SQLException {
        logger.debug("Récupération des comptes du client : {}", numClient);
        List<Compte> liste = new ArrayList<>();
        String sql = "SELECT c.numCompte, c.solde, c.typeCompte FROM Compte c JOIN Compte_Client cc ON c.numCompte = cc.numCompte WHERE cc.numClient = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numClient);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                liste.add(new Compte(rs.getInt("numCompte"), rs.getDouble("solde"), rs.getString("typeCompte")));
            }
        }
        logger.info("Nombre de comptes récupérés pour le client {} : {}", numClient, liste.size());
        return liste;
    }

    public List<Client> obtenirClientsParAgent(int numAgent) throws SQLException {
        logger.debug("Récupération des clients de l'agent : {}", numAgent);
        List<Client> listeClients = new ArrayList<>();
        String sql = "SELECT numClient, nomClient, prenomClient, adClient, dateNaissClient, numAgent FROM Client WHERE numAgent = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, numAgent);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Client client = new Client(rs.getInt("numClient"), rs.getString("nomClient"), rs.getString("prenomClient"), rs.getString("adClient"), rs.getDate("dateNaissClient"), rs.getInt("numAgent"));
                listeClients.add(client);
            }
        }
        logger.info("Nombre de clients récupérés pour l'agent {} : {}", numAgent, listeClients.size());
        return listeClients;
    }
}