package it.uniroma1.lcl.mynn;

public class TanH implements FunzioneDiAttivazione
{
	@Override
	public double process(double sommaPesata)
	{
		return (2.0 / (1 + Math.exp(-2.0*sommaPesata))) - 1;
	}
	@Override
	public double processDerivata(double sommaPesata)
	{
		return 1 - Math.pow(this.process(sommaPesata), 2);
	}
}
