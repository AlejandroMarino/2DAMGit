package org.example.jakarta.servlets;

import common.Constantes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.jakarta.listeners.ThymeLeafListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.Random;

@WebServlet(name = "respuesta", value = {"/a", "/enviar"})
public class Respuesta extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeLeafListener.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        HttpSession session = req.getSession();
        Integer contador = (Integer) session.getAttribute(Constantes.CONTADOR);
        if (contador == null) {
            contador = 0;
        }
        Integer random = (Integer) session.getAttribute(Constantes.RANDOM);
        if (random == null) {
            random = new Random().nextInt(99) + 1;
        }

        String template;
        int numero;

        if (req.getParameter(Constantes.NUMERO) != null && !req.getParameter(Constantes.NUMERO).isEmpty()) {
            numero = Integer.parseInt(req.getParameter(Constantes.NUMERO));
            req.getSession().setAttribute(Constantes.NUMERO, numero);
            if (numero == random) {
                contador ++;
                context.setVariable(Constantes.MENSAJE, Constantes.HAS_ACERTADO_EN + contador + Constantes.INTENTOS);
                contador = 0;
                random = new Random().nextInt(99) + 1;
                context.setVariable(Constantes.VOLVER, Constantes.VOLVER_A_JUGAR);
                template = Constantes.RESULTADO;
            } else if (numero > random) {
                contador ++;
                context.setVariable(Constantes.MENSAJE, Constantes.EL_NUMERO_ES_MENOR_QUE + numero + Constantes.LLEVAS + contador + Constantes.INTENTOS);
                context.setVariable(Constantes.VOLVER, Constantes.VOLVER_A_INTENTARLO);
                template = Constantes.RESULTADO;
            } else {
                contador ++;
                context.setVariable(Constantes.MENSAJE, Constantes.EL_NUMERO_ES_MAYOR_QUE + numero + Constantes.LLEVAS + contador + Constantes.INTENTOS);
                context.setVariable(Constantes.VOLVER, Constantes.VOLVER_A_INTENTARLO);
                template = Constantes.RESULTADO;
            }
        }
        else {
            context.setVariable(Constantes.ERROR, Constantes.NUMERO_NO_VALIDO);

            template = Constantes.ERROR;
        }
        session.setAttribute(Constantes.RANDOM, random);
        session.setAttribute(Constantes.CONTADOR, contador);
        templateEngine.process(template, context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
