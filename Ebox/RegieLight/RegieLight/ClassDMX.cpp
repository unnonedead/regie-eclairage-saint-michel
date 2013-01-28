#include "StdAfx.h"
#include "ClassDMX.h"
#include"conio.h"

using namespace std;

void ClassDMX::ConfigurerPort()
{
	startcode = 0;
	do
	{
		SStatus = FT_ListDevices(0,Buf,FT_LIST_BY_INDEX|FT_OPEN_BY_DESCRIPTION);

		SPort = FT_W32_CreateFile(Buf,
			GENERIC_READ | GENERIC_WRITE,
			0,
			0,
			OPEN_EXISTING,
			FILE_ATTRIBUTE_NORMAL | FT_OPEN_BY_DESCRIPTION,
			0);
	}while( SPort == INVALID_HANDLE_VALUE);

	if (FT_W32_GetCommState(SPort,&ftDCB)) {
		ftDCB.BaudRate = 250000;
		ftDCB.Parity = FT_PARITY_NONE;
		ftDCB.StopBits = FT_STOP_BITS_2;
		ftDCB.ByteSize = FT_BITS_8;
		ftDCB.fOutX = false;
		ftDCB.fInX = false;
		ftDCB.fErrorChar = false;
		ftDCB.fBinary = true;
		ftDCB.fRtsControl = false;
		ftDCB.fAbortOnError = false;

		if (!FT_W32_SetCommState(SPort,&ftDCB)) {
			cout << "Set baud rate error";
		}
	}
}
bool ClassDMX::EnvoyerTrame()
{
	bool Resultat;

	FT_W32_SetCommBreak(SPort);
	FT_W32_ClearCommBreak(SPort);
	FT_W32_WriteFile(SPort,&startcode,1,&bytesWritten,NULL);
	Resultat = FT_W32_WriteFile(SPort,TrameDmx, 512, &bytesWritten, NULL);
	Sleep(30);
	return Resultat;
}
void ClassDMX::Execute()
{
	while(TrameDmx[0] != 1)
	{
		EnvoyerTrame();
	}

}