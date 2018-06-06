/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm3;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Scott
 */
public class Population {

    /**
     * @param args the command line arguments
     */
    final static  int elitism = 100;
    final static int popSize = 1000 + elitism;
    final static int maxIter = 500000;
    final static double crossoverRate = 0.75;
    //for function 1 and 2
    //final static double mutationRate = 0.001;
    //for function 3 as it uses the variable mutation rate
    static double mutationRate = 0.04;
    static double mutator = 0.8;
    final static int tournamentSize = 175;
    
    public static Random rand = new Random();
    
    private Individual[] population;
    private double totalFitness;
    
    public Population(){
        population = new Individual[popSize];
        
        for (int i = 0; i < popSize; i++){
            population[i] = new Individual();
            population[i].randGenes();
        }
        
        this.evaluate();
        
        
    }
    
    public void setPopulation(Individual[] newPop){
        System.arraycopy(newPop, 0, this.population, 0, popSize);
    }
    
    public double evaluate(){
        this.totalFitness = 0;
        for (int i = 0; i < popSize; i++){
            this.totalFitness += population[i].evaluate();
        }
        return this.totalFitness;
    }
    
    public Individual rouletteWheelSelection(){
        double randNum = rand.nextDouble() * this.totalFitness;
        int idx;
        for (idx = 0; idx < popSize && randNum > 0; idx++){
            randNum -= population[idx].getFitnessValue();
        }
        return population[idx-1];
    }
    
    public Individual tournamentSelection(){
        int[] tournament = new int[tournamentSize];
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomID = (int) (Math.random() * popSize);
            tournament[i] = randomID;
        }
        
        //double fittestValue = 15.75;
        double fittestValue = 150;
        int fittestPosition = 0;
        for (int i = 0; i < tournamentSize; i++){
            double currentFitness = population[tournament[i]].getFitnessValue();
            if (currentFitness < fittestValue){
                fittestPosition = tournament[i];
                fittestValue = currentFitness;
            }
        }
        
        return population[fittestPosition];
    }
    
    
    public Individual findBestIndividual(){
        int idxMax = 0;
        int idxMin = 0;
        double currentMax = 0.0;
        //double currentMin = 1.0;
        double currentMin = 100.0;
        double currentVal;
        
        for (int idx = 0; idx < popSize; idx++){
            currentVal = population[idx].getFitnessValue();
            if (currentMax < currentMin){
                currentMax = currentMin = currentVal;
                idxMax = idxMin = idx;
            }
            if (currentVal > currentMax){
                currentMax = currentVal;
                idxMax = idx;
            }
            if (currentVal < currentMin){
                currentMin = currentVal;
                idxMin = idx;
            }
        }
        
//        return population[idxMax]; //maximization
        return population[idxMin]; //minimization
    }
    
    //1 point crossover
    public static Individual[] crossover(Individual indiv1,Individual indiv2) {
        Individual[] newIndiv = new Individual[2];
        newIndiv[0] = new Individual();
        newIndiv[1] = new Individual();

        int randPoint = rand.nextInt(Individual.geneSize/2);
        int i;
        
        for (i=0; i<randPoint; ++i) {
            newIndiv[0].setGene(i, indiv1.getGene(i));
            newIndiv[1].setGene(i, indiv2.getGene(i));
        }
        for (; i<Individual.geneSize; ++i) {
            newIndiv[0].setGene(i, indiv2.getGene(i));
            newIndiv[1].setGene(i, indiv1.getGene(i));
        }
        
        //the following is 2 point crossover used for function 2
        
//        for (i=0; i<randPoint; ++i) {
//            newIndiv[0].setGene(i, indiv1.getGene(i));
//            newIndiv[1].setGene(i, indiv2.getGene(i));
//        }
//        for (; i<Individual.geneSize/2; ++i) {
//            newIndiv[0].setGene(i, indiv2.getGene(i));
//            newIndiv[1].setGene(i, indiv1.getGene(i));
//        }
//        for (; i < randPoint + (Individual.geneSize/2); i++){
//            newIndiv[0].setGene(i, indiv1.getGene(i));
//            newIndiv[1].setGene(i, indiv2.getGene(i));
//        }
//        for (; i < Individual.geneSize; i++){
//            newIndiv[0].setGene(i, indiv2.getGene(i));
//            newIndiv[1].setGene(i, indiv1.getGene(i));
//        }

        return newIndiv;
    }
    
    
    public static void main(String[] args) throws InterruptedException {
        Population pop = new Population();
        Individual[] newPop = new Individual[popSize];
        Individual[] indiv = new Individual[2];
        double[] bests = new double[1000];
        int bestCount = 0;
        boolean mutated = false;
        double stuckAt = 0;
        int sm = 0;
        
        //Current population
        //System.out.print("Total Fitness = " + pop.totalFitness);
        //System.out.print(" : Best Fitness = " + pop.findBestIndividual().getFitnessValue());
        System.out.println(pop.totalFitness/popSize);
        
        //main loop
        int count;
        for (int iter = 0; iter < maxIter; iter++){
            count = 0;
            for (int i = 0; i < elitism; i++){
                newPop[count] = pop.findBestIndividual();
                count++;
            }
            
            // build new population
            while (count < popSize){
                //roulette wheel selection for function 1
//                indiv[0] = pop.rouletteWheelSelection();
//                indiv[1] = pop.rouletteWheelSelection();
                
                //tournament selection for function 2 and 3
                indiv[0] = pop.tournamentSelection();
                indiv[1] = pop.tournamentSelection();
                
                
                //crossover
                if (rand.nextDouble() < crossoverRate){
                    indiv = crossover(indiv[0], indiv[1]);
                }
                
                //mutation
                if (rand.nextDouble() < mutationRate){
                    indiv[0].mutate();
                }
                if (rand.nextDouble() < mutationRate){
                    indiv[1].mutate();
                }
                
                //add to new population
                newPop[count] = indiv[0];
                newPop[count+1] = indiv[1];
                count += 2;
                
            }
            
            pop.setPopulation(newPop);
            
            //reevaluate new population
            pop.evaluate();
            //System.out.println(pop.totalFitness/popSize);
            //System.out.print("Total Fitness = " + pop.totalFitness);
            System.out.println(pop.findBestIndividual().getFitnessValue());
            //System.out.println(" : Average Fitness = " + pop.totalFitness/popSize);
            
            Individual bestIndiv = pop.findBestIndividual();
            
            //used to attempt to escape local minimum in function 3 by increasing
            //mutation rate temporarily
            if(sm > 0){
                sm++;
            }
            bests[bestCount] = bestIndiv.getFitnessValue();
            bestCount++;
            if (bestIndiv.getFitnessValue() == 0.0){
                TimeUnit.HOURS.sleep(1);
            }
            if (bestCount == 1000){
                if (bests[0] == bests[999]){
                    bestCount = 0;
                    mutationRate += mutator;
                    mutated = true;
                    stuckAt = bests[0];
                    sm = 1;
                }
                else {
                    bestCount = 0;
                }
                
            }
            else{
                if (mutated == true && bestIndiv.getFitnessValue() < stuckAt || sm == 1000){
                    mutationRate -= mutator;
                    mutated = false;
                    sm = 0;
                }
            }
            
        }
        
    }
    
}
