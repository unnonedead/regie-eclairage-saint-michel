#pragma once
#include "stdafx.h"
#include "conio.h"
#include <iostream>
#include <windows.h>
#include <vector>
#include "ftd2xx.h"
#include <string>
#include"ClassThread.h"

class ClassDMX : public ClassThread
{
public: 
	unsigned char TrameDmx[512];
protected:
	char Buf[64];
	FT_HANDLE SPort;
	FT_STATUS SStatus;
	COMMCONFIG tR;
	FTDCB ftDCB;
	char startcode;
	ULONG bytesWritten;

public:
	void ConfigurerPort();
	bool EnvoyerTrame();
	void Execute();
};

