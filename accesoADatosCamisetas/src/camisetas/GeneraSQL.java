package camisetas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GeneraSQL {
	
	public static void main(String[] args) {
		
		String rutaArchivoLectura = "src\\ficheros\\camisetas_finales.txt";
		String rutaSQL = "src\\ficheros\\camisetas.sql";
		
		try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivoLectura));
			 BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSQL))) {
			
			bw.write("INSERT INTO camisetas (cantidad, color, marca, modelo, talla) VALUES\n");
			
			String linea;
			boolean primeraLinea = true;
			while ((linea = br.readLine()) != null) {
				String[] campos = linea.split(",");
				
				if (campos.length >=5) {
					
					int cantidad = Integer.parseInt(campos[1].trim());
					String color = campos[2].trim();
					String marca = campos[3].trim();
					String modelo = campos[4].trim();
					String talla = campos[5].trim();
					
					String sql = String.format("(%d, '%s', '%s', '%s', '%s')", cantidad, color, marca, modelo, talla);
					
					
					if(!primeraLinea) {
						bw.write(",\n");
					} else {
						primeraLinea = false;
					}
					bw.write(sql);
				} else {
					System.out.println("Linea [[" + linea + "]] no válida.");
				}								
			}
			 bw.write(";\n");
	         System.out.println("Archivo SQL generado: " + rutaSQL);
			
			
		} catch (IOException e) {
			System.out.println("Error al procesar los archivos: " + e.getMessage());
		}		
	}
}
