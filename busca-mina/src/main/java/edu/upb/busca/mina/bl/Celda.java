/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.busca.mina.bl;

/**
 *
 * @author rlaredo
 */
public record Celda(
        boolean esMina,
        int minasVecinas,
        boolean estaRevelada,
        boolean tieneBandera) {
}
