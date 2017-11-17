package it.uniroma1.lcl.mynn;

public class Identity implements FunzioneDiAttivazione
{
	@Override
	public double process(double sommaPesata)
	{
		return sommaPesata;
	}
	@Override
	public double processDerivata(double sommaPesata)
	{
		return 1.0;
	}
}
