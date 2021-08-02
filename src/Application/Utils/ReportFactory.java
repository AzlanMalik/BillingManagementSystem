package Application.Utils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class ReportFactory {

    private static String singleInvoice = "F:\\Invoice_Table_Based.jrxml";
    private static String doubleInvoice = "";

    private static JasperReport singleInvoiceCompiled;
    private static JasperReport doubleInvoiceCompiled;

    private static String outputFile = "F:\\" + "JasperReportExample.pdf";


    public static JasperReport compileReport(String fileName){
        JasperReport jasperReport = null;
        InputStream input = null;
        try {
            input = new FileInputStream(new File(fileName));

            JasperDesign jasperDesign = JRXmlLoader.load(input);

            jasperReport = JasperCompileManager.compileReport(jasperDesign);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        }
        return jasperReport;
    }


    public static String getSingleInvoice() {
        return singleInvoice;
    }

    public static void setSingleInvoice(String singleInvoice) {
        ReportFactory.singleInvoice = singleInvoice;
        ReportFactory.singleInvoiceCompiled = compileReport(singleInvoice);
    }

    public static String getDoubleInvoice() {
        return doubleInvoice;
    }

    public static void setDoubleInvoice(String doubleInvoice) {
        ReportFactory.doubleInvoice = doubleInvoice;
        ReportFactory.doubleInvoiceCompiled = compileReport(doubleInvoice);
    }


    public static JasperReport getSingleInvoiceCompiled() {
        return singleInvoiceCompiled;
    }

    public static void setSingleInvoiceCompiled(JasperReport singleInvoiceCompiled) {
        ReportFactory.singleInvoiceCompiled = singleInvoiceCompiled;
    }

    public static JasperReport getDoubleInvoiceCompiled() {
        return doubleInvoiceCompiled;
    }

    public static void setDoubleInvoiceCompiled(JasperReport doubleInvoiceCompiled) {
        ReportFactory.doubleInvoiceCompiled = doubleInvoiceCompiled;
    }
}
