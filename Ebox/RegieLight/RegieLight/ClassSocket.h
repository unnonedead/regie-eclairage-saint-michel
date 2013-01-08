#include"winsock2.h"
#pragma comment(lib, "wsock32.lib")
#include <string>

using namespace std;


class ClassSocket 
{
	public:
		char* InitialiserDll();
		char* CreerSocket();
		char* EnvoyerMessage(char* MessageEnvoye);
		char* RecevoirMessage();
		void FermerConnection();
	protected:
		SOCKET monSocket;
		SOCKADDR_IN monAdresse;
};
