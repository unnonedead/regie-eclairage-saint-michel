#pragma once
#include "stdafx.h"
#include "ClassSocket.h"
#include "pthread.h"


char* ClassSocket::InitialiserDll() 
{	
	char* MesErr = "";

	//Déclaration des données pour WSAStartup
	WORD wVersionRequested;
	WSADATA wsaData;

	// Fixe la version de la DLL socket pour l'application à 2;
	wVersionRequested = MAKEWORD(1,0);// ou wVersionRequested=MAKEWORD( 2, 0 );

	// Traitement des erreurs pour API Windows
	int err = WSAStartup( wVersionRequested, &wsaData );

	if (err != 0 )
	{//Traitement de l'erreur
		switch (err)
		{	
		case WSASYSNOTREADY : strcpy(MesErr,\
								  "Le réseau n'est pas prêt pour la communication.");
			break;
		case WSAVERNOTSUPPORTED	: strcpy(MesErr,"Version non \
												compatible.");
			break;
		case WSAEINPROGRESS : strcpy(MesErr,"Socket windows \
											bloquante de version 1.1 en cours.");
			break;
		case WSAEPROCLIM : strcpy(MesErr,"Limitation des tâches \
										 supportée par windows.");
			break;
		case WSAEFAULT : strcpy(MesErr,"IpWSAData n'est pas un \
									   pointeur valide.");
			break;
		}

	}
	else
	{
		MesErr = "La DLL a bien ete chargee";
	}
	return MesErr;	
}

char* ClassSocket::CreerSocket() 
{

	monAdresse.sin_addr.s_addr	= inet_addr("192.168.100.157");
	monAdresse.sin_family		= AF_INET;
	monAdresse.sin_port		= htons(2013);

	char* MsgCreerSocket;

	monSocket = socket(AF_INET, SOCK_STREAM, 0);

	if(monSocket!= INVALID_SOCKET)
	{
		MsgCreerSocket = "La Socket a bien ete creer!";
	}
	else
	{
		monSocket = WSAGetLastError();
		switch(monSocket)
		{

		case WSANOTINITIALISED:strcpy(MsgCreerSocket,\
								   "A successful WSAStartup call must occur before using this function.");
			break;

		case WSAECONNRESET:strcpy(MsgCreerSocket,\
							   "An incoming connection was indicated, but was subsequently terminated by the remote peer prior to accepting the call.");
			break;

		case WSAEFAULT:strcpy(MsgCreerSocket,\
						   "The addrlen parameter is too small or addr is not a valid part of the user address space.");
			break;

		case WSAEINTR:strcpy(MsgCreerSocket,\
						  "A blocking Windows Sockets 1.1 call was canceled through WSACancelBlockingCall.");
			break;

		case WSAEINVAL:strcpy(MsgCreerSocket,\
						   "The listen function was not invoked prior to accept.");
			break;

		case WSAEINPROGRESS:strcpy(MsgCreerSocket,\
								"A blocking Windows Sockets 1.1 call is in progress, or the service provider is still processing a callback function.");
			break;

		case WSAEMFILE:strcpy(MsgCreerSocket,\
						   "The queue is nonempty upon entry to accept and there are no descriptors available.");
			break;

		case WSAENETDOWN:strcpy(MsgCreerSocket,\
							 "The network subsystem has failed.");
			break;

		case WSAENOBUFS:strcpy(MsgCreerSocket,\
							"No buffer space is available.");
			break;

		case WSAENOTSOCK:strcpy(MsgCreerSocket,\
							 "The descriptor is not a socket.");
			break;

		case WSAEOPNOTSUPP:strcpy(MsgCreerSocket,\
							   "The referenced socket is not a type that supports connection-oriented service.");
			break;

		case WSAEWOULDBLOCK:strcpy(MsgCreerSocket,\
								"The socket is marked as nonblocking and no connections are present to be accepted.");
			break;
		}

	}
	return MsgCreerSocket;

}

int ClassSocket::EnvoyerMessage(char* Message) 
{
	const char* MessageEnvoye = Message;
	int ErrEnvoye;
	char* MessEnvoye;



	ErrEnvoye = send (monSocketDialog,MessageEnvoye, 500,0);

	if(ErrEnvoye != SOCKET_ERROR)
	{
		MessEnvoye = Message;

	}
	else
	{
		MessEnvoye = "pas envoye";
	}


	return ErrEnvoye;
}

void ClassSocket::FermerConnection() 
{
	closesocket(monSocketDialog);

}