package cine;


import anotacion.Programacion2;

@Programacion2(
        nombreAutor1 = "Ignacio",
        apellidoAutor1 = "de las Alas-Pumariño Martínez",
        emailUPMAutor1 = "i.dmartinez@alumnos.upm.es",
        nombreAutor2 = "Adrián Arley",
        apellidoAutor2 = "Ochoa",
        emailUPMAutor2 = "aa.ochoa@alumnos.upm.es"
)

/**
 * Created by Nacho on 06/04/2017.
 */
public class Sesion {


    private String hora;
    private int asientosDisponibles;
    private int sigIdCompra;
    private int[][] estadoAsientos;

    public Sesion (String hora, int filas, int columnas){

        this.hora = hora;
        this.estadoAsientos = new int[filas][columnas];
        this.asientosDisponibles = filas * columnas;
        this.sigIdCompra = 1;
    } //De constructor

    public void comprarEntrada (int fila, int columna){ //Método para comprar entradas en una sesión
        estadoAsientos[fila-1][columna-1] = sigIdCompra;
        sigIdCompra++;
        asientosDisponibles--;

    }//De comprarEntrada

    public int getIdEntrada (int fila, int columna){ //Devuelve el id de venta asociado a una fila y una columna
        return estadoAsientos[fila-1][columna-1];

    }//De getIdEntrada

    public int getButacasDisponiblesSesion (){ //Devuelve el número de butacas disponibles en la sesión
        return asientosDisponibles;

    }//De getButacasDisponiblesSesion

    public String getHora (){ //Devuelve la hora de una sesión
        return hora;

    }//De getHora

    public char[][] getEstadoSesion (){ //Devuelve la matriz asociada al estado de una sesión donde un asiento vacío está representado por "O" y un asiento ocupado por "#"
        char[][] estadoSesion = new char[estadoAsientos.length][estadoAsientos[0].length];
        for (int i = 0; i < estadoAsientos.length; i++){
            for (int j = 0; j < estadoAsientos[0].length; j++){
                if (estadoAsientos[i][j] != 0){
                    estadoSesion[i][j] = '#';
                }
                else {
                    estadoSesion[i][j] = 'O';
                }
            }
        }
        return estadoSesion;

    }//De getEstadoSesion

    public String recogerEntradas (int id){ //Dado un id de venta, devuelve un String con la hora de la sesión y los asientos asociados a ese id
        String entrada = new String(hora + "+");
        String aux = "";
        for (int i = 0; i < estadoAsientos.length; i++){
            for (int j = 0; j < estadoAsientos[0].length; j++){
                if (estadoAsientos[i][j] == id){
                    String asientos = (i+1) + "," + (j+1) + "+";
                    aux = aux + asientos;
                }
            }
        }

        if (aux.equals("")){
            return null;
        }

        else{
            return entrada + aux;
        }

    }//De recogerEntradas

    public ButacasContiguas recomendarButacasContiguas (int noButacas){ //Método que dado un numero de butacas, recomienda la mejor posición dentro de la sala, primero buscando en la mitad trasera y luego en la mitad delantera
        int butacasDisponibles = 0;
        int vaux = 0;
        boolean done = false;
        ButacasContiguas butacasContiguas = null;
        for (int i = (estadoAsientos.length+1)/2+1; i <= estadoAsientos.length && butacasDisponibles != noButacas && !done; i++){
            for (int j = estadoAsientos[0].length-1; j >= 1; j--){
                if (estadoAsientos[i-1][j] == 0){
                    for (int k = 0; k < noButacas ;k++){
                        if (j-k >= 0 && estadoAsientos[i-1][j-k] == 0){
                            butacasDisponibles++;
                        }else{
                            butacasDisponibles=0;
                        }
                        vaux = j-k+1;
                    }
                }
                if (butacasDisponibles == noButacas){
                    ButacasContiguas aux = new ButacasContiguas(i,vaux,noButacas);
                    butacasContiguas = aux;
                    done = !done;
                }
            } //Búsqueda en la mitad trasera de la sala
        }
        for (int i = (estadoAsientos.length+1)/2; i > 0 && butacasDisponibles != noButacas && !done; i--){
            for (int j = estadoAsientos[0].length-1; j >= 1; j--){
                if (estadoAsientos[i-1][j] == 0){
                    for (int k = 0; k < noButacas ;k++){
                        if (j-k >= 0 && estadoAsientos[i-1][j-k] == 0){
                            butacasDisponibles++;
                        }else{
                            butacasDisponibles=0;
                        }
                        vaux = j-k+1;
                    }
                }
                if (butacasDisponibles == noButacas){
                    ButacasContiguas aux = new ButacasContiguas(i,vaux,noButacas);
                    butacasContiguas = aux;
                    done = !done;
                }
            }//Búsqueda en la mitad delantera de la sala;
        }
        return butacasContiguas;
    }//De recomendarButacasContiguas

    public void comprarEntradasRecomendadas (ButacasContiguas butacas){ //Compra las entradas recomendadas por el método recomendarButacasContiguas
        for (int i = 0; i < butacas.getNoButacas(); i++){
            estadoAsientos[butacas.getFila()-1][butacas.getColumna()-1+i] = sigIdCompra;
        }
        sigIdCompra++;
        asientosDisponibles = asientosDisponibles - butacas.getNoButacas();
    }//De comprarEntradasRecomendadas

    public boolean equals (Sesion obj){ //Método que comprueba si dos sesiones son iguales. Se considera que si lo son si coincide sus atributos hora
        return this.getHora().compareTo(obj.getHora()) == 0;
    } //De equals
}//De Sesion
