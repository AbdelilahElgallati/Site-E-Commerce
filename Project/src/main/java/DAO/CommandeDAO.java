package DAO;

import java.util.List;
import Class.Commande;

public interface CommandeDAO {
    public void AddCommande(Commande c);
    public List<Commande> getCommandesByNomUtilisateur(String nomUtilisateur);
}
