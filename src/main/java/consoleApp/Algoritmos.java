package consoleApp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Algoritmos {
    private static int contador;

    public static int getContador() {
        return contador;
    }

    public static void resetContador() {
        contador = 0;
    }

    public static double distancia(Punto a, Punto b) {
        return (Math.sqrt((b.getX() - a.getX()) * (b.getX() - a.getX()) + (b.getY() - a.getY()) * (b.getY() - a.getY())));
    }

    public static ArrayList<Punto> exhaustivo(ArrayList<Punto> t, int izq, int der) {
        ArrayList<Punto> retorno = new ArrayList<>();
        double distancia = 0;

        // valores a comparar
        retorno.add(t.get(0));
        retorno.add(t.get(1));
        double minDistance = distancia(retorno.get(0), retorno.get(1));
        contador++;

        for (int i = izq; i < der; i++) {
            for (int j = i + 1; j < der; j++) {
                distancia = distancia(t.get(i), t.get(j));
                contador++;
                if (distancia < minDistance) {
                    minDistance = distancia;
                    retorno.set(0, t.get(i));
                    retorno.set(1, t.get(j));
                }
            }
        }

        return retorno;
    }

    public static ArrayList<Punto> exhaustivoPoda(ArrayList<Punto> t, int izq, int der) {
        ArrayList<Punto> retorno = new ArrayList<>();
        double distancia = 0;

        // valores a comparar
        retorno.add(t.get(0));
        retorno.add(t.get(1));
        quickSort(t, 'x');

        double minDistance = distancia(retorno.get(0), retorno.get(1));
        contador++;

        // debo de ordenar el array

        for (int i = izq; i < der; i++) {
            for (int j = i + 1; j < der; j++) {
                if ((t.get(j).getX() - t.get(i).getX()) > minDistance) {
                    break;
                }
                distancia = distancia(t.get(i), t.get(j));
                contador++;
                if (distancia < minDistance) {
                    minDistance = distancia;
                    retorno.set(0, t.get(i));
                    retorno.set(1, t.get(j));
                }
            }
        }
        return retorno;
    }


    public static ArrayList<Punto> dyv(ArrayList<Punto> t, int izq, int der)  {
        ArrayList<Punto> retorno = new ArrayList<>();
        int nElementos = (der - izq) + 1;

        if (nElementos > 4) {
            int pivote = izq + (der - izq) / 2;
            double minDistance;

            ArrayList<Punto> solIzq = dyv(t, izq, pivote);
            ArrayList<Punto> solDer = dyv(t, pivote + 1, der);

            double distIzq = distancia(solIzq.get(0), solIzq.get(1));
            contador++;
            double distDer = distancia(solDer.get(0), solDer.get(1));
            contador++;

            if (distIzq <= distDer) {
                minDistance = distIzq;
                retorno = solIzq;
            } else {
                minDistance = distDer;
                retorno = solDer;
            }

            // nos centramos en la franja
            int franjaIzq = 0, franjaDer = 0;
            for (franjaIzq = pivote; franjaIzq >= 0; franjaIzq--) {
                if ((t.get(pivote + 1).getX() - t.get(franjaIzq).getX()) > minDistance) {
                    break;
                }
            }

            for (franjaDer = pivote + 1; franjaDer < der; franjaDer++) {
                if ((t.get(franjaDer).getX() - t.get(pivote).getX()) > minDistance) {
                    break;
                }
            }

            double distancia = 0;
            for (int i = pivote; i > franjaIzq; i--) {
                for (int j = pivote + 1; j < franjaDer; j++) {
                    if (t.get(j).getX() - t.get(i).getX() > minDistance) {
                        break;
                    } else {
                        distancia = distancia(t.get(i), t.get(j));
                        contador++;
                        if (distancia < minDistance) {
                            retorno.set(0, t.get(i));
                            retorno.set(1, t.get(j));
                            minDistance = distancia;
                        }
                    }
                }
            }
        } else {
            retorno = exhaustivo(t, izq, der);
        }

        return retorno;
    }

    public static ArrayList<Punto> dyvMejorado(ArrayList<Punto> t, int izq, int der) {
        ArrayList<Punto> retorno = new ArrayList<>();
        int nElementos = (der - izq) + 1;
        int casoBase = (int) Math.sqrt(nElementos);

        if (nElementos > casoBase) {
            int pivote = izq + (der - izq) / 2;
            double minDistance;

            ArrayList<Punto> solIzq = dyvMejorado(t, izq, pivote);
            ArrayList<Punto> solDer = dyvMejorado(t, pivote + 1, der);

            double distIzq = distancia(solIzq.get(0), solIzq.get(1));
            contador++;
            double distDer = distancia(solDer.get(0), solDer.get(1));
            contador++;

            if (distIzq <= distDer) {
                minDistance = distIzq;
                retorno = solIzq;
            } else {
                minDistance = distDer;
                retorno = solDer;
            }

            // nos centramos en la franja
            ArrayList<Punto> franja = new ArrayList<>();
            for(int i = izq ; i<der;i++) {
                if(Math.abs(t.get(i).getX() - t.get(pivote).getX()) < minDistance) {
                    franja.add(t.get(i));
                }
            }

            // ordeno los elementos en franja por el eje y -> creo la franja 2S
            quickSort(franja, 'y');

            // ¿Comprobamos aquellos puntos que estan a menos de 12 posiciones?
                // no oentiendo que se pretende decir con esta afirmacion
                // no entiendo lo de los 11 puntos de la franja
            double distancia = 0;
            for (int i = 0; i < franja.size(); i++) {
                for (int j = i + 1; j < franja.size() && (franja.get(j).getY() - franja.get(i).getY()) < minDistance; j++) {
                    distancia = distancia(franja.get(i), franja.get(j));
                    contador++;
                    if(distancia < minDistance) {
                        retorno.set(0, franja.get(i));
                        retorno.set(1, franja.get(j));
                        minDistance = distancia;
                    }
                }
            }
            
        } else {
            retorno = exhaustivo(t, izq, der);
        }

        return retorno;
    }

    /* ----- ORDENACION ----- */
    public static void quickSort(ArrayList<Punto> t, char eje) {
        quickSort(t, 0, t.size() - 1, eje);
    }

    public static void quickSort(ArrayList<Punto> t, int izq, int der, char eje) {
        int q = 0;

        switch (eje) {
            case 'x' -> {
                if (izq < der) {
                    q = partition(t, izq, der, 'x');
                    quickSort(t, izq, q - 1, 'x');
                    quickSort(t, q + 1, der, 'x');
                }
            }

            case 'y' -> {
                if (izq < der) {
                    q = partition(t, izq, der, 'y');
                    quickSort(t, izq, q - 1, 'y');
                    quickSort(t, q + 1, der, 'y');
                }
            }
        }
    }

    public static int partition(ArrayList<Punto> t, int izq, int der, char eje) {
        Punto pivote = t.get(der);
        int limIzq = izq - 1;

        switch (eje) {
            case 'x' -> {
                for (int j = izq; j < der; j++) {
                    if (t.get(j).getX() <= pivote.getX()) {
                        limIzq++;
                        // Intercambiamos t[limIzq] con t[j]
                        Punto aux = t.get(limIzq);
                        t.set(limIzq, t.get(j));
                        t.set(j, aux);
                    }
                }
            }
            case 'y' -> {
                for (int j = izq; j < der; j++) {
                    if (t.get(j).getY() <= pivote.getY()) {
                        limIzq++;
                        // Intercambiamos t[limIzq] con t[j]
                        Punto aux = t.get(limIzq);
                        t.set(limIzq, t.get(j));
                        t.set(j, aux);
                    }
                }
            }
        }
        // Colocamos el pivote en su posición correcta
        Punto aux = t.get(limIzq + 1);
        t.set(limIzq + 1, t.get(der));
        t.set(der, aux);

        return limIzq + 1;  // Retornamos la posición del pivote
    }

}
