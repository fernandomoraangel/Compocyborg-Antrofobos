package music;


import java.util.Properties;

import FitnessEvaluation.FitnessFunction;
import Individuals.Individual;
import Individuals.Phenotype;


public class MusicEval implements FitnessFunction
{
	double fitness = 0;//Initialise fitness

	@Override
	public void setProperties(Properties i) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canCache() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public double evaluateString(Phenotype p) {
		Melody m = new Melody (p.getString());
		return m.calculeFitness();
    }

	@Override
	public void getFitness(Individual i) {
		// TODO Auto-generated method stub
		// System.out.println(i.getPhenotype().getString());
		i.getFitness().setDouble(this.evaluateString(i.getPhenotype()));
		//i.getFitness().setDouble(fitness);
	}
	
}
