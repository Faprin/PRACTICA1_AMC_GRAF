package consoleApp;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class App {

    public static void menu() {
        System.out.println(" ------- Menu ------- ");
        System.out.println("1. Crear un dataset aleatorio");
        System.out.println("2. Cargar un dataset en memoria");
        System.out.println("3. Comprobar estrategias");
        System.out.println("4. Comparar todas las estrategias");
        System.out.println("5, Comparar dos estrategias");

        System.out.println("9. Salir");

        System.out.print(">>  ");

    }

    public static void comparar2EstrategiasMenu() {

        System.out.println(" ------- Comparar 2 Estrategias ------- ");
        System.out.println("1. Exhaustivo");
        System.out.println("2. Exhaustivo-Poda");
        System.out.println("3. Divide Y Venceras");
        System.out.println("4. Divide y Venceras Mejorado");
        System.out.println("5. Volver al menu principal (Poner 2 veces)");

        System.out.println(" ");
    }

    public static ArrayList<Punto> copiaArray(ArrayList<Punto> origen) {
        ArrayList destino = new ArrayList();
        for (int i = 0; i < origen.size(); i++) {
            destino.add(origen.get(i));
        }

        return destino;
    }

    public static ArrayList<Punto> generaDatasetPorTalla(int talla) {
        Random random = new Random(System.nanoTime());
        ArrayList<Punto> datos = new ArrayList();

        for (int i = 0; i < talla; i++) {
            datos.add(new Punto());
            datos.get(i).setId(i + 1);
            datos.get(i).setX(random.nextDouble(500 - 0 + 1));
            datos.get(i).setY(random.nextDouble(500 - 0 + 1));
        }

        return datos;
    }

    public static String obtenerNombreAlgoritmo(int algoritmo) {
        return switch (algoritmo) {
            case 1 -> "Exhaustivo";
            case 2 -> "Exhaustivo Poda";
            case 3 -> "Divide y Vencerás";
            case 4 -> "DyV Mejorado";
            default -> "Desconocido";
        };
    }


    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int eleccion = 0;
        String path = "datasets/ch130.tsp";

        for (int i = 0; i < 50; i++) {
            System.out.println(" ");
        }

        do {
            menu();
            eleccion = in.nextInt();

            switch (eleccion) {
                case 1 -> {
                    System.out.println(" ------- Tipo de dataset ------- ");
                    System.out.println("1. dataset para caso general");
                    System.out.println("2. dataset para caso peor");
                    System.out.println("Indica el tipo de dataset: ");
                    eleccion = in.nextInt();

                    do {
                        switch (eleccion) {
                            case 1 -> {
                                // genera un dataset aleatorio para el caso general
                                System.out.println("Indica la capacidad del dataset: ");
                                int capacidad = in.nextInt();
                                ProcessFile.createFile(capacidad);
                            }
                            case 2 -> {
                                // genera un dataset para el caso peor
                                System.out.println("Indica la capacidad del dataset: ");
                                int capacidad = in.nextInt();
                                ProcessFile.createFileWorstCase(capacidad);
                            }
                            default -> {
                                System.out.println("Tipo no disponible");
                            }
                        }
                    } while (eleccion != 1 && eleccion != 2);

                }
                case 2 -> {
                    System.out.println("Indica la ruta del fichero: ");
                    in.nextLine();
                    path = in.nextLine();
                }
                case 3 -> {
                    if (!path.equals(" ")) {
                        double startTime = 0, endTime = 0, duration;
                        float dur = 0;
                        ArrayList<Punto> memoria = ProcessFile.reader(path);
                        ArrayList<Punto> estrategias = new ArrayList<>();

                        // En tu clase App
                        System.out.println("Estrategia                Punto 1                           Punto 2                           Distancia       Calculadas       Tiempo (mseg)");
                        System.out.println("=============================================================================================================================================");

                        // exhaustivo
                        estrategias = copiaArray(memoria);
                        Algoritmos.resetContador();
                        startTime = System.nanoTime();
                        ArrayList<Punto> exhaustivo = Algoritmos.exhaustivo(estrategias, 0, estrategias.size());
                        endTime = System.nanoTime();
                        duration = (endTime - startTime) / 1e6;
                        ProcessFile.fileXArray(estrategias, "exhaustivo");

                        System.out.printf("%-20s %-35s %-35s %-15.10f %-15d %-15.4f%n",
                                "Exhaustivo",
                                exhaustivo.get(0),
                                exhaustivo.get(1),
                                Algoritmos.distancia(exhaustivo.get(0), exhaustivo.get(1)),
                                Algoritmos.getContador(),
                                duration);

                        // exhaustivo poda ->
                        estrategias = copiaArray(memoria);
                        Algoritmos.resetContador();
                        startTime = System.nanoTime();
                        ArrayList<Punto> exhaustivoPoda = Algoritmos.exhaustivoPoda(estrategias, 0, estrategias.size());
                        endTime = System.nanoTime();
                        duration = (endTime - startTime) / 1e6;
                        ProcessFile.fileXArray(estrategias, "exhaustivoPoda");

                        System.out.printf("%-20s %-35s %-35s %-15.10f %-15d %-15.4f%n",
                                "Exhaustivo Poda",
                                exhaustivoPoda.get(0),
                                exhaustivoPoda.get(1),
                                Algoritmos.distancia(exhaustivoPoda.get(0), exhaustivoPoda.get(1)),
                                Algoritmos.getContador(),
                                duration);


                        // divide y venceras
                        estrategias = copiaArray(memoria);
                        Algoritmos.resetContador();
                        startTime = System.nanoTime();
                        Algoritmos.quickSort(estrategias, 'x');
                        ArrayList<Punto> dyv = Algoritmos.dyv(estrategias, 0, estrategias.size());
                        endTime = System.nanoTime();
                        duration = (endTime - startTime) / 1e6;
                        ProcessFile.fileXArray(estrategias, "dyv");

                        System.out.printf("%-20s %-35s %-35s %-15.10f %-15d %-15.4f%n",
                                "DyV",
                                dyv.get(0),
                                dyv.get(1),
                                Algoritmos.distancia(dyv.get(0), dyv.get(1)),
                                Algoritmos.getContador(),
                                duration);


                        // divide y venceras mejorado
                        estrategias = copiaArray(memoria);
                        Algoritmos.resetContador();
                        startTime = System.nanoTime();
                        Algoritmos.quickSort(estrategias, 'x');
                        ArrayList<Punto> dyvMejorado = Algoritmos.dyvMejorado(estrategias, 0, estrategias.size());
                        endTime = System.nanoTime();
                        duration = (endTime - startTime) / 1e6;
                        ProcessFile.fileXArray(estrategias, "dyvMejorado");

                        System.out.printf("%-20s %-35s %-35s %-15.10f %-15d %-15.4f%n",
                                "DyV Mejorado",
                                dyvMejorado.get(0),
                                dyvMejorado.get(1),
                                Algoritmos.distancia(dyvMejorado.get(0), dyvMejorado.get(1)),
                                Algoritmos.getContador(),
                                duration);

                    }
                }

                case 4 -> {
                    double tiempoExhaustivo[] = new double[5],
                            tiempoExhaustivoPoda[] = new double[5],
                            tiempoDyV[] = new double[5],
                            tiempoDyVMejorado[] = new double[5];

                    int iter = 0;
                    System.out.println(" ");
                    System.out.println(String.format("%-8s%-15s%-20s%-20s%-30s", "TALLA", "EXHAUSTIVO", "EXHAUSTIVO-PODA", "DIVIDE Y VENCERAS", "DIVIDE Y VENCERAS MEJORADO"));

                    for (int i = 1000; i <= 5000; i += 1000) {
                        for (int j = 0; j < 10; j++) {
                            ArrayList<Punto> dataset = generaDatasetPorTalla(i);
                            ArrayList<Punto> estrategias = copiaArray(dataset);
                            double startTime = 0, endTime = 0;

                            // exhaustivo
                            Algoritmos.resetContador();
                            startTime = System.nanoTime();
                            Algoritmos.exhaustivo(estrategias, 0, estrategias.size());
                            endTime = System.nanoTime();
                            tiempoExhaustivo[iter] += (endTime - startTime);

                            // exhaustivoPoda
                            Algoritmos.resetContador();
                            estrategias = copiaArray(dataset);
                            startTime = System.nanoTime();
                            Algoritmos.exhaustivoPoda(estrategias, 0, estrategias.size());
                            endTime = System.nanoTime();
                            tiempoExhaustivoPoda[iter] += (endTime - startTime);

                            // divide y venceras
                            Algoritmos.resetContador();
                            estrategias = copiaArray(dataset);
                            startTime = System.nanoTime();
                            Algoritmos.quickSort(estrategias, 'x');
                            Algoritmos.dyv(estrategias, 0, estrategias.size());
                            endTime = System.nanoTime();
                            tiempoDyV[iter] += (endTime - startTime);

                            // divide y venceras mejorado
                            Algoritmos.resetContador();
                            estrategias = copiaArray(dataset);
                            startTime = System.nanoTime();
                            Algoritmos.quickSort(estrategias, 'x');
                            Algoritmos.dyvMejorado(estrategias, 0, estrategias.size());
                            endTime = System.nanoTime();
                            tiempoDyVMejorado[iter] += (endTime - startTime);
                        }
                        System.out.println(String.format("%-8d%-15.4f%-20.4f%-20.4f%-30.4f",
                                i,
                                tiempoExhaustivo[iter] / 1e6,
                                tiempoExhaustivoPoda[iter] / 1e6,
                                tiempoDyV[iter] / 1e6,
                                tiempoDyVMejorado[iter] / 1e6));
                        iter++;
                    }

                    ProcessFile.createSizeFile(tiempoExhaustivo, "exhaustivo", 1000, 1000);
                    ProcessFile.createSizeFile(tiempoExhaustivoPoda, "exhaustivo-poda", 1000, 1000);
                    ProcessFile.createSizeFile(tiempoDyV, "dyv", 1000, 1000);
                    ProcessFile.createSizeFile(tiempoDyVMejorado, "dyvMejorado", 1000, 1000);

                    System.out.println(" ");
                }

                case 5 -> {
                    int algoritmo1 = 0, algoritmo2 = 0;
                    for (int i = 0; i < 50; i++) {
                        System.out.println();
                    }

                    do {
                        comparar2EstrategiasMenu();
                        System.out.print("Algoritmo 1: ");
                        in.nextLine();
                        algoritmo1 = in.nextInt();
                        System.out.print("Algoritmo 2: ");
                        in.nextLine();
                        algoritmo2 = in.nextInt();

                        String nombreAlgoritmo1 = obtenerNombreAlgoritmo(algoritmo1);
                        String nombreAlgoritmo2 = obtenerNombreAlgoritmo(algoritmo2);

                        double tiempoExhaustivo[] = new double[5],
                                tiempoExhaustivoPoda[] = new double[5],
                                tiempoDyV[] = new double[5],
                                tiempoDyVMejorado[] = new double[5];

                        int calculadasExhaustivo = 0, calculadasExhaustivoPoda = 0, calculadasDyV = 0, calculadasDyVMejorado = 0;
                        int iter = 0;

                        if (algoritmo1 != 5) {
                            System.out.println(String.format("%-20s%-40s%-40s", "", nombreAlgoritmo1, nombreAlgoritmo2));
                            System.out.println(String.format("%-10s%-25s%-25s%-25s%-25s", "Talla", "Tiempo", "Distancia", "Tiempo", "Distancia"));
                        }


                        for (int i = 1000; i <= 5000; i += 1000) {
                            if (algoritmo1 != 5) {
                                System.out.print(" " + i);
                            }
                            for (int j = 0; j < 10; j++) {
                                ArrayList<Punto> dataset = generaDatasetPorTalla(i);
                                ArrayList<Punto> estrategias = new ArrayList<>();
                                double startTime = 0, endTime = 0;
                                long calculadas = 0;

                                switch (algoritmo1) {
                                    case 1 -> {
                                        // exhautsivo
                                        Algoritmos.resetContador();
                                        estrategias = copiaArray(dataset);
                                        startTime = System.nanoTime();
                                        Algoritmos.exhaustivo(estrategias, 0, estrategias.size());
                                        endTime = System.nanoTime();
                                        tiempoExhaustivo[iter] += (endTime - startTime);
                                        calculadasExhaustivo += Algoritmos.getContador();
                                    }
                                    case 2 -> {
                                        // exhaustivo pod
                                        Algoritmos.resetContador();
                                        estrategias = copiaArray(dataset);
                                        startTime = System.nanoTime();
                                        Algoritmos.exhaustivoPoda(estrategias, 0, estrategias.size());
                                        endTime = System.nanoTime();
                                        tiempoExhaustivoPoda[iter] += (endTime - startTime);
                                        calculadasExhaustivoPoda += Algoritmos.getContador();
                                    }
                                    case 3 -> {
                                        // dyv
                                        Algoritmos.resetContador();
                                        estrategias = copiaArray(dataset);
                                        startTime = System.nanoTime();
                                        Algoritmos.quickSort(estrategias, 'x');
                                        Algoritmos.dyv(estrategias, 0, estrategias.size());
                                        endTime = System.nanoTime();
                                        tiempoDyV[iter] += (endTime - startTime);
                                        calculadasDyV += Algoritmos.getContador();
                                    }
                                    case 4 -> {
                                        // dyvMejorado
                                        Algoritmos.resetContador();
                                        estrategias = copiaArray(dataset);
                                        startTime = System.nanoTime();
                                        Algoritmos.quickSort(estrategias, 'x');
                                        Algoritmos.dyvMejorado(estrategias, 0, estrategias.size());
                                        endTime = System.nanoTime();
                                        tiempoDyVMejorado[iter] += (endTime - startTime);
                                        calculadasDyVMejorado += Algoritmos.getContador();
                                    }
                                }

                                switch (algoritmo2) {
                                    case 1 -> {
                                        // exhautsivo
                                        Algoritmos.resetContador();
                                        estrategias = copiaArray(dataset);
                                        startTime = System.nanoTime();
                                        Algoritmos.exhaustivo(estrategias, 0, estrategias.size());
                                        endTime = System.nanoTime();
                                        tiempoExhaustivo[iter] += (endTime - startTime);
                                        calculadasExhaustivo += Algoritmos.getContador();
                                    }
                                    case 2 -> {
                                        // exhaustivo pod
                                        Algoritmos.resetContador();
                                        estrategias = copiaArray(dataset);
                                        startTime = System.nanoTime();
                                        Algoritmos.exhaustivoPoda(estrategias, 0, estrategias.size());
                                        endTime = System.nanoTime();
                                        tiempoExhaustivoPoda[iter] += (endTime - startTime);
                                        calculadasExhaustivoPoda += Algoritmos.getContador();
                                    }
                                    case 3 -> {
                                        // dyv
                                        Algoritmos.resetContador();
                                        estrategias = copiaArray(dataset);
                                        startTime = System.nanoTime();
                                        Algoritmos.quickSort(estrategias, 'x');
                                        Algoritmos.dyv(estrategias, 0, estrategias.size());
                                        endTime = System.nanoTime();
                                        tiempoDyV[iter] += (endTime - startTime);
                                        calculadasDyV += Algoritmos.getContador();
                                    }
                                    case 4 -> {
                                        // dyvMejorado
                                        Algoritmos.resetContador();
                                        estrategias = copiaArray(dataset);
                                        startTime = System.nanoTime();
                                        Algoritmos.quickSort(estrategias, 'x');
                                        Algoritmos.dyvMejorado(estrategias, 0, estrategias.size());
                                        endTime = System.nanoTime();
                                        tiempoDyVMejorado[iter] += (endTime - startTime);
                                        calculadasDyVMejorado += Algoritmos.getContador();
                                    }
                                }

                            }
                            switch (algoritmo1) {
                                case 1 -> {
                                    // muestro la informacionç
                                    System.out.print(String.format("%-5s%-26.4f%-15d%-1s%-1s", "", tiempoExhaustivo[iter] / 1e6, calculadasExhaustivo, "", ""));
                                    ProcessFile.createSizeFile(tiempoExhaustivo, "exhaustivo", 1000, 1000);
                                }
                                case 2 -> {
                                    System.out.print(String.format("%-5s%-26.4f%-15d%-1s%-1s", "", tiempoExhaustivoPoda[iter] / 1e6, calculadasExhaustivoPoda, "", ""));
                                    ProcessFile.createSizeFile(tiempoExhaustivoPoda, "exhaustivoPoda", 1000, 1000);
                                }
                                case 3 -> {
                                    System.out.print(String.format("%-5s%-26.4f%-15d%-1s%-1s", "", tiempoDyV[iter] / 1e6, calculadasDyV, "", ""));
                                    ProcessFile.createSizeFile(tiempoDyV, "dyv", 1000, 1000);
                                }
                                case 4 -> {
                                    System.out.print(String.format("%-5s%-26.4f%-15d%-1s%-1s", "", tiempoDyVMejorado[iter] / 1e6, calculadasDyVMejorado, "", ""));
                                    ProcessFile.createSizeFile(tiempoDyVMejorado, "dyvMejorado", 1000, 1000);
                                }
                            }

                            switch (algoritmo2) {
                                case 1 -> {
                                    // muestro la informacion
                                    System.out.println(String.format("%-1s%-1s%-5s%-26.4f%-16d", "", "", "", tiempoExhaustivo[iter] / 1e6, calculadasExhaustivo));
                                    ProcessFile.createSizeFile(tiempoExhaustivo, "exhaustivo", 1000, 1000);
                                }
                                case 2 -> {
                                    System.out.println(String.format("%-1s%-1s%-5s%-26.4f%-16d", "", "", "", tiempoExhaustivoPoda[iter] / 1e6, calculadasExhaustivoPoda));
                                    ProcessFile.createSizeFile(tiempoExhaustivoPoda, "exhaustivoPoda", 1000, 1000);
                                }
                                case 3 -> {
                                    System.out.println(String.format("%-1s%-1s%-5s%-26.4f%-16d", "", "", "", tiempoDyV[iter] / 1e6, calculadasDyV));
                                    ProcessFile.createSizeFile(tiempoDyV, "dyv", 1000, 1000);
                                }
                                case 4 -> {
                                    System.out.println(String.format("%-1s%-1s%-5s%-26.4f%-16d", "", "", "", tiempoDyVMejorado[iter] / 1e6, calculadasDyVMejorado));
                                    ProcessFile.createSizeFile(tiempoDyVMejorado, "dyvMejorado", 1000, 1000);
                                }
                            }
                            iter++;

                            // printeo de exhaustivo

                        }
                    } while (algoritmo1 != 5);

                }
            }

        } while (eleccion != 9);
    }
}