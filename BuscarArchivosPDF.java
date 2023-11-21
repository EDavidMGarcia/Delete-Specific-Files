import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;

public class BuscarArchivosPDF {

    public static void main(String[] args) {
        String carpetaOrigen = "URL donde se encuentran los documentos";
        String archivoListaPDF = "URL donde se encuentra la lista_pdf.txt";

        List<String> nombresPDF = new ArrayList<>();
        
        try {
            nombresPDF = Files.readAllLines(Paths.get(archivoListaPDF));

            buscarYBorrarPDFEnSubcarpetas(carpetaOrigen, nombresPDF);

            System.out.println("Proceso completado.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String nombrePDF : nombresPDF) {
            System.out.println("No se encontró el archivo: " + nombrePDF);
        }
    }

    private static void buscarYBorrarPDFEnSubcarpetas(String carpetaActual, List<String> nombresPDF) throws IOException {
        File[] archivos = new File(carpetaActual).listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    buscarYBorrarPDFEnSubcarpetas(archivo.getAbsolutePath(), nombresPDF);
                } else {
                    if (archivo.getName().toLowerCase().endsWith(".pdf")) {
                        String nombrePDF = archivo.getName();
                        if (nombresPDF.contains(nombrePDF)) {
                            Files.delete(archivo.toPath());
                            System.out.println("Se borró " + nombrePDF);
                            nombresPDF.remove(nombrePDF);
                        }
                    }
                }
            }
        }
    }
}
