package servlets;

import config.ThymeLeafListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name="Servlet", value="/llamar")
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeLeafListener.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        HttpSession session = req.getSession();
        String cS;
        if (req.getParameter("colorS") != null && !req.getParameter("colorS").isBlank()){
            cS = req.getParameter("colorS");
            session.setAttribute("colorSesion",cS);
            context.setVariable("colorSesion",cS);
        }
        String cG =(String) getServletContext().getAttribute("colorG");
        if (req.getParameter("colorG") != null && !req.getParameter("colorG").isBlank()){
            cG = req.getParameter("colorG");
            getServletContext().setAttribute("colorG",cG);
        }
        context.setVariable("colorGloval",cG);
        templateEngine.process("colores",context,resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
