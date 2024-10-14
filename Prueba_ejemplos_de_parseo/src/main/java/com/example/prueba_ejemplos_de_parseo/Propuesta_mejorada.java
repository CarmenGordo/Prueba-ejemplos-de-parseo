package com.example.prueba_ejemplos_de_parseo;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Propuesta_mejorada {
    private static final String GastosMejoradas = "gastosMejoradas.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("\n--- Propuesta Mejorada: Gestor de gastos  ---");
            System.out.println("1. Añadir gastos");
            System.out.println("2. Ver todos los gastos");
            System.out.println("3. Buscar gasto");
            System.out.println("4. Calcular y mostrar el total de los gastos");
            System.out.println("5. Mostrar los gastos según categoría");
            System.out.println("6. Editar un gasto existente");
            System.out.println("7. Eliminar un gasto");
            System.out.println("8. Buscar gastos por rango de fechas");
            System.out.println("9. Exportar todos los gastos a un archivo CSV");
            System.out.println("10. Mostrar estadísticas básicas (total, promedio, máximo, mínimo, gastos por categoría");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    anadirGastos(scanner);
                    break;
                case 2:
                    verGastos();
                    break;
                case 3:
                    buscarGastos(scanner);
                    break;
                case 4:
                    calcularGastos(scanner);
                    break;
                case 5:
                    mostrarGastosCategoria(scanner);
                    break;
                case 6:
                    editarGastos(scanner);
                    break;
                case 7:
                    eliminarGastos(scanner);
                    break;
                case 8:
                    buscarFechaGastos(scanner);
                    break;
                case 9:
                    exportarGastos(scanner);
                    break;
                case 10:
                    mostrarEstadisticasGastos(scanner);
                    break;
                case 0:
                    System.out.println("¡Nos vemos! :)");
                    break;
                default:
                    System.out.println("Esta opción no es posible");
            }
        } while (opcion != 0);
        scanner.close();
    }

    //METODOS:
    //anadirGastos
    private static void anadirGastos(Scanner scanner) {

        System.out.print("Introduce el nombre del nuevo gasto: ");
        String nombre = scanner.nextLine();

        //.now() : hace que coja dorectamente la hora
        System.out.print("Introduce la fecha: ");
        System.out.print("El día: ");
        //si se escribe con letras:
        boolean tipoValidoFecha = false;
        int dia = 0;
        int mes = 0;
        int año = 0;
        while (!tipoValidoFecha) {
            try {
                dia = scanner.nextInt();
                System.out.print("El mes: ");
                mes = scanner.nextInt();
                System.out.print("El año: ");
                año = scanner.nextInt();
                tipoValidoFecha = true;
            } catch (InputMismatchException e) {
                System.out.println("La fecha necesita estar en números enteros");
                scanner.next();
            }
        }
        //recopilar año, mes y dia
        LocalDate fecha = LocalDate.of(año, mes, dia);
        // despues de poner un nextInt salta el siguiente nextLine, por eso se pone uno vacio
        scanner.nextLine();

        System.out.print("Introduce la categoría: ");
        String categoria = scanner.nextLine();

        System.out.print("Introduce la descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Introduce la cantidad: ");
        double cantidad = 0;
        boolean tipoValido = false;
        while (!tipoValido) {
            try {
                cantidad = scanner.nextDouble();
                tipoValido = true;
            } catch (InputMismatchException e) {
                System.out.println("La cantidad necesita estar en números enteros");
                scanner.next();
            }
        }


        try (PrintWriter writer = new PrintWriter(new
                FileWriter(GastosMejoradas, true))) {
            writer.println(nombre + " ; " +
                    fecha + " ; " +
                    categoria + " ; " +
                    descripcion + " ; " +
                    cantidad + " .");
            System.out.println("El gasto se ha añadido");

        } catch (IOException e) {
            System.out.println("Error al añadir el gasto: " +
                    e.getMessage());
        }
    }

    //verGastos
    private static void verGastos() {
        try (BufferedReader reader = new BufferedReader(new
                FileReader(GastosMejoradas))) {
            String linea;
            System.out.println("\n--- Gastos: ---");
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";", 5);
                System.out.println("Nombre: " + partes[0]);
                System.out.println("Fecha: " + partes[1]);
                System.out.println("Categoría: " + partes[2]);
                System.out.println("Descripción: " + partes[3]);
                System.out.println("Cantidad: " + partes[4]);
                System.out.println("-------------------------");
            }
        } catch (IOException e) {
            System.out.println("Ha ocurrido el siguiente error: " +
                    e.getMessage());
        }
    }

    //buscarGastos
    private static void buscarGastos(Scanner scanner) {
        System.out.print("Introduce el titulo del gasto: ");
        String tituloClave = scanner.nextLine().toLowerCase();
        try (BufferedReader reader = new BufferedReader(new
                FileReader(GastosMejoradas))) {
            String linea;
            boolean encontrada = false;
            System.out.println("\n Resultados de la búsqueda: \n");
            while ((linea = reader.readLine()) != null) {
                if (linea.toLowerCase().contains(tituloClave)) {
                    String[] partes = linea.split(":", 5);
                    System.out.println("Nombre: " + partes[0]);
                    System.out.println("Fecha: " + partes[1]);
                    System.out.println("Categoría: " + partes[2]);
                    System.out.println("Descripción: " + partes[3]);
                    System.out.println("Cantidad: " + partes[4]);
                    System.out.println("-------------------------");
                    encontrada = true;
                }
            }
            if (!encontrada) {
                System.out.println("No se encontraron Gastos con esa palabra clave.");
            }
        } catch (IOException e) {
            System.out.println("Error al buscar Gastos: " + e.getMessage());
        }
    }

    //calcularGastos
    private static void calcularGastos(Scanner scanner) {
        //filtre por fecha
        System.out.print("Introduce el mes del gasto: ");
        int mesClave = scanner.nextInt();

        System.out.print("Introduce el año del gasto: ");
        int añoClave = scanner.nextInt();
        double total = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(GastosMejoradas))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";", 5);
                // extraer la fecha
                String fechaString = partes[1].trim();
                String cantidadString = partes[4].replace(" .", "").trim();

                // parsear la fecha a LocalDate y obtener el mes
                LocalDate fecha = LocalDate.parse(fechaString);
                int mesGasto = fecha.getMonthValue();
                int añoGasto = fecha.getYear();

                // verificar si el mes coincide y sumar
                try {
                    if (mesGasto == mesClave && añoGasto == añoClave) {
                        double cantidad = Double.parseDouble(cantidadString);
                        total += cantidad;
                    }
                } catch (Exception e) {
                    System.out.println("La fecha no coincide");
                }
            }
            System.out.println("El total de los Gastos en el mes " + mesClave + " es: " + total);

        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    //mostrarGastosCategoria
    private static void mostrarGastosCategoria(Scanner scanner) {
        System.out.print("Introduce la categoria a buscar:");
        String categoriaClave = scanner.nextLine().toLowerCase();

        try (BufferedReader reader = new BufferedReader(new
                FileReader(GastosMejoradas))) {
            String linea;
            boolean encontrada = false;

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";", 5);


                if (partes.length < 5) {
                    // ignorar líneas que no tienen el formato deseado
                    System.out.println("Formato de línea incorrecto, se ignorará: " + linea);
                    continue;
                }

                String categoria = partes[2].trim().toLowerCase();

                if (categoria.contains(categoriaClave)) {
                    System.out.println("Nombre: " + partes[0]);
                    System.out.println("Fecha: " + partes[1]);
                    System.out.println("Categoría: " + partes[2]);
                    System.out.println("Descripción: " + partes[3]);
                    System.out.println("Cantidad: " + partes[4]);
                    System.out.println("-------------------------");
                    encontrada = true;
                }

            }

            if (!encontrada) {
                System.out.println("No se encontraron Gastos con esa palabra clave.");
            }

        } catch (IOException e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    //editarGastos
    private static void editarGastos(Scanner scanner) {
        System.out.print("Introduce la palabra clave a buscar: ");
        String palabraClave = scanner.nextLine().toLowerCase();
        File file = new File(GastosMejoradas);
        StringBuilder contenidoActualizado = new StringBuilder();
        boolean encontrado = false;

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {
            String linea;

            System.out.println("\nResultados de la búsqueda inicial:\n");
            // bus car gastos
            while ((linea = reader.readLine()) != null) {
                if (linea.toLowerCase().contains(palabraClave)) {
                    String[] partes = linea.split(";", -1);
                    if (partes.length >= 5) {
                        System.out.println("Nombre: " + partes[0].trim());
                        System.out.println("Fecha: " + partes[1].trim());
                        System.out.println("Categoría: " + partes[2].trim());
                        System.out.println("Descripción: " + partes[3].trim());
                        System.out.println("Cantidad: " + partes[4].trim());
                        System.out.println("---");
                        encontrado = true;

                        // editar nombre
                        System.out.print("Introduce el nuevo nombre (deja en blanco para no cambiar): ");
                        String nuevoNombre = scanner.nextLine();
                        if (!nuevoNombre.isEmpty()) {
                            partes[0] = nuevoNombre;
                        }

                        // editar fecha
                        System.out.print("Introduce la nueva fecha (formato YYYY-MM-DD) (deja en blanco para no cambiar): ");
                        String nuevaFecha = scanner.nextLine();
                        if (!nuevaFecha.isEmpty()) {
                            partes[1] = nuevaFecha;
                        }

                        //editar categoría
                        System.out.print("Introduce la nueva categoría (deja en blanco para no cambiar): ");
                        String nuevaCategoria = scanner.nextLine();
                        if (!nuevaCategoria.isEmpty()) {
                            partes[2] = nuevaCategoria;
                        }

                        //editar descripción
                        System.out.print("Introduce la nueva descripción (deja en blanco para no cambiar): ");
                        String nuevaDescripcion = scanner.nextLine();
                        if (!nuevaDescripcion.isEmpty()) {
                            partes[3] = nuevaDescripcion;
                        }

                        //editar cantidad
                        System.out.print("Introduce la nueva cantidad (deja en blanco para no cambiar): ");
                        String cantidadInput = scanner.nextLine();
                        if (!cantidadInput.isEmpty()) {
                            try {
                                double nuevaCantidad = Double.parseDouble(cantidadInput);
                                partes[4] = String.valueOf(nuevaCantidad);
                            } catch (NumberFormatException e) {
                                System.out.println("Formato de cantidad incorrecto. Se mantendrá la cantidad actual: " + partes[4]);
                            }
                        }

                        // sobreescribir la linea modificada
                        contenidoActualizado.append(String.join(";", partes)).append("\n");
                        System.out.println("El gasto ha sido editado correctamente!");
                    } else {
                        System.out.println("Formato de línea incorrecto: " + linea);
                        contenidoActualizado.append(linea).append("\n");
                    }
                } else {
                    //si no mantiene las lineas igual que antes
                    contenidoActualizado.append(linea).append("\n");
                }
            }

        } catch (Exception e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
        }

        //sobreescribir el archivo
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.write(contenidoActualizado.toString());
        } catch (Exception e) {
            System.out.println("Error al sobreescribir el archivo: " + e.getMessage());
        }
    }

    //eliminarGastos
    private static void eliminarGastos(Scanner scanner) {
        System.out.print("Introduce el titulo del gasto: ");
        String palabraClave = scanner.nextLine().toLowerCase();
        //crear un nuevo archivo temporal
        File file = new File(GastosMejoradas);
        File fileTemporal = new File("temporal.txt");
        boolean encontrado = false;

        List<String> lineas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;

            System.out.println("\nResultados de la búsqueda inicial: \n");
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea); // Agregar cada línea a la lista
                if (linea.toLowerCase().contains(palabraClave)) {
                    String[] partes = linea.split(";", 5);
                    System.out.println("Nombre: " + partes[0]);
                    System.out.println("Fecha: " + partes[1]);
                    System.out.println("Categoría: " + partes[2]);
                    System.out.println("Descripción: " + partes[3]);
                    System.out.println("Cantidad: " + partes[4]);
                    System.out.println("---");
                    encontrado = true;
                }
            }

            if (!encontrado) {
                System.out.println("No se encontró ningún gasto.");
                return; // Salimos si no se encontraron gastos
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return; // Salimos si hay un error al leer el archivo
        }

        // confirmar la eliminación
        System.out.print("¿Deseas eliminar todos los gastos que coinciden? (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        if (!confirmacion.equals("s")) {
            System.out.println("Operación cancelada.");
            // Salimos si no se confirma la eliminación
            return;
        }

        // escribir en el archivo temporal, quitando los gastos eliminados
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileTemporal))) {
            System.out.println("\nEliminando gastos...\n");

            for (String linea : lineas) {
                if (!linea.toLowerCase().contains(palabraClave)) {
                    // Escribir la línea sin cambios en el archivo temporal
                    writer.println(linea);
                } else {
                    encontrado = true; // Se ha encontrado y eliminado al menos un gasto
                }
            }

            // renombrar archivo temporal al original
            if (encontrado) {
                file.delete(); // Eliminar el archivo original
                fileTemporal.renameTo(file); // Renombrar el temporal al original
                System.out.println("Los gastos han sido eliminados correctamente.");
            } else {
                System.out.println("No se encontraron gastos para eliminar.");
            }
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    //buscarFechaGastos
    private static void buscarFechaGastos(Scanner scanner) {
        System.out.print("Introduce la fecha del gasto: ");
        System.out.print("Introduce el día: ");
        int dia = scanner.nextInt();
        System.out.print("Introduce el mes: ");
        int mes = scanner.nextInt();
        System.out.print("Introduce el año: ");
        int anio = scanner.nextInt();
        scanner.nextLine();

        String fechaClave = String.format("%04d-%02d-%02d", anio, mes, dia);

        try (BufferedReader reader = new BufferedReader(new
                FileReader(GastosMejoradas))) {
            String linea;
            boolean encontrada = false;

            System.out.println("\nResultados de la búsqueda por fecha:\n");
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";", 5); // Suponiendo que los datos están separados por ';'
                String fechaGasto = partes[1].trim(); // La fecha está en la segunda posición

                if (fechaGasto.equals(fechaClave)) {
                    System.out.println("Nombre: " + partes[0]);
                    System.out.println("Fecha: " + partes[1]);
                    System.out.println("Categoría: " + partes[2]);
                    System.out.println("Descripción: " + partes[3]);
                    System.out.println("Cantidad: " + partes[4]);
                    System.out.println("---");
                    encontrada = true;
                }
            }

            if (!encontrada) {
                System.out.println("No se encontraron gastos para la fecha especificada");
            }

        } catch (Exception e) {
            System.out.println("Error al buscar los gastos: " + e.getMessage());
        }
    }

    //exportarGastos
    private static void exportarGastos(Scanner scanner) {
        File exportado = new File("gastos_exportados.txt");
        try (BufferedReader reader = new BufferedReader(
                new FileReader(GastosMejoradas));
             PrintWriter writer = new PrintWriter(new FileWriter(exportado))) {

            String linea;
            System.out.println("Exportando gastos a " + exportado.getAbsolutePath() + "...");

            // leer todas las lineas de GastosMejoradas y escribirla en exportado
            while ((linea = reader.readLine()) != null) {
                writer.println(linea);
            }

            System.out.println("Exportación completada! :)");

        } catch (IOException e) {
            System.out.println("Error al exportar los gastos: " + e.getMessage());
        }
    }

    //mostrarEstadisticasGastos
    private static void mostrarEstadisticasGastos(Scanner scanner) {
        double totalGastos = 0;
        int numeroGastos = 0;
        double gastoMinimo = Double.MAX_VALUE;
        double gastoMaximo = Double.MIN_VALUE;

        try (BufferedReader reader = new BufferedReader(
                new FileReader(GastosMejoradas))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";", 5);
                if (partes.length == 5) {
                    try {
                        // eliminar espacios en blanco y el punto final
                        String cantidadStr = partes[4].trim(); // La cantidad es la quinta parte
                        cantidadStr = cantidadStr.replaceAll("\\s+", ""); // Eliminar espacios en blanco
                        if (cantidadStr.endsWith(".")) { // Eliminar punto al final si está solo
                            cantidadStr = cantidadStr.substring(0, cantidadStr.length() - 1);
                        }

                        double cantidad = Double.parseDouble(cantidadStr);
                        totalGastos += cantidad;
                        numeroGastos++;

                        if (cantidad < gastoMinimo) {
                            gastoMinimo = cantidad;
                        }

                        if (cantidad > gastoMaximo) {
                            gastoMaximo = cantidad;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Formato de cantidad incorrecto en la línea: " + linea);
                    }
                }
            }

            double gastoPromedio = (numeroGastos > 0) ? (totalGastos / numeroGastos) : 0;

            System.out.println("Estadísticas de Gastos:");
            System.out.println("Total de Gastos: " + totalGastos);
            System.out.println("Número de Gastos: " + numeroGastos);
            System.out.println("Gasto Promedio: " + gastoPromedio);

            if (numeroGastos > 0) {
                System.out.println("Gasto Mínimo: " + (gastoMinimo == Double.MAX_VALUE ? 0 : gastoMinimo));
                System.out.println("Gasto Máximo: " + (gastoMaximo == Double.MIN_VALUE ? 0 : gastoMaximo));
            } else {
                System.out.println("No se encontraron gastos para mostrar estadísticas.");
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo de gastos: " + e.getMessage());
        }
    }
}