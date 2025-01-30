package camisetas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LectorCamisetas {

	public static void main(String[] args) {

		String rutaArchivo = "src\\ficheros\\camisetas.txt";
		String rutaArchivoSinErrores = "src\\ficheros\\camisetas_finales.txt";
		String rutaArchivoConErrores = "src\\ficheros\\camisetas_con_errores.log";

		int lineasAnalizadas = 0;
		int lineasError = 0;
		StringBuilder reporteErrores = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
			 BufferedWriter bwSinError = new BufferedWriter(new FileWriter(rutaArchivoSinErrores));
			 BufferedWriter bwConError = new BufferedWriter(new FileWriter(rutaArchivoConErrores))) {

			String linea;
			while ((linea = br.readLine()) != null) {
				lineasAnalizadas++;				
				if (!procesarLinea(linea, bwSinError, reporteErrores)) {
					lineasError++;
				}
			}
			
			bwConError.write("total líneas analizadas: " + lineasAnalizadas + "\n");
			bwConError.write("Total líneas eliminadas: " + lineasError + "\n");
			bwConError.write("Las líneas eliminadas son:\n");
            bwConError.write(reporteErrores.toString());
            
			System.out.printf("Lineas analizadas: %d\n", lineasAnalizadas);
			System.out.printf("Lineas eliminadas: %d\n", lineasError);

		} catch (IOException e) {
			System.out.println("Error al procesar archivo" + e.getMessage());
		}
	}

	private static boolean procesarLinea(String linea, BufferedWriter bwSinError, StringBuilder reporteErrores) throws IOException{
		String[] campos = linea.split(",");		
		int numeroLinea = Integer.parseInt(campos[0].trim());
		int cantidad = Integer.parseInt(campos[1].trim());
		String color = campos[2].trim();
		String marca = campos[3].trim();
		String modelo = campos[4].trim();
		String talla = campos[5].trim();
			
		if (!validaCampo(campos)) {
			System.out.printf("ERROR: linea nº[%d] con campo vacío.\n", Integer.parseInt(campos[0].trim()));
			reporteErrores.append(linea).append("\n");
			return false;
		}
		
		if (campos.length == 6) {		
			bwSinError.write(linea.replace("á", "a").replace("ú", "u").replace("ó", "o").toLowerCase() + "\n");
			System.out.printf("Linea nº[%d], cantidad:[%d], color:[%s], marca:[%s], modelo:[%s], talla:[%s].\n",
					numeroLinea, cantidad, color, marca, modelo, talla);
			return true;
		} else {			
			System.out.printf("ERROR: linea nº[%d] con formato incorrecto.\n", numeroLinea);
			reporteErrores.append(linea).append("\n");
			return false;
		}
	}

	private static boolean validaCampo(String[] campos) {
		for (String campo : campos) {
				if (campo.trim().isEmpty()) {
					return false;
				}
			}
			return true;		
	}
}