#pragma once
#include "ClassSocket.h"
#include "ClassDMX.h"
#include "ClassThread.h"

class SocketServeur : public ClassSocket , public ClassThread
{
public:
	unsigned char anciennevaleur;
protected : 
	ClassDMX* monDMX;
public:
	SocketServeur(ClassDMX* t);
	~SocketServeur();
	char* BindSocket();
	char* EcouterReseau();
	char* AccepterConnection();
	void RecevoirMessage();
	void Execute();
};
