/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm3;

import java.util.Random;

/**
 *
 * @author Scott
 */
public class Individual {
    //for function 1
//    public static final int geneSize = 8;
    //for function 2 and 3
    public static final int geneSize = 10;
    //for function 1
//    private int[] genes = new int[geneSize];
    //for function 2 and 3
    private double[] genes = new double[geneSize];
    //for function 1
//    private int fitnessValue;
    //for function 2 and 3
    private double fitnessValue;
    
    public Individual(){
        
    }
    
    //using ints for function 1
//    public int getFitnessValue(){
    //using double for function 2 and 3
    public double getFitnessValue(){
        return fitnessValue;
    }
    //using ints for function 1
//    public void setFitnessValue(int fitnessValue){
    //using double for function 2 and 3
    public void setFitnessValue(double fitnessValue){
        this.fitnessValue = fitnessValue;
    }
    
    //using ints for function 1
//    public void setGene(int index, int gene){
    //using doubles for function 2 and 3
    public void setGene(int index, double gene){
        this.genes[index] = gene;
    }
    
    //for function 1
//    public int getGene(int index){
    //for function 2 and 3
    public double getGene(int index){
        return genes[index];
    }
    
    //function 1 and 2
//    public void randGenes(){
//        Random rand = new Random();
//        for (int i =  0; i < geneSize; i++){
//            this.setGene(i, rand.nextInt(2));
//        }
//    }
    
    //function 3
    public void randGenes(){
        Random rand = new Random();
        for (int i = 0; i < geneSize; i++){
            if (rand.nextDouble() < 0.5){
                this.setGene(i,rand.nextDouble() * -5.12);
            }
            else{
                this.setGene(i, rand.nextDouble() * 5.12);
            }
        }
    }
    //function 1 and 2
//    public void mutate(){
//        Random rand = new Random();
//        int index = rand.nextInt(geneSize);
//        this.setGene(index, 1 - this.getGene(index)); //flip
//    }
    
    //function 3
    public void mutate(){
        Random rand = new Random();
        int index = rand.nextInt(geneSize);
        double x = rand.nextDouble() * 5.12;
        if (rand.nextInt(2) < 0.5){
                x -= 2 * x;
            }
        this.setGene(index,rand.nextDouble() * 5.12);
    }
    
    
    //Evaluate for function 1
//    public int evaluate(){
//        int fitness = 0;
//        for (int i = 0; i < geneSize; i++){
//            fitness += this.getGene(i) * Math.pow(2,geneSize - 1 - i);
//        }
//        fitness *= fitness;
//        this.setFitnessValue(fitness);
//        return fitness;
//    }
    
    
    //evaluate for function 2
//    public double evaluate(){
//        double x = 0, y = 0, fitness;
//        int i;
//        for (i = 1; i < geneSize/2; i++){
//            x += this.getGene(i);
//        }
//        for (i = geneSize/2 + 1; i < geneSize; i++){
//            y += this.getGene(i);
//        }
//        if (this.getGene(0) == 1){
//            x = -x;
//        }
//        if (this.getGene(5) == 1){
//            y = -y;
//        }
//        
//        fitness = (0.26 * ((x * x) + (y * y)) - 0.48 * x * y);
////        System.out.println(x + " " + y);
//        
//        
//        this.setFitnessValue(fitness);
//        return fitness;
//    }
    
    //evaluate for function 3
    public double evaluate(){
        double fitness = 0;
        for (int i = 0; i < geneSize; i++){
            //System.out.println(this.getGene(i));
            double x = this.getGene(i);
            double r = Math.toRadians(2  * Math.PI) * x;
            fitness += (x * x) - (10 * (Math.cos(r)));
        }
        fitness += 10 * geneSize;
        
        this.setFitnessValue(fitness);
        return fitness;
    }
    
    
}
