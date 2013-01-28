#pragma once
#include "StdAfx.h"
#include "SocketServeur.h"

char* SocketServeur::BindSocket() 
{
	char* MesErr = "";
	int err = 0;

	err = bind (monSocket,(sockaddr*)&monAdresse,sizeof(monAdresse));
	if(err == 0)
	{
		MesErr = "La Socket à bien ete associer";
	}
	else
	{
		err = WSAGetLastError();
		//Traitement de l'erreur
		switch (err)
		{
		case WSANOTINITIALISED:cout <<
								   "Note  A successful WSAStartup call must occur before using this function.";
			break;

		case WSAENETDOWN:cout <<
							 "The network subsystem has failed.";
			break;

		case WSAEACCES:cout <<
						   "An attempt was made to access a socket in a way forbidden by its access permissions.This error is returned if nn attempt\
						   to bind a datagram socket to the broadcast address failed because the setsockopt option SO_BROADCAST is not enabled.";
			break;

		case WSAEADDRINUSE:cout <<
							   "Only one usage of each socket address (protocol/network address/port) is normally permitted. This error is returned \
							   if a process on the computer is already bound to the same fully qualified address and the socket has not been marked \
							   to allow address reuse with SO_REUSEADDR. For example, the IP address and port specified in the name parameter are \
							   already bound to another socket being used by another application. For more information, see the SO_REUSEADDR\
							   socket option in the SOL_SOCKET Socket Options reference, Using SO_REUSEADDR and SO_EXCLUSIVEADDRUSE,\
							   and SO_EXCLUSIVEADDRUSE.";
			break;

		case WSAEADDRNOTAVAIL:cout <<
								  "The requested address is not valid in its context. This error is returned if the specified address pointed to by\
								  the name parameter is not a valid local IP address on this computer.";
			break;

		case WSAEFAULT:cout <<
						   "The system detected an invalid pointer address in attempting to use a pointer argument in a call. This error is \
						   returned if the name parameter is NULL, the name or namelen parameter is not a valid part of the user address space,\
						   the namelen parameter is too small, the name parameter contains an incorrect address format for the associated address\
						   family, or the first two bytes of the memory block specified by name do not match the address family associated with\
						   the socket descriptor s.";
			break;

		case WSAEINPROGRESS:cout <<
								"A blocking Windows Sockets 1.1 call is in progress, or the service provider is still processing a callback function.";
			break;

		case WSAEINVAL:cout <<
						   "An invalid argument was supplied. This error is returned of the socket s is already bound to an address.";
			break;

		case WSAENOBUFS:cout <<
							"An operation on a socket could not be performed because the system lacked sufficient buffer space or because a\
							queue was full. This error is returned of not enough buffers are available or there are too many connections.";
			break;

		case WSAENOTSOCK:cout <<
							 "An operation was attempted on something that is not a socket. This error is returned if the descriptor in the s \
							 parameter is not a socket.";
			break;
		}
	}

	return MesErr;
}

char* SocketServeur::EcouterReseau() 
{
	char* MesErr = "";
	int err = 0;

	err = listen (monSocket, 1);
	if(err == 0)
	{
		MesErr = "Le serveur ecoute le reseaux";
	}
	else
	{

		err = WSAGetLastError();
		//Traitement de l'erreur
		switch (err)
		{
		case WSANOTINITIALISED:strcpy(MesErr,\
								   "A successful WSAStartup call must occur before using this function.");
			break;

		case WSAENETDOWN:strcpy(MesErr,\
							 "The network subsystem has failed.");
			break;

		case WSAEADDRINUSE:strcpy(MesErr,\
							   "The socket's local address is already in use and the socket was not marked to allow address reuse with SO_REUSEADDR.\
							   This error usually occurs during execution of the bind function, but could be delayed until this function if the bind \
							   was to a partially wildcard address (involving ADDR_ANY) and if a specific address needs to be committed at the time of\
							   this function.");
			break;

		case WSAEINPROGRESS:strcpy(MesErr,\
								"A blocking Windows Sockets 1.1 call is in progress, or the service provider is still processing a callback function.");
			break;

		case WSAEINVAL:strcpy(MesErr,\
						   "The socket has not been bound with bind.");
			break;

		case WSAEISCONN:strcpy(MesErr,\
							"The socket is already connected.");
			break;

		case WSAEMFILE:strcpy(MesErr,\
						   "No more socket descriptors are available.");
			break;

		case WSAENOBUFS:strcpy(MesErr,\
							"No buffer space is available.");
			break;

		case WSAENOTSOCK:strcpy(MesErr,\
							 "The descriptor is not a socket.");
			break;

		case WSAEOPNOTSUPP:strcpy(MesErr,\
							   "The referenced socket is not of a type that supports the listen operation.");
			break;
		}
	}
	return MesErr;
}

