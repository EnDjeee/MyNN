package it.uniroma1.lcl.mynn;

public class Logistic implements FunzioneDiAttivazione
{
	@Override
	public double process(double sommaPesata)
	{
		return (1.0 / (1 + Math.exp(-sommaPesata)));
	}
	@Override
	public double processDerivata(double sommaPesata)
	{
		return this.process(sommaPesata)*(1 - this.process(sommaPesata));
	}
}
