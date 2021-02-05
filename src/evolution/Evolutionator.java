package evolution;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.JOptionPane;

import Algorithm.AbstractAlgorithm;
import Algorithm.MyFirstSearchEngine;
import Algorithm.Pipeline;
import Algorithm.SimplePipeline;
import FitnessEvaluation.FitnessFunction;
import Main.AbstractRun;
import Mapper.GEGrammar;
import Operator.*;
import Operator.Operations.*;
import Util.Constants;
import Util.Random.MersenneTwisterFast;
import Util.Statistics.IndividualCatcher;
import Util.Statistics.StatCatcher;

/**
 * Run main class. 
 * Steps to setup the algorithm. 
 * Create the operators you want to use eg: mutation, selection. 
 * Create specific operations eg: Int flip mutation, Tournament Selection.
 * Add the operations to the operators
 * Set the population in each operator.
 * Add opertors to the init pipeline in the desired order of execution.
 * Add operators to the loop pipeline in the desired order of execution.
 * Create a main for the algorithm to run this needs to call init, setup and run(int number_of_iterations) 
 *      (or the step() method can be used in a loop)
 * @author erikhemberg
 */
@SuppressWarnings("serial")
public class Evolutionator extends AbstractRun {

    private long startTime;
	private Properties propertiesFile;
	
    /** Creates a new instance of Run */
    public  Evolutionator() {
    	
        this.rng = new MersenneTwisterFast();
       super.propertiesFilePath = GUI.mainGui.getPropertiesFileName();
        this.startTime = System.currentTimeMillis();
    }
    protected void init() {
        long st = System.currentTimeMillis();
        
        StatCatcher stats = new StatCatcher(Integer.parseInt(this.properties.getProperty("generations")));
        
        // Call init() in the algorithm
        // Header for the column output
       
        this.algorithm.init();
        long et = System.currentTimeMillis();
        this.collector = this.getCollector();
        if (this.collector != null) {

        }
    }
    public String getProperties(String p)
    
    {
    	return this.properties.getProperty(p);	
    }
    
    
    
    public void experiment() {
        
    	

            //Read the command-line arguments
            
                //Initialize timing the execution
                long st = System.currentTimeMillis();    
                //Create the Main object
                //Setup the algorithm
                this.setup();
                //Initialize the algorithm
                this.init();
                //Hack for number of iterations!!?? Create a proper method
                int its = this.run();
                //Print collected data
                this.printStuff();
                //Time the execution
                long et = System.currentTimeMillis();
                System.out.println("Done running: Total time(Ms) for " 
                            + its + " generations was:"+(et-st));

    }
    
    /**
     * Setup the algorithm. Read the properties. Create the modules(Operators) 
     * and operations
     */
    
