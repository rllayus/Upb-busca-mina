/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.busca.mina.bl;

/**
 *
 * @author rlaredo
 */
public record Tablero(Celda[][] celdas, int filas, int columnas, EstadoJuego estado) {
    public Tablero(Celda[][] celdas) {
        this(celdas, celdas.length, celdas[0].length, EstadoJuego.EN_CURSO);
    }
}
