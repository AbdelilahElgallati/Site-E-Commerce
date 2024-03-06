package Servlets;

import Class.Commande;
import DAOImplement.CommandeDaoImpl;
import Class.Produit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/Panier", name = "Panier")
public class PanierServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(true);
        List<Produit> panier = (List<Produit>) session.getAttribute("panier");
        if(panier == null) {
            panier = new ArrayList<Produit>();
        }
        session.setAttribute("panier", panier);
        String Nom = (String) session.getAttribute("nom");
        int nombreArticles = panier.size();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>Page de panier</title>");
        out.println("<link rel='stylesheet' type='text/css' href='View/Css/Panierstyle.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"container\">");
        out.println("<h1>Bienvenue <span id=\"nom_personne\">" + Nom +"</span>, dans ma servlet d'achat</h1>");
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Informations du disque commandé</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if (panier.isEmpty()) {
            out.println("<tr>");
            out.println("<td>Votre panier est vide</td>");
            out.println("</tr>");
        } else {
            for (Produit article : panier) {
                out.println("<tr>");
                out.println("<td>" + article.getNom_produit() + "</td>");
                out.println("</tr>");
            }
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("<div class=\"actions-container\">\n");
        if (!panier.isEmpty()) {
            out.println("   <h2>Votre panier contient : " + nombreArticles + " disques</h2>");
        }
        out.println("   <div class ='actions'>");
        out.println("       <a class='custom-link' href='/Project_war_exploded/Home'>Commander un autre disque</a>");
        if (!panier.isEmpty()) {
            out.println("       <form action='' method='post'>");
            out.println("           <button type='submit'>Enregistrer votre commande</button>");
            out.println("       </form>");
        }
        out.println("   </div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        List<Produit> panier = (List<Produit>) session.getAttribute("panier");
        String Nom = (String) session.getAttribute("nom");
        int somme =0 ;
        for (Produit article : panier){
            somme = somme + (int) article.getPrix();
        }
        Commande c = new Commande();
        c.setNom_user(Nom);
        c.setNom_prod(panier.toString());
        c.setPrix_total(somme);
        CommandeDaoImpl CD = new CommandeDaoImpl();
        CD.AddCommande(c);
        resp.sendRedirect(req.getContextPath() + "/Commande");
    }
}