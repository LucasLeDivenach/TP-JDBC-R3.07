CREATE TABLE IF NOT EXISTS Agence (
                                      numAgence INT PRIMARY KEY,
                                      telAgence VARCHAR(50),
    adAgence VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS Agent (
                                     numAgent INT PRIMARY KEY,
                                     nomAgent VARCHAR(50),
    prenomAgent VARCHAR(50),
    salaire DECIMAL(10, 2),
    estDirecteur BOOLEAN,
    numAgence INT REFERENCES Agence(numAgence)
    );

CREATE TABLE IF NOT EXISTS Client (
                                      numClient INT PRIMARY KEY,
                                      nomClient VARCHAR(50),
    prenomClient VARCHAR(50),
    adClient VARCHAR(100),
    dateNaissClient DATE,
    ageClient INT,
    numAgent INT REFERENCES Agent(numAgent)
    );

CREATE TABLE IF NOT EXISTS Compte (
                                      numCompte INT PRIMARY KEY,
                                      solde REAL,
                                      typeCompte VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS Operation (
                                         numOperation INT PRIMARY KEY,
                                         dateOperation DATE,
                                         typeOperation VARCHAR(50),
    montant REAL,
    numCompte INT REFERENCES Compte(numCompte)
    );

CREATE TABLE IF NOT EXISTS Compte_Client (
    numCompte INT NOT NULL,
    numClient INT NOT NULL,
    CONSTRAINT fk_numCompte FOREIGN KEY (numCompte) REFERENCES Compte(numCompte),
    CONSTRAINT fk_numClient FOREIGN KEY (numClient) REFERENCES Client(numClient),
    CONSTRAINT pk_Compte_Client PRIMARY KEY (numCompte, numClient)
    );

ALTER TABLE Client
    ALTER COLUMN nomClient SET NOT NULL,
    ALTER COLUMN prenomClient SET NOT NULL,
    ALTER COLUMN adClient SET NOT NULL,
    ALTER COLUMN dateNaissClient SET NOT NULL,
    ALTER COLUMN ageClient SET NOT NULL,
    ALTER COLUMN numAgent SET NOT NULL,
    ADD CONSTRAINT chk_age CHECK (ageClient >= 18);

ALTER TABLE Compte
    ALTER COLUMN solde SET NOT NULL,
    ALTER COLUMN typeCompte SET NOT NULL,
    ADD CONSTRAINT ck_solde CHECK (solde >= 0);

ALTER TABLE Agence
    ALTER COLUMN telAgence SET NOT NULL,
    ALTER COLUMN adAgence SET NOT NUL,
    ADD CONSTRAINT unique_telAgence UNIQUE (telAgence);

ALTER TABLE Agent
    ALTER COLUMN nomAgent SET NOT NULL,
    ALTER COLUMN prenomAgent SET NOT NULL,
    ALTER COLUMN salaire SET NOT NULL,
    ALTER COLUMN numAgence SET NOT NULL,
    ALTER COLUMN estDirecteur SET DEFAULT false,
    ADD CONSTRAINT ck_salaire CHECK (salaire > 1800);

ALTER TABLE Operation
    ALTER COLUMN dateOperation SET NOT NULL,
    ALTER COLUMN typeOperation SET NOT NULL,
    ALTER COLUMN montant SET NOT NULL,
    ALTER COLUMN numCompte SET NOT NULL,
    ADD CONSTRAINT ck_montant CHECK (montant > 0);
