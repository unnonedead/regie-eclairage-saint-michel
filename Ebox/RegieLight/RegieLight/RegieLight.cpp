// RegieLight.cpp : définit le point d'entrée pour l'application console.
//
#pragma once
#include "stdafx.h"
#include "conio.h"
#include <iostream>
#include "SocketServeur.h"
#include <windows.h>
#include <vector>
#include "ftd2xx.h"
#include <string>
#include "pthread.h"
#include "ClassDMX.h"

using namespace std;

void* Fonctionthread(void* t);


int _tmain(int argc, _TCHAR* argv[])
{
	int NbConnecter = 0;

	ClassDMX *monDmx;
	monDmx = new ClassDMX();
	SocketServeur *monserveur ;
	monserveur = new SocketServeur(monDmx);

	monDmx->ConfigurerPort();
	cout << monserveur->InitialiserDll()<< endl;
	cout << monserveur->CreerSocket()<< endl;
	cout << monserveur->BindSocket()<< endl;

	pthread_t monthreadSocket;
	pthread_t monthreadDMX;

	while(1)
	{
		for(int i=0; i<512;i++)
		{
			monDmx->TrameDmx[i] = 0;
		}

		//if(NbConnecter != 1)
		//{
		cout << monserveur->EcouterReseau()<< endl;
		cout << monserveur->AccepterConnection()<< endl;
		/*NbConnecter = 1;*/
		/*}*/

		pthread_create(&monthreadSocket,NULL,Fonctionthread,monserveur);
		pthread_create(&monthreadDMX,NULL,Fonctionthread,monDmx);

		pthread_join(monthreadSocket,NULL);
		pthread_join(monthreadDMX, NULL);
		pthread_cancel(monthreadSocket);
		monserveur->FermerConnection();
		system("cls");

		/*NbConnecter = 0;*/

	}

	getch();
	return 0;

}

void* Fonctionthread(void* t)
{
	((ClassThread*)t)->Execute();	
	return NULL;
}
