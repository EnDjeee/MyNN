package it.uniroma1.lcl.mynn;

public class Step implements FunzioneDiAttivazione
{
	@Override
	public double process(double sommaPesata)
	{
		if(sommaPesata < 0) return 0;
		return 1;
	}
	@Override
	public double processDerivata(double sommaPesata)
	{
		return 0.0;
	}
}
