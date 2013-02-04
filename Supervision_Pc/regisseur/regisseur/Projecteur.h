#using <mscorlib.dll>

using namespace System;


class projo
{
	int puissance;
	int num_projecteur;
	unsigned char mes_projo[512];//declaration du tableau de 512 projo (aussi égale a la trame dmx)
	void fondu_au_noir();
	void varier_puissance();
};