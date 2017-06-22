package cine;

import anotacion.Programacion2;
import list.ArrayList;

@Programacion2(
        nombreAutor1 = "Ignacio",
        apellidoAutor1 = "de las Alas-Pumariño Martínez",
        emailUPMAutor1 = "i.dmartinez@alumnos.upm.es",
        nombreAutor2 = "Adrián Arley",
        apellidoAutor2 = "Ochoa",
        emailUPMAutor2 = "aa.ochoa@alumnos.upm.es"
)

/**
 * Created by Nacho on 5/4/17.
 */
public class Sala {

    private String pelicula;
    private ArrayList<Sesion> sesiones;
    private int filas;
    private int columnas;

    public Sala (String pelicula, String[] horasSesiones, int filas, int columnas){

        this.pelicula = pelicula;
        this.sesiones = new ArrayList<Sesion>();
        for (int i = 0; i < horasSesiones.length; i++){
            int pos = 0;
            Sesion sesion = new Sesion(horasSesiones[i], filas, columnas);
            for (int j = 0; sesiones.size() > j && j < sesiones.size(); j++){
                if (sesiones.get(j).getHora().compareTo(horasSesiones[i]) < 0){
                    pos++;
                }
            }
            sesiones.add(pos, sesion);
        }
        this.filas = filas;
        this.columnas = columnas;

    }//De constructor

    public void comprarEntrada (int sesion, int fila, int columna){ //Método para comprar entradas en una sesión dada
        if(sesiones.size() > sesion - 1){
            sesiones.get(sesion- 1).comprarEntrada(fila, columna);
        }
    }//De comprarEntrada

    public int getIdEntrada (int sesion, int fila, int columna){ //Devuelve el id de venta asociado a una sesión, fila y  columna
        if(sesiones.size() > sesion - 1){
            return sesiones.get(sesion-1).getIdEntrada(fila, columna);
        }
        else{
            return 0;
        }
    }//De getIdEntrada

    public String[] getHorasDeSesionesDeSala (){ //Método que devuelve las horas de sesiones asociadas a una sala
        String[] horasSesiones = new String[sesiones.size()];
        String aux;
        for (int i = 0; i < sesiones.size(); i++){
            aux = sesiones.get(i).getHora();
            horasSesiones[i] = aux;
        }

        return horasSesiones;

    }//De getHorasDeSesionesDeSala

    public char[][] getEstadoSesion (int sesion){ //Devuelve la matriz asociada al estado de una sesión donde un asiento vacío está representado por "O" y un asiento ocupado por "#"
        return sesiones.get(sesion-1).getEstadoSesion();

    }//De getEstadoSesion

    public String getPelicula (){ //Devuelve la película asociada a una sala
        return pelicula;

    }//De getPelicula

    public String recogerEntradas (int id, int sesion){ //Dado un id de venta y una sesión, devuelve un String con la película, la hora de la sesión y los asientos asociados a ese id
        if (this.sesiones.get(sesion-1).recogerEntradas(id) == null){
            return null;

        }
        else{
            return this.pelicula + "@" + this.sesiones.get(sesion-1).recogerEntradas(id);
        }

    }//De recogerEntradas

    public int getButacasDisponiblesSesion (int sesion){ //Devuelve el número de butacas disponibles para una sesión
        return sesiones.get(sesion-1).getButacasDisponiblesSesion();

    }//De getButacasDisponiblesSesion

    public ButacasContiguas recomendarButacasContiguas (int noButacas, int sesion){ //Método recomendar butacas para una sesión dada
        return sesiones.get(sesion-1).recomendarButacasContiguas(noButacas);

    }//De recomendarButacasContiguas

    public void comprarEntradasRecomendadas (int sesion, ButacasContiguas butacas){ //Compra las entradas recomendadas por el método recomendarButacasContiguas
        sesiones.get(sesion-1).comprarEntradasRecomendadas(butacas);

    }//De comprarEntradasRecomendadas

    public void incluirSesion (String horaSesion){ //Añade una sesión a una sala
        int pos = 0;
        Sesion sesion = new Sesion(horaSesion, filas, columnas);
        for (int i = 0; i < sesiones.size(); i++){
            if (sesiones.size() == 0 || sesiones.get(i).getHora().compareTo(horaSesion) >= 0){
                i = sesiones.size();
            }
            else{
                pos++;
            }

        }
        sesiones.add(pos, sesion);
    }//De incluirSesion

    public void borrarSesion (String horaSesion){ //Elimina una sesión de una sala
        int i = 0;
        while (i < sesiones.size()){
            if(sesiones.size() == 0){
                i = sesiones.size() + 1;
            }
            else if (sesiones.get(i).getHora().equals(horaSesion)){
                sesiones.removeElementAt(i);
            }
            i++;
        }
    }//De borrarSesion
}//De Sala
