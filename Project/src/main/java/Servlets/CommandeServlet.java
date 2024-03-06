package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import Class.Commande;
import DAOImplement.CommandeDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/Commande", name = "Commande")
public class CommandeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(true);
        List<String> panier = (List<String>) session.getAttribute("panier");
        String Nom = (String) session.getAttribute("nom");
        List<Commande> commandes = new CommandeDaoImpl().getCommandesByNomUtilisateur(Nom);

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>Commandes</title>");
        out.println("<link rel='stylesheet' type='text/css' href='View/Css/Commandestyle.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"container\">");
        out.println("   <h1>Bienvenue <span id=\"nom_personne\">" + Nom +"</span> dans la page de commande</h1>");
        out.println("<div class=\"commandes\">");
        out.println("<h2>Vos commandes</h2>");
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Nom du produit</th>");
        out.println("<th>Prix du produit</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        for (Commande commande : commandes) {
            String produitsString = commande.getNom_prod();
            String[] produitsArray = produitsString.replaceAll("[\\[\\]]", "").split(", (?=Produit)");

            for (String produit : produitsArray) {
                String nomProduit = produit.split("nom_produit='")[1].split("'")[0];
                double prixProduit = Double.parseDouble(produit.split("prix=")[1].split("}")[0]);

                out.println("<tr>");
                out.println("<td>" + nomProduit + "</td>");
                out.println("<td>" + prixProduit + "</td>");
                out.println("</tr>");
            }
        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
