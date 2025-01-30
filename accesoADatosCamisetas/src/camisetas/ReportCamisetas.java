package camisetas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ReportCamisetas {

	public static void main(String[] args) throws IOException {

		String rutaArchivo = "src\\ficheros\\camisetas_sin_errores.txt";
		String rutaReport = "src\\ficheros\\camisetas_frecuencias.log";

		Map<Integer, Integer> cantidadFrecuencia = new HashMap<>();
		Map<String, Integer> colorFrecuencia = new HashMap<>();
		Map<String, Integer> marcaFrecuencia = new HashMap<>();
		Map<String, Integer> modeloFrecuencia = new HashMap<>();
		Map<String, Integer> tallaFrecuencia = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {

			String linea;
			try {
				while ((linea = br.readLine()) != null) {
					String[] campos = linea.split(",");
					if (campos.length >= 6) {
						int cantidad = Integer.parseInt(campos[1].trim());
						String color = campos[2].trim().replace("á", "a").replace("ú", "u").replace("ó", "o").toLowerCase();
						String marca = campos[3].trim().toLowerCase();
						String modelo = campos[4].trim().toLowerCase();
						String talla = campos[5].trim().toLowerCase();

						cantidadFrecuencia.compute(cantidad, (key, val) -> (val == null) ? 1 : val + 1);
						colorFrecuencia.compute(color, (key, val) -> (val == null) ? 1 : val + 1);
						marcaFrecuencia.compute(marca, (key, val) -> (val == null) ? 1 : val + 1);
						modeloFrecuencia.compute(modelo, (key, val) -> (val == null) ? 1 : val + 1);
						tallaFrecuencia.compute(talla, (key, val) -> (val == null) ? 1 : val + 1);
					} else {
						System.out.println("Línea no válida: " + linea);
					}
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try (BufferedWriter bwReporte = new BufferedWriter(new FileWriter(rutaReport))) {
				mostrarFrecuenciaOrdenada("[[Cantidad]]", cantidadFrecuencia, bwReporte);
				mostrarFrecuenciaOrdenada("[[Color]]", colorFrecuencia, bwReporte);
				mostrarFrecuenciaOrdenada("[[Marca]]", marcaFrecuencia, bwReporte);
				mostrarFrecuenciaOrdenada("[[Modelo]]", modeloFrecuencia, bwReporte);
				mostrarFrecuenciaOrdenada("[[Talla]]", tallaFrecuencia, bwReporte);

			} catch (IOException e) {
				System.out.println("Error de escritura en reportes: " + e.getMessage());
			}
		}
	}

	private static <clave extends Comparable<clave>> void mostrarFrecuenciaOrdenada(String campo, Map<clave, Integer> frecuencia, BufferedWriter bw) throws IOException {
		bw.write(campo + ":\n");
		Map<clave, Integer> frecuenciaOrdenada = new TreeMap<>(frecuencia);
		for (Map.Entry<clave, Integer> entrada : frecuenciaOrdenada.entrySet()) {
			bw.write(entrada.getKey() + ": " + entrada.getValue() + "\n");
			System.out.printf("%s: %d\n", entrada.getKey(), entrada.getValue());
		}
		bw.write("\n");
	}

}
