#pragma once
#include "ClassSocket.h"

class SocketServeur : public ClassSocket
{
public:
	char* BindSocket();
	char* EcouterReseau();
	char* AccepterConnection();
};
