/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javanunes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;




public class ReSeLinux {

   public static String caminho(String buffer) {
              int   inicioPath =0;
              int fimPath = 0;
               inicioPath = buffer.indexOf("/");
               fimPath = buffer.indexOf("   ");
               /// se encontra / no começo 
               if( inicioPath > -1){
                    // se encontra espaço no final
                    if(fimPath > -1)
                       return buffer.substring( inicioPath, fimPath );
               }  
               return null;
   }
    
   public static String contexto(String buffer) {
              int   inicioPath =0;
              String[] params = null;
               inicioPath = buffer.indexOf("system_u:");
               /// se encontra system_u:  no começo 
               if( inicioPath > -1){
                   params =    buffer.substring(inicioPath).split(":");
                   return params[2];
                    //return buffer.substring( inicioPath );
               }      
               return null;
   }
    
   public static void  arquivo(){
       final String  file = "/tmp/selinux.txt";
       String buffer = "";
       String root= null;
       String params= null;
       String comando1 = null;
       String comando2 = null;
       try(BufferedReader br = new BufferedReader(new FileReader(file))){    
           while(br.ready()){
               buffer = br.readLine();
               root = caminho(buffer);
               params = contexto(buffer);
               if(root != null && params != null){
                    comando1 =("semanage fcontext -d "+ root);
                   comando2 = ("chcon  -t  " +params + "  " +  root);
                   Runtime.getRuntime().exec(comando1);
                   Runtime.getRuntime().exec(comando2);
                   System.out.println(comando2);
               }
           }
       }
       catch(Exception e){
            System.out.println("Erro ao ler arquivo de contextos "+e);
       }
   }
  
   public static void tela(){
       System.out.println("\n* Esse utilitário ajuda você importar os contextos selinux de uma máquina para outra                              *");
       System.out.println("* vá na outra máquina que deseja exportar e digite semanage fcontext -l > /tmp/selinux.txt                     * ");
       System.out.println("* pegue o selinux.txt criado e jogue na pasta /tmp/ desse computador                                                                  *");
       System.out.println("* rode esse programa e diga S para ele pegar os rotulos de conexto do selinux.txt e aplica-los aqui      *");
   }
    
    public static void main(String[] args) {
        // TODO code application logic here
           String resposta= null;
           tela();
            Scanner  teclado = new Scanner(System.in);
            System.out.print("Isso pode demorar: Deseja aplicar os contextos contidos em /tmp/selinux.txt nesse servidor? [ S / N ] : ");
            resposta = teclado.nextLine();
            if(resposta.equals("S") || resposta.equals("s") ){
                 arquivo();
            }
            else{
                System.out.println("\nTchau!");
            }
    } //main 
    
}
