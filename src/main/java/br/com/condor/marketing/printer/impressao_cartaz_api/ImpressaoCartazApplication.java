package br.com.condor.marketing.printer.impressao_cartaz_api;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories("br.com.condor.marketing.printer.impressao_cartaz_api")
@EnableTransactionManagement
public class ImpressaoCartazApplication {

    private static final Logger logger = LoggerFactory.getLogger(ImpressaoCartazApplication.class);

    public static void main(String[] args) {
        if (StringUtils.isNotBlank(System.getProperty("http.proxyHost")))
            logger.info("Configuração de proxy - HTTP: " + System.getProperty("http.proxyHost") + ":" + System.getProperty("http.proxyPort"));
        if (StringUtils.isNotBlank(System.getProperty("https.proxyHost")))
            logger.info("Configuração de proxy - HTTPS: " + System.getProperty("https.proxyHost") + ":" + System.getProperty("https.proxyPort"));

        if (args != null && args.length != 0) {
            logger.info("Inicializando com os argumentos:");
            for (String string : args)
                logger.info(string);
        }

        final ConfigurableApplicationContext ctx = SpringApplication.run(ImpressaoCartazApplication.class, args);
        try {
            logger.info("---------------------------------------- Versão " + ctx.getEnvironment().getProperty("version") + " ----------------------------------------");
        } catch (Exception e) {
            logger.error("Erro ao inicializar aplicação. Erro: {} - Stacktrace: {}", e.getMessage(), ExceptionUtils.getStackTrace(e));
            ctx.close();
        }
    }
}