char* SocketServeur::AccepterConnection() 
{
	int sizeof_monAdresse = sizeof(monAdresse);
	monSocketDialog = accept(monSocket,(sockaddr*)&monAdresse,&sizeof_monAdresse);
	char* MsgAccept = "";
	if(monSocket!= INVALID_SOCKET)
	{
		MsgAccept = "Le Client c'est connecter";
	}
	else
	{
		monSocketDialog = WSAGetLastError();
		switch(monSocket)
		{

		case WSANOTINITIALISED:strcpy(MsgAccept,\
								   "A successful WSAStartup call must occur before using this function.");
			break;

		case WSAECONNRESET:strcpy(MsgAccept,\
							   "An incoming connection was indicated, but was subsequently terminated by the remote peer prior to accepting the call.");
			break;

		case WSAEFAULT:strcpy(MsgAccept,\
						   "The addrlen parameter is too small or addr is not a valid part of the user address space.");
			break;

		case WSAEINTR:strcpy(MsgAccept,\
						  "A blocking Windows Sockets 1.1 call was canceled through WSACancelBlockingCall.");
			break;

		case WSAEINVAL:strcpy(MsgAccept,\
						   "The listen function was not invoked prior to accept.");
			break;

		case WSAEINPROGRESS:strcpy(MsgAccept,\
								"A blocking Windows Sockets 1.1 call is in progress, or the service provider is still processing a callback function.");
			break;

		case WSAEMFILE:strcpy(MsgAccept,\
						   "The queue is nonempty upon entry to accept and there are no descriptors available.");
			break;

		case WSAENETDOWN:strcpy(MsgAccept,\
							 "The network subsystem has failed.");
			break;

		case WSAENOBUFS:strcpy(MsgAccept,\
							"No buffer space is available.");
			break;

		case WSAENOTSOCK:strcpy(MsgAccept,\
							 "The descriptor is not a socket.");
			break;

		case WSAEOPNOTSUPP:strcpy(MsgAccept,\
							   "The referenced socket is not a type that supports connection-oriented service.");
			break;

		case WSAEWOULDBLOCK:strcpy(MsgAccept,\
								"The socket is marked as nonblocking and no connections are present to be accepted.");
			break;
		}

	}
	return MsgAccept;

}

void SocketServeur::RecevoirMessage() 
{	

	recv(monSocketDialog,(char*)monDMX->TrameDmx,512,0);

	//if(monDMX->TrameDmx[0] < 0 || monDMX->TrameDmx[0]> 255)
	//{

	//}
	/*
	else
	{
	err = WSAGetLastError();
	switch(err)
	{
	case WSANOTINITIALISED:
	{
	MessRecu = "A successful WSAStartup call must occur before using this function.";
	break;
	}

	case WSAENETDOWN:
	{
	MessRecu = "The network subsystem has failed.";
	break;
	}

	case WSAEFAULT:
	{
	MessRecu = "The buf parameter is not completely contained in a valid part of the user address space.";
	break;
	}

	case WSAENOTCONN:
	{
	MessRecu = "The socket is not connected.";
	break;
	}

	case WSAEINTR:
	{
	MessRecu = "The (blocking) call was canceled through WSACancelBlockingCall.";
	break;
	}

	case WSAEINPROGRESS:
	{
	MessRecu = "A blocking Windows Sockets 1.1 call is in progress, or the service provider is still processing a callback function.";
	break;
	}

	case WSAENETRESET:
	{
	MessRecu = "For a connection-oriented socket, this error indicates that the connection has been broken due to keep-alive activity\
	that detected a failure while the operation was in progress. For a datagram socket, this error indicates that the time \
	to live has expired.";
	break;
	}

	case WSAENOTSOCK:
	{
	MessRecu = "The descriptor is not a socket.";
	break;
	}

	case WSAEOPNOTSUPP:
	{
	MessRecu = "MSG_OOB was specified, but the socket is not stream-style such as type SOCK_STREAM, OOB data is not supported in\
	the communication domain associated with this socket, or the socket is unidirectional and supports only send operations.";
	break;
	}

	case WSAESHUTDOWN:
	{
	MessRecu = "The socket has been shut down; it is not possible to receive on a socket after shutdown has been invoked with how set to\
	SD_RECEIVE or SD_BOTH.";
	break;
	}

	case WSAEWOULDBLOCK:
	{
	MessRecu = "The socket is marked as nonblocking and the receive operation would block.";
	break;
	}

	case WSAEMSGSIZE:
	{
	MessRecu = "The message was too large to fit into the specified buffer and was truncated.";
	break;
	}

	case WSAEINVAL:
	{
	MessRecu = "The socket has not been bound with bind, or an unknown flag was specified, or MSG_OOB was specified for a socket\
	with SO_OOBINLINE enabled or (for byte stream sockets only) len was zero or negative.";
	break;
	}

	case WSAECONNABORTED:
	{
	MessRecu = "The virtual circuit was terminated due to a time-out or other failure. The application should close the socket as it\
	is no longer usable.";
	break;
	}

	case WSAETIMEDOUT:

	{
	MessRecu = "The connection has been dropped because of a network failure or because the peer system failed to respond.";
	break;
	}

	case WSAECONNRESET:
	{
	MessRecu = "The virtual circuit was reset by the remote side executing a hard or abortive close. The application should close\
	the socket as it is no longer usable. On a UDP-datagram socket, this error would indicate that a previous send operation \
	resulted in an ICMP 'Port Unreachable' message.";
	break;
	}
	}
	}*/

}
void SocketServeur::Execute()
{	
	while(monDMX->TrameDmx[0] != 1)
	{
		SocketServeur::RecevoirMessage();
		if(SocketServeur::EnvoyerMessage("test") == SOCKET_ERROR)
		{
			monDMX->TrameDmx[0] = 1;
		}
		 
	}
}

SocketServeur::SocketServeur(ClassDMX* t)
{
	monDMX = t;
}