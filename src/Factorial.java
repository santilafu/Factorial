public class Factorial {

    //FUNCIONES DE FACTORIAL

    /**
     * VERSIÓN ITERATIVA
     * Calcula el factorial usando un bucle for.
     * Es más eficiente en memoria porque solo usa variables simples.
     *
     * @param n El número del que queremos calcular el factorial
     * @return El factorial de n (n!)
     */
    public static long factorialIterativo(int n) {
        long resultado = 1;
        for (int i = 1; i <= n; i++) {
            resultado = resultado * i;
        }
        return resultado;
    }
    /**
     * VERSIÓN RECURSIVA
     * Calcula el factorial llamándose a sí misma.
     * Consume más memoria debido a las llamadas en la pila.
     *
     * @param n El número del que queremos calcular el factorial
     * @return El factorial de n (n!)
     */
    public static long factorialRecursivo(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorialRecursivo(n - 1);
    }

    //PRUEBAS DE RENDIMIENTO

    public static void main(String[] args) {

        System.out.println("PRUEBAS DE RENDIMIENTO PARA FUNCIONES DE FACTORIAL\n");

        //  PRUEBA DE REGRESIÓN
        // Verificamos que ambas funciones dan resultados correctos
        // con valores conocidos antes de hacer más pruebas
        System.out.println(" PRUEBA DE REGRESIÓN");
        System.out.println("Comprobamos que las funciones dan resultados correctos:\n");

        int[] valoresTest = {0, 1, 5, 10, 15};
        long[] resultadosEsperados = {1, 1, 120, 3628800L, 1307674368000L};

        boolean todoOk = true;
        for (int i = 0; i < valoresTest.length; i++) {
            long resIter = factorialIterativo(valoresTest[i]);
            long resRecur = factorialRecursivo(valoresTest[i]);
            boolean correcto = (resIter == resultadosEsperados[i]) && (resRecur == resultadosEsperados[i]);

            System.out.println(valoresTest[i] + "! = " + resultadosEsperados[i] +
                    " → Iterativo: " + resIter +
                    ", Recursivo: " + resRecur +
                    (correcto ? " ✓" : "ERROR"));

            if (!correcto) todoOk = false;
        }
        System.out.println(todoOk ? "\nTodas las pruebas de regresión PASADAS\n" : "\n HAY ERRORES\n");

        // PRUEBA DE VOLUMEN
        // Ejecutamos muchas veces la función para ver cómo se comporta
        System.out.println(" PRUEBA DE VOLUMEN ");
        System.out.println("Ejecutamos 100,000 veces el factorial de 20:\n");

        int repeticiones = 100000;
        int numeroVolumen = 20;

        // Medimos tiempo iterativo
        long inicioIter = System.nanoTime();
        for (int i = 0; i < repeticiones; i++) {
            factorialIterativo(numeroVolumen);
        }
        long finIter = System.nanoTime();
        long tiempoIter = (finIter - inicioIter) / 1000000; // Convertir a milisegundos

        // Medimos tiempo recursivo
        long inicioRecur = System.nanoTime();
        for (int i = 0; i < repeticiones; i++) {
            factorialRecursivo(numeroVolumen);
        }
        long finRecur = System.nanoTime();
        long tiempoRecur = (finRecur - inicioRecur) / 1000000;

        System.out.println("Iterativo:  " + tiempoIter + " ms");
        System.out.println("Recursivo:  " + tiempoRecur + " ms");
        System.out.println("Diferencia: " + Math.abs(tiempoIter - tiempoRecur) + " ms");
        String ganador;
        if (tiempoIter < tiempoRecur) {
            ganador = "ITERATIVO";
        } else if (tiempoRecur < tiempoIter) {
            ganador = "RECURSIVO";
        } else {
            ganador = "EMPATE";
        }
        System.out.println("Ganador:    " + ganador + "\n");

        // PRUEBA DE ESTRÉS
        // Probamos con números cada vez más grandes hasta que falle
        System.out.println("PRUEBA DE ESTRÉS");
        System.out.println("Probamos con números cada vez más grandes:\n");

        int[] numerosEstres = {10, 15, 20, 25, 30, 50, 100, 1000, 5000, 10000};

        for (int n : numerosEstres) {
            // Probamos iterativo
            long tiempoIterEstres = -1;
            try {
                long inicio = System.nanoTime();
                factorialIterativo(n);
                tiempoIterEstres = System.nanoTime() - inicio;
            } catch (Exception e) {
                // Si falla, dejamos -1
            }

            // Probamos recursivo
            long tiempoRecurEstres = -1;
            try {
                long inicio = System.nanoTime();
                factorialRecursivo(n);
                tiempoRecurEstres = System.nanoTime() - inicio;
            } catch (StackOverflowError e) {
                // Error de pila llena (demasiadas llamadas recursivas)
            }

            String estadoIter = tiempoIterEstres >= 0 ? tiempoIterEstres + " ns" : "ERROR";
            String estadoRecur = tiempoRecurEstres >= 0 ? tiempoRecurEstres + " ns" : "STACKOVERFLOW";

            System.out.println("n=" + n + " → Iterativo: " + estadoIter + " | Recursivo: " + estadoRecur);
        }

        //  PRUEBA DE USO DE RECURSOS
        System.out.println("\n PRUEBA DE USO DE RECURSOS (MEMORIA)");
        System.out.println("Medimos la memoria usada antes y después:\n");

        Runtime runtime = Runtime.getRuntime();

        // Limpiamos memoria antes de medir
        runtime.gc();
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        // Ejecutamos muchas veces
        for (int i = 0; i < 10000; i++) {
            factorialIterativo(20);
        }
        long memoriaIterativo = runtime.totalMemory() - runtime.freeMemory();

        runtime.gc();
        memoriaAntes = runtime.totalMemory() - runtime.freeMemory();

        for (int i = 0; i < 10000; i++) {
            factorialRecursivo(20);
        }
        long memoriaRecursivo = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("Memoria usada (aprox.):");
        System.out.println("Iterativo:  " + (memoriaIterativo / 1024) + " KB");
        System.out.println("Recursivo:  " + (memoriaRecursivo / 1024) + " KB");

        // CONCLUSIONES
        System.out.println("\n CONCLUSIONES");
        System.out.println("La versión ITERATIVA es más estable con números grandes");
        System.out.println("La versión RECURSIVA puede causar StackOverflow");
        System.out.println("Para producción, se recomienda usar la versión ITERATIVA");
    }
}