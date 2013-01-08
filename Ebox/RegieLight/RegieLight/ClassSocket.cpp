#pragma once
#include "stdafx.h"
#include "ClassSocket.h"



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

	monAdresse.sin_addr.s_addr	= inet_addr("127.0.0.1");
	monAdresse.sin_family		= AF_INET;
	monAdresse.sin_port		= htons(8091);

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

char* ClassSocket::EnvoyerMessage(char* Message) 
{
	const char* MessageEnvoye = Message;
	int ErrEnvoye;
	char* MessEnvoye;
	
	

	ErrEnvoye = send (monSocket,MessageEnvoye, 500,0);

	if(ErrEnvoye != SOCKET_ERROR)
	{
		MessEnvoye = Message;
		
	}
	else
	{
		MessEnvoye = "pas envoye";
	}

	
	return MessEnvoye;
}

char* ClassSocket::RecevoirMessage() 
{
	int err;
	char* MessRecu;
	char MessageRecu[500];

	err = recv(monSocket,MessageRecu,500,0);
	
	if(err != SOCKET_ERROR)
	{
		MessRecu = MessageRecu;
		
	}
	else
	{
		err = WSAGetLastError();
		switch(err)
		{
		case WSANOTINITIALISED:strcpy(MessRecu,\
			"A successful WSAStartup call must occur before using this function.");
			break;

		case WSAENETDOWN: strcpy(MessRecu,\
			"The network subsystem has failed.");
			break;

		case WSAEFAULT: strcpy(MessRecu,\
			"The buf parameter is not completely contained in a valid part of the user address space.");
			break;

		case WSAENOTCONN: strcpy(MessRecu,\
			"The socket is not connected.");
			break;

		case WSAEINTR: strcpy(MessRecu,\
			"The (blocking) call was canceled through WSACancelBlockingCall.");
			break;

		case WSAEINPROGRESS: strcpy(MessRecu,\
			"A blocking Windows Sockets 1.1 call is in progress, or the service provider is still processing a callback function.");
			break;

		case WSAENETRESET: strcpy(MessRecu,\
			"For a connection-oriented socket, this error indicates that the connection has been broken due to keep-alive activity\
			that detected a failure while the operation was in progress. For a datagram socket, this error indicates that the time \
			to live has expired.");
			break;

		case WSAENOTSOCK: strcpy(MessRecu,\
			"The descriptor is not a socket.");
			break;

		case WSAEOPNOTSUPP: strcpy(MessRecu,\
			"MSG_OOB was specified, but the socket is not stream-style such as type SOCK_STREAM, OOB data is not supported in\
			the communication domain associated with this socket, or the socket is unidirectional and supports only send operations.");
			break;

		case WSAESHUTDOWN: strcpy(MessRecu,\
			"The socket has been shut down; it is not possible to receive on a socket after shutdown has been invoked with how set to\
			SD_RECEIVE or SD_BOTH.");
			break;

		case WSAEWOULDBLOCK: strcpy(MessRecu,\
			"The socket is marked as nonblocking and the receive operation would block.");
			break;

		case WSAEMSGSIZE: strcpy(MessRecu,\
			"The message was too large to fit into the specified buffer and was truncated.");
			break;

		case WSAEINVAL: strcpy(MessRecu,\
			"The socket has not been bound with bind, or an unknown flag was specified, or MSG_OOB was specified for a socket\
			with SO_OOBINLINE enabled or (for byte stream sockets only) len was zero or negative.");
			break;

		case WSAECONNABORTED: strcpy(MessRecu,\
			"The virtual circuit was terminated due to a time-out or other failure. The application should close the socket as it\
			is no longer usable.");
			break;

		case WSAETIMEDOUT: strcpy(MessRecu,\
			"The connection has been dropped because of a network failure or because the peer system failed to respond.");
			break;

		case WSAECONNRESET: strcpy(MessRecu,\
			"The virtual circuit was reset by the remote side executing a hard or abortive close. The application should close\
			the socket as it is no longer usable. On a UDP-datagram socket, this error would indicate that a previous send operation \
			resulted in an ICMP 'Port Unreachable' message.");
			break;
		}
	}
	return MessRecu;

}

void ClassSocket::FermerConnection() 
{
	closesocket(monSocket);

}