    public Collector getCollector() {
        if (((AbstractAlgorithm) this.getAlgorithm()).getLoopPipeline() != null) {
            Collection<Module> m = ((AbstractAlgorithm) this.getAlgorithm()).getLoopPipeline().getModules();
            Iterator<Module> iM = m.iterator();
            Module mo;
            while (iM.hasNext()) {
                mo = iM.next();
                if (mo.getClass().getName().equals(Collector.class.getName())) {
                    if (((Collector) mo).getOperation() instanceof StatisticsCollectionOperation) {
                        return (Collector) mo;
                    }
                }
            }
        }
        return null;
    }
    public void setup() {
        String[] args = null;
		//Read properties
        this.readProperties(args);
        //set rng seed
        this.setSeed();
        /*
         * Operators and Operations
         * Example of setting up an algorithm. 
         * For specific details of operators and operations used see 
         * the respective source or API
         */        
        
        //Grammar
        
        GEGrammar grammar = getGEGrammar(this.properties);
        
        //Search engine
        MyFirstSearchEngine alg = new MyFirstSearchEngine();
        //Initialiser
        initialiser = getInitialiser(grammar, this.rng, this.properties);
        //Crossover
        CrossoverOperation singlePointCrossover = new SinglePointCrossover(this.rng, this.properties);
        CrossoverModule crossoverModule = new CrossoverModule(this.rng, singlePointCrossover);
        //Mutation
        MutationOperation mutation = getMutationOperation(this.rng, this.properties);
        MutationOperator mutationModule = new MutationOperator(this.rng, mutation);
        //Selection
        SelectionOperation selectionOperation = getSelectionOperation(this.properties, this.rng);
        SelectionScheme selectionScheme = new SelectionScheme(this.rng, selectionOperation);
        //Replacement
        ReplacementOperation replacementOperation = new ReplacementOperation(this.properties);
        JoinOperator replacementStrategy = this.getJoinOperator(this.properties, this.rng, selectionScheme.getPopulation(), replacementOperation);
        //Elite selection
        EliteOperationSelection eliteSelectionOperation = new EliteOperationSelection(this.properties);
        SelectionScheme eliteSelection = new SelectionScheme(this.rng, eliteSelectionOperation);
        //Elite replacement
        EliteReplacementOperation eliteReplacementOperation = new EliteReplacementOperation(this.properties);
        EliteReplacementOperator eliteReplacementStrategy = new EliteReplacementOperator(this.rng, eliteSelection.getPopulation(), eliteReplacementOperation);
        //Fitness function
        FitnessFunction fitnessFunction = getFitnessFunction(this.properties);
        MyFitnessEvaluationOperation fitnessEvaluationOperation = new MyFitnessEvaluationOperation(fitnessFunction);
        fitnessEvaluationOperation.setProperties(properties);
        FitnessEvaluator fitnessEvaluator = new FitnessEvaluator(this.rng, fitnessEvaluationOperation); 


        
      //Statistics
        StatCatcher stats = new StatCatcher(Integer.parseInt(this.properties.getProperty("generations")));
        IndividualCatcher indCatch = new IndividualCatcher(this.properties);
        stats.addTime(startTime);//Set initialisation time for the statCatcher (Not completly accurate here)
        MyStatisticsCollectionOperation statsCollection = new MyStatisticsCollectionOperation(stats, indCatch, this.properties);
        super.collector = new Collector(statsCollection);

        
        /*
         * Init
         */
        //Pipeline
        Pipeline pipelineInit = new SimplePipeline();
        alg.setInitPipeline(pipelineInit);
        //FitnessEvaluator for the init pipeline
        FitnessEvaluator fitnessEvaluatorInit = new FitnessEvaluator(this.rng, fitnessEvaluationOperation);
        //Set population
        fitnessEvaluatorInit.setPopulation(initialiser.getPopulation());// Fitness evaluation will be performed on the selected population, nuevo
        fitnessEvaluator.setPopulation(selectionScheme.getPopulation());
        collector.setPopulation(initialiser.getPopulation());
        //Add modules to pipeline
        pipelineInit.addModule(initialiser);
        pipelineInit.addModule(fitnessEvaluatorInit);
        pipelineInit.addModule(collector);                  
        /*
         * Loop
         */
        //Pipeline
        Pipeline pipelineLoop = new SimplePipeline();
        alg.setLoopPipeline(pipelineLoop);
        //Set population passing
        selectionScheme.setPopulation(initialiser.getPopulation());
        replacementStrategy.setPopulation(initialiser.getPopulation());
        crossoverModule.setPopulation(selectionScheme.getPopulation());
        mutationModule.setPopulation(selectionScheme.getPopulation());
        fitnessEvaluator.setPopulation(selectionScheme.getPopulation());
        eliteSelection.setPopulation(initialiser.getPopulation());
        eliteReplacementStrategy.setPopulation(initialiser.getPopulation());
        collector.setPopulation(initialiser.getPopulation());
        //Add modules to pipeline
        pipelineLoop.addModule(eliteSelection); //Remove elites
        pipelineLoop.addModule(selectionScheme);
        pipelineLoop.addModule(crossoverModule);
        pipelineLoop.addModule(mutationModule);
        pipelineLoop.addModule(fitnessEvaluator);
        pipelineLoop.addModule(replacementStrategy);
        pipelineLoop.addModule(eliteReplacementStrategy); //Add elites
        pipelineLoop.addModule(collector);
        
        this.algorithm = alg;
     
    }

    
	/**   
     */
    private void setSeed() {
        long seed;
        if(this.properties.getProperty(Constants.RNG_SEED)!=null) {
            seed = Long.parseLong(this.properties.getProperty(Constants.RNG_SEED));
            this.rng.setSeed(seed);
        }
    }
    
    
	public String getPhenotipe() {
			return this.getBestIndiv().toString();

		
	}
	@Override
	public void experiment(String[] args) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setup(String[] args) {
		// TODO Auto-generated method stub
		
	}

    /**
     * Run
     * @param args arguments
     */
    
}
