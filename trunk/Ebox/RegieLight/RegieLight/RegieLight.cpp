// RegieLight.cpp : définit le point d'entrée pour l'application console.
//
#include "stdafx.h"
#include "conio.h"
#include <iostream>
#include "SocketServeur.h"

using namespace std;


int _tmain(int argc, _TCHAR* argv[])
{
	SocketServeur monserveur;
	cout << monserveur.InitialiserDll();
	cout << "\n";
	cout << monserveur.CreerSocket();

	getch();
	return 0;
}

