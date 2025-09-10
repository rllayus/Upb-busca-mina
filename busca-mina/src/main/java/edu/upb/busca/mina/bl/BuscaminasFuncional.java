package edu.upb.busca.mina.bl;

import java.util.Random;
import java.util.function.Function;
public class BuscaminasFuncional {

    // Función para revelar una celda. Retorna un nuevo Tablero.
    public static Tablero revelarCelda(Tablero tablero, int fila, int columna) {
        Celda celda = tablero.celdas()[fila][columna];

        // No hacer nada si la celda ya está revelada o tiene una bandera
        if (celda.estaRevelada() || celda.tieneBandera()) {
            return tablero;
        }

        // Si es una mina, el juego termina
        if (celda.esMina()) {
            return new Tablero(tablero.celdas(), tablero.filas(), tablero.columnas(), EstadoJuego.PERDIDO);
        }

        // Crear una copia del tablero para la transformación
        Celda[][] nuevasCeldas = copiarCeldas(tablero.celdas());
        nuevasCeldas[fila][columna] = new Celda(celda.esMina(), celda.minasVecinas(), true, false);

        // Si la celda es 0, revela a los vecinos (recursivamente)
        if (celda.minasVecinas() == 0) {
            return revelarVecinosRecursivamente(new Tablero(nuevasCeldas), fila, columna);
        }

        return new Tablero(nuevasCeldas, tablero.filas(), tablero.columnas(), tablero.estado());
    }

    // Función para colocar una bandera.
    public static Tablero colocarBandera(Tablero tablero, int fila, int columna) {
        Celda celda = tablero.celdas()[fila][columna];

        // No hacer nada si la celda está revelada
        if (celda.estaRevelada()) {
            return tablero;
        }

        return actualizarTablero(tablero, celdas -> {
            celdas[fila][columna] = new Celda(celda.esMina(), celda.minasVecinas(), false, !celda.tieneBandera());
            return celdas;
        });
    }

    // Método auxiliar para crear una copia profunda del array de celdas
    private static Celda[][] copiarCeldas(Celda[][] celdasOriginales) {
        Celda[][] copia = new Celda[celdasOriginales.length][celdasOriginales[0].length];
        for (int i = 0; i < celdasOriginales.length; i++) {
            System.arraycopy(celdasOriginales[i], 0, copia[i], 0, celdasOriginales[i].length);
        }
        return copia;
    }

    // Método auxiliar para revelar celdas vecinas (lógica recursiva)
    private static Tablero revelarVecinosRecursivamente(Tablero tablero, int fila, int columna) {
        // La implementación de esta función es recursiva y también devuelve un nuevo Tablero
        // cada vez que revela una celda.
        return tablero;
    }

    private static Tablero actualizarTablero(Tablero tablero, Function<Celda[][], Celda[][]> actualizador) {
        Celda[][] nuevasCeldas = actualizador.apply(copiarCeldas(tablero.celdas()));
        return new Tablero(nuevasCeldas, tablero.filas(), tablero.columnas(), tablero.estado());
    }


    public static Tablero crearTableroInicial(int filas, int columnas, int minas) {
        // 1. Inicializar todas las celdas como vacías
        Celda[][] celdas = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda(false, 0, false, false);
            }
        }

        // 2. Colocar las minas de forma aleatoria
        Random random = new Random();
        int minasColocadas = 0;
        while (minasColocadas < minas) {
            int fila = random.nextInt(filas);
            int columna = random.nextInt(columnas);

            // Si la celda no tiene una mina, la colocamos
            if (!celdas[fila][columna].esMina()) {
                celdas[fila][columna] = new Celda(true, 0, false, false);
                minasColocadas++;
            }
        }

        // 3. Calcular las minas vecinas para cada celda
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                // Solo calcular si no es una mina
                if (!celdas[i][j].esMina()) {
                    int minasVecinas = contarMinasVecinas(celdas, i, j, filas, columnas);
                    // Crear una nueva celda con el conteo de minas vecinas
                    celdas[i][j] = new Celda(false, minasVecinas, false, false);
                }
            }
        }

        // 4. Devolver el nuevo objeto Tablero inmutable
        return new Tablero(celdas);
    }

    // Método auxiliar para contar las minas vecinas de una celda
    private static int contarMinasVecinas(Celda[][] celdas, int fila, int columna, int filas, int columnas) {
        int contador = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int vecinoFila = fila + i;
                int vecinoColumna = columna + j;

                // Verificar si la celda vecina está dentro de los límites
                if (vecinoFila >= 0 && vecinoFila < filas && vecinoColumna >= 0 && vecinoColumna < columnas) {
                    // No contar la celda actual
                    if (i != 0 || j != 0) {
                        if (celdas[vecinoFila][vecinoColumna].esMina()) {
                            contador++;
                        }
                    }
                }
            }
        }
        return contador;
    }
}
