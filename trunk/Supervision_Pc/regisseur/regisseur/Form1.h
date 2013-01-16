#pragma once


namespace regisseur {

	using namespace System;
	using namespace System::Text;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;
	using namespace System::Net::Sockets;

	/// <summary>
	/// Description résumée de Form1
	///
	/// AVERTISSEMENT : si vous modifiez le nom de cette classe, vous devrez modifier la
	///          propriété 'Nom du fichier de ressources' de l'outil de compilation de ressource managée
	///          pour tous les fichiers .resx dont dépend cette classe. Dans le cas contraire,
	///          les concepteurs ne pourront pas interagir correctement avec les ressources
	///          localisées associées à ce formulaire.
	/// </summary>
	public ref class Form1 : public System::Windows::Forms::Form
	{
	public:

		Form1(void)
		{
			InitializeComponent();
			//
			//TODO : ajoutez ici le code du constructeur
			//
		}

	protected:
		/// <summary>
		/// Nettoyage des ressources utilisées.
		/// </summary>
		~Form1()
		{
			if (components)
			{
				delete components;
			}
		}
			 int num_color;		
			 String^ adresse;
			 TcpClient^ Client;
			 Int32 port;

	private: System::Windows::Forms::TrackBar^  trackbar;

	//////////////////////////////////////////////////////
    private: System::Windows::Forms::GroupBox^  groupBox1;
	private: System::Windows::Forms::Button^  btn_1;
	private: System::Windows::Forms::Button^  btn_conn;
	private: System::Windows::Forms::TextBox^  tb_ip;
	private: System::Windows::Forms::TextBox^  tb_port;
	private: System::Windows::Forms::Label^  lbl_ip;
	private: System::Windows::Forms::Label^  lbl_port;
	private: System::Windows::Forms::Button^  btn_envoi;

			 System::ComponentModel::Container ^components;

#pragma region Windows Form Designer generated code
			 /// <summary>
			 /// Méthode requise pour la prise en charge du concepteur - ne modifiez pas
			 /// le contenu de cette méthode avec l'éditeur de code.
			 /// </summary>
			 void InitializeComponent(void)
			 {
				 this->groupBox1 = (gcnew System::Windows::Forms::GroupBox());
				 this->btn_1 = (gcnew System::Windows::Forms::Button());
				 this->trackbar = (gcnew System::Windows::Forms::TrackBar());
				 this->btn_conn = (gcnew System::Windows::Forms::Button());
				 this->tb_ip = (gcnew System::Windows::Forms::TextBox());
				 this->tb_port = (gcnew System::Windows::Forms::TextBox());
				 this->lbl_ip = (gcnew System::Windows::Forms::Label());
				 this->lbl_port = (gcnew System::Windows::Forms::Label());
				 this->btn_envoi = (gcnew System::Windows::Forms::Button());
				 this->groupBox1->SuspendLayout();
				 (cli::safe_cast<System::ComponentModel::ISupportInitialize^  >(this->trackbar))->BeginInit();
				 this->SuspendLayout();
				 // 
				 // groupBox1
				 // 
				 this->groupBox1->Controls->Add(this->btn_1);
				 this->groupBox1->Controls->Add(this->trackbar);
				 this->groupBox1->Location = System::Drawing::Point(12, 12);
				 this->groupBox1->Name = L"groupBox1";
				 this->groupBox1->Size = System::Drawing::Size(235, 309);
				 this->groupBox1->TabIndex = 13;
				 this->groupBox1->TabStop = false;
				 this->groupBox1->Text = L"Projecteurs";
				 // 
				 // btn_1
				 // 
				 this->btn_1->Location = System::Drawing::Point(13, 275);
				 this->btn_1->Name = L"btn_1";
				 this->btn_1->Size = System::Drawing::Size(45, 23);
				 this->btn_1->TabIndex = 24;
				 this->btn_1->UseVisualStyleBackColor = false;
				 this->btn_1->Click += gcnew System::EventHandler(this, &Form1::button1_Click);
				 // 
				 // trackbar
				 // 
				 this->trackbar->BackColor = System::Drawing::SystemColors::MenuHighlight;
				 this->trackbar->Cursor = System::Windows::Forms::Cursors::Hand;
				 this->trackbar->Location = System::Drawing::Point(13, 19);
				 this->trackbar->Maximum = 255;
				 this->trackbar->Name = L"trackbar";
				 this->trackbar->Orientation = System::Windows::Forms::Orientation::Vertical;
				 this->trackbar->Size = System::Drawing::Size(45, 250);
				 this->trackbar->TabIndex = 0;
				 this->trackbar->TickFrequency = 26;
				 // 
				 // btn_conn
				 // 
				 this->btn_conn->Location = System::Drawing::Point(253, 97);
				 this->btn_conn->Name = L"btn_conn";
				 this->btn_conn->Size = System::Drawing::Size(177, 38);
				 this->btn_conn->TabIndex = 14;
				 this->btn_conn->Text = L"Connection";
				 this->btn_conn->UseVisualStyleBackColor = true;
				 this->btn_conn->Click += gcnew System::EventHandler(this, &Form1::btn_conn_Click);
				 // 
				 // tb_ip
				 // 
				 this->tb_ip->Location = System::Drawing::Point(309, 31);
				 this->tb_ip->Name = L"tb_ip";
				 this->tb_ip->Size = System::Drawing::Size(121, 20);
				 this->tb_ip->TabIndex = 15;
				 this->tb_ip->Text = L"192.168.100.197";
				 // 
				 // tb_port
				 // 
				 this->tb_port->Location = System::Drawing::Point(309, 57);
				 this->tb_port->Name = L"tb_port";
				 this->tb_port->Size = System::Drawing::Size(121, 20);
				 this->tb_port->TabIndex = 16;
				 this->tb_port->Text = L"2013";
				 // 
				 // lbl_ip
				 // 
				 this->lbl_ip->AutoSize = true;
				 this->lbl_ip->Location = System::Drawing::Point(271, 38);
				 this->lbl_ip->Name = L"lbl_ip";
				 this->lbl_ip->Size = System::Drawing::Size(23, 13);
				 this->lbl_ip->TabIndex = 17;
				 this->lbl_ip->Text = L"IP :";
				 // 
				 // lbl_port
				 // 
				 this->lbl_port->AutoSize = true;
				 this->lbl_port->Location = System::Drawing::Point(271, 64);
				 this->lbl_port->Name = L"lbl_port";
				 this->lbl_port->Size = System::Drawing::Size(32, 13);
				 this->lbl_port->TabIndex = 18;
				 this->lbl_port->Text = L"Port :";
				 // 
				 // btn_envoi
				 // 
				 this->btn_envoi->Location = System::Drawing::Point(253, 141);
				 this->btn_envoi->Name = L"btn_envoi";
				 this->btn_envoi->Size = System::Drawing::Size(177, 38);
				 this->btn_envoi->TabIndex = 19;
				 this->btn_envoi->Text = L"Envoyer";
				 this->btn_envoi->UseVisualStyleBackColor = true;
				 this->btn_envoi->Click += gcnew System::EventHandler(this, &Form1::btn_envoi_Click);
				 // 
				 // Form1
				 // 
				 this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
				 this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
				 this->ClientSize = System::Drawing::Size(438, 400);
				 this->Controls->Add(this->btn_envoi);
				 this->Controls->Add(this->lbl_port);
				 this->Controls->Add(this->lbl_ip);
				 this->Controls->Add(this->tb_port);
				 this->Controls->Add(this->tb_ip);
				 this->Controls->Add(this->btn_conn);
				 this->Controls->Add(this->groupBox1);
				 this->Name = L"Form1";
				 this->Text = L"Form1";
				 this->Load += gcnew System::EventHandler(this, &Form1::Form1_Load);
				 this->groupBox1->ResumeLayout(false);
				 this->groupBox1->PerformLayout();
				 (cli::safe_cast<System::ComponentModel::ISupportInitialize^  >(this->trackbar))->EndInit();
				 this->ResumeLayout(false);
				 this->PerformLayout();

			 }
#pragma endregion

	private: System::Void Form1_Load(System::Object^  sender, System::EventArgs^  e) 
			 {

			 }


	private: System::Void button1_Click(System::Object^  sender, System::EventArgs^  e) 
			 {
				 change_couleur() ;
			 }

	void change_couleur()
			 {
				 num_color = num_color +1;

				 switch (num_color)
				 {
				 case 1:
					 btn_1->BackColor = System::Drawing::Color::Purple;

					 break;
				 case 2:
					 btn_1->BackColor = System::Drawing::Color::OrangeRed;

					 break;
				 case 3:
					 btn_1->BackColor = System::Drawing::Color::YellowGreen;

					 break;
				 case 4:
					 btn_1->BackColor = System::Drawing::Color::Red;

					 break;
				 case 5:
					 btn_1->BackColor = System::Drawing::Color::RoyalBlue;

					 break;
				 case 6:
					 btn_1->BackColor = System::Drawing::Color::WhiteSmoke;
					 num_color = 0;
					 break;
				 }
			 }
	private: System::Void btn_conn_Click(System::Object^  sender, System::EventArgs^  e) 
			 {

				 int port = 2013;
				 adresse = "192.168.100.197";
				 Client = gcnew TcpClient;
				 Client->Connect(adresse,port);
				 
			 }


	private: System::Void btn_envoi_Click(System::Object^  sender, System::EventArgs^  e) 
			 {
				 int dmx[512];
				 dmx[0] = trackbar->Value;	 
				 

				 NetworkStream^ netStream = Client->GetStream();
				 if ( netStream->CanWrite )
				 {
					 array<Byte>^sendBytes = Encoding::UTF8->GetBytes(dmx[0].ToString());
					 netStream->Write( sendBytes, 0, sendBytes->Length);
				 }
				 else
				 {
					 Client->Close();
					 netStream->Close();
					 return;
				 }
			 }
};
}

