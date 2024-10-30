package consoleApp;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class ProcessFile {

    public static ArrayList<Punto> reader(String path) {
        ArrayList<Punto> puntos = new ArrayList<>();

        try (FileReader r = new FileReader(path);
             BufferedReader b = new BufferedReader(r)) {
            // salto las 3 primeras lineas
            for (int i = 0; i < 3; i++) {
                b.readLine();
            }

            // leo la dimension y la formateo
            String dimensionWOFormat = b.readLine();
            String[] step = dimensionWOFormat.split(": ");
            int dimension = Integer.parseInt(step[1]);

            // saltamos las 2 siguientes lineas
            for (int i = 0; i < 2; i++) {
                b.readLine();
            }

            // leemos el fichero y almacenamos en el array
            for (int i = 0; i < dimension; i++) {
                Punto p = new Punto();
                String pointWOFormat = b.readLine();
                String[] steps = pointWOFormat.split(" ");
                p.setId(Integer.parseInt(steps[0]));
                p.setX(Double.parseDouble(steps[1]));
                p.setY(Double.parseDouble(steps[2]));
                puntos.add(p);
            }

        } catch (Exception ex) {
            System.out.println("ERROR a la hora de leer el archivo con direccion " + path + " ->  " + ex.getMessage());
        }

        return puntos;
    }

    public static void createFile(int dimension) {
        try {
            // definicion del nombre del archivo
            Random numRandom = new Random(System.nanoTime());
            String nomFichero = "dataset" + dimension + ".tsp";
            File ficheroAleatorio = new File(nomFichero);

            // creamos el fileWriter y metemos el nombre
            FileWriter fichero = new FileWriter(nomFichero);
            fichero.write("NAME: " + nomFichero + "\n");

            // agrego el resto de cabeceras para que mantenga el mismo formato
            fichero.write("TYPE: TSP \nCOMMENT: " + dimension + "\nDIMENSION: " + dimension + "\nEDGE_WEIGHT_TYPE: NAN\nNODE_COORD_SECTION\n");

            // iremos escribiendo en el fichero los datos que generemos aleatoriamente
            for (int i = 0; i < dimension; i++) {
                double x = numRandom.nextDouble(500 - 0 + 1);
                double y = numRandom.nextDouble(500 - 0 + 1);
                fichero.write(i + 1 + " " + Math.round(x * 1e10) / 1e10 + " " + Math.round(y * 1e10) / 1e10 + "\n");
            }

            // indicamos el final del fichero
            fichero.write("EOF");
            fichero.close();
            System.out.println("Fichero creado satisfactoriamente");

        } catch (Exception e) {
            System.out.println("No se ha podido crear el nuevo fichero: " + e.getMessage());
        }
    }

    public static void fileXArray(ArrayList<Punto> t, String algoritmo) {
        String nomFichero = algoritmo + ".tsp";

        try {
            File generaFichero = new File(nomFichero);
            FileWriter fichero = new FileWriter(generaFichero);

            for(Punto p : t) {
                fichero.write(p.getId() + " " + p.getX() + " " + p.getY() + "\n");
            }

            fichero.write("EOF\n");
            fichero.close();

        } catch (Exception ex) {
            System.out.println("No se ha podido crear el fichero del algoritmo " + algoritmo + ": " + ex.getMessage());
        }
    }

    public static void createSizeFile(double []tiempos, String algoritmo, int tallaInicial, int incremento) {
        String nomFichero = algoritmo + ".dat";

        try {
            File generaFichero = new File(nomFichero);
            FileWriter fichero = new FileWriter(generaFichero);

            fichero.write(" Talla        Tiempo\n");
            for (double tiempo : tiempos) {
                fichero.write(" " + tallaInicial + "        " + tiempo / 1e6 + "\n");
                tallaInicial += incremento;
            }

            fichero.close();

        }catch (Exception e) {
            System.out.println("No se ha podido crear " + nomFichero + ": " + e.getMessage());
        }
    }

    public static void createFileWorstCase(int dimension) {
        try {
            // definicion del nombre del archivo
            Random numRandom = new Random(System.nanoTime());
            Random rand = new Random();
            String nomFichero = "dataset" + dimension + ".tsp";
            File ficheroAleatorio = new File(nomFichero);

            // creamos el fileWriter y metemos el nombre
            FileWriter fichero = new FileWriter(nomFichero);
            fichero.write("NAME: " + nomFichero + "\n");

            // agrego el resto de cabeceras para que mantenga el mismo formato
            fichero.write("TYPE: TSP \nCOMMENT: " + dimension + "\nDIMENSION: " + dimension + "\nEDGE_WEIGHT_TYPE: NAN\nNODE_COORD_SECTION\n");

            // genero la variable x para que todos los puntos tengan la misma coordenada x
            double x = numRandom.nextDouble(500 - 0 + 1);

            // iremos escribiendo en el fichero la coordenada x con las disintas coordenadas y, teniendo un control de que no se escriban en la misma y para forzar el pero caso
            for (int i = 0; i < dimension; i++) {
                int aux1 = rand.nextInt(1000) + 7;
                double y = aux1 / ((double)i + 1 + i * 0.100);
                int num = rand.nextInt(3);
                y += (i % 500) - num * (rand.nextInt(100)); //Ajuste adicional para evitar repeticiones
                fichero.write(i + 1 + " " + Math.round(x * 1e10) / 1e10 + " " + Math.round(y * 1e10) / 1e10 + "\n");
            }

            // indicamos el final del fichero
            fichero.write("EOF");
            fichero.close();
            System.out.println("Fichero creado satisfactoriamente");

        } catch (Exception e) {
            System.out.println("No se ha podido crear el nuevo fichero: " + e.getMessage());
        }
    }
}
