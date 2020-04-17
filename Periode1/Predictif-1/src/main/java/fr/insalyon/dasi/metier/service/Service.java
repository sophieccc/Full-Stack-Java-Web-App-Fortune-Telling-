package fr.insalyon.dasi.metier.service;

import fr.insalyon.dasi.dao.ClientDao;
import fr.insalyon.dasi.dao.ConsultationDao;
import fr.insalyon.dasi.dao.EmployeDao;
import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.dao.MediumDao;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.technique.service.AstroTest;
import fr.insalyon.dasi.technique.service.Message;
import fr.insalyon.dasi.technique.service.Statistics;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DASI Team
 */
public class Service {

    protected ClientDao clientDao = new ClientDao();
    protected EmployeDao employeDao = new EmployeDao();
    protected ConsultationDao consultationDao = new ConsultationDao();
    protected MediumDao mediumDao = new MediumDao();

    public Long inscrireClient(Client client) {
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            clientDao.creer(client);
            JpaUtil.validerTransaction();
            resultat = client.getId();
            Message.envoyerMail("Predictif", client.getMail(), "Inscription réussie", "yay");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service inscrireClient(client)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
            Message.envoyerMail("Predictif", client.getMail(), "Inscription refusée", "rip");
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Long creerConsultation(Consultation c) {
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            consultationDao.creer(c);
            JpaUtil.validerTransaction();
            resultat = c.getId();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service creerConsultation(c)");
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Client rechercherClientParId(Long id) {
        Client resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = clientDao.chercherParId(id);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service rechercherClientParId(id)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Employe rechercherEmployeParId(Long id) {
        Employe resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = employeDao.chercherParId(id);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service rechercherEmployeParId(id)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public Client authentifierClient(String mail, String motDePasse) {
        Client resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche du client
            Client client = clientDao.chercherParMail(mail);
            if (client != null) {
                // Vérification du mot de passe
                if (client.getMotDePasse().equals(motDePasse)) {
                    resultat = client;
                }
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service authentifierClient(mail,motDePasse)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Employe authentifierEmploye(String mail, String motDePasse) 
    {
        Employe resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            // Recherche du client
            Employe employe = employeDao.chercherParMail(mail);
            if (employe != null) {
                // Vérification du mot de passe
                if (employe.getMotDePasse().equals(motDePasse)) {
                    resultat = employe;
                }
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service authentifierClient(mail,motDePasse)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public List<Client> listerClients() {
        List<Client> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = clientDao.listerClients();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service listerClients()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public List<Medium> filterMediums(String type) {
        JpaUtil.creerContextePersistance();
        List<Medium> resultat=null;
        try {
            switch(type){
                case "Cartomancien":
                    resultat  = mediumDao.listerCartomanciens();
                    break;
                case "Astrologue" :
                    resultat  = mediumDao.listerAstrologues();
                    break;
                case "Spirite" :
                    resultat  = mediumDao.listerSpirites();
                    break;
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service listerMediums()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    
    public List<Medium> listerMediums() {
        List<Medium> resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = mediumDao.listerMediums();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service listerClients()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Medium chercherMedium(String denomination) {
        JpaUtil.creerContextePersistance();
        Medium resultat = null;
        try {
            // Recherche du médium
            Medium medium = mediumDao.chercherParDenomination(denomination);
            if (medium != null) {
                resultat = medium;
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service chercherMedium(String denomination)", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Employe choisirEmploye(String genre){
        Employe resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = employeDao.chercherParGenre(genre);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service choisirEmploye()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public Medium demanderConsultation(Long mediumId) { //identifiant du medium choisi
        Medium resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            resultat = mediumDao.chercherParId(mediumId);
            Employe e = choisirEmploye(resultat.getGenre());
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service chercherParId()", ex);
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }
    
    public void confirmConsultation(Consultation c) {
        // send text to client, need client and medium for this
        c.setHeureDebut(Timestamp.valueOf(LocalDateTime.MIN).toString());
    }
    
    public void terminerConsultation(Consultation c) {
        // send text to client, need client and medium for this
        c.setHeureFin(Timestamp.valueOf(LocalDateTime.MIN).toString());
    }
    
    public List<String> generatePrediction(Client c, int amour, int sante, int travail) 
    {
        List<String> result = null;
        AstroTest astro = new AstroTest();
        try {
            result=astro.getPredictions(c.getProfil().getCouleurBonheur(), 
                    c.getProfil().getAnimalTotem(), amour, sante, travail);
        } catch (IOException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void ajouterCommentaire(Consultation c, String comment) 
    {
        c.setCommentaire(comment);
    }
    
    public void showConsultation(Consultation c) { 
        System.out.println(c.toString());
    }
    
    // methods like this can be changed in future to just return a list 
    // and then in the IHM can process them
    
    public void showClientConsultations(Client c) { 
        List<Consultation> consultations = c.getConsultations();
        for(int i=0; i < c.getConsultations().size(); i++) {
            System.out.println(consultations.get(i).toString());
        }
    }
    
    public void showMediums(List<Medium> mediums) {
        for(int i=0; i < mediums.size(); i++) {
            System.out.println(mediums.get(i).toString());
        }
    }
  
    public void showProfilAstral(Client c) {
        System.out.println(c.getProfil().toString());
    }
    
    public void statistics() 
    {
        Statistics stats = null;
        List<Medium> top5 = mediumDao.listerTop5();
        stats.setTop5(top5);
        SortedMap<Integer, Medium> consultationsParMedium = null;
        List<Medium> mediumList = mediumDao.listerMediums();
        for (Medium m : mediumList)
        {
            consultationsParMedium.put(m.getNbConsultations(), m);
        }
        stats.setConsultationsParMedium(consultationsParMedium);
        SortedMap<Integer, Employe> clientsParEmploye = null;
        List<Employe> employeList=employeDao.listerEmployes();
        for (Employe e : employeList)
        {
            clientsParEmploye.put(e.getNbConsultations(), e);
        }
        stats.setClientsParEmploye(clientsParEmploye);
    } 
}