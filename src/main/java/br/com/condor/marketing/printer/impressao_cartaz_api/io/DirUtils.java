package br.com.condor.marketing.printer.impressao_cartaz_api.io;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DirUtils {

    private DirUtils() {
    }

    public static String MKT_PRINTER_STR_DIR;
    // public static final String MKT_PRINTER_STR_DIR = "//10.1.1.19/mkt-printer/";
    // public static final String MKT_PRINTER_STR_DIR = "C:/Users/Carlos.Godoi/Desktop/mkt-printer/";

    public static int MKT_PRINTER_STR_LEN;
    // public static final int MKT_PRINTER_STR_LEN = MKT_PRINTER_STR_DIR.length();

    public static Path MKT_PRINTER_PATH_DIR;
    // public static final Path MKT_PRINTER_PATH_DIR = Paths.get(MKT_PRINTER_STR_DIR);

    @Value("${project.path}")
    public void setMktStr(final String value) {
        MKT_PRINTER_STR_DIR = value;

        MKT_PRINTER_STR_LEN = MKT_PRINTER_STR_DIR.length();
        MKT_PRINTER_PATH_DIR = Paths.get(MKT_PRINTER_STR_DIR);
    }
}
