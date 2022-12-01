package config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;


@WebListener
public class ThymeLeafListener implements ServletContextListener {

    public static final String TEMPLATE_ENGINE_ATTR = "thymeleaf.TemplateEngineInstance";
    private ITemplateEngine templateEngine;
    private JakartaServletWebApplication application;

    public ThymeLeafListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.application = JakartaServletWebApplication.buildApplication(sce.getServletContext());
        this.templateEngine = templateEngine(this.application);

        sce.getServletContext().setAttribute(TEMPLATE_ENGINE_ATTR, templateEngine);
        String colorGloval = "blue";
        sce.getServletContext().setAttribute("colorG",colorGloval);
    }

    private ITemplateEngine templateEngine(IWebApplication application) {
        TemplateEngine templateEngine = new TemplateEngine();

        WebApplicationTemplateResolver templateResolver = templateResolver(application);
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    private WebApplicationTemplateResolver templateResolver(IWebApplication application) {
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));

        templateResolver.setCacheable(true);

        return templateResolver;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

