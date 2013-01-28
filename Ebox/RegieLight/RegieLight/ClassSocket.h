#include"winsock2.h"
#pragma comment(lib, "wsock32.lib")
#include <string>
#include"ClassThread.h"


using namespace std;


class ClassSocket
{
	public:
		char* InitialiserDll();
		char* CreerSocket();
		int EnvoyerMessage(char* MessageEnvoye);
		void FermerConnection();
	protected:
		SOCKET monSocketDialog;
		SOCKET monSocket;
		SOCKADDR_IN monAdresse;

};
