package consoleApp;


import java.util.ArrayList;

public class Algoritmos {
    private static int contador;

    private static double minDistance;

    private static void resetminDistance() {
        minDistance = Double.MAX_VALUE;
    }

    public static double getMinDistance() { return minDistance; }

    public static int getContador() {
        return contador;
    }

    public static void resetContador() {
        contador = 0;
    }

    public static double distancia(Punto a, Punto b) {
        contador++;
        return (Math.sqrt((b.getX() - a.getX()) * (b.getX() - a.getX()) + (b.getY() - a.getY()) * (b.getY() - a.getY())));
    }

    public static ArrayList<Punto> exhaustivo(ArrayList<Punto> t) {
        // no tengo el constructor completo porque se no es recursivo y siempre va a tener el array el mismo tamaño
        minDistance = Double.MAX_VALUE;
        resetContador();
        return exhaustivo(t, 0, t.size() -1);
    }

    public static ArrayList<Punto> exhaustivo(ArrayList<Punto> t, int izq, int der) {
        ArrayList<Punto> retorno = new ArrayList<>(2);
        double distancia = 0; // guardamos la distancia calculada

        retorno.add(t.get(0));
        retorno.add(t.get(1));

        for (int i = izq; i <= der; i++) {
            for (int j = i + 1; j <= der; j++) {
                distancia = distancia(t.get(i), t.get(j));
                if (distancia < minDistance) {
                    minDistance = distancia;
                    retorno.set(0, t.get(i));
                    retorno.set(1, t.get(j));
                }
            }
        }

        return retorno;
    }


    public static ArrayList<Punto> exhaustivoPoda(ArrayList<Punto> t) {
        minDistance = Double.MAX_VALUE;
        resetContador();
        quickSort(t, 'x');
        return exhaustivoPoda(t, 0, t.size() -1);
    }

    public static ArrayList<Punto> exhaustivoPoda(ArrayList<Punto> t, int izq, int der) {
        double distancia = 0;
        ArrayList<Punto> retorno = new ArrayList<>();


        for (int i = izq; i <= der; i++) {
            for (int j = i + 1; j <= der; j++) {
                if ((t.get(j).getX() - t.get(i).getX()) > minDistance) {
                    break;
                } else {
                    distancia = distancia(t.get(i), t.get(j));
                    if (distancia < minDistance) {
                        minDistance = distancia;
                        retorno.clear();
                        retorno.add(t.get(i));
                        retorno.add(t.get(j));
                    }
                }
            }
        }

        return retorno;
    }

    public static ArrayList<Punto> dyv(ArrayList<Punto> t) {
        minDistance = Double.MAX_VALUE;
        resetContador();
        return dyv(t, 0, t.size() - 1);
    }

    public static ArrayList<Punto> dyv(ArrayList<Punto> t, int izq, int der) {
        ArrayList<Punto> retorno = new ArrayList<>();
        int nElementos = (der - izq) + 1;

        if (nElementos > 4) {
            int pivote = izq + (der - izq) / 2;

            ArrayList<Punto> solIzq = dyv(t, izq, pivote);
            double distIzq = minDistance;
            ArrayList<Punto> solDer = dyv(t, pivote + 1, der);
            double distDer = minDistance;

            if (distIzq <= distDer) {
                minDistance = distIzq;
                retorno = solIzq;
            } else {
                minDistance = distDer;
                retorno = solDer;
            }

            // calculo la parte de la izquierda de la franja
            int franjaIzq = pivote;
            while (franjaIzq >= izq && (t.get(pivote).getX() - t.get(franjaIzq).getX()) < minDistance) {
                franjaIzq--;
            }

            // calculo  la parte de la derecha de la franja
            int franjaDer = pivote + 1;
            while (franjaDer <= der && (t.get(franjaDer).getX() - t.get(pivote).getX()) < minDistance) {
                franjaDer++;
            }

            double distancia = 0;
            for (int i = pivote; i > franjaIzq; i--) {
                for (int j = pivote + 1; j < franjaDer; j++) {
                    if (t.get(j).getX() - t.get(i).getX() > minDistance) {
                        break;
                    } else {
                        distancia = distancia(t.get(i), t.get(j));
                        if (distancia < minDistance) {
                            retorno.set(0, t.get(i));
                            retorno.set(1, t.get(j));
                            minDistance = distancia;
                        }
                    }
                }
            }
        } else {
            retorno = exhaustivoPoda(t, izq, der);
        }

        return retorno;
    }

    public static ArrayList<Punto> dyvMejorado(ArrayList<Punto> t) {
        minDistance = Double.MAX_VALUE;
        resetContador();
        return dyvMejorado(t, 0, t.size() -1 );
    }

    public static ArrayList<Punto> dyvMejorado(ArrayList<Punto> t, int izq, int der) {
        ArrayList<Punto> retorno = new ArrayList<>();
        int nElementos = (der - izq) + 1;

        if (nElementos >= 4) {
            int pivote = (izq + der) / 2;

            ArrayList<Punto> solIzq = dyvMejorado(t, izq, pivote);
            double distIzq = minDistance;
            ArrayList<Punto> solDer = dyvMejorado(t, pivote + 1, der);
            double distDer = minDistance;

            if (distIzq <= distDer) {
                minDistance = distIzq;
                retorno = solIzq;
            } else {
                minDistance = distDer;
                retorno = solDer;
            }

            // calculo la parte de la izquierda de la franja
            int franjaIzq = pivote;
            while (franjaIzq >= izq && (t.get(pivote).getX() - t.get(franjaIzq).getX()) < minDistance) {
                franjaIzq--;
            }

            // calculo  la parte de la derecha de la franja
            int franjaDer = pivote + 1;
            while (franjaDer <= der && (t.get(franjaDer).getX() - t.get(pivote).getX()) < minDistance) {
                franjaDer++;
            }

            // array auxiliar que vamos a ordenar por y y despues hacerle un exhaustivo
            ArrayList<Punto> franja = new ArrayList<>();
            for (int i = franjaIzq + 1; i < franjaDer; i++) {
                franja.add(t.get(i));
            }

            // ordeno los elementos en franja por el eje y
            quickSort(franja, 'y');

            double distancia = 0;
            for (int i = 0; i < franja.size(); i++) {
                for (int j = i + 1; j < franja.size() && (franja.get(j).getY() - franja.get(i).getY()) < minDistance ; j++) {
                    distancia = distancia(franja.get(i), franja.get(j));
                    if (distancia < minDistance) {
                        retorno.clear();
                        retorno.add(franja.get(i));
                        retorno.add(franja.get(j));
                        minDistance = distancia;
                    }
                }
            }

        } else {
            retorno = exhaustivoPoda(t, izq, der);
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
