package it.uniroma1.lcl.mynn;

public class ReLU implements FunzioneDiAttivazione
{
	@Override
	public double process(double sommaPesata)
	{
		if(sommaPesata < 0) return 0;
		return sommaPesata;
	}
	@Override
	public double processDerivata(double sommaPesata)
	{
		if(sommaPesata < 0) return 0;
		return 1.0;
	}
}
